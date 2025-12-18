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

    @ApiModelProperty(value = "员工姓名")
    private String empName;

    @ApiModelProperty(value = "员工岗位")
    private String empPosition;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    // 服务明细
    @ApiModelProperty(value = "美容服务明细")
    private List<BeautyDetailVO> beautyServices;

    @ApiModelProperty(value = "寄养服务明细")
    private FosterDetailVO fosterService;

    @ApiModelProperty(value = "医疗服务明细")
    private MedicalDetailVO medicalService;
}
