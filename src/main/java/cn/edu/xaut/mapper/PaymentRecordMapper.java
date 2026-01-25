package cn.edu.xaut.mapper;

import cn.edu.xaut.domain.entity.payment.PaymentRecordDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 支付记录Mapper
 */
@Mapper
public interface PaymentRecordMapper extends BaseMapper<PaymentRecordDO> {

    /**
     * 根据订单ID查询支付记录
     * @param orderId 订单ID
     * @return 支付记录
     */
    PaymentRecordDO selectByOrderId(@Param("orderId") Integer orderId);

    /**
     * 根据支付流水号查询支付记录
     * @param payNo 支付流水号
     * @return 支付记录
     */
    PaymentRecordDO selectByPayNo(@Param("payNo") String payNo);

    /**
     * 根据订单ID查询支付记录列表
     * @param orderId 订单ID
     * @return 支付记录列表
     */
    List<PaymentRecordDO> selectListByOrderId(@Param("orderId") Integer orderId);

    /**
     * 根据支付状态查询支付记录列表
     * @param payStatus 支付状态
     * @return 支付记录列表
     */
    List<PaymentRecordDO> selectByPayStatus(@Param("payStatus") String payStatus);
}
