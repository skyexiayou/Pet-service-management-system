package cn.edu.xaut.service.orderproduct;

/**
 * 订单用品关联服务接口

 */

import cn.edu.xaut.domain.entity.order.OrderProductDO;

import java.util.List;

public interface OrderProductService {
    OrderProductDO getOrderProductById(Integer orderProductId);
    List<OrderProductDO> getOrderProductsByRelId(Integer relId);
    List<OrderProductDO> getAllOrderProducts();
    Integer createOrderProduct(OrderProductDO orderProduct);
    Integer updateOrderProduct(Integer orderProductId, OrderProductDO orderProduct);
    Integer deleteOrderProduct(Integer orderProductId);
}