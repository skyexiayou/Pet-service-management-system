package cn.edu.xaut.domain.dto.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 预约创建DTO
 */
@Data
@ApiModel(value = "AppointmentCreateDTO", description = "预约创建请求")
public class AppointmentCreateDTO {

    @NotNull(message = "宠物ID不能为空")
    @ApiModelProperty(value = "宠物ID", required = true, example = "1")
    private Integer petId;

    @NotNull(message = "门店ID不能为空")
    @ApiModelProperty(value = "门店ID", required = true, example = "1")
    private Integer storeId;

    @NotNull(message = "预约时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "预约时间", required = true, example = "2025-12-25 10:00:00")
    private Date apptTime;

    @ApiModelProperty(value = "员工ID（可选）", example = "1")
    private Integer empId;

    @ApiModelProperty(value = "美容服务ID列表（可选）", example = "[1, 3]")
    private List<Integer> beautyIds;

    @ApiModelProperty(value = "寄养服务参数（可选）")
    private FosterParamDTO fosterParam;

    @ApiModelProperty(value = "医疗服务参数（可选）")
    private MedicalParamDTO medicalParam;
}
