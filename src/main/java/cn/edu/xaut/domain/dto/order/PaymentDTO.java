package cn.edu.xaut.domain.dto.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 支付请求DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "PaymentDTO", description = "支付请求")
public class PaymentDTO {

    @NotBlank(message = "支付方式不能为空")
    @Pattern(regexp = "微信支付|支付宝|现金", message = "支付方式必须为微信支付、支付宝或现金")
    @ApiModelProperty(value = "支付方式（微信支付/支付宝/现金）", required = true, example = "微信支付")
    private String payMethod;
}
