package cn.edu.xaut.service.admin;

import cn.edu.xaut.domain.dto.admin.AppointmentReviewDTO;
import cn.edu.xaut.domain.dto.admin.OrderRefundDTO;
import cn.edu.xaut.domain.dto.admin.StatisticsQueryDTO;
import cn.edu.xaut.domain.vo.admin.MonthlyReportVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * 管理员预约订单管理Service接口
 * 创建时间：2025-12-18
 */
public interface AdminAppointmentService {

    /**
     * 查询门店的待审核预约列表
     * @param storeId 门店ID
     * @return 预约列表
     */
    List<Map<String, Object>> getPendingAppointments(Integer storeId);

    /**
     * 审核预约
     * @param apptId 预约ID
     * @param reviewDTO 审核信息
     * @param storeId 门店ID
     */
    void reviewAppointment(Integer apptId, AppointmentReviewDTO reviewDTO, Integer storeId);

    /**
     * 查询门店订单列表
     * @param storeId 门店ID
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 订单列表
     */
    Page<Map<String, Object>> getStoreOrders(Integer storeId, Integer pageNum, Integer pageSize);

    /**
     * 订单退款
     * @param orderId 订单ID
     * @param refundDTO 退款信息
     * @param storeId 门店ID
     */
    void refundOrder(Integer orderId, OrderRefundDTO refundDTO, Integer storeId);

    /**
     * 查询门店月报
     * @param storeId 门店ID
     * @param queryDTO 查询条件
     * @return 月报数据
     */
    Page<MonthlyReportVO> getMonthlyReport(Integer storeId, StatisticsQueryDTO queryDTO);
}
