package cn.edu.xaut.domain.dto.medical;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 医疗服务DTO
 * @date 2025.12.18
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "医疗服务DTO")
public class MedicalDTO {

    @Schema(description = "医疗服务名称", required = true, example = "宠物疫苗接种")
    @NotNull(message = "医疗服务名称不能为空")
    private String medName;

    @Schema(description = "医疗服务类型", example = "疫苗", allowableValues = {"疫苗", "体检", "治疗"})
    private String medType;

    @Schema(description = "医疗服务价格", required = true, example = "150.00")
    @NotNull(message = "医疗服务价格不能为空")
    @Min(value = 0, message = "医疗服务价格不能为负数")
    private BigDecimal price;

    @Schema(description = "服务时长（分钟）", example = "30")
    @Min(value = 1, message = "服务时长不能小于1分钟")
    private Integer duration;

    @Schema(description = "服务说明")
    private String medRemarks;
}