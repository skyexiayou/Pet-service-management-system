package cn.edu.xaut.domain.vo.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 管理员美容服务记录详情VO
 */
@Data
@ApiModel(value = "AdminBeautyServiceDetailVO", description = "管理员美容服务记录详情")
public class AdminBeautyServiceDetailVO {

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

    @ApiModelProperty(value = "宠物主人ID")
    private Integer userId;

    @ApiModelProperty(value = "宠物主人姓名")
    private String userName;

    @ApiModelProperty(value = "宠物主人电话")
    private String userPhone;

    @ApiModelProperty(value = "门店ID")
    private Integer storeId;

    @ApiModelProperty(value = "门店名称")
    private String storeName;

    @ApiModelProperty(value = "门店地址")
    private String storeAddress;

    @ApiModelProperty(value = "门店电话")
    private String storePhone;

    @ApiModelProperty(value = "员工ID")
    private Integer empId;

    @ApiModelProperty(value = "负责员工姓名")
    private String empName;

    @ApiModelProperty(value = "员工电话")
    private String empPhone;

    @ApiModelProperty(value = "员工职位")
    private String empPosition;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "预约时间")
    private Date apptTime;

    @ApiModelProperty(value = "预约状态")
    private String apptStatus;

    @ApiModelProperty(value = "备注")
    private String remarks;


    @ApiModelProperty(value = "总价格")
    private BigDecimal totalPrice;
}
