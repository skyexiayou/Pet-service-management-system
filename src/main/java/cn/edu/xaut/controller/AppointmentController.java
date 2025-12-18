package cn.edu.xaut.controller;

import cn.edu.xaut.domain.dto.appointment.AppointmentCreateDTO;
import cn.edu.xaut.domain.vo.ResponseVO;
import cn.edu.xaut.domain.vo.appointment.AppointmentDetailVO;
import cn.edu.xaut.domain.vo.appointment.AppointmentVO;
import cn.edu.xaut.service.appointment.AppointmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 预约管理Controller
 */
@RestController
@RequestMapping("/api/appointments")
@Api(tags = "预约管理")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @ApiOperation("提交多服务组合预约")
    @PostMapping
    public ResponseVO<Integer> createAppointment(
            @Validated @RequestBody AppointmentCreateDTO dto) {
        Integer apptId = appointmentService.createAppointment(dto);
        return ResponseVO.success(apptId);
    }

    @ApiOperation("查询用户的预约列表")
    @GetMapping("/user/{userId}")
    public ResponseVO<List<AppointmentVO>> getAppointmentsByUserId(
            @ApiParam(value = "用户ID", required = true) @PathVariable Integer userId) {
        List<AppointmentVO> appointments = appointmentService.getAppointmentsByUserId(userId);
        return ResponseVO.success(appointments);
    }

    @ApiOperation("查询预约详情（含服务明细）")
    @GetMapping("/{apptId}")
    public ResponseVO<AppointmentDetailVO> getAppointmentDetail(
            @ApiParam(value = "预约ID", required = true) @PathVariable Integer apptId) {
        AppointmentDetailVO detail = appointmentService.getAppointmentDetail(apptId);
        return ResponseVO.success(detail);
    }

    @ApiOperation("取消预约")
    @PutMapping("/{apptId}/cancel")
    public ResponseVO<Void> cancelAppointment(
            @ApiParam(value = "预约ID", required = true) @PathVariable Integer apptId) {
        appointmentService.cancelAppointment(apptId);
        return ResponseVO.success(null);
    }
}
