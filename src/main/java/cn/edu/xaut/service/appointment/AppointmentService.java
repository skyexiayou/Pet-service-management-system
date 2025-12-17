package cn.edu.xaut.service.appointment;

import cn.edu.xaut.domain.dto.AppointmentDTO;
import cn.edu.xaut.domain.entity.Appointment;

import java.util.List;

public interface AppointmentService {

    Appointment getAppointmentById(Integer apptId);

    List<Appointment> getAppointmentsByUserId(Integer userId);

    List<Appointment> getAppointmentsByStatus(String status);

    List<Appointment> getAppointmentsByServiceTime(String serviceTime);

    List<Appointment> getAllAppointments();

    Integer createAppointment(AppointmentDTO appointmentDTO);

    Integer updateAppointment(Integer apptId, AppointmentDTO appointmentDTO);

    Integer cancelAppointment(Integer apptId);

    Integer deleteAppointment(Integer apptId);
}
