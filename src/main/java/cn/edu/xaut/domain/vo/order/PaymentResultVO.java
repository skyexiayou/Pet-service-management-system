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
 * 支付结果VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "支付结果VO", description = "支付结果返回对象")
public class PaymentResultVO {

    @ApiModelProperty(value = "支付凭证号", example = "PAY_202512180001")
    private String paymentVoucher;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "支付时间", example = "2025-12-18 14:30:00")
    private Date payTime;

    @ApiModelProperty(value = "支付方式", example = "微信支付")
    private String payMethod;

    @ApiModelProperty(value = "支付总金额", example = "500.00")
    private BigDecimal totalAmount;
}