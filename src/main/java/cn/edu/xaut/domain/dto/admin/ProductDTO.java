package cn.edu.xaut.domain.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 宠物用品DTO
 * @date 2025-12-18
 */
@Data
@ApiModel(description = "宠物用品DTO")
public class ProductDTO {

    @ApiModelProperty(value = "用品名称", required = true, example = "狗粮")
    @NotBlank(message = "用品名称不能为空")
    @Size(max = 100, message = "用品名称长度不能超过100个字符")
    private String productName;

    @ApiModelProperty(value = "用品类型", required = true, example = "食品")
    @NotBlank(message = "用品类型不能为空")
    @Size(max = 50, message = "用品类型长度不能超过50个字符")
    private String productType;

    @ApiModelProperty(value = "供应商", example = "XX宠物用品公司")
    @Size(max = 100, message = "供应商名称长度不能超过100个字符")
    private String supplier;
}
