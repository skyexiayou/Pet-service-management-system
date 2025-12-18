package cn.edu.xaut.domain.entity.fosterrecord;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 寄养记录表实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("fosterRecord")
@ApiModel(value = "FosterRecordDO", description = "寄养记录表实体类")
public class FosterRecordDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 寄养ID */
    @TableId(value = "FosterID", type = IdType.AUTO)
    @ApiModelProperty(value = "寄养ID", dataType = "Integer", example = "1")
    private Integer fosterId;

    /** 宠物ID */
    @TableField("PetID")
    @ApiModelProperty(value = "宠物ID", dataType = "Integer", example = "1")
    private Integer petId;

    /** 门店ID */
    @TableField("StoreID")
    @ApiModelProperty(value = "门店ID", dataType = "Integer", example = "1")
    private Integer storeId;

    /** 员工ID */
    @TableField("EmpID")
    @ApiModelProperty(value = "员工ID", dataType = "Integer", example = "1")
    private Integer empId;

    /** 开始日期 */
    @TableField("StartDate")
    @ApiModelProperty(value = "开始日期", dataType = "Date", example = "2023-01-01")
    private Date startDate;

    /** 结束日期 */
    @TableField("EndDate")
    @ApiModelProperty(value = "结束日期", dataType = "Date", example = "2023-01-07")
    private Date endDate;

    /** 寄养费用 */
    @TableField("FosterFee")
    @ApiModelProperty(value = "寄养费用", dataType = "BigDecimal", example = "700.00")
    private BigDecimal fosterFee;

    /** 寄养状态 */
    @TableField("FosterStatus")
    @ApiModelProperty(value = "寄养状态", dataType = "String", example = "已完成")
    private String fosterStatus;

    /** 寄养备注 */
    @TableField("FosterRemarks")
    @ApiModelProperty(value = "寄养备注", dataType = "String", example = "需要每天遛弯")
    private String fosterRemarks;

    /** 日常状态 */
    @TableField("DailyStatus")
    @ApiModelProperty(value = "日常状态", dataType = "String", example = "状态良好")
    private String dailyStatus;
}