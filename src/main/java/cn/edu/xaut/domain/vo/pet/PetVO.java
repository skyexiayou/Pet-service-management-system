package cn.edu.xaut.domain.vo.pet;

import cn.edu.xaut.domain.vo.user.UserVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 宠物信息VO
 * @date 2025.12.17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "宠物信息VO")
public class PetVO {

    @Schema(description = "宠物ID", example = "1")
    private Integer petId;

    @Schema(description = "宠物名称", example = "旺财")
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

    @Schema(description = "所属用户")
    private UserVO user;
}