package cn.edu.xaut.domain.dto.medicalrecord;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

/**
 * 医疗预约DTO - 用于用户预约医疗服务
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "MedicalAppointmentDTO", description = "医疗预约数据传输对象")
public class MedicalAppointmentDTO {

    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID", required = true, example = "1")
    private Integer userId;

    @NotNull(message = "宠物ID不能为空")
    @ApiModelProperty(value = "宠物ID", required = true, example = "1")
    private Integer petId;

    @NotNull(message = "门店ID不能为空")
    @ApiModelProperty(value = "门店ID", required = true, example = "1")
    private Integer storeId;

    @NotNull(message = "医生ID不能为空")
    @ApiModelProperty(value = "医生ID", required = true, example = "1")
    private Integer empId;

    @NotNull(message = "预约时间不能为空")
    @ApiModelProperty(value = "预约时间", required = true, example = "2025-01-20 14:00:00")
    private Date appointmentTime;

    @ApiModelProperty(value = "症状描述", example = "宠物最近食欲不振，精神萎靡")
    private String symptoms;
}
