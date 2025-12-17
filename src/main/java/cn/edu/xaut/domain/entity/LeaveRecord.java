package cn.edu.xaut.domain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class LeaveRecord {
    private Integer leaveId;
    private Integer empId;
    private Integer storeId;
    private String leaveType;
    private Date startTime;
    private Date endTime;
    private Date applyTime;
    private Integer approverId;
    private String approveStatus;
    private Date approveTime;
    private String leaveReason;
}