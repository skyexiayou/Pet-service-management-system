package cn.edu.xaut.domain.vo.medical;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 医疗服务详情VO
 */
@Data
@ApiModel(value = "MedicalServiceDetailVO", description = "医疗服务详情")
public class MedicalServiceDetailVO {

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

    @ApiModelProperty(value = "用药情况")
    private String medication;

    @ApiModelProperty(value = "医疗费用")
    private BigDecimal medicalFee;

    @ApiModelProperty(value = "复诊建议")
    private String followUpAdvice;

    @ApiModelProperty(value = "医生姓名")
    private String empName;

    @ApiModelProperty(value = "医生岗位")
    private String empPosition;

    @ApiModelProperty(value = "门店名称")
    private String storeName;

    @ApiModelProperty(value = "门店地址")
    private String storeAddress;
}
