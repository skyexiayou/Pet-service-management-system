package cn.edu.xaut.controller.admin;

import cn.edu.xaut.domain.dto.admin.LeaveRecordDTO;
import cn.edu.xaut.domain.dto.admin.ReportQueryDTO;
import cn.edu.xaut.domain.vo.PageResultVO;
import cn.edu.xaut.domain.vo.ResponseVO;
import cn.edu.xaut.domain.vo.admin.LeaveRecordVO;
import cn.edu.xaut.domain.vo.admin.MonthlyReportSummaryVO;
import cn.edu.xaut.service.admin.AdminLeaveRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员-请假记录管理Controller
 * @date 2025-12-19
 */
@RestController
@RequestMapping("/api/admin/leave-records")
@Api(tags = "管理员-请假记录管理")
public class AdminLeaveRecordController {

    @Autowired
    private AdminLeaveRecordService adminLeaveRecordService;

    @ApiOperation("分页查询所有请假记录")
    @GetMapping
    public ResponseVO<PageResultVO<LeaveRecordVO>> getAllLeaveRecords(
            @ApiParam("页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam("每页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @ApiParam("审批状态（待审批/已通过/已驳回）") @RequestParam(required = false) String approveStatus,
            @ApiParam("请假类型（事假/病假/年假）") @RequestParam(required = false) String leaveType,
            @ApiParam("员工姓名（模糊查询）") @RequestParam(required = false) String empName) {
        
        PageResultVO<LeaveRecordVO> result = adminLeaveRecordService.getAllLeaveRecords(
                pageNum, pageSize, approveStatus, leaveType, empName);
        return ResponseVO.success(result);
    }

    @ApiOperation("根据ID获取请假记录详情")
    @GetMapping("/{leaveId}")
    public ResponseVO<LeaveRecordVO> getLeaveRecordById(
            @ApiParam("请假记录ID") @PathVariable Integer leaveId) {
        
        LeaveRecordVO result = adminLeaveRecordService.getLeaveRecordById(leaveId);
        return ResponseVO.success(result);
    }

    @ApiOperation("创建请假记录")
    @PostMapping
    public ResponseVO<Integer> createLeaveRecord(
            @Validated @RequestBody LeaveRecordDTO dto) {
        
        Integer leaveId = adminLeaveRecordService.createLeaveRecord(dto);
        return ResponseVO.success(leaveId);
    }

    @ApiOperation("更新请假记录")
    @PutMapping("/{leaveId}")
    public ResponseVO<Integer> updateLeaveRecord(
            @ApiParam("请假记录ID") @PathVariable Integer leaveId,
            @RequestBody LeaveRecordDTO dto) {
        
        Integer result = adminLeaveRecordService.updateLeaveRecord(leaveId, dto);
        return ResponseVO.success(result);
    }

    @ApiOperation("删除请假记录")
    @DeleteMapping("/{leaveId}")
    public ResponseVO<Integer> deleteLeaveRecord(
            @ApiParam("请假记录ID") @PathVariable Integer leaveId) {
        
        Integer result = adminLeaveRecordService.deleteLeaveRecord(leaveId);
        return ResponseVO.success(result);
    }

    @ApiOperation("审批请假记录")
    @PutMapping("/{leaveId}/approve")
    public ResponseVO<Integer> approveLeaveRecord(
            @ApiParam("请假记录ID") @PathVariable Integer leaveId,
            @ApiParam("审批状态（已通过/已驳回）") @RequestParam String approveStatus,
            @ApiParam("审批人ID") @RequestParam(required = false) Integer approverId) {
        
        Integer result = adminLeaveRecordService.approveLeaveRecord(leaveId, approveStatus, approverId);
        return ResponseVO.success(result);
    }

    @ApiOperation("查询月度报表（按月份区间累加）")
    @GetMapping("/monthly-report")
    public ResponseVO<MonthlyReportSummaryVO> getMonthlyReportSummary(
            @ApiParam("起始月份（格式：YYYY-MM）") @RequestParam String startMonth,
            @ApiParam("终止月份（格式：YYYY-MM）") @RequestParam String endMonth,
            @ApiParam("门店ID（可选）") @RequestParam(required = false) Integer storeId) {
        
        ReportQueryDTO queryDTO = new ReportQueryDTO();
        queryDTO.setStartMonth(startMonth);
        queryDTO.setEndMonth(endMonth);
        queryDTO.setStoreId(storeId);
        
        MonthlyReportSummaryVO result = adminLeaveRecordService.getMonthlyReportSummary(queryDTO);
        return ResponseVO.success(result);
    }
}
