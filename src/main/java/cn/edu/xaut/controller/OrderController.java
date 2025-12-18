package cn.edu.xaut.controller;

import cn.edu.xaut.domain.dto.order.OrderFromAppointmentDTO;
import cn.edu.xaut.domain.dto.order.OrderFromProductsDTO;
import cn.edu.xaut.domain.dto.order.PaymentDTO;
import cn.edu.xaut.domain.vo.ResponseVO;
import cn.edu.xaut.domain.vo.order.OrderDetailVO;
import cn.edu.xaut.domain.vo.order.OrderVO;
import cn.edu.xaut.domain.vo.order.PaymentResultVO;
import cn.edu.xaut.service.order.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单管理Controller
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Api(tags = "订单管理")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/appointment")
    @ApiOperation("生成关联预约的订单")
    public ResponseVO<Integer> createOrderFromAppointment(
            @Valid @RequestBody OrderFromAppointmentDTO dto
    ) {
        Integer orderId = orderService.createOrderFromAppointment(dto);
        return ResponseVO.success(orderId);
    }

    @PostMapping("/products")
    @ApiOperation("生成纯用品购买订单")
    public ResponseVO<Integer> createOrderFromProducts(
            @Valid @RequestBody OrderFromProductsDTO dto
    ) {
        Integer orderId = orderService.createOrderFromProducts(dto);
        return ResponseVO.success(orderId);
    }

    @GetMapping("/user/{userId}")
    @ApiOperation("查询用户的订单列表")
    public ResponseVO<List<OrderVO>> getOrdersByUserId(
            @ApiParam(value = "用户ID", required = true, example = "1")
            @PathVariable Integer userId
    ) {
        List<OrderVO> orders = orderService.getOrdersByUserId(userId);
        return ResponseVO.success(orders);
    }

    @GetMapping("/{orderId}")
    @ApiOperation("查询订单详情")
    public ResponseVO<OrderDetailVO> getOrderDetail(
            @ApiParam(value = "订单ID", required = true, example = "1")
            @PathVariable Integer orderId
    ) {
        OrderDetailVO detail = orderService.getOrderDetail(orderId);
        return ResponseVO.success(detail);
    }

    @PutMapping("/{orderId}/pay")
    @ApiOperation("在线支付")
    public ResponseVO<PaymentResultVO> payOrder(
            @ApiParam(value = "订单ID", required = true, example = "1")
            @PathVariable Integer orderId,
            @Valid @RequestBody PaymentDTO dto
    ) {
        PaymentResultVO result = orderService.payOrder(orderId, dto);
        return ResponseVO.success(result);
    }
}
