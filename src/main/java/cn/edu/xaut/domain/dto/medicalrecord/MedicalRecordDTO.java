package cn.edu.xaut.domain.dto.medicalrecord;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 医疗记录DTO - 用于创建/更新操作
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "MedicalRecordDTO", description = "医疗记录数据传输对象")
public class MedicalRecordDTO {

    @NotNull(message = "宠物ID不能为空")
    @ApiModelProperty(value = "宠物ID", required = true, example = "1")
    private Integer petId;

    @NotNull(message = "医生ID不能为空")
    @ApiModelProperty(value = "医生ID", required = true, example = "1")
    private Integer empId;

    @NotNull(message = "门店ID不能为空")
    @ApiModelProperty(value = "门店ID", required = true, example = "1")
    private Integer storeId;

    @NotNull(message = "就诊时间不能为空")
    @ApiModelProperty(value = "就诊时间", required = true, example = "2025-01-15 10:00:00")
    private Date medicalTime;

    @NotBlank(message = "诊断结果不能为空")
    @ApiModelProperty(value = "诊断结果", required = true, example = "感冒发烧")
    private String diagnosis;

    @ApiModelProperty(value = "用药情况", example = "阿莫西林 每日2次")
    private String medication;

    @NotNull(message = "医疗费用不能为空")
    @DecimalMin(value = "0.00", message = "医疗费用不能为负数")
    @ApiModelProperty(value = "医疗费用", required = true, example = "200.00")
    private BigDecimal medicalFee;

    @ApiModelProperty(value = "复诊建议", example = "一周后复查")
    private String followUpAdvice;

    @ApiModelProperty(value = "处方ID", example = "1")
    private Integer prescriptionId;

    @ApiModelProperty(value = "药品费用", example = "100.00")
    private BigDecimal drugTotalPrice;

    @ApiModelProperty(value = "用药说明", example = "每日两次，每次一片")
    private String drugUsage;

    @ApiModelProperty(value = "用药禁忌", example = "避免与XX药物同时使用")
    private String drugTaboo;

    @ApiModelProperty(value = "状态（待就诊/已完成/已取消）- 注意：数据库可能不支持此字段", example = "待就诊")
    private String status;
}
