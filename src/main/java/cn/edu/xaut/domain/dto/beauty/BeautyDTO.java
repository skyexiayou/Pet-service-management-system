package cn.edu.xaut.domain.dto.beauty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 美容项目DTO
 * @date 2025.12.18
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "美容项目DTO")
public class BeautyDTO {

    @Schema(description = "美容项目名称", required = true, example = "宠物洗澡")
    @NotNull(message = "美容项目名称不能为空")
    private String beautyName;

    @Schema(description = "美容项目类型", example = "美容", allowableValues = {"美容", "洗护", "造型"})
    private String beautyType;

    @Schema(description = "美容项目价格", required = true, example = "100.00")
    @NotNull(message = "美容项目价格不能为空")
    @Min(value = 0, message = "美容项目价格不能为负数")
    private BigDecimal price;

    @Schema(description = "服务时长（分钟）", example = "60")
    @Min(value = 1, message = "服务时长不能小于1分钟")
    private Integer duration;

    @Schema(description = "服务说明")
    private String beautyRemarks;
}