package cn.edu.xaut.service.order;

import cn.edu.xaut.domain.entity.order.PetOrderDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal; // 优化：导入BigDecimal，避免全类名
import java.util.List;
import java.util.Map;

/**
 * 宠物订单业务层接口
 * 处理宠物订单的生成、查询、状态更新、取消等核心业务逻辑
 */
public interface PetOrderService extends IService<PetOrderDO> {

    /**
     * 根据处方ID生成宠物订单（默认诊疗费用）
     * @param prescriptionId 处方ID
     * @return 生成的订单ID
     */
    Integer generateOrderByPrescription(Integer prescriptionId);

    /**
     * 根据处方ID和指定诊疗费用生成宠物订单
     * @param prescriptionId 处方ID
     * @param medicalFee 诊疗费用（精准到分）
     * @return 生成的订单ID
     */
    Integer generateOrderByPrescription(Integer prescriptionId, BigDecimal medicalFee);

    /**
     * 根据用户ID查询该用户的所有订单列表
     * @param userId 用户ID
     * @return 该用户的宠物订单列表
     */
    List<PetOrderDO> getOrdersByUserId(Integer userId);

    /**
     * 根据订单ID查询订单详情
     * @param orderId 订单ID
     * @return 订单详情对象
     */
    PetOrderDO getOrderDetail(Integer orderId);

    /**
     * 根据处方ID查询对应的订单信息
     * @param prescriptionId 处方ID
     * @return 该处方对应的订单对象
     */
    PetOrderDO getOrderByPrescriptionId(Integer prescriptionId);

    /**
     * 查询系统中所有的宠物订单列表（管理员用）
     * @return 所有宠物订单列表
     */
    List<PetOrderDO> getAllOrders();

    /**
     * 根据订单状态查询订单列表
     * @param orderStatus 订单状态（如：待支付、已支付、已取消、已完成等）
     * @return 对应状态的订单列表
     */
    List<PetOrderDO> getOrdersByStatus(String orderStatus);

    /**
     * 更新订单状态
     * @param orderId 订单ID
     * @param orderStatus 新的订单状态
     * @return 操作结果：true=更新成功，false=更新失败
     */
    Boolean updateOrderStatus(Integer orderId, String orderStatus);

    /**
     * 取消订单（需验证用户权限）
     * @param orderId 订单ID
     * @param userId 用户ID（用于校验订单归属）
     * @return 操作结果：true=取消成功，false=取消失败
     */
    Boolean cancelOrder(Integer orderId, Integer userId);
    
    /**
     * 根据门店ID查询订单列表，支持分页和状态过滤
     * @param storeId 门店ID
     * @param orderStatus 订单状态（可选）
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return 订单列表，包含关联的用户、宠物和门店信息
     */
    List<Map<String, Object>> getOrderListByStoreId(Integer storeId, String orderStatus, Integer pageNum, Integer pageSize);
    
    /**
     * 根据门店ID查询订单总数，支持状态过滤
     * @param storeId 门店ID
     * @param orderStatus 订单状态（可选）
     * @return 订单总数
     */
    Integer getOrderCountByStoreId(Integer storeId, String orderStatus);
}