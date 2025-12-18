package cn.edu.xaut.service.admin.impl;

import cn.edu.xaut.domain.dto.admin.AppointmentReviewDTO;
import cn.edu.xaut.domain.dto.admin.OrderRefundDTO;
import cn.edu.xaut.domain.dto.admin.StatisticsQueryDTO;
import cn.edu.xaut.domain.entity.appointment.AppointmentDO;
import cn.edu.xaut.domain.entity.order.OrderDO;
import cn.edu.xaut.domain.vo.admin.MonthlyReportVO;
import cn.edu.xaut.exception.BusinessException;
import cn.edu.xaut.mapper.AdminAppointmentMapper;
import cn.edu.xaut.mapper.AppointmentMapper;
import cn.edu.xaut.mapper.OrderMapper;
import cn.edu.xaut.mapper.OrderProductMapper;
import cn.edu.xaut.mapper.PetProductStoreMapper;
import cn.edu.xaut.service.admin.AdminAppointmentService;
import cn.edu.xaut.service.order.OrderService;
import cn.edu.xaut.utils.AmountCalculationUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 管理员预约订单管理Service实现
 * 创建时间：2025-12-18
 */
@Service
@RequiredArgsConstructor
public class AdminAppointmentServiceImpl implements AdminAppointmentService {

    private final AdminAppointmentMapper adminAppointmentMapper;
    private final AppointmentMapper appointmentMapper;
    private final OrderMapper orderMapper;
    private final OrderProductMapper orderProductMapper;
    private final PetProductStoreMapper petProductStoreMapper;
    private final OrderService orderService;

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

            // 生成订单
            // 计算服务费用
            Map<String, Object> fees = orderMapper.calculateServiceFees(apptId);
            BigDecimal beautyFee = fees.get("beautyFee") != null ? (BigDecimal) fees.get("beautyFee") : BigDecimal.ZERO;
            BigDecimal fosterFee = fees.get("fosterFee") != null ? (BigDecimal) fees.get("fosterFee") : BigDecimal.ZERO;
            BigDecimal medicalFee = fees.get("medicalFee") != null ? (BigDecimal) fees.get("medicalFee") : BigDecimal.ZERO;

            BigDecimal totalAmount = beautyFee.add(fosterFee).add(medicalFee);

            // 创建订单
            OrderDO order = new OrderDO();
            order.setApptId(apptId);
            order.setTotalAmount(totalAmount);
            order.setPayStatus("未支付");
            order.setOrderCreateTime(new Date());
            orderMapper.insert(order);

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
    public Page<Map<String, Object>> getStoreOrders(Integer storeId, Integer pageNum, Integer pageSize) {
        Page<Map<String, Object>> page = new Page<>(pageNum, pageSize);
        List<Map<String, Object>> orders = adminAppointmentMapper.selectStoreOrders(storeId);
        
        // 手动分页
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, orders.size());
        
        page.setTotal(orders.size());
        if (start < orders.size()) {
            page.setRecords(orders.subList(start, end));
        }
        
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refundOrder(Integer orderId, OrderRefundDTO refundDTO, Integer storeId) {
        // 查询订单信息
        OrderDO order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }

        // 查询预约信息验证门店权限
        AppointmentDO appointment = appointmentMapper.selectById(order.getApptId());
        if (appointment == null || !appointment.getStoreId().equals(storeId)) {
            throw new BusinessException("无权限操作其他门店的订单");
        }

        // 验证订单状态
        if (!"已支付".equals(order.getPayStatus())) {
            throw new BusinessException("订单状态不是'已支付'，无法退款");
        }

        // 验证服务是否已完成
        if ("已完成".equals(appointment.getApptStatus())) {
            throw new BusinessException("服务已完成，无法退款");
        }

        // 更新订单状态为退款
        order.setPayStatus("退款");
        orderMapper.updateById(order);

        // 恢复用品库存
        List<Map<String, Object>> orderProducts = orderProductMapper.selectByOrderId(orderId);
        for (Map<String, Object> product : orderProducts) {
            Integer relId = (Integer) product.get("relId");
            Integer quantity = (Integer) product.get("quantity");
            
            // 恢复库存
            petProductStoreMapper.increaseStock(relId, quantity);
        }

        // 更新预约状态为已取消
        appointment.setApptStatus("已取消");
        appointmentMapper.updateById(appointment);
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
