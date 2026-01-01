package cn.edu.xaut.domain.vo.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 管理员寄养记录详情VO
 */
@Data
@ApiModel(value = "AdminFosterRecordDetailVO", description = "管理员寄养记录详情")
public class AdminFosterRecordDetailVO {

    @ApiModelProperty(value = "寄养ID")
    private Integer fosterId;

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

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "开始日期")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "结束日期")
    private Date endDate;

    @ApiModelProperty(value = "寄养费用")
    private BigDecimal fosterFee;

    @ApiModelProperty(value = "寄养状态")
    private String fosterStatus;

    @ApiModelProperty(value = "寄养备注")
    private String fosterRemarks;

    @ApiModelProperty(value = "日常状态")
    private String dailyStatus;
}
