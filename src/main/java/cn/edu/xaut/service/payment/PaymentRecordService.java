package cn.edu.xaut.service.payment;

import cn.edu.xaut.domain.entity.payment.PaymentRecordDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 支付记录服务接口
 */
public interface PaymentRecordService extends IService<PaymentRecordDO> {

    /**
     * 订单支付
     * @param orderId 订单ID
     * @param payRecord 支付记录
     * @return 支付记录ID
     */
    Integer payOrder(Integer orderId, PaymentRecordDO payRecord);

    /**
     * 根据订单ID查询支付记录
     * @param orderId 订单ID
     * @return 支付记录
     */
    PaymentRecordDO getPaymentByOrderId(Integer orderId);

    /**
     * 根据支付流水号查询支付记录
     * @param payNo 支付流水号
     * @return 支付记录
     */
    PaymentRecordDO getPaymentByPayNo(String payNo);

    /**
     * 根据订单ID查询支付记录列表
     * @param orderId 订单ID
     * @return 支付记录列表
     */
    List<PaymentRecordDO> getPaymentListByOrderId(Integer orderId);

    /**
     * 退款处理
     * @param orderId 订单ID
     * @param remark 退款备注
     * @return 是否成功
     */
    Boolean refund(Integer orderId, String remark);

    /**
     * 查询支付状态
     * @param orderId 订单ID
     * @return 支付状态
     */
    String getPaymentStatus(Integer orderId);
    
    /**
     * 保存支付记录
     * @param paymentData 支付记录数据
     * @return 支付记录ID
     */
    Integer savePaymentRecord(PaymentRecordDO paymentData);
}
