package cn.edu.xaut.service.payment.impl;

import cn.edu.xaut.domain.entity.order.OrderDrugDO;
import cn.edu.xaut.domain.entity.order.PetOrderDO;
import cn.edu.xaut.domain.entity.payment.PaymentRecordDO;
import cn.edu.xaut.domain.entity.petdrugstore.PetDrugStoreDO;
import cn.edu.xaut.domain.entity.petmedicalprescription.PetMedicalPrescriptionDO;
import cn.edu.xaut.exception.OrderException;
import cn.edu.xaut.exception.PaymentException;
import cn.edu.xaut.exception.StockException;
import cn.edu.xaut.mapper.*;
import cn.edu.xaut.service.order.OrderDrugService;
import cn.edu.xaut.service.payment.PaymentRecordService;
import cn.edu.xaut.utils.PayNoGenerator;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class PaymentRecordServiceImpl extends ServiceImpl<PaymentRecordMapper, PaymentRecordDO>
        implements PaymentRecordService {

    @Autowired
    private PetOrderMapper petOrderMapper;

    @Autowired
    private OrderDrugService orderDrugService;

    @Autowired
    private PetDrugStoreMapper petDrugStoreMapper;

    @Autowired
    private PetMedicalPrescriptionMapper prescriptionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer payOrder(Integer orderId, PaymentRecordDO payRecord) {
        PetOrderDO order = petOrderMapper.selectById(orderId);
        if (order == null) {
            throw new OrderException(OrderException.ORDER_NOT_FOUND, OrderException.MSG_ORDER_NOT_FOUND);
        }

        if (!"待支付".equals(order.getOrderStatus())) {
            if ("已支付".equals(order.getOrderStatus())) {
                throw new OrderException(OrderException.ORDER_ALREADY_PAID, OrderException.MSG_ORDER_ALREADY_PAID);
            }
            throw new OrderException(OrderException.ORDER_STATUS_INVALID, OrderException.MSG_ORDER_STATUS_INVALID);
        }

        if (order.getPrescriptionId() != null) {
            PetMedicalPrescriptionDO prescription = prescriptionMapper.selectById(order.getPrescriptionId());
            if (prescription == null || !"已生成订单".equals(prescription.getPresStatus())) {
                throw new OrderException(OrderException.PRESCRIPTION_INVALID, OrderException.MSG_PRESCRIPTION_INVALID);
            }
            if (prescription.getValidTime() != null && prescription.getValidTime().before(new Date())) {
                throw new OrderException(OrderException.PRESCRIPTION_EXPIRED, OrderException.MSG_PRESCRIPTION_EXPIRED);
            }
        }

        List<OrderDrugDO> orderDrugs = orderDrugService.getOrderDrugsByOrderId(orderId);

        for (OrderDrugDO orderDrug : orderDrugs) {
            PetDrugStoreDO stock = petDrugStoreMapper.selectByStoreIdAndDrugId(
                    order.getStoreId(), orderDrug.getDrugId());
            if (stock == null) {
                throw new StockException(StockException.STOCK_NOT_FOUND,
                        "药品【" + orderDrug.getDrugName() + "】库存信息不存在");
            }
            if (stock.getStoreStock() < orderDrug.getQuantity()) {
                throw new StockException(StockException.STOCK_INSUFFICIENT,
                        "药品【" + orderDrug.getDrugName() + "】库存不足，当前库存：" + stock.getStoreStock());
            }
        }

        String payNo = PayNoGenerator.generate();

        payRecord.setOrderId(orderId);
        payRecord.setPayNo(payNo);
        payRecord.setPayAmount(order.getTotalAmount());
        payRecord.setPayStatus("已支付");
        payRecord.setPayTime(LocalDateTime.now());

        int result = baseMapper.insert(payRecord);
        if (result != 1) {
            throw new PaymentException("支付记录创建失败");
        }

        // 更新订单状态为已支付
        order.setOrderStatus("已支付");
        petOrderMapper.updateById(order);

        // 扣减库存
        for (OrderDrugDO orderDrug : orderDrugs) {
            PetDrugStoreDO stock = petDrugStoreMapper.selectByStoreIdAndDrugId(
                    order.getStoreId(), orderDrug.getDrugId());
            if (stock != null) {
                stock.setStoreStock(stock.getStoreStock() - orderDrug.getQuantity());
                petDrugStoreMapper.updateById(stock);
            }
        }

        return payRecord.getPaymentId();
    }

    @Override
    public PaymentRecordDO getPaymentByOrderId(Integer orderId) {
        // 简化实现
        return null;
    }

    @Override
    public PaymentRecordDO getPaymentByPayNo(String payNo) {
        // 简化实现
        return null;
    }

    @Override
    public List<PaymentRecordDO> getPaymentListByOrderId(Integer orderId) {
        // 简化实现
        return null;
    }

    @Override
    public Boolean refund(Integer orderId, String remark) {
        // 简化实现
        return null;
    }

    @Override
    public String getPaymentStatus(Integer orderId) {
        // 简化实现
        return "未知状态";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer savePaymentRecord(PaymentRecordDO paymentData) {
        // 验证订单是否存在
        PetOrderDO order = petOrderMapper.selectById(paymentData.getOrderId());
        if (order == null) {
            throw new OrderException(OrderException.ORDER_NOT_FOUND, OrderException.MSG_ORDER_NOT_FOUND);
        }

        // 验证支付金额是否与订单金额一致
        if (paymentData.getPayAmount().compareTo(order.getTotalAmount()) != 0) {
            throw new PaymentException("支付金额与订单金额不符");
        }

        // 验证支付流水号唯一性
        PaymentRecordDO existingPayRecord = baseMapper.selectByPayNo(paymentData.getPayNo());
        if (existingPayRecord != null) {
            throw new PaymentException("支付流水号已存在");
        }

        // 保存支付记录
        int result = baseMapper.insert(paymentData);
        if (result != 1) {
            throw new PaymentException("支付记录创建失败");
        }

        // 更新订单状态为已支付
        order.setOrderStatus("已支付");
        petOrderMapper.updateById(order);

        // 手动执行库存扣减，因为触发器是AFTER UPDATE，而我们是INSERT操作
        if ("已支付".equals(paymentData.getPayStatus())) {
            List<OrderDrugDO> orderDrugs = orderDrugService.getOrderDrugsByOrderId(paymentData.getOrderId());
            for (OrderDrugDO orderDrug : orderDrugs) {
                PetDrugStoreDO stock = petDrugStoreMapper.selectByStoreIdAndDrugId(
                        order.getStoreId(), orderDrug.getDrugId());
                if (stock != null) {
                    stock.setStoreStock(stock.getStoreStock() - orderDrug.getQuantity());
                    petDrugStoreMapper.updateById(stock);
                }
            }
        }

        return paymentData.getPaymentId();
    }
}