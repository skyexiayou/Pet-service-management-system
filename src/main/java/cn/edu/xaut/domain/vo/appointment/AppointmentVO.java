package cn.edu.xaut.domain.vo.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 预约列表项VO
 */
@Data
@ApiModel(value = "AppointmentVO", description = "预约列表项")
public class AppointmentVO {

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "预约时间")
    private Date apptTime;

    @ApiModelProperty(value = "预约状态")
    private String apptStatus;

    @ApiModelProperty(value = "员工姓名")
    private String empName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
