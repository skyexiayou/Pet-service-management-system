package cn.edu.xaut.domain.dto.beauty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 用户美容预约DTO
 */
@Data
@ApiModel(value = "BeautyAppointmentDTO", description = "用户美容预约数据传输对象")
public class BeautyAppointmentDTO {

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

    @NotNull(message = "预约时间不能为空")
    @ApiModelProperty(value = "预约时间", required = true)
    private Date apptTime;

    @NotNull(message = "美容项目不能为空")
    @ApiModelProperty(value = "美容项目ID列表", required = true)
    private List<Integer> beautyIds;

    @ApiModelProperty(value = "备注")
    private String remarks;
}
