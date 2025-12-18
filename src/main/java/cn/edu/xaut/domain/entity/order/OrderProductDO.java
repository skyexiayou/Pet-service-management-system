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
 * 订单用品表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("orderProduct")
@ApiModel(value = "OrderProductDO", description = "订单用品表实体类")
public class OrderProductDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 用品订单ID */
    @TableId(value = "OrderProductID", type = IdType.AUTO)
    @ApiModelProperty(value = "用品订单ID", dataType = "Integer", example = "1")
    private Integer orderProductId;

    /** 订单ID */
    @TableField("OrderID")
    @ApiModelProperty(value = "订单ID", dataType = "Integer", example = "1")
    private Integer orderId;

    /** 用品关联ID */
    @TableField("RelID")
    @ApiModelProperty(value = "用品关联ID", dataType = "Integer", example = "1")
    private Integer relId;

    /** 购买数量 */
    @TableField("Quantity")
    @ApiModelProperty(value = "购买数量", dataType = "Integer", example = "2")
    private Integer quantity;

    /** 实际成交单价 */
    @TableField("ActualPrice")
    @ApiModelProperty(value = "实际成交单价", dataType = "BigDecimal", example = "180.00")
    private BigDecimal actualPrice;

    /** 用品小计 */
    @TableField("Subtotal")
    @ApiModelProperty(value = "用品小计", dataType = "BigDecimal", example = "360.00")
    private BigDecimal subtotal;

    /** 创建时间 */
    @TableField("CreateTime")
    @ApiModelProperty(value = "创建时间", dataType = "Date", example = "2025-12-18 14:00:00")
    private Date createTime;
}
