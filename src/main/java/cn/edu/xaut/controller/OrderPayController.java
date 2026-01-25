package cn.edu.xaut.controller;

import cn.edu.xaut.domain.entity.order.OrderDrugDO;
import cn.edu.xaut.domain.entity.order.PetOrderDO;
import cn.edu.xaut.domain.entity.payment.PaymentRecordDO;
import cn.edu.xaut.domain.vo.ResponseVO;
import cn.edu.xaut.domain.vo.appointment.AppointmentDetailVO;
import cn.edu.xaut.exception.BusinessException;
import cn.edu.xaut.mapper.AppointmentMapper;
import cn.edu.xaut.service.order.OrderDrugService;
import cn.edu.xaut.service.order.PetOrderService;
import cn.edu.xaut.service.payment.PaymentRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单支付控制器
 * 包含管理员专属接口和用户端接口
 */
@RestController
@RequestMapping("/api")
@Tag(name = "订单支付管理", description = "订单生成、支付、查询相关接口")
public class OrderPayController {

    @Autowired
    private PetOrderService petOrderService;

    @Autowired
    private OrderDrugService orderDrugService;

    @Autowired
    private PaymentRecordService paymentRecordService;

    @Autowired
    private AppointmentMapper appointmentMapper;

    /**
     * 查询待诊断预约列表（管理员专属）
     */
    @GetMapping("/admin/appointment/waitDiagnose")
    @Operation(summary = "查询待诊断预约列表", description = "管理员查询所有待诊断的预约，包含宠物完整档案信息")
    public ResponseVO<List<AppointmentDetailVO>> getWaitDiagnoseAppointments() {
        // 设置默认值：storeId为null表示查询所有门店，pageNum为1，pageSize为100
        List<AppointmentDetailVO> appointments = appointmentMapper.selectWaitDiagnoseAppointments(null, null, 1, 100,
                0);
        return ResponseVO.success(appointments);
    }

    /**
     * 根据处方ID生成订单（管理员专属）
     */
    @PostMapping("/admin/prescription/generateOrder")
    @Operation(summary = "根据处方生成订单", description = "根据处方ID生成对应的药品订单")
    public ResponseVO<Map<String, Object>> generateOrderByPrescription(
            @Parameter(description = "处方ID", required = true) @RequestParam Integer prescriptionId) {
        if (prescriptionId == null) {
            return ResponseVO.paramError("处方ID不能为空");
        }
        Integer orderId = petOrderService.generateOrderByPrescription(prescriptionId);
        Map<String, Object> result = new HashMap<>();
        result.put("orderId", orderId);
        result.put("message", "订单生成成功");
        return ResponseVO.success(result);
    }

    /**
     * 获取所有订单列表（管理员专属）
     */
    @GetMapping("/admin/order/list")
    @Operation(summary = "获取所有订单列表", description = "管理员查询所有订单信息")
    public ResponseVO<List<PetOrderDO>> getAllOrders() {
        List<PetOrderDO> orders = petOrderService.getAllOrders();
        return ResponseVO.success(orders);
    }

    /**
     * 根据订单状态查询订单列表（管理员专属）
     */
    @GetMapping("/admin/order/listByStatus")
    @Operation(summary = "根据状态查询订单", description = "管理员根据订单状态查询订单列表")
    public ResponseVO<List<PetOrderDO>> getOrdersByStatus(
            @Parameter(description = "订单状态", required = true) @RequestParam String orderStatus) {
        if (orderStatus == null || orderStatus.trim().isEmpty()) {
            return ResponseVO.paramError("订单状态不能为空");
        }
        List<PetOrderDO> orders = petOrderService.getOrdersByStatus(orderStatus);
        return ResponseVO.success(orders);
    }

