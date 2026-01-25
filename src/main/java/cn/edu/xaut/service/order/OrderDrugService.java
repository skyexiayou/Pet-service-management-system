package cn.edu.xaut.service.order;

import cn.edu.xaut.domain.entity.order.OrderDrugDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 订单药品明细服务接口
 */
public interface OrderDrugService extends IService<OrderDrugDO> {

    /**
     * 根据订单ID查询药品明细列表
     * @param orderId 订单ID
     * @return 药品明细列表
     */
    List<OrderDrugDO> getOrderDrugsByOrderId(Integer orderId);

    /**
     * 批量保存订单药品明细
     * @param orderDrugs 订单药品明细列表
     * @return 是否成功
     */
    Boolean batchSaveOrderDrugs(List<OrderDrugDO> orderDrugs);

    /**
     * 根据订单ID删除药品明细
     * @param orderId 订单ID
     * @return 是否成功
     */
    Boolean deleteByOrderId(Integer orderId);
}
