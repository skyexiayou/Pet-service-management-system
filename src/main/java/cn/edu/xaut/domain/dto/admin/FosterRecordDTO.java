package cn.edu.xaut.domain.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 管理员创建/更新寄养记录DTO
 */
@Data
@ApiModel(value = "FosterRecordDTO", description = "管理员寄养记录数据传输对象")
public class FosterRecordDTO {

    @NotNull(message = "宠物ID不能为空")
    @ApiModelProperty(value = "宠物ID", required = true)
    private Integer petId;

    @NotNull(message = "门店ID不能为空")
    @ApiModelProperty(value = "门店ID", required = true)
    private Integer storeId;

    @ApiModelProperty(value = "员工ID")
    private Integer empId;

    @NotNull(message = "开始日期不能为空")
    @ApiModelProperty(value = "开始日期", required = true)
    private Date startDate;

    @NotNull(message = "结束日期不能为空")
    @ApiModelProperty(value = "结束日期", required = true)
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