    /**
     * 获取我的订单列表（用户端）
     */
    @GetMapping("/order/myList")
    @Operation(summary = "获取我的订单列表", description = "用户查询自己的订单列表")
    public ResponseVO<List<PetOrderDO>> getMyOrders(
            @Parameter(description = "用户ID", required = true) @RequestParam Integer userId) {
        if (userId == null) {
            return ResponseVO.paramError("用户ID不能为空");
        }
        List<PetOrderDO> orders = petOrderService.getOrdersByUserId(userId);
        return ResponseVO.success(orders);
    }

    /**
     * 查询订单详情
     */
    @GetMapping("/order/detail/{orderId}")
    @Operation(summary = "查询订单详情", description = "根据订单ID查询订单详情，包含药品明细")
    public ResponseVO<Map<String, Object>> getOrderDetail(
            @Parameter(description = "订单ID", required = true) @PathVariable Integer orderId) {
        if (orderId == null) {
            return ResponseVO.paramError("订单ID不能为空");
        }
        PetOrderDO order = petOrderService.getOrderDetail(orderId);
        if (order == null) {
            return ResponseVO.error(404, "订单不存在");
        }
        // 查询订单药品明细
        List<OrderDrugDO> orderDrugs = orderDrugService.getOrderDrugsByOrderId(orderId);

        // 查询支付记录
        PaymentRecordDO payRecord = paymentRecordService.getPaymentByOrderId(orderId);

        Map<String, Object> result = new HashMap<>();
        result.put("order", order);
        result.put("orderDrugs", orderDrugs);
        result.put("payRecord", payRecord);
        return ResponseVO.success(result);
    }

    /**
     * 支付订单
     */
    @PostMapping("/order/pay")
    @Operation(summary = "支付订单", description = "用户支付订单")
    public ResponseVO<Map<String, Object>> payOrder(
            @Parameter(description = "订单ID", required = true) @RequestParam Integer orderId,
            @Parameter(description = "支付方式", required = true) @RequestParam String payType) {
        if (orderId == null) {
            return ResponseVO.paramError("订单ID不能为空");
        }
        if (payType == null || payType.trim().isEmpty()) {
            return ResponseVO.paramError("支付方式不能为空");
        }

        PaymentRecordDO payRecord = new PaymentRecordDO();
        payRecord.setPayType(payType);

        Integer paymentId = paymentRecordService.payOrder(orderId, payRecord);

        Map<String, Object> result = new HashMap<>();
        result.put("paymentId", paymentId);
        result.put("message", "支付成功");
        return ResponseVO.success(result);
    }

    /**
     * 查询支付结果
     */
    @GetMapping("/payment/result/{orderId}")
    @Operation(summary = "查询支付结果", description = "根据订单ID查询支付结果")
    public ResponseVO<Map<String, Object>> getPaymentResult(
            @Parameter(description = "订单ID", required = true) @PathVariable Integer orderId) {
        if (orderId == null) {
            return ResponseVO.paramError("订单ID不能为空");
        }

        PaymentRecordDO payRecord = paymentRecordService.getPaymentByOrderId(orderId);
        String payStatus = paymentRecordService.getPaymentStatus(orderId);

        Map<String, Object> result = new HashMap<>();
        result.put("payStatus", payStatus);
        result.put("payRecord", payRecord);
        return ResponseVO.success(result);
    }

    /**
     * 取消订单
     */
    @PostMapping("/order/cancel")
    @Operation(summary = "取消订单", description = "用户取消未支付的订单")
    public ResponseVO<String> cancelOrder(
            @Parameter(description = "订单ID", required = true) @RequestParam Integer orderId,
            @Parameter(description = "用户ID", required = true) @RequestParam Integer userId) {
        if (orderId == null) {
            return ResponseVO.paramError("订单ID不能为空");
        }
        if (userId == null) {
            return ResponseVO.paramError("用户ID不能为空");
        }

        Boolean result = petOrderService.cancelOrder(orderId, userId);
        if (result) {
            return ResponseVO.success("订单取消成功");
        }
        return ResponseVO.error(500, "订单取消失败");
    }

