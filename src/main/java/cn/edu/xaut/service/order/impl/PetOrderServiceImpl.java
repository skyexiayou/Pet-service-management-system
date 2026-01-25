package cn.edu.xaut.service.order.impl;

import cn.edu.xaut.domain.entity.medicalrecord.MedicalRecordDO;
import cn.edu.xaut.domain.entity.order.OrderDrugDO;
import cn.edu.xaut.domain.entity.order.PetOrderDO;
import cn.edu.xaut.domain.entity.pet.PetDO;
import cn.edu.xaut.domain.entity.petdrug.PetDrugDO;
import cn.edu.xaut.domain.entity.petmedicalprescription.PetMedicalPrescriptionDO;
import cn.edu.xaut.domain.entity.prescriptiondrug.PrescriptionDrugDO;
import cn.edu.xaut.exception.OrderException;
import cn.edu.xaut.mapper.*;
import cn.edu.xaut.service.order.OrderDrugService;
import cn.edu.xaut.service.order.PetOrderService;
import cn.edu.xaut.utils.OrderNoGenerator;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 宠物订单服务实现类
 */
@Service
public class PetOrderServiceImpl extends ServiceImpl<PetOrderMapper, PetOrderDO> implements PetOrderService {

    @Autowired
    private PetMedicalPrescriptionMapper prescriptionMapper;

    @Autowired
    private PrescriptionDrugMapper prescriptionDrugMapper;

    @Autowired
    private MedicalRecordMapper medicalRecordMapper;

    @Autowired
    private OrderDrugService orderDrugService;

    @Autowired
    private PetDrugMapper petDrugMapper;

    @Autowired
    private PetMapper petMapper;

    /**
     * 根据处方ID生成订单
     * 核心业务方法，必须保证事务原子性
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer generateOrderByPrescription(Integer prescriptionId) {
        return generateOrderByPrescription(prescriptionId, BigDecimal.ZERO);
    }
    
    /**
     * 根据处方ID生成订单（包含诊疗费用）
     * 核心业务方法，必须保证事务原子性
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer generateOrderByPrescription(Integer prescriptionId, java.math.BigDecimal medicalFee) {
        // 1. Check prescription existence
        PetMedicalPrescriptionDO prescription = prescriptionMapper.selectById(prescriptionId);
        if (prescription == null) {
            throw new OrderException(OrderException.PRESCRIPTION_INVALID, OrderException.MSG_PRESCRIPTION_INVALID);
        }

        // 2. Check prescription status
        if (!"已开具".equals(prescription.getPresStatus())) {
            throw new OrderException(OrderException.PRESCRIPTION_INVALID, "Prescription status is invalid, cannot generate order");
        }

        // 3. Check prescription expiry
        if (prescription.getValidTime() != null && prescription.getValidTime().before(new Date())) {
            throw new OrderException(OrderException.PRESCRIPTION_EXPIRED, OrderException.MSG_PRESCRIPTION_EXPIRED);
        }

        // 4. Check if order already generated
        if ("已生成订单".equals(prescription.getPresStatus())) {
            throw new OrderException(OrderException.ORDER_CREATE_FAILED, OrderException.MSG_PRESCRIPTION_ORDER_EXISTS);
        }

        // 5. Get prescription drugs
        List<PrescriptionDrugDO> prescriptionDrugs = prescriptionDrugMapper.selectByPrescriptionId(prescriptionId);
        if (prescriptionDrugs == null || prescriptionDrugs.isEmpty() && medicalFee.compareTo(BigDecimal.ZERO) <= 0) {
            throw new OrderException(OrderException.ORDER_CREATE_FAILED, "Prescription drug details are empty and no medical fee, cannot generate order");
        }

        // 6. Calculate total amount
        BigDecimal totalAmount = medicalFee;
        for (PrescriptionDrugDO drug : prescriptionDrugs) {
            // Get drug info to calculate amount
            PetDrugDO drugInfo = petDrugMapper.selectById(drug.getDrugId());
            if (drugInfo != null) {
                BigDecimal drugAmount = drugInfo.getUnitPrice().multiply(new BigDecimal(drug.getDrugNum()));
                totalAmount = totalAmount.add(drugAmount);
            }
        }

        // 8. Create order
        PetOrderDO order = new PetOrderDO();
        order.setOrderNo(OrderNoGenerator.generate());
        order.setUserId(prescription.getUserId());
        order.setPetId(prescription.getPetId());
        order.setStoreId(prescription.getStoreId());
        order.setPrescriptionId(prescriptionId);
        order.setOrderType(prescriptionDrugs != null && !prescriptionDrugs.isEmpty() ? "药品订单" : "医疗服务订单");
        order.setOrderStatus("待支付");
        order.setTotalAmount(totalAmount);
        order.setCreateTime(LocalDateTime.now());
        order.setRemark("Prescription order with medical fee");

        // 9. 保存订单
        int insertResult = baseMapper.insert(order);
        if (insertResult <= 0) {
            throw new OrderException(OrderException.ORDER_CREATE_FAILED, OrderException.MSG_ORDER_CREATE_FAILED);
        }

        // 10. Create order drug details if there are drugs
        if (prescriptionDrugs != null && !prescriptionDrugs.isEmpty()) {
            List<OrderDrugDO> orderDrugs = new ArrayList<>();
            for (PrescriptionDrugDO prescriptionDrug : prescriptionDrugs) {
                // Get drug info to build order drug
                PetDrugDO drugInfo = petDrugMapper.selectById(prescriptionDrug.getDrugId());
                if (drugInfo != null) {
                    OrderDrugDO orderDrug = new OrderDrugDO();
                    orderDrug.setOrderId(order.getOrderId());
                    orderDrug.setDrugId(prescriptionDrug.getDrugId());
                    orderDrug.setDrugName(drugInfo.getDrugName());
                    orderDrug.setDrugSpec(drugInfo.getDrugSpec());
                    orderDrug.setPrice(drugInfo.getUnitPrice());
                    orderDrug.setQuantity(prescriptionDrug.getDrugNum());
                    // Calculate amount
                    BigDecimal amount = drugInfo.getUnitPrice().multiply(new BigDecimal(prescriptionDrug.getDrugNum()));
                    orderDrug.setAmount(amount);
                    // Check if drug is prescription
                    orderDrug.setIsPrescription(getDrugPrescriptionFlag(prescriptionDrug.getDrugId()));
                    orderDrugs.add(orderDrug);
                }
            }

            // 11. 批量保存订单药品明细
            Boolean saveResult = orderDrugService.batchSaveOrderDrugs(orderDrugs);
            if (!saveResult) {
                throw new OrderException(OrderException.ORDER_CREATE_FAILED, "订单药品明细保存失败");
            }
        }

        // 12. 更新处方状态为已生成订单
        prescription.setPresStatus("已生成订单");
        prescriptionMapper.updateById(prescription);

        return order.getOrderId();
    }

    /**
     * 根据宠物ID获取用户ID
     */
    private Integer getUserIdByPetId(Integer petId) {
        PetDO pet = petMapper.selectById(petId);
        return pet != null ? pet.getUserId() : null;
    }

