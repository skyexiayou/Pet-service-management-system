package cn.edu.xaut.domain.dto.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用品购买项DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ProductPurchaseDTO", description = "用品购买项")
public class ProductPurchaseDTO {

    @NotNull(message = "用品关联ID不能为空")
    @ApiModelProperty(value = "用品关联ID（PetProductStore.RelID）", required = true, example = "1")
    private Integer relId;

    @NotNull(message = "购买数量不能为空")
    @Min(value = 1, message = "购买数量至少为1")
    @ApiModelProperty(value = "购买数量", required = true, example = "2")
    private Integer quantity;
}
