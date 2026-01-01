package cn.edu.xaut.controller.admin;

import cn.edu.xaut.domain.entity.order.OrderDO;
import cn.edu.xaut.domain.vo.ResponseVO;
import cn.edu.xaut.domain.vo.order.OrderDetailVO;
import cn.edu.xaut.exception.BusinessException;
import cn.edu.xaut.mapper.OrderMapper;
import cn.edu.xaut.service.order.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 管理员订单管理Controller
 */
@RestController
@RequestMapping("/api/admin/orders")
@Api(tags = "管理员-订单管理")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderMapper orderMapper;
    private final OrderService orderService;

    @GetMapping
    @ApiOperation("查询所有订单列表（分页）")
    public ResponseVO<Page<OrderDO>> getAllOrders(
            @ApiParam("页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam("每页大小") @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<OrderDO> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<OrderDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(OrderDO::getOrderCreateTime);
        Page<OrderDO> result = orderMapper.selectPage(page, wrapper);
        return ResponseVO.success(result);
    }

    @GetMapping("/{orderId}")
    @ApiOperation("查询订单详情")
    public ResponseVO<OrderDetailVO> getOrderDetail(
            @ApiParam("订单ID") @PathVariable Integer orderId) {
        OrderDetailVO detail = orderService.getOrderDetail(orderId);
        return ResponseVO.success(detail);
    }

    @PostMapping
    @ApiOperation("创建订单")
    public ResponseVO<Integer> createOrder(@Valid @RequestBody Map<String, Object> orderData) {
        OrderDO order = new OrderDO();
        
        if (orderData.containsKey("apptId")) {
            Object apptIdObj = orderData.get("apptId");
            if (apptIdObj != null) {
                order.setApptId(Integer.valueOf(apptIdObj.toString()));
            }
        }
        
        if (orderData.containsKey("totalAmount")) {
            Object amountObj = orderData.get("totalAmount");
            if (amountObj != null) {
                order.setTotalAmount(new BigDecimal(amountObj.toString()));
            }
        }
        
        order.setPayStatus(orderData.containsKey("payStatus") ? 
            (String) orderData.get("payStatus") : "未支付");
        order.setPayMethod((String) orderData.get("payMethod"));
        order.setOrderCreateTime(new Date());
        
        orderMapper.insert(order);
        return ResponseVO.success(order.getOrderId());
    }

    @PutMapping("/{orderId}")
    @ApiOperation("更新订单")
    public ResponseVO<Void> updateOrder(
            @ApiParam("订单ID") @PathVariable Integer orderId,
            @Valid @RequestBody Map<String, Object> updateData) {
        OrderDO order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        if (updateData.containsKey("payStatus")) {
            order.setPayStatus((String) updateData.get("payStatus"));
        }
        if (updateData.containsKey("payMethod")) {
            order.setPayMethod((String) updateData.get("payMethod"));
        }
        if (updateData.containsKey("totalAmount")) {
            Object amountObj = updateData.get("totalAmount");
            if (amountObj != null) {
                order.setTotalAmount(new BigDecimal(amountObj.toString()));
            }
        }
        
        orderMapper.updateById(order);
        return ResponseVO.success(null);
    }

    @DeleteMapping("/{orderId}")
    @ApiOperation("删除订单")
    public ResponseVO<Void> deleteOrder(
            @ApiParam("订单ID") @PathVariable Integer orderId) {
        OrderDO order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        if ("已支付".equals(order.getPayStatus())) {
            throw new BusinessException("已支付订单不能删除");
        }
        
        orderMapper.deleteById(orderId);
        return ResponseVO.success(null);
    }

    @PutMapping("/{orderId}/refund")
    @ApiOperation("订单退款")
    public ResponseVO<Void> refundOrder(
            @ApiParam("订单ID") @PathVariable Integer orderId,
            @RequestBody Map<String, Object> refundData) {
        OrderDO order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        if (!"已支付".equals(order.getPayStatus())) {
            throw new BusinessException("只有已支付订单才能退款");
        }
        
        order.setPayStatus("已退款");
        orderMapper.updateById(order);
        
        return ResponseVO.success(null);
    }
}
