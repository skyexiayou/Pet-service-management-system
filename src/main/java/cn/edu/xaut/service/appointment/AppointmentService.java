package cn.edu.xaut.service.appointment;

import cn.edu.xaut.domain.dto.appointment.AppointmentCreateDTO;
import cn.edu.xaut.domain.vo.appointment.AppointmentDetailVO;
import cn.edu.xaut.domain.vo.appointment.AppointmentVO;

import java.util.List;

/**
 * 预约服务接口
 */
public interface AppointmentService {

    /**
     * 创建多服务组合预约
     *
     * @param dto 预约创建DTO
     * @return 预约ID
     */
    Integer createAppointment(AppointmentCreateDTO dto);

    /**
     * 查询用户的预约列表
     *
     * @param userId 用户ID
     * @return 预约列表
     */
    List<AppointmentVO> getAppointmentsByUserId(Integer userId);

    /**
     * 查询预约详情（含服务明细）
     *
     * @param apptId 预约ID
     * @return 预约详情
     */
    AppointmentDetailVO getAppointmentDetail(Integer apptId);

    /**
     * 取消预约
     *
     * @param apptId 预约ID
     */
    void cancelAppointment(Integer apptId);
}
