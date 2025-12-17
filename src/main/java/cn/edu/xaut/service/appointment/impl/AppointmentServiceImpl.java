package cn.edu.xaut.service.appointment.impl;

import cn.edu.xaut.domain.dto.AppointmentDTO;
import cn.edu.xaut.domain.entity.Appointment;
import cn.edu.xaut.mapper.AppointmentMapper;
import cn.edu.xaut.service.appointment.AppointmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Override
    public Appointment getAppointmentById(Integer apptId) {
        return appointmentMapper.selectAppointmentById(apptId);
    }

    @Override
    public List<Appointment> getAppointmentsByUserId(Integer userId) {
        return appointmentMapper.selectAppointmentsByUserId(userId);
    }

    @Override
    public List<Appointment> getAppointmentsByStatus(String status) {
        return appointmentMapper.selectAppointmentsByStatus(status);
    }

    @Override
    public List<Appointment> getAppointmentsByServiceTime(String serviceTime) {
        return appointmentMapper.selectAppointmentsByServiceTime(serviceTime);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentMapper.selectAllAppointments();
    }

    @Override
    public Integer createAppointment(AppointmentDTO appointmentDTO) {
        Appointment appointment = new Appointment();
        BeanUtils.copyProperties(appointmentDTO, appointment);
        appointment.setApptTime(new Date());
        appointment.setStatus("pending");
        appointmentMapper.insertAppointment(appointment);
        return appointment.getApptId();
    }

    @Override
    public Integer updateAppointment(Integer apptId, AppointmentDTO appointmentDTO) {
        Appointment appointment = new Appointment();
        BeanUtils.copyProperties(appointmentDTO, appointment);
        appointment.setApptId(apptId);
        return appointmentMapper.updateAppointment(appointment);
    }

    @Override
    public Integer cancelAppointment(Integer apptId) {
        return appointmentMapper.updateAppointmentStatus(apptId, "cancelled");
    }

    @Override
    public Integer deleteAppointment(Integer apptId) {
        return appointmentMapper.deleteAppointment(apptId);
    }
}
