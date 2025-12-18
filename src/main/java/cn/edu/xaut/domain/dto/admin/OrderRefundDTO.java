package cn.edu.xaut.domain.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 订单退款DTO
 * 创建时间：2025-12-18
 */
@Data
@ApiModel(description = "订单退款请求")
public class OrderRefundDTO {

    @ApiModelProperty(value = "退款原因", required = true, example = "客户要求退款")
    @NotBlank(message = "退款原因不能为空")
    private String refundReason;
}
