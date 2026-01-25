package cn.edu.xaut.mapper;

import cn.edu.xaut.domain.entity.order.OrderDrugDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单药品明细Mapper
 */
@Mapper
public interface OrderDrugMapper extends BaseMapper<OrderDrugDO> {

    /**
     * 根据订单ID查询药品明细列表
     * @param orderId 订单ID
     * @return 药品明细列表
     */
    List<OrderDrugDO> selectByOrderId(@Param("orderId") Integer orderId);

    /**
     * 批量插入订单药品明细
     * @param orderDrugs 订单药品明细列表
     * @return 影响行数
     */
    Integer batchInsert(@Param("list") List<OrderDrugDO> orderDrugs);

    /**
     * 根据订单ID删除药品明细
     * @param orderId 订单ID
     * @return 影响行数
     */
    Integer deleteByOrderId(@Param("orderId") Integer orderId);
}
