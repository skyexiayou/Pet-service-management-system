package cn.edu.xaut.domain.entity.leaverecord;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema; // 替换旧版Swagger2注解
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 请假记录实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("leaveRecord")
@Schema(name = "LeaveRecordDO", description = "请假记录实体类") // 替换@ApiModel
public class LeaveRecordDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 请假ID */
    @TableId(value = "LeaveID", type = IdType.AUTO)
    @Schema(description = "请假ID", type = "Integer", example = "1") // 替换@ApiModelProperty
    private Integer leaveId;

    /** 员工ID */
    @TableField("EmpID")
    @Schema(description = "员工ID", type = "Integer", example = "1")
    private Integer empId;

    /** 门店ID */
    @TableField("StoreID")
    @Schema(description = "门店ID", type = "Integer", example = "1")
    private Integer storeId;

    /** 请假类型 */
    @TableField("LeaveType")
    @Schema(description = "请假类型", type = "String", example = "事假")
    private String leaveType;

    /** 开始时间 */
    @TableField("StartTime")
    @Schema(description = "开始时间", type = "string", format = "date", example = "2023-01-01")
    private Date startTime;

    /** 结束时间 */
    @TableField("EndTime")
    @Schema(description = "结束时间", type = "string", format = "date", example = "2023-01-03")
    private Date endTime;

    /** 申请时间 */
    @TableField("ApplyTime")
    @Schema(description = "申请时间", type = "string", format = "date-time", example = "2022-12-30 10:00:00")
    private Date applyTime;

    /** 审批人ID */
    @TableField("ApproverID")
    @Schema(description = "审批人ID", type = "Integer", example = "2")
    private Integer approverId;

    /** 审批状态 */
    @TableField("ApproveStatus")
    @Schema(description = "审批状态", type = "String", example = "已批准")
    private String approveStatus;

    /** 审批时间 */
    @TableField("ApproveTime")
    @Schema(description = "审批时间", type = "string", format = "date-time", example = "2022-12-30 14:00:00")
    private Date approveTime;

    /** 请假原因 */
    @TableField("LeaveReason")
    @Schema(description = "请假原因", type = "String", example = "身体不适")
    private String leaveReason;
}