package cn.edu.xaut.domain.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 管理员创建美容服务预约DTO
 */
@Data
@ApiModel(value = "AdminBeautyAppointmentDTO", description = "管理员美容服务预约数据传输对象")
public class AdminBeautyAppointmentDTO {

    @NotNull(message = "宠物ID不能为空")
    @ApiModelProperty(value = "宠物ID", required = true)
    private Integer petId;

    @NotNull(message = "门店ID不能为空")
    @ApiModelProperty(value = "门店ID", required = true)
    private Integer storeId;

    @ApiModelProperty(value = "员工ID")
    private Integer empId;

    @NotNull(message = "预约时间不能为空")
    @ApiModelProperty(value = "预约时间", required = true)
    private Date apptTime;

    @NotNull(message = "美容项目不能为空")
    @ApiModelProperty(value = "美容项目ID列表", required = true)
    private List<Integer> beautyIds;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "预约状态")
    private String apptStatus;
}
