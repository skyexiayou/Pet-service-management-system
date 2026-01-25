package cn.edu.xaut.service.order.impl;

import cn.edu.xaut.domain.entity.order.OrderDrugDO;
import cn.edu.xaut.mapper.OrderDrugMapper;
import cn.edu.xaut.service.order.OrderDrugService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 订单药品明细服务实现类
 */
@Service
public class OrderDrugServiceImpl extends ServiceImpl<OrderDrugMapper, OrderDrugDO> implements OrderDrugService {

    /**
     * 根据订单ID查询药品明细列表
     */
    @Override
    public List<OrderDrugDO> getOrderDrugsByOrderId(Integer orderId) {
        return baseMapper.selectByOrderId(orderId);
    }

    /**
     * 批量保存订单药品明细
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchSaveOrderDrugs(List<OrderDrugDO> orderDrugs) {
        if (orderDrugs == null || orderDrugs.isEmpty()) {
            return true;
        }
        Integer result = baseMapper.batchInsert(orderDrugs);
        return result != null && result > 0;
    }

    /**
     * 根据订单ID删除药品明细
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteByOrderId(Integer orderId) {
        Integer result = baseMapper.deleteByOrderId(orderId);
        return result != null && result >= 0;
    }
}
