package cn.edu.xaut.domain.vo.beauty;

import cn.edu.xaut.domain.vo.appointment.BeautyDetailVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 美容服务详情VO
 */
@Data
@ApiModel(value = "BeautyServiceDetailVO", description = "美容服务详情")
public class BeautyServiceDetailVO {

    @ApiModelProperty(value = "预约ID")
    private Integer apptId;

    @ApiModelProperty(value = "宠物ID")
    private Integer petId;

    @ApiModelProperty(value = "宠物名称")
    private String petName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "预约时间")
    private Date apptTime;

    @ApiModelProperty(value = "预约状态")
    private String apptStatus;

    @ApiModelProperty(value = "门店名称")
    private String storeName;

    @ApiModelProperty(value = "门店地址")
    private String storeAddress;

    @ApiModelProperty(value = "员工姓名")
    private String empName;

    @ApiModelProperty(value = "美容服务明细列表")
    private List<BeautyDetailVO> beautyDetails;
}
