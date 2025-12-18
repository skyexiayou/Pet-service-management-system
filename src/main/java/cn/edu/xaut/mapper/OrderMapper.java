package cn.edu.xaut.mapper;

import cn.edu.xaut.domain.entity.order.OrderDO;
import cn.edu.xaut.domain.vo.order.OrderDetailVO;
import cn.edu.xaut.domain.vo.order.OrderVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 订单Mapper接口
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderDO> {

    /**
     * 查询用户的订单列表
     * @param userId 用户ID
     * @return 订单列表
     */
    List<OrderVO> selectOrdersByUserId(@Param("userId") Integer userId);

    /**
     * 查询订单详情（含预约信息）
     * @param orderId 订单ID
     * @return 订单详情
     */
    OrderDetailVO selectOrderDetail(@Param("orderId") Integer orderId);

    /**
     * 计算服务费用
     * @param apptId 预约ID
     * @return 服务费用（beautyFee, fosterFee, medicalFee）
     */
    Map<String, Object> calculateServiceFees(@Param("apptId") Integer apptId);
}
