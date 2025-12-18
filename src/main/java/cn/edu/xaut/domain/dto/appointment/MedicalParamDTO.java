package cn.edu.xaut.domain.dto.appointment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 医疗服务参数DTO
 */
@Data
@ApiModel(value = "MedicalParamDTO", description = "医疗服务参数")
public class MedicalParamDTO {

    @NotBlank(message = "症状描述不能为空")
    @ApiModelProperty(value = "症状描述", required = true, example = "咳嗽、精神差")
    private String symptom;
}
