package cn.edu.xaut.domain.dto.pet;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.util.Date;

/**
 * 宠物DTO
 * @date 2025.12.17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "宠物信息DTO")
public class PetDTO {

    @Schema(description = "用户ID", required = true, example = "1")
    @NotNull(message = "用户ID不能为空")
    private Integer userId;

    @Schema(description = "宠物名称", required = true, example = "旺财")
    @NotNull(message = "宠物名称不能为空")
    private String petName;

    @Schema(description = "宠物品种", example = "金毛")
    private String breed;

    @Schema(description = "宠物性别", example = "male", allowableValues = {"male", "female"})
    private String gender;

    @Schema(description = "出生日期")
    private Date birthDate;

    @Schema(description = "疫苗状态", example = "completed", allowableValues = {"completed", "incomplete", "expired"})
    private String vaccineStatus;

    @Schema(description = "备注信息")
    private String remarks;

    @Schema(description = "宠物多媒体文件路径")
    private String petMediaPath;
}