package cn.edu.xaut.domain.vo.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 寄养服务明细VO
 */
@Data
@ApiModel(value = "FosterDetailVO", description = "寄养服务明细")
public class FosterDetailVO {

    @ApiModelProperty(value = "寄养ID")
    private Integer fosterId;

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
}
