package cn.edu.xaut.domain.vo.beauty;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 美容服务列表项VO
 */
@Data
@ApiModel(value = "BeautyServiceVO", description = "美容服务列表项")
public class BeautyServiceVO {

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

    @ApiModelProperty(value = "美容项目名称列表")
    private List<String> beautyNames;

    @ApiModelProperty(value = "总价格")
    private BigDecimal totalPrice;
}
