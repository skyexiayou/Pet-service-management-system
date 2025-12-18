package cn.edu.xaut.controller.admin;

import cn.edu.xaut.domain.dto.admin.AppointmentReviewDTO;
import cn.edu.xaut.domain.dto.admin.OrderRefundDTO;
import cn.edu.xaut.domain.dto.admin.StatisticsQueryDTO;
import cn.edu.xaut.domain.vo.ResponseVO;
import cn.edu.xaut.domain.vo.admin.MonthlyReportVO;
import cn.edu.xaut.service.admin.AdminAppointmentService;
import cn.edu.xaut.utils.AdminAuthUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 管理员预约订单管理Controller
 * 创建时间：2025-12-18
 */
@RestController
@RequestMapping("/api/admin/appointments")
@Api(tags = "管理员-预约订单管理")
@RequiredArgsConstructor
public class AdminAppointmentController {

    private final AdminAppointmentService adminAppointmentService;
    private final AdminAuthUtil adminAuthUtil;

    @GetMapping("/pending")
    @ApiOperation(value = "查询待审核预约列表", notes = "查询当前门店所有待审核的预约")
    public ResponseVO<List<Map<String, Object>>> getPendingAppointments(
            @ApiParam(value = "管理员用户名", example = "管理员张三") @RequestParam(required = false) String userName,
            @ApiParam(value = "管理员手机号", example = "13900000001") @RequestParam(required = false) String phone) {
        
        Integer storeId = adminAuthUtil.getAdminStoreId(userName, phone);
        List<Map<String, Object>> appointments = adminAppointmentService.getPendingAppointments(storeId);
        return ResponseVO.success(appointments);
    }

    @PutMapping("/{apptId}/review")
    @ApiOperation(value = "审核预约", notes = "审核预约（通过或驳回），审核通过可选分配员工，审核驳回需填写驳回原因")
    public ResponseVO<Void> reviewAppointment(
            @ApiParam(value = "预约ID", required = true, example = "1") @PathVariable Integer apptId,
            @ApiParam(value = "审核信息", required = true) @Valid @RequestBody AppointmentReviewDTO reviewDTO,
            @ApiParam(value = "管理员用户名", example = "管理员张三") @RequestParam(required = false) String userName,
            @ApiParam(value = "管理员手机号", example = "13900000001") @RequestParam(required = false) String phone) {
        
        Integer storeId = adminAuthUtil.getAdminStoreId(userName, phone);
        adminAppointmentService.reviewAppointment(apptId, reviewDTO, storeId);
        return ResponseVO.success(null);
    }

    @GetMapping("/orders")
    @ApiOperation(value = "查询门店订单列表", notes = "分页查询当前门店的所有订单")
    public ResponseVO<Page<Map<String, Object>>> getStoreOrders(
            @ApiParam(value = "管理员用户名", example = "管理员张三") @RequestParam(required = false) String userName,
            @ApiParam(value = "管理员手机号", example = "13900000001") @RequestParam(required = false) String phone,
            @ApiParam(value = "页码", example = "1") @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页大小", example = "10") @RequestParam(defaultValue = "10") Integer pageSize) {
        
        Integer storeId = adminAuthUtil.getAdminStoreId(userName, phone);
        Page<Map<String, Object>> orders = adminAppointmentService.getStoreOrders(storeId, pageNum, pageSize);
        return ResponseVO.success(orders);
    }

    @PutMapping("/orders/{orderId}/refund")
    @ApiOperation(value = "订单退款", notes = "对已支付订单进行退款，退款后自动恢复用品库存，预约状态更新为已取消")
    public ResponseVO<Void> refundOrder(
            @ApiParam(value = "订单ID", required = true, example = "1") @PathVariable Integer orderId,
            @ApiParam(value = "退款信息", required = true) @Valid @RequestBody OrderRefundDTO refundDTO,
            @ApiParam(value = "管理员用户名", example = "管理员张三") @RequestParam(required = false) String userName,
            @ApiParam(value = "管理员手机号", example = "13900000001") @RequestParam(required = false) String phone) {
        
        Integer storeId = adminAuthUtil.getAdminStoreId(userName, phone);
        adminAppointmentService.refundOrder(orderId, refundDTO, storeId);
        return ResponseVO.success(null);
    }

    @GetMapping("/monthly-report")
    @ApiOperation(value = "查询门店月报", notes = "查询门店月度运营数据，包括订单量、营收、新增客户等统计信息")
    public ResponseVO<Page<MonthlyReportVO>> getMonthlyReport(
            @ApiParam(value = "管理员用户名", example = "管理员张三") @RequestParam(required = false) String userName,
            @ApiParam(value = "管理员手机号", example = "13900000001") @RequestParam(required = false) String phone,
            @ApiParam(value = "统计月份（格式：YYYY-MM）", required = true, example = "2025-12") @RequestParam String statMonth,
            @ApiParam(value = "页码", example = "1") @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页大小", example = "10") @RequestParam(defaultValue = "10") Integer pageSize) {
        
        Integer storeId = adminAuthUtil.getAdminStoreId(userName, phone);
        StatisticsQueryDTO queryDTO = new StatisticsQueryDTO();
        queryDTO.setStatMonth(statMonth);
        queryDTO.setPageNum(pageNum);
        queryDTO.setPageSize(pageSize);
        Page<MonthlyReportVO> report = adminAppointmentService.getMonthlyReport(storeId, queryDTO);
        return ResponseVO.success(report);
    }
}
