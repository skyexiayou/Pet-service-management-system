package cn.edu.xaut.service.orderproduct.impl;

/**
 * 订单用品关联服务实现类
 */

import cn.edu.xaut.domain.entity.order.OrderProductDO;
import cn.edu.xaut.mapper.OrderProductMapper;
import cn.edu.xaut.service.orderproduct.OrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderProductServiceImpl implements OrderProductService {

    @Autowired
    private OrderProductMapper orderProductMapper;

    @Override
    public OrderProductDO getOrderProductById(Integer orderProductId) {
        return orderProductMapper.selectById(orderProductId);
    }

    @Override
    public List<OrderProductDO> getOrderProductsByRelId(Integer relId) {
        return orderProductMapper.selectOrderProductsByRelId(relId);
    }

    @Override
    public List<OrderProductDO> getAllOrderProducts() {
        return orderProductMapper.selectList(null);
    }

    @Override
    public Integer createOrderProduct(OrderProductDO orderProduct) {
        return orderProductMapper.insert(orderProduct);
    }

    @Override
    public Integer updateOrderProduct(Integer orderProductId, OrderProductDO orderProduct) {
        orderProduct.setOrderProductId(orderProductId);
        return orderProductMapper.updateById(orderProduct);
    }

    @Override
    public Integer deleteOrderProduct(Integer orderProductId) {
        return orderProductMapper.deleteById(orderProductId);
    }
}