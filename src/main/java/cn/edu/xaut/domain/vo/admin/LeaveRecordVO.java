package cn.edu.xaut.domain.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 请假记录VO
 * @date 2025-12-19
 */
@Data
@ApiModel(description = "请假记录VO")
public class LeaveRecordVO {

    @ApiModelProperty(value = "请假ID", example = "1")
    private Integer leaveId;

    @ApiModelProperty(value = "员工ID", example = "1")
    private Integer empId;

    @ApiModelProperty(value = "员工姓名", example = "张三")
    private String empName;

    @ApiModelProperty(value = "员工电话", example = "13800138000")
    private String empPhone;

    @ApiModelProperty(value = "员工岗位", example = "美容师")
    private String position;

    @ApiModelProperty(value = "门店ID", example = "1")
    private Integer storeId;

    @ApiModelProperty(value = "门店名称", example = "宠物乐园总店")
    private String storeName;

    @ApiModelProperty(value = "请假类型", example = "病假")
    private String leaveType;

    @ApiModelProperty(value = "开始时间", example = "2025-12-20 09:00:00")
    private Date startTime;

    @ApiModelProperty(value = "结束时间", example = "2025-12-21 18:00:00")
    private Date endTime;

    @ApiModelProperty(value = "申请时间", example = "2025-12-19 10:00:00")
    private Date applyTime;

    @ApiModelProperty(value = "审批人ID", example = "2")
    private Integer approverId;

    @ApiModelProperty(value = "审批人姓名", example = "李四")
    private String approverName;

    @ApiModelProperty(value = "审批状态", example = "待审批")
    private String approveStatus;

    @ApiModelProperty(value = "审批时间", example = "2025-12-19 14:00:00")
    private Date approveTime;

    @ApiModelProperty(value = "请假原因", example = "身体不适需要休息")
    private String leaveReason;
}
