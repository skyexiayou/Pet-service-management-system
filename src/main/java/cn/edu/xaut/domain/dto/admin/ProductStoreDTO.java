package cn.edu.xaut.domain.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品库存DTO
 *
 * @date 2025-12-18
 */
@Data
@ApiModel(description = "商品库存DTO")
public class ProductStoreDTO {

    @ApiModelProperty(value = "商品ID", required = true, example = "1")
    @NotNull(message = "商品ID不能为空")
    private Integer productId;

    @ApiModelProperty(value = "售价", required = true, example = "180.00")
    @NotNull(message = "售价不能为空")
    @DecimalMin(value = "0.01", message = "售价必须大于0")
    private BigDecimal price;

    @ApiModelProperty(value = "库存数量", required = true, example = "100")
    @NotNull(message = "库存数量不能为空")
    @Min(value = 0, message = "库存数量不能为负数")
    private Integer storeStock;

    @ApiModelProperty(value = "上架状态", required = true, example = "在售", notes = "支持：在售/下架/缺货")
    @NotBlank(message = "上架状态不能为空")
    @Pattern(regexp = "^(在售|下架|缺货)$", message = "上架状态必须为'在售'、'下架'或'缺货'")
    private String shelfStatus;
}