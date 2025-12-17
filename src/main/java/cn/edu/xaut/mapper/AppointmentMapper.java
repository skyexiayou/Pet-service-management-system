package cn.edu.xaut.mapper;

import cn.edu.xaut.domain.entity.Appointment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface AppointmentMapper {
    Appointment selectAppointmentById(@Param("apptId") Integer apptId);
    
    List<Appointment> selectAppointmentsByUserId(@Param("userId") Integer userId);
    
    List<Appointment> selectAppointmentsByStoreId(@Param("storeId") Integer storeId);
    
    List<Appointment> selectAppointmentsByStatus(@Param("status") String status);
    
    List<Appointment> selectAppointmentsByServiceTime(@Param("serviceTime") String serviceTime);
    
    List<Appointment> selectAppointmentsByDateRange(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
    
    List<Appointment> selectAllAppointments();
    
    int insertAppointment(Appointment appointment);
    
    int updateAppointment(Appointment appointment);
    
    int updateAppointmentStatus(@Param("apptId") Integer apptId, @Param("status") String status);
    
    int deleteAppointment(@Param("apptId") Integer apptId);
}