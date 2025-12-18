package cn.edu.xaut.domain.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 医疗记录创建DTO
 * 创建时间：2025-12-18
 */
@Data
@ApiModel(description = "医疗记录创建请求")
public class MedicalRecordCreateDTO {

    @ApiModelProperty(value = "预约ID", required = true, example = "1")
    @NotNull(message = "预约ID不能为空")
    private Integer apptId;

    @ApiModelProperty(value = "诊断结果", required = true, example = "感冒，轻度发烧")
    @NotBlank(message = "诊断结果不能为空")
    private String diagnosis;

    @ApiModelProperty(value = "用药情况", example = "阿莫西林 100mg，每日2次")
    private String medication;

    @ApiModelProperty(value = "医疗费用", required = true, example = "120.00")
    @NotNull(message = "医疗费用不能为空")
    private BigDecimal medicalFee;

    @ApiModelProperty(value = "复诊建议", example = "注意保暖，3天后复诊")
    private String followUpAdvice;
}
