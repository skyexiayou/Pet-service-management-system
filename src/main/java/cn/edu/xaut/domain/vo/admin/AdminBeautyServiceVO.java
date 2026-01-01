package cn.edu.xaut.domain.vo.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 管理员美容服务记录列表VO
 */
@Data
@ApiModel(value = "AdminBeautyServiceVO", description = "管理员美容服务记录列表项")
public class AdminBeautyServiceVO {

    @ApiModelProperty(value = "预约ID")
    private Integer apptId;

    @ApiModelProperty(value = "宠物ID")
    private Integer petId;

    @ApiModelProperty(value = "宠物名称")
    private String petName;

    @ApiModelProperty(value = "宠物主人ID")
    private Integer userId;

    @ApiModelProperty(value = "宠物主人姓名")
    private String userName;

    @ApiModelProperty(value = "门店ID")
    private Integer storeId;

    @ApiModelProperty(value = "门店名称")
    private String storeName;

    @ApiModelProperty(value = "员工ID")
    private Integer empId;

    @ApiModelProperty(value = "负责员工姓名")
    private String empName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "预约时间")
    private Date apptTime;

    @ApiModelProperty(value = "预约状态")
    private String apptStatus;

    @ApiModelProperty(value = "美容项目名称列表")
    private List<String> beautyNames;

    @ApiModelProperty(value = "总价格")
    private BigDecimal totalPrice;
}
