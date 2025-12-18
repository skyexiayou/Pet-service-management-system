package cn.edu.xaut.mapper;

import cn.edu.xaut.domain.entity.order.OrderProductDO;
import cn.edu.xaut.domain.vo.order.ProductDetailVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单用品Mapper接口
 */
@Mapper
public interface OrderProductMapper extends BaseMapper<OrderProductDO> {

    /**
     * 批量插入订单用品明细
     * @param list 订单用品列表
     * @return 插入数量
     */
    int batchInsert(@Param("list") List<OrderProductDO> list);

    /**
     * 查询订单的用品明细
     * @param orderId 订单ID
     * @return 用品明细列表
     */
    List<ProductDetailVO> selectProductDetailsByOrderId(@Param("orderId") Integer orderId);

    /**
     * 根据RelID查询订单用品
     * @param relId 关联ID
     * @return 订单用品列表
     */
    List<OrderProductDO> selectOrderProductsByRelId(@Param("relId") Integer relId);

    /**
     * 根据订单ID查询订单用品信息
     * @param orderId 订单ID
     * @return 订单用品列表
     */
    List<java.util.Map<String, Object>> selectByOrderId(@Param("orderId") Integer orderId);
}
