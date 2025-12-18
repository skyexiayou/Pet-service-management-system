package cn.edu.xaut.domain.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 请假审批DTO
 * @date 2025-12-18
 */
@Data
@ApiModel(description = "请假审批DTO")
public class LeaveApprovalDTO {

    @ApiModelProperty(value = "审批状态", required = true, example = "已通过", notes = "支持：已通过/已驳回")
    @NotBlank(message = "审批状态不能为空")
    @Pattern(regexp = "^(已通过|已驳回)$", message = "审批状态必须为'已通过'或'已驳回'")
    private String approveStatus;

    @ApiModelProperty(value = "驳回原因", example = "请假理由不充分")
    private String rejectReason;
}
