package cn.edu.xaut.domain.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.util.Date;

/**
 * 请假记录DTO
 * @date 2025-12-19
 */
@Data
@ApiModel(description = "请假记录DTO")
public class LeaveRecordDTO {

    @ApiModelProperty(value = "员工ID", required = true, example = "1")
    @NotNull(message = "员工ID不能为空")
    private Integer empId;

    @ApiModelProperty(value = "门店ID", required = true, example = "1")
    @NotNull(message = "门店ID不能为空")
    private Integer storeId;

    @ApiModelProperty(value = "请假类型（事假/病假/年假）", required = true, example = "病假")
    @NotNull(message = "请假类型不能为空")
    private String leaveType;

    @ApiModelProperty(value = "开始时间", required = true, example = "2025-12-20 09:00:00")
    @NotNull(message = "开始时间不能为空")
    private Date startTime;

    @ApiModelProperty(value = "结束时间", required = true, example = "2025-12-21 18:00:00")
    @NotNull(message = "结束时间不能为空")
    private Date endTime;

    @ApiModelProperty(value = "请假原因", example = "身体不适需要休息")
    private String leaveReason;

    @ApiModelProperty(value = "审批状态（待审批/已通过/已驳回）", example = "待审批")
    private String approveStatus;

    @ApiModelProperty(value = "审批人ID", example = "2")
    private Integer approverId;
}
