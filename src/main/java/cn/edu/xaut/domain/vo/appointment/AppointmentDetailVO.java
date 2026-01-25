package cn.edu.xaut.domain.vo.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 预约详情VO
 */
@Data
@ApiModel(value = "AppointmentDetailVO", description = "预约详情")
public class AppointmentDetailVO {

    // 预约基本信息
    @ApiModelProperty(value = "预约ID")
    private Integer apptId;

    @ApiModelProperty(value = "宠物ID")
    private Integer petId;

    @ApiModelProperty(value = "宠物名称")
    private String petName;

    @ApiModelProperty(value = "宠物品种")
    private String breed;

    @ApiModelProperty(value = "宠物性别")
    private String gender;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "宠物出生日期")
    private Date birthDate;

    @ApiModelProperty(value = "宠物年龄")
    private Integer petAge;

    @ApiModelProperty(value = "过敏药物")
    private String allergyDrug;

    @ApiModelProperty(value = "既往病史")
    private String medicalHistory;

    @ApiModelProperty(value = "疫苗接种情况")
    private String vaccineStatus;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "用户电话")
    private String userPhone;

    @ApiModelProperty(value = "门店ID")
    private Integer storeId;

    @ApiModelProperty(value = "门店名称")
    private String storeName;

    @ApiModelProperty(value = "门店地址")
    private String storeAddress;

    @ApiModelProperty(value = "门店电话")
    private String storePhone;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "预约时间")
    private Date apptTime;

    @ApiModelProperty(value = "预约状态")
    private String apptStatus;

    @ApiModelProperty(value = "诊断状态")
    private String diagnoseStatus;

    @ApiModelProperty(value = "诊断描述")
    private String diagnoseDesc;

    @ApiModelProperty(value = "员工ID")
    private Integer empId;

    @ApiModelProperty(value = "员工姓名")
    private String empName;

    @ApiModelProperty(value = "员工电话")
    private String empPhone;

    @ApiModelProperty(value = "员工岗位")
    private String empPosition;

    @ApiModelProperty(value = "服务类型")
    private String serviceType;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    // 服务明细
    @ApiModelProperty(value = "医疗服务明细")
    private MedicalDetailVO medicalService;
}
