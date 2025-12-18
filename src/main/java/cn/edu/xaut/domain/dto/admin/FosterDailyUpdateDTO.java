package cn.edu.xaut.domain.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 寄养每日状态更新DTO
 * 创建时间：2025-12-18
 */
@Data
@ApiModel(description = "寄养每日状态更新请求")
public class FosterDailyUpdateDTO {

    @ApiModelProperty(value = "每日状态记录", required = true, example = "2025-12-18：宠物状态良好，食欲正常")
    @NotBlank(message = "每日状态记录不能为空")
    private String dailyStatus;
}
