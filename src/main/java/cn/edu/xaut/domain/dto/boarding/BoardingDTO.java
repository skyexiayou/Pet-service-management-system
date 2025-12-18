package cn.edu.xaut.domain.dto.boarding;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 寄养服务DTO
 * @date 2025.12.18
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "寄养服务DTO")
public class BoardingDTO {

    @Schema(description = "寄养服务名称", required = true, example = "宠物寄养")
    @NotNull(message = "寄养服务名称不能为空")
    private String boardingName;

    @Schema(description = "寄养服务类型", example = "标准寄养", allowableValues = {"标准寄养", "豪华寄养", "家庭寄养"})
    private String boardingType;

    @Schema(description = "寄养服务价格（每天）", required = true, example = "80.00")
    @NotNull(message = "寄养服务价格不能为空")
    @Min(value = 0, message = "寄养服务价格不能为负数")
    private BigDecimal price;

    @Schema(description = "服务说明")
    private String boardingRemarks;
}