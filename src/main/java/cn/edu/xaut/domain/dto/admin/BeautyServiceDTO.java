package cn.edu.xaut.domain.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 美容服务DTO
 * @date 2025-12-18
 */
@Data
@ApiModel(description = "美容服务DTO")
public class BeautyServiceDTO {

    @ApiModelProperty(value = "服务名称", required = true, example = "全身清洁")
    @NotBlank(message = "服务名称不能为空")
    @Size(max = 100, message = "服务名称长度不能超过100个字符")
    private String beautyName;

    @ApiModelProperty(value = "服务类型", required = true, example = "美容")
    @NotBlank(message = "服务类型不能为空")
    @Size(max = 50, message = "服务类型长度不能超过50个字符")
    private String beautyType;

    @ApiModelProperty(value = "价格", required = true, example = "80.00")
    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.01", message = "价格必须大于0")
    private BigDecimal price;

    @ApiModelProperty(value = "时长（分钟）", example = "60")
    @Min(value = 1, message = "时长必须大于0")
    private Integer duration;

    @ApiModelProperty(value = "服务说明", example = "包含洗澡、吹干、梳理毛发")
    private String beautyRemarks;
}
