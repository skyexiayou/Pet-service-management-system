package cn.edu.xaut.domain.vo.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单列表项VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "OrderVO", description = "订单列表项")
public class OrderVO {

    @ApiModelProperty(value = "订单ID", example = "1")
    private Integer orderId;

    @ApiModelProperty(value = "预约ID", example = "1")
    private Integer apptId;

    @ApiModelProperty(value = "订单总金额", example = "500.00")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "支付状态", example = "已支付")
    private String payStatus;

    @ApiModelProperty(value = "支付方式", example = "WECHAT")
    private String payMethod;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "支付时间", example = "2025-12-18 14:30:00")
    private Date payTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "订单创建时间", example = "2025-12-18 14:00:00")
    private Date orderCreateTime;
}
