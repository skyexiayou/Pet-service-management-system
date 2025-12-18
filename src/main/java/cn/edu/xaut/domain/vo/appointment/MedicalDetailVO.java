package cn.edu.xaut.domain.vo.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 医疗服务明细VO
 */
@Data
@ApiModel(value = "MedicalDetailVO", description = "医疗服务明细")
public class MedicalDetailVO {

    @ApiModelProperty(value = "医疗记录ID")
    private Integer medicalId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "就诊时间")
    private Date medicalTime;

    @ApiModelProperty(value = "诊断结果")
    private String diagnosis;

    @ApiModelProperty(value = "用药情况")
    private String medication;

    @ApiModelProperty(value = "医疗费用")
    private BigDecimal medicalFee;

    @ApiModelProperty(value = "复诊建议")
    private String followUpAdvice;
}
