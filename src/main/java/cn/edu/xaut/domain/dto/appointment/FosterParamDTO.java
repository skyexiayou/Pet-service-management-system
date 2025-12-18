package cn.edu.xaut.domain.dto.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

/**
 * 寄养服务参数DTO
 */
@Data
@ApiModel(value = "FosterParamDTO", description = "寄养服务参数")
public class FosterParamDTO {

    @NotNull(message = "寄养开始日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "寄养开始日期", required = true, example = "2025-12-25")
    private Date startDate;

    @NotNull(message = "寄养结束日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "寄养结束日期", required = true, example = "2025-12-30")
    private Date endDate;

    @ApiModelProperty(value = "寄养备注", example = "每日喂食2次")
    private String fosterRemarks;
}
