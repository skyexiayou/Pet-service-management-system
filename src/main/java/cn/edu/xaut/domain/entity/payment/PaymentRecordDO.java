package cn.edu.xaut.domain.entity.payment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 支付记录实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("payment_record")
@Schema(description = "支付记录实体类")
public class PaymentRecordDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 支付ID */
    @TableId(value = "PayID", type = IdType.AUTO)
    @Schema(description = "支付ID", example = "1")
    private Integer paymentId;

    /** 订单ID */
    @TableField("OrderID")
    @Schema(description = "订单ID", example = "1")
    private Integer orderId;

    /** 支付流水号 */
    @TableField("PayNo")
    @Schema(description = "支付流水号", example = "PAY2023010112345678")
    private String payNo;

    /** 支付金额 */
    @TableField("PayAmount")
    @Schema(description = "支付金额", example = "200.00")
    private BigDecimal payAmount;

    /** 支付方式（微信/支付宝/银行卡） */
    @TableField("PayType")
    @Schema(description = "支付方式", example = "微信")
    private String payType;

    /** 支付状态（待支付/已支付/支付失败/已退款） */
    @TableField("PayStatus")
    @Schema(description = "支付状态", example = "待支付")
    private String payStatus;

    /** 支付时间 */
    @TableField("PayTime")
    @Schema(description = "支付时间", example = "2023-01-01 10:30:00")
    private LocalDateTime payTime;

    /** 备注 */
    @TableField("Remark")
    @Schema(description = "备注", example = "支付成功")
    private String remark;

    // ========== 非数据库字段，用于联表查询 ==========

    /** 订单编号 */
    @TableField(exist = false)
    @Schema(description = "订单编号")
    private String orderNo;
}
