package cn.edu.xaut.service.admin.impl;

import cn.edu.xaut.domain.dto.admin.AppointmentReviewDTO;
import cn.edu.xaut.domain.dto.admin.StatisticsQueryDTO;
import cn.edu.xaut.domain.entity.appointment.AppointmentDO;
import cn.edu.xaut.domain.vo.admin.MonthlyReportVO;
import cn.edu.xaut.exception.BusinessException;
import cn.edu.xaut.mapper.AdminAppointmentMapper;
import cn.edu.xaut.mapper.AppointmentMapper;
import cn.edu.xaut.service.admin.AdminAppointmentService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 管理员预约管理Service实现
 * 创建时间：2025-12-18
 */
@Service
@RequiredArgsConstructor
public class AdminAppointmentServiceImpl implements AdminAppointmentService {

    private final AdminAppointmentMapper adminAppointmentMapper;
    private final AppointmentMapper appointmentMapper;

    @Override
    public Page<Map<String, Object>> getAllAppointments(Integer pageNum, Integer pageSize) {
        Page<Map<String, Object>> page = new Page<>(pageNum, pageSize);
        List<Map<String, Object>> appointments = adminAppointmentMapper.selectAllAppointments();
        
        // 手动分页
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, appointments.size());
        
        page.setTotal(appointments.size());
        if (start < appointments.size()) {
            page.setRecords(appointments.subList(start, end));
        }
        
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAppointment(Integer apptId, Map<String, Object> updateData) {
        AppointmentDO appointment = appointmentMapper.selectById(apptId);
        if (appointment == null) {
            throw new BusinessException("预约不存在");
        }
        
        // 更新字段
        if (updateData.containsKey("apptStatus")) {
            appointment.setApptStatus((String) updateData.get("apptStatus"));
        }
        if (updateData.containsKey("empId")) {
            Object empIdObj = updateData.get("empId");
            if (empIdObj != null) {
                appointment.setEmpId(Integer.valueOf(empIdObj.toString()));
            }
        }
        
        appointmentMapper.updateById(appointment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAppointment(Integer apptId) {
        AppointmentDO appointment = appointmentMapper.selectById(apptId);
        if (appointment == null) {
            throw new BusinessException("预约不存在");
        }
        
        // 直接删除预约，不再处理订单
        appointmentMapper.deleteById(apptId);
    }

    @Override
    public List<Map<String, Object>> getPendingAppointments(Integer storeId) {
        return adminAppointmentMapper.selectPendingAppointments(storeId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reviewAppointment(Integer apptId, AppointmentReviewDTO reviewDTO, Integer storeId) {
        // 查询预约信息
        AppointmentDO appointment = appointmentMapper.selectById(apptId);
        if (appointment == null) {
            throw new BusinessException("预约不存在");
        }

        // 验证门店权限
        if (!appointment.getStoreId().equals(storeId)) {
            throw new BusinessException("无权限操作其他门店的预约");
        }

        // 验证预约状态
        if (!"待服务".equals(appointment.getApptStatus())) {
            throw new BusinessException("预约状态不是'待服务'，无法审核");
        }

        if (reviewDTO.getApproved()) {
            // 审核通过
            // 分配员工（如果提供）
            if (reviewDTO.getEmpId() != null) {
                appointment.setEmpId(reviewDTO.getEmpId());
                appointmentMapper.updateById(appointment);
            }
        } else {
            // 审核驳回 - 设置为已取消状态
            if (reviewDTO.getRejectReason() == null || reviewDTO.getRejectReason().trim().isEmpty()) {
                throw new BusinessException("驳回原因不能为空");
            }
            appointment.setApptStatus("已取消");
            appointmentMapper.updateById(appointment);
        }
    }

    @Override
    public Page<MonthlyReportVO> getMonthlyReport(Integer storeId, StatisticsQueryDTO queryDTO) {
        Page<MonthlyReportVO> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        
        List<MonthlyReportVO> reports = adminAppointmentMapper.selectMonthlyReport(
            storeId, 
            queryDTO.getStatMonth()
        );
        
        // 手动分页
        int start = (queryDTO.getPageNum() - 1) * queryDTO.getPageSize();
        int end = Math.min(start + queryDTO.getPageSize(), reports.size());
        
        page.setTotal(reports.size());
        if (start < reports.size()) {
            page.setRecords(reports.subList(start, end));
        }
        
        return page;
    }
}