    /**
     * 订单退款（管理员专属）
     */
    @PostMapping("/admin/order/refund")
    @Operation(summary = "订单退款", description = "管理员为用户处理订单退款")
    public ResponseVO<String> refundOrder(
            @Parameter(description = "订单ID", required = true) @RequestParam Integer orderId,
            @Parameter(description = "退款备注") @RequestParam(required = false) String remark) {
        if (orderId == null) {
            return ResponseVO.paramError("订单ID不能为空");
        }

        Boolean result = paymentRecordService.refund(orderId, remark);
        if (result) {
            return ResponseVO.success("退款成功");
        }
        return ResponseVO.error(500, "退款失败");
    }

    /**
     * 订单生成接口
     */
    @PostMapping("/order/create")
    @Operation(summary = "生成订单", description = "处方生成成功后自动触发，生成药品订单")
    public ResponseVO<Map<String, Object>> createOrder(
            @RequestBody Map<String, Object> orderData) {
        // 验证请求参数
        Integer userId = (Integer) orderData.get("userId");
        Integer petId = (Integer) orderData.get("petId");
        Integer storeId = (Integer) orderData.get("storeId");
        Integer prescriptionId = (Integer) orderData.get("prescriptionId");
        BigDecimal totalAmount = (BigDecimal) orderData.get("totalAmount");
        String remark = (String) orderData.get("remark");

        if (userId == null || petId == null || storeId == null || prescriptionId == null || totalAmount == null) {
            return ResponseVO.paramError("缺少必要参数");
        }

        // 调用服务生成订单
        Integer orderId = petOrderService.generateOrderByPrescription(prescriptionId, BigDecimal.ZERO);

        // 获取生成的订单信息
        PetOrderDO order = petOrderService.getOrderDetail(orderId);

        Map<String, Object> result = new HashMap<>();
        result.put("orderId", orderId);
        result.put("orderNo", order.getOrderNo());
        return ResponseVO.success(result);
    }

    /**
     * 支付记录提交接口
     */
    @PostMapping("/payment/create")
    @Operation(summary = "提交支付记录", description = "支付成功后提交支付记录，触发库存扣减")
    public ResponseVO<Map<String, Object>> createPayment(
            @RequestBody PaymentRecordDO paymentData) {
        // 验证请求参数
        if (paymentData.getOrderId() == null || paymentData.getPayNo() == null ||
                paymentData.getPayType() == null || paymentData.getPayAmount() == null ||
                paymentData.getPayStatus() == null || paymentData.getPayTime() == null) {
            return ResponseVO.paramError("缺少必要参数");
        }

        // 调用服务保存支付记录
        Integer paymentId = paymentRecordService.savePaymentRecord(paymentData);

        Map<String, Object> result = new HashMap<>();
        result.put("paymentId", paymentId);
        result.put("message", "支付成功，库存已扣减");
        return ResponseVO.success(result);
    }

    /**
     * 订单列表查询接口
     */
    @GetMapping("/order/list")
    @Operation(summary = "查询订单列表", description = "根据门店ID查询订单列表，不传storeId则查询所有门店订单")
    public ResponseVO<Map<String, Object>> getOrderList(
            @Parameter(description = "门店ID") @RequestParam(required = false) Integer storeId,
            @Parameter(description = "订单状态") @RequestParam(required = false) String orderStatus,
            @Parameter(description = "页码") @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页条数") @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        // 允许storeId为null，表示查询所有门店订单

        // 调用服务查询订单列表
        List<Map<String, Object>> orders = petOrderService.getOrderListByStoreId(storeId, orderStatus, pageNum,
                pageSize);
        Integer total = petOrderService.getOrderCountByStoreId(storeId, orderStatus);

        Map<String, Object> result = new HashMap<>();
        result.put("list", orders);
        result.put("total", total);
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        return ResponseVO.success(result);
    }
}