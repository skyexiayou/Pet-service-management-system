package cn.edu.xaut.domain.vo.medical;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 医疗服务列表项VO
 */
@Data
@ApiModel(value = "MedicalServiceVO", description = "医疗服务列表项")
public class MedicalServiceVO {

    @ApiModelProperty(value = "医疗记录ID")
    private Integer medicalId;

    @ApiModelProperty(value = "预约ID")
    private Integer apptId;

    @ApiModelProperty(value = "宠物ID")
    private Integer petId;

    @ApiModelProperty(value = "宠物名称")
    private String petName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "就诊时间")
    private Date medicalTime;

    @ApiModelProperty(value = "诊断结果")
    private String diagnosis;

    @ApiModelProperty(value = "医疗费用")
    private BigDecimal medicalFee;

    @ApiModelProperty(value = "医生姓名")
    private String empName;

    @ApiModelProperty(value = "门店名称")
    private String storeName;
}
