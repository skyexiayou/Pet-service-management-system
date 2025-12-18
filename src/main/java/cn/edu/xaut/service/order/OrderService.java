package cn.edu.xaut.service.order;

import cn.edu.xaut.domain.dto.order.OrderFromAppointmentDTO;
import cn.edu.xaut.domain.dto.order.OrderFromProductsDTO;
import cn.edu.xaut.domain.dto.order.PaymentDTO;
import cn.edu.xaut.domain.vo.order.OrderDetailVO;
import cn.edu.xaut.domain.vo.order.OrderVO;
import cn.edu.xaut.domain.vo.order.PaymentResultVO;

import java.util.List;

/**
 * 订单服务接口
 */
public interface OrderService {

    /**
     * 生成关联预约的订单
     * @param dto 订单创建请求
     * @return 订单ID
     */
    Integer createOrderFromAppointment(OrderFromAppointmentDTO dto);

    /**
     * 生成纯用品购买订单
     * @param dto 订单创建请求
     * @return 订单ID
     */
    Integer createOrderFromProducts(OrderFromProductsDTO dto);

    /**
     * 查询用户的订单列表
     * @param userId 用户ID
     * @return 订单列表
     */
    List<OrderVO> getOrdersByUserId(Integer userId);

    /**
     * 查询订单详情
     * @param orderId 订单ID
     * @return 订单详情
     */
    OrderDetailVO getOrderDetail(Integer orderId);

    /**
     * 在线支付
     * @param orderId 订单ID
     * @param dto 支付请求
     * @return 支付结果
     */
    PaymentResultVO payOrder(Integer orderId, PaymentDTO dto);
}
