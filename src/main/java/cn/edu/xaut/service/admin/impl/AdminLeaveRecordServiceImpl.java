package cn.edu.xaut.service.admin.impl;

import cn.edu.xaut.domain.dto.admin.LeaveRecordDTO;
import cn.edu.xaut.domain.dto.admin.ReportQueryDTO;
import cn.edu.xaut.domain.entity.leaverecord.LeaveRecordDO;
import cn.edu.xaut.domain.vo.PageResultVO;
import cn.edu.xaut.domain.vo.admin.LeaveRecordVO;
import cn.edu.xaut.domain.vo.admin.MonthlyReportSummaryVO;
import cn.edu.xaut.domain.vo.admin.MonthlyReportVO;
import cn.edu.xaut.exception.BusinessException;
import cn.edu.xaut.mapper.LeaveRecordMapper;
import cn.edu.xaut.service.admin.AdminLeaveRecordService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 管理员请假记录服务实现类
 * @date 2025-12-19
 */
@Service
public class AdminLeaveRecordServiceImpl implements AdminLeaveRecordService {

    @Autowired
    private LeaveRecordMapper leaveRecordMapper;

    @Override
    public PageResultVO<LeaveRecordVO> getAllLeaveRecords(Integer pageNum, Integer pageSize,
                                                          String approveStatus, String leaveType, String empName) {
        Page<LeaveRecordVO> page = new Page<>(pageNum, pageSize);
        Page<LeaveRecordVO> resultPage = leaveRecordMapper.selectAllLeaveRecordsWithDetail(page, approveStatus, leaveType, empName);

        return PageResultVO.<LeaveRecordVO>builder()
                .total(resultPage.getTotal())
                .list(resultPage.getRecords())
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();
    }

