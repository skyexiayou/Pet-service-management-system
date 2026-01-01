package cn.edu.xaut.service.admin;

import cn.edu.xaut.domain.dto.admin.LeaveRecordDTO;
import cn.edu.xaut.domain.dto.admin.ReportQueryDTO;
import cn.edu.xaut.domain.vo.PageResultVO;
import cn.edu.xaut.domain.vo.admin.LeaveRecordVO;
import cn.edu.xaut.domain.vo.admin.MonthlyReportSummaryVO;

/**
 * 管理员请假记录服务接口
 * @date 2025-12-19
 */
public interface AdminLeaveRecordService {

    /**
     * 分页查询所有请假记录
     *
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param approveStatus 审批状态（可选）
     * @param leaveType 请假类型（可选）
     * @param empName 员工姓名（可选，模糊查询）
     * @return 请假记录分页结果
     */
    PageResultVO<LeaveRecordVO> getAllLeaveRecords(Integer pageNum, Integer pageSize, 
                                                    String approveStatus, String leaveType, String empName);

    /**
     * 根据ID获取请假记录详情
     *
     * @param leaveId 请假记录ID
     * @return 请假记录详情
     */
    LeaveRecordVO getLeaveRecordById(Integer leaveId);

    /**
     * 创建请假记录
     *
     * @param dto 请假记录DTO
     * @return 新创建的请假记录ID
     */
    Integer createLeaveRecord(LeaveRecordDTO dto);

    /**
     * 更新请假记录
     *
     * @param leaveId 请假记录ID
     * @param dto 请假记录DTO
     * @return 更新结果
     */
    Integer updateLeaveRecord(Integer leaveId, LeaveRecordDTO dto);

    /**
     * 删除请假记录
     *
     * @param leaveId 请假记录ID
     * @return 删除结果
     */
    Integer deleteLeaveRecord(Integer leaveId);

    /**
     * 审批请假记录
     *
     * @param leaveId 请假记录ID
     * @param approveStatus 审批状态（已通过/已驳回）
     * @param approverId 审批人ID
     * @return 审批结果
     */
    Integer approveLeaveRecord(Integer leaveId, String approveStatus, Integer approverId);

    /**
     * 查询月度报表（按月份区间累加）
     *
     * @param queryDTO 查询条件
     * @return 月度报表汇总
     */
    MonthlyReportSummaryVO getMonthlyReportSummary(ReportQueryDTO queryDTO);
}
