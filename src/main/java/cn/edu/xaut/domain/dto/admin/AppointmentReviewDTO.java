package cn.edu.xaut.domain.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 预约审核DTO
 * 创建时间：2025-12-18
 */
@Data
@ApiModel(description = "预约审核请求")
public class AppointmentReviewDTO {

    @ApiModelProperty(value = "是否通过审核", required = true, example = "true")
    @NotNull(message = "审核结果不能为空")
    private Boolean approved;

    @ApiModelProperty(value = "分配的员工ID（审核通过时可选）", example = "1")
    private Integer empId;

    @ApiModelProperty(value = "驳回原因（审核不通过时必填）", example = "预约时间冲突")
    private String rejectReason;
}
