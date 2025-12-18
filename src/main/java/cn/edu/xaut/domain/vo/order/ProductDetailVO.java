package cn.edu.xaut.domain.vo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 用品明细VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ProductDetailVO", description = "用品明细")
public class ProductDetailVO {

    @ApiModelProperty(value = "用品名称", example = "狗粮")
    private String productName;

    @ApiModelProperty(value = "用品类型", example = "食品")
    private String productType;

    @ApiModelProperty(value = "购买数量", example = "2")
    private Integer quantity;

    @ApiModelProperty(value = "实际单价", example = "180.00")
    private BigDecimal actualPrice;

    @ApiModelProperty(value = "小计", example = "360.00")
    private BigDecimal subtotal;
}
