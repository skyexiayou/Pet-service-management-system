package cn.edu.xaut.domain.dto.foster;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.util.Date;

/**
 * 用户寄养预约DTO
 */
@Data
@ApiModel(value = "FosterAppointmentDTO", description = "用户寄养预约数据传输对象")
public class FosterAppointmentDTO {

    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID", required = true)
    private Integer userId;

    @NotNull(message = "宠物ID不能为空")
    @ApiModelProperty(value = "宠物ID", required = true)
    private Integer petId;

    @NotNull(message = "门店ID不能为空")
    @ApiModelProperty(value = "门店ID", required = true)
    private Integer storeId;

    @ApiModelProperty(value = "员工ID（可选）")
    private Integer empId;

    @NotNull(message = "开始日期不能为空")
    @ApiModelProperty(value = "开始日期", required = true)
    private Date startDate;

    @NotNull(message = "结束日期不能为空")
    @ApiModelProperty(value = "结束日期", required = true)
    private Date endDate;

    @ApiModelProperty(value = "备注")
    private String remarks;
}
