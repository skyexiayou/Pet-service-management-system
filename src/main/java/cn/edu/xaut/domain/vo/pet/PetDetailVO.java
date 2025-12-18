package cn.edu.xaut.domain.vo.pet;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 宠物档案详情VO
 * @date 2025.12.18
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "宠物档案详情VO")
public class PetDetailVO {

    @Schema(description = "宠物ID", example = "1")
    private Integer petId;

    @Schema(description = "宠物名称", example = "旺财")
    private String petName;

    @Schema(description = "宠物品种", example = "金毛")
    private String breed;

    @Schema(description = "宠物性别", example = "M")
    private String gender;

    @Schema(description = "出生日期")
    private Date birthDate;

    @Schema(description = "疫苗状态", example = "已接种")
    private String vaccineStatus;

    @Schema(description = "备注信息")
    private String remarks;

    @Schema(description = "宠物多媒体文件路径")
    private String petMediaPath;

    @Schema(description = "用户ID", example = "1")
    private Integer userId;

    @Schema(description = "服务记录列表")
    private List<ServiceRecordVO> relatedServiceRecords;
}