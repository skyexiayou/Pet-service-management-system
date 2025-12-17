package cn.edu.xaut.controller;

import cn.edu.xaut.domain.dto.AppointmentDTO;
import cn.edu.xaut.domain.entity.Appointment;
import cn.edu.xaut.service.appointment.AppointmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@Api(tags = "预约管理")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @ApiOperation("获取所有预约")
    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @ApiOperation("根据ID获取预约")
    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable("id") Integer apptId) {
        return appointmentService.getAppointmentById(apptId);
    }

    @ApiOperation("根据用户ID获取预约")
    @GetMapping("/user/{userId}")
    public List<Appointment> getAppointmentsByUserId(@PathVariable("userId") Integer userId) {
        return appointmentService.getAppointmentsByUserId(userId);
    }

    @ApiOperation("根据状态获取预约")
    @GetMapping("/status/{status}")
    public List<Appointment> getAppointmentsByStatus(@PathVariable("status") String status) {
        return appointmentService.getAppointmentsByStatus(status);
    }

    @ApiOperation("创建预约")
    @PostMapping
    public Integer createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        return appointmentService.createAppointment(appointmentDTO);
    }

    @ApiOperation("更新预约")
    @PutMapping("/{id}")
    public Integer updateAppointment(@PathVariable("id") Integer apptId, @RequestBody AppointmentDTO appointmentDTO) {
        return appointmentService.updateAppointment(apptId, appointmentDTO);
    }

    @ApiOperation("取消预约")
    @PutMapping("/{id}/cancel")
    public Integer cancelAppointment(@PathVariable("id") Integer apptId) {
        return appointmentService.cancelAppointment(apptId);
    }

    @ApiOperation("删除预约")
    @DeleteMapping("/{id}")
    public Integer deleteAppointment(@PathVariable("id") Integer apptId) {
        return appointmentService.deleteAppointment(apptId);
    }
}