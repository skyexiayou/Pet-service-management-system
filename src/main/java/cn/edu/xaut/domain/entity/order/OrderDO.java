package cn.edu.xaut.domain.entity.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("`order`")
@ApiModel(value = "OrderDO", description = "订单表实体类")
public class OrderDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 订单ID */
    @TableId(value = "OrderID", type = IdType.AUTO)
    @ApiModelProperty(value = "订单ID", dataType = "Integer", example = "1")
    private Integer orderId;

    /** 预约ID */
    @TableField("ApptID")
    @ApiModelProperty(value = "预约ID", dataType = "Integer", example = "1")
    private Integer apptId;

    /** 用品关联ID */
    @TableField("RelID")
    @ApiModelProperty(value = "用品关联ID", dataType = "Integer", example = "1")
    private Integer relId;

    /** 订单总金额 */
    @TableField("TotalAmount")
    @ApiModelProperty(value = "订单总金额", dataType = "BigDecimal", example = "500.00")
    private BigDecimal totalAmount;

    /** 支付状态 */
    @TableField("PayStatus")
    @ApiModelProperty(value = "支付状态", dataType = "String", example = "已支付")
    private String payStatus;

    /** 支付方式 */
    @TableField("PayMethod")
    @ApiModelProperty(value = "支付方式", dataType = "String", example = "WECHAT")
    private String payMethod;

    /** 支付时间 */
    @TableField("PayTime")
    @ApiModelProperty(value = "支付时间", dataType = "Date", example = "2025-12-18 14:30:00")
    private Date payTime;

    /** 订单创建时间 */
    @TableField("OrderCreateTime")
    @ApiModelProperty(value = "订单创建时间", dataType = "Date", example = "2025-12-18 14:00:00")
    private Date orderCreateTime;
}
