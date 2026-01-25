package cn.edu.xaut.mapper;

import cn.edu.xaut.domain.entity.order.PetOrderDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 宠物订单Mapper
 */
@Mapper
public interface PetOrderMapper extends BaseMapper<PetOrderDO> {

    /**
     * 根据用户ID查询订单列表
     * @param userId 用户ID
     * @return 订单列表
     */
    List<PetOrderDO> selectByUserId(@Param("userId") Integer userId);

    /**
     * 根据订单ID查询订单详情（含关联信息）
     * @param orderId 订单ID
     * @return 订单详情
     */
    PetOrderDO selectDetailById(@Param("orderId") Integer orderId);

    /**
     * 根据处方ID查询订单
     * @param prescriptionId 处方ID
     * @return 订单信息
     */
    PetOrderDO selectByPrescriptionId(@Param("prescriptionId") Integer prescriptionId);

    /**
     * 根据订单编号查询订单
     * @param orderNo 订单编号
     * @return 订单信息
     */
    PetOrderDO selectByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 查询所有订单列表（管理员用）
     * @return 订单列表
     */
    List<PetOrderDO> selectAllOrders();

    /**
     * 根据订单状态查询订单列表
     * @param orderStatus 订单状态
     * @return 订单列表
     */
    List<PetOrderDO> selectByOrderStatus(@Param("orderStatus") String orderStatus);
    
    /**
     * 根据门店ID查询订单列表，支持分页和状态过滤
     * @param storeId 门店ID
     * @param orderStatus 订单状态
     * @param offset 偏移量
     * @param limit 每页条数
     * @return 订单列表
     */
    List<Map<String, Object>> selectOrderListByStoreId(
            @Param("storeId") Integer storeId,
            @Param("orderStatus") String orderStatus,
            @Param("offset") Integer offset,
            @Param("limit") Integer limit);
    
    /**
     * 根据门店ID查询订单总数，支持状态过滤
     * @param storeId 门店ID
     * @param orderStatus 订单状态
     * @return 订单总数
     */
    Integer selectOrderCountByStoreId(
            @Param("storeId") Integer storeId,
            @Param("orderStatus") String orderStatus);
}
