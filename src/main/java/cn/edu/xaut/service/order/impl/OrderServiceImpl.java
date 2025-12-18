package cn.edu.xaut.service.order.impl;

import cn.edu.xaut.domain.dto.order.OrderFromAppointmentDTO;
import cn.edu.xaut.domain.dto.order.OrderFromProductsDTO;
import cn.edu.xaut.domain.dto.order.PaymentDTO;
import cn.edu.xaut.domain.dto.order.ProductPurchaseDTO;
import cn.edu.xaut.domain.entity.appointment.AppointmentDO;
import cn.edu.xaut.domain.entity.order.OrderDO;
import cn.edu.xaut.domain.entity.order.OrderProductDO;
import cn.edu.xaut.domain.entity.petproductstore.PetProductStoreDO;
import cn.edu.xaut.domain.vo.appointment.BeautyDetailVO;
import cn.edu.xaut.domain.vo.appointment.FosterDetailVO;
import cn.edu.xaut.domain.vo.appointment.MedicalDetailVO;
import cn.edu.xaut.domain.vo.order.OrderDetailVO;
import cn.edu.xaut.domain.vo.order.OrderVO;
import cn.edu.xaut.domain.vo.order.PaymentResultVO;
import cn.edu.xaut.domain.vo.order.ProductDetailVO;
import cn.edu.xaut.exception.BusinessException;
import cn.edu.xaut.mapper.*;
import cn.edu.xaut.service.order.OrderService;
import cn.edu.xaut.utils.AmountCalculationUtil;
import cn.edu.xaut.utils.PaymentVoucherUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单服务实现类
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderProductMapper orderProductMapper;
    private final AppointmentMapper appointmentMapper;
    private final PetProductStoreMapper petProductStoreMapper;
    private final ApptBeautyMapper apptBeautyMapper;
    private final ApptFosterMapper apptFosterMapper;
    private final ApptMedicalMapper apptMedicalMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createOrderFromAppointment(OrderFromAppointmentDTO dto) {
        // 1. 验证预约状态
        AppointmentDO appointment = appointmentMapper.selectById(dto.getApptId());
        if (appointment == null) {
            throw new BusinessException("预约不存在");
        }
        if (!"待服务".equals(appointment.getApptStatus())) {
            throw new BusinessException("预约状态不是'待服务'，无法生成订单");
        }

        // 2. 计算服务费用
        Map<String, Object> serviceFees = orderMapper.calculateServiceFees(dto.getApptId());
        BigDecimal beautyFee = new BigDecimal(serviceFees.get("beautyFee").toString());
        BigDecimal fosterFee = new BigDecimal(serviceFees.get("fosterFee").toString());
        BigDecimal medicalFee = new BigDecimal(serviceFees.get("medicalFee").toString());

        // 3. 计算用品费用
        BigDecimal productFee = BigDecimal.ZERO;
        List<OrderProductDO> orderProducts = new ArrayList<>();
        
        if (dto.getProducts() != null && !dto.getProducts().isEmpty()) {
            for (ProductPurchaseDTO product : dto.getProducts()) {
                PetProductStoreDO productStore = petProductStoreMapper.selectById(product.getRelId());
                if (productStore == null) {
                    throw new BusinessException("用品不存在");
                }
                
                BigDecimal subtotal = AmountCalculationUtil.calculateProductSubtotal(
                    product.getQuantity(), productStore.getPrice()
                );
                productFee = productFee.add(subtotal);
                
                OrderProductDO orderProduct = new OrderProductDO();
                orderProduct.setRelId(product.getRelId());
                orderProduct.setQuantity(product.getQuantity());
                orderProduct.setActualPrice(productStore.getPrice());
                orderProduct.setSubtotal(subtotal);
                orderProduct.setCreateTime(new Date());
                orderProducts.add(orderProduct);
            }
        }

        // 4. 计算订单总金额
        BigDecimal totalAmount = AmountCalculationUtil.calculateTotalAmount(
            beautyFee, fosterFee, medicalFee, productFee
        );

        // 5. 创建订单记录
        OrderDO order = new OrderDO();
        order.setApptId(dto.getApptId());
        order.setTotalAmount(totalAmount);
        order.setPayStatus("未支付");
        order.setOrderCreateTime(new Date());
        orderMapper.insert(order);

        // 6. 创建订单用品明细
        if (!orderProducts.isEmpty()) {
            for (OrderProductDO orderProduct : orderProducts) {
                orderProduct.setOrderId(order.getOrderId());
            }
            orderProductMapper.batchInsert(orderProducts);
        }

        return order.getOrderId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createOrderFromProducts(OrderFromProductsDTO dto) {
        // 1. 验证库存并锁定
        List<OrderProductDO> orderProducts = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (ProductPurchaseDTO product : dto.getProducts()) {
            // 使用行锁查询
            PetProductStoreDO productStore = petProductStoreMapper.selectByRelIdForUpdate(product.getRelId());
            if (productStore == null) {
                throw new BusinessException("用品不存在");
            }
            
            // 验证库存
            if (productStore.getStoreStock() < product.getQuantity()) {
                throw new BusinessException(
                    String.format("该门店该用品库存不足，当前库存：%d", productStore.getStoreStock())
                );
            }
            
            // 计算小计
            BigDecimal subtotal = AmountCalculationUtil.calculateProductSubtotal(
                product.getQuantity(), productStore.getPrice()
            );
            totalAmount = totalAmount.add(subtotal);
            
            OrderProductDO orderProduct = new OrderProductDO();
            orderProduct.setRelId(product.getRelId());
            orderProduct.setQuantity(product.getQuantity());
            orderProduct.setActualPrice(productStore.getPrice());
            orderProduct.setSubtotal(subtotal);
            orderProduct.setCreateTime(new Date());
            orderProducts.add(orderProduct);
        }

        // 2. 创建订单记录
        OrderDO order = new OrderDO();
        order.setTotalAmount(totalAmount);
        order.setPayStatus("未支付");
        order.setOrderCreateTime(new Date());
        orderMapper.insert(order);

        // 3. 创建订单用品明细
        for (OrderProductDO orderProduct : orderProducts) {
            orderProduct.setOrderId(order.getOrderId());
        }
        orderProductMapper.batchInsert(orderProducts);

        // 4. 扣减库存
        for (ProductPurchaseDTO product : dto.getProducts()) {
            int updated = petProductStoreMapper.decreaseStock(product.getRelId(), product.getQuantity());
            if (updated == 0) {
                throw new BusinessException("库存扣减失败，可能库存不足");
            }
        }

        return order.getOrderId();
    }

    @Override
    public List<OrderVO> getOrdersByUserId(Integer userId) {
        return orderMapper.selectOrdersByUserId(userId);
    }

    @Override
    public OrderDetailVO getOrderDetail(Integer orderId) {
        // 1. 查询订单基本信息
        OrderDetailVO detail = orderMapper.selectOrderDetail(orderId);
        if (detail == null) {
            throw new BusinessException("订单不存在");
        }

        // 2. 如果关联预约，查询服务明细
        if (detail.getApptId() != null) {
            // 查询美容服务明细
            List<BeautyDetailVO> beautyServices = apptBeautyMapper.selectBeautyDetailsByApptId(detail.getApptId());
            detail.setBeautyServices(beautyServices);

            // 查询寄养服务明细
            FosterDetailVO fosterService = apptFosterMapper.selectFosterDetailByApptId(detail.getApptId());
            detail.setFosterService(fosterService);

            // 查询医疗服务明细
            MedicalDetailVO medicalService = apptMedicalMapper.selectMedicalDetailByApptId(detail.getApptId());
            detail.setMedicalService(medicalService);
        }

        // 3. 查询用品明细
        List<ProductDetailVO> products = orderProductMapper.selectProductDetailsByOrderId(orderId);
        detail.setProducts(products);

        return detail;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentResultVO payOrder(Integer orderId, PaymentDTO dto) {
        // 1. 查询订单
        OrderDO order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }

        // 2. 验证订单状态
        if (!"未支付".equals(order.getPayStatus())) {
            throw new BusinessException("订单状态不是'未支付'，无法支付");
        }

        // 3. 生成支付凭证号
        String paymentVoucher = PaymentVoucherUtil.generatePaymentVoucher();

        // 4. 更新订单状态
        order.setPayStatus("已支付");
        order.setPayMethod(dto.getPayMethod());
        order.setPayTime(new Date());
        orderMapper.updateById(order);

        // 5. 返回支付结果
        PaymentResultVO result = new PaymentResultVO();
        result.setPaymentVoucher(paymentVoucher);
        result.setPayTime(order.getPayTime());
        result.setPayMethod(order.getPayMethod());
        result.setTotalAmount(order.getTotalAmount());

        return result;
    }
}
