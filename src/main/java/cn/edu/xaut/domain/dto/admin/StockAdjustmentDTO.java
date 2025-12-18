package cn.edu.xaut.domain.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 库存调整DTO
 * @date 2025-12-18
 */
@Data
@ApiModel(description = "库存调整DTO")
public class StockAdjustmentDTO {

    @ApiModelProperty(value = "调整类型", required = true, example = "ADD", notes = "支持：ADD（补货）/REDUCE（减库存）")
    @NotBlank(message = "调整类型不能为空")
    @Pattern(regexp = "^(ADD|REDUCE)$", message = "调整类型必须为ADD或REDUCE")
    private String adjustmentType;

    @ApiModelProperty(value = "调整数量", required = true, example = "10")
    @NotNull(message = "调整数量不能为空")
    @Min(value = 1, message = "调整数量必须大于0")
    private Integer quantity;
}