    /**
     * 获取药品是否为处方药标识
     */
    private Integer getDrugPrescriptionFlag(Integer drugId) {
        // 由于数据库中没有isPrescription字段，默认返回0
        return 0;
    }

    /**
     * 根据用户ID查询订单列表
     */
    @Override
    public List<PetOrderDO> getOrdersByUserId(Integer userId) {
        return baseMapper.selectByUserId(userId);
    }

    /**
     * 根据订单ID查询订单详情
     */
    @Override
    public PetOrderDO getOrderDetail(Integer orderId) {
        return baseMapper.selectDetailById(orderId);
    }

    /**
     * 根据处方ID查询订单
     */
    @Override
    public PetOrderDO getOrderByPrescriptionId(Integer prescriptionId) {
        return baseMapper.selectByPrescriptionId(prescriptionId);
    }

    /**
     * 查询所有订单列表（管理员用）
     */
    @Override
    public List<PetOrderDO> getAllOrders() {
        return baseMapper.selectAllOrders();
    }

    /**
     * 根据订单状态查询订单列表
     */
    @Override
    public List<PetOrderDO> getOrdersByStatus(String orderStatus) {
        return baseMapper.selectByOrderStatus(orderStatus);
    }

    /**
     * 更新订单状态
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateOrderStatus(Integer orderId, String orderStatus) {
        PetOrderDO order = baseMapper.selectById(orderId);
        if (order == null) {
            throw new OrderException(OrderException.ORDER_NOT_FOUND, OrderException.MSG_ORDER_NOT_FOUND);
        }
        order.setOrderStatus(orderStatus);
        return baseMapper.updateById(order) > 0;
    }

    /**
     * 取消订单
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean cancelOrder(Integer orderId, Integer userId) {
        PetOrderDO order = baseMapper.selectById(orderId);
        if (order == null) {
            throw new OrderException(OrderException.ORDER_NOT_FOUND, OrderException.MSG_ORDER_NOT_FOUND);
        }
        // 校验订单归属
        if (!order.getUserId().equals(userId)) {
            throw new OrderException(OrderException.ORDER_STATUS_INVALID, "无权操作此订单");
        }
        // 只有待支付的订单可以取消
        if (!"待支付".equals(order.getOrderStatus())) {
            throw new OrderException(OrderException.ORDER_STATUS_INVALID, "只有待支付的订单可以取消");
        }
        order.setOrderStatus("已取消");
        return baseMapper.updateById(order) > 0;
    }
    
    /**
     * 根据门店ID查询订单列表，支持分页和状态过滤
     */
    @Override
    public List<Map<String, Object>> getOrderListByStoreId(Integer storeId, String orderStatus, Integer pageNum, Integer pageSize) {
        // 计算偏移量
        int offset = (pageNum - 1) * pageSize;
        return baseMapper.selectOrderListByStoreId(storeId, orderStatus, offset, pageSize);
    }
    
    /**
     * 根据门店ID查询订单总数，支持状态过滤
     */
    @Override
    public Integer getOrderCountByStoreId(Integer storeId, String orderStatus) {
        return baseMapper.selectOrderCountByStoreId(storeId, orderStatus);
    }
}
