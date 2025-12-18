package cn.edu.xaut.domain.vo.foster;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 寄养服务详情VO
 */
@Data
@ApiModel(value = "FosterServiceDetailVO", description = "寄养服务详情")
public class FosterServiceDetailVO {

    @ApiModelProperty(value = "寄养ID")
    private Integer fosterId;

    @ApiModelProperty(value = "预约ID")
    private Integer apptId;

    @ApiModelProperty(value = "宠物ID")
    private Integer petId;

    @ApiModelProperty(value = "宠物名称")
    private String petName;

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

    @ApiModelProperty(value = "每日状态记录")
    private String dailyStatus;

    @ApiModelProperty(value = "门店名称")
    private String storeName;

    @ApiModelProperty(value = "门店地址")
    private String storeAddress;

    @ApiModelProperty(value = "员工姓名")
    private String empName;
}