    @Override
    public LeaveRecordVO getLeaveRecordById(Integer leaveId) {
        LeaveRecordVO vo = leaveRecordMapper.selectLeaveRecordDetailById(leaveId);
        if (vo == null) {
            throw new BusinessException("请假记录不存在");
        }
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createLeaveRecord(LeaveRecordDTO dto) {
        // 校验时间
        if (dto.getEndTime().before(dto.getStartTime())) {
            throw new BusinessException("结束时间不能早于开始时间");
        }

        LeaveRecordDO leaveRecord = new LeaveRecordDO();
        BeanUtils.copyProperties(dto, leaveRecord);
        
        // 设置默认值
        leaveRecord.setApplyTime(new Date());
        if (leaveRecord.getApproveStatus() == null || leaveRecord.getApproveStatus().isEmpty()) {
            leaveRecord.setApproveStatus("待审批");
        }

        leaveRecordMapper.insert(leaveRecord);
        return leaveRecord.getLeaveId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateLeaveRecord(Integer leaveId, LeaveRecordDTO dto) {
        // 校验记录是否存在
        LeaveRecordDO existing = leaveRecordMapper.selectById(leaveId);
        if (existing == null) {
            throw new BusinessException("请假记录不存在");
        }

        // 校验时间
        if (dto.getEndTime() != null && dto.getStartTime() != null 
            && dto.getEndTime().before(dto.getStartTime())) {
            throw new BusinessException("结束时间不能早于开始时间");
        }

        // 更新字段
        if (dto.getEmpId() != null) existing.setEmpId(dto.getEmpId());
        if (dto.getStoreId() != null) existing.setStoreId(dto.getStoreId());
        if (dto.getLeaveType() != null) existing.setLeaveType(dto.getLeaveType());
        if (dto.getStartTime() != null) existing.setStartTime(dto.getStartTime());
        if (dto.getEndTime() != null) existing.setEndTime(dto.getEndTime());
        if (dto.getLeaveReason() != null) existing.setLeaveReason(dto.getLeaveReason());
        if (dto.getApproveStatus() != null) {
            existing.setApproveStatus(dto.getApproveStatus());
            if ("已通过".equals(dto.getApproveStatus()) || "已驳回".equals(dto.getApproveStatus())) {
                existing.setApproveTime(new Date());
                if (dto.getApproverId() != null) {
                    existing.setApproverId(dto.getApproverId());
                }
            }
        }

        return leaveRecordMapper.updateById(existing);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteLeaveRecord(Integer leaveId) {
        // 校验记录是否存在
        LeaveRecordDO existing = leaveRecordMapper.selectById(leaveId);
        if (existing == null) {
            throw new BusinessException("请假记录不存在");
        }

        return leaveRecordMapper.deleteById(leaveId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer approveLeaveRecord(Integer leaveId, String approveStatus, Integer approverId) {
        // 校验记录是否存在
        LeaveRecordDO existing = leaveRecordMapper.selectById(leaveId);
        if (existing == null) {
            throw new BusinessException("请假记录不存在");
        }

        // 校验状态
        if (!"待审批".equals(existing.getApproveStatus())) {
            throw new BusinessException("该请假记录已审批，不能重复审批");
        }

        // 校验审批状态值
        if (!"已通过".equals(approveStatus) && !"已驳回".equals(approveStatus)) {
            throw new BusinessException("审批状态只能是'已通过'或'已驳回'");
        }

        existing.setApproveStatus(approveStatus);
        existing.setApproverId(approverId);
        existing.setApproveTime(new Date());

        return leaveRecordMapper.updateById(existing);
    }

    @Override
    public MonthlyReportSummaryVO getMonthlyReportSummary(ReportQueryDTO queryDTO) {
        // 校验月份区间
        if (queryDTO.getStartMonth().compareTo(queryDTO.getEndMonth()) > 0) {
            throw new BusinessException("起始月份不能晚于终止月份");
        }

        // 查询月度明细
        List<MonthlyReportVO> monthlyDetails = leaveRecordMapper.selectMonthlyReportByRange(
                queryDTO.getStartMonth(), queryDTO.getEndMonth(), queryDTO.getStoreId());

        // 计算累加汇总
        MonthlyReportSummaryVO summary = new MonthlyReportSummaryVO();
        summary.setStartMonth(queryDTO.getStartMonth());
        summary.setEndMonth(queryDTO.getEndMonth());
        summary.setMonthlyDetails(monthlyDetails);

        // 初始化累计值
        int totalNewUserCount = 0;
        int totalOrderCount = 0;
        int totalBeautyOrderCount = 0;
        int totalFosterOrderCount = 0;
        int totalMedicalOrderCount = 0;
        BigDecimal totalProductSales = BigDecimal.ZERO;
        BigDecimal totalBeautyRevenue = BigDecimal.ZERO;
        BigDecimal totalFosterRevenue = BigDecimal.ZERO;
        BigDecimal totalMedicalRevenue = BigDecimal.ZERO;
        BigDecimal totalRevenue = BigDecimal.ZERO;

        // 累加各月数据
        for (MonthlyReportVO detail : monthlyDetails) {
            if (detail.getNewUserCount() != null) totalNewUserCount += detail.getNewUserCount();
            if (detail.getTotalOrderCount() != null) totalOrderCount += detail.getTotalOrderCount();
            if (detail.getBeautyOrderCount() != null) totalBeautyOrderCount += detail.getBeautyOrderCount();
            if (detail.getFosterOrderCount() != null) totalFosterOrderCount += detail.getFosterOrderCount();
            if (detail.getMedicalOrderCount() != null) totalMedicalOrderCount += detail.getMedicalOrderCount();
            if (detail.getProductSales() != null) totalProductSales = totalProductSales.add(detail.getProductSales());
            if (detail.getBeautyRevenue() != null) totalBeautyRevenue = totalBeautyRevenue.add(detail.getBeautyRevenue());
            if (detail.getFosterRevenue() != null) totalFosterRevenue = totalFosterRevenue.add(detail.getFosterRevenue());
            if (detail.getMedicalRevenue() != null) totalMedicalRevenue = totalMedicalRevenue.add(detail.getMedicalRevenue());
            if (detail.getTotalRevenue() != null) totalRevenue = totalRevenue.add(detail.getTotalRevenue());
        }

        summary.setTotalNewUserCount(totalNewUserCount);
        summary.setTotalOrderCount(totalOrderCount);
        summary.setTotalBeautyOrderCount(totalBeautyOrderCount);
        summary.setTotalFosterOrderCount(totalFosterOrderCount);
        summary.setTotalMedicalOrderCount(totalMedicalOrderCount);
        summary.setTotalProductSales(totalProductSales);
        summary.setTotalBeautyRevenue(totalBeautyRevenue);
        summary.setTotalFosterRevenue(totalFosterRevenue);
        summary.setTotalMedicalRevenue(totalMedicalRevenue);
        summary.setTotalRevenue(totalRevenue);

        return summary;
    }
}
