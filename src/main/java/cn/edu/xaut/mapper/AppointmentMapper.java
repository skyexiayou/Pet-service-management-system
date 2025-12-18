package cn.edu.xaut.mapper;

import cn.edu.xaut.domain.entity.appointment.AppointmentDO;
import cn.edu.xaut.domain.vo.appointment.AppointmentDetailVO;
import cn.edu.xaut.domain.vo.appointment.AppointmentVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 预约Mapper接口
 */
@Mapper
public interface AppointmentMapper extends BaseMapper<AppointmentDO> {

    /**
     * 查询用户的预约列表
     *
     * @param userId 用户ID
     * @return 预约列表
     */
    List<AppointmentVO> selectAppointmentsByUserId(@Param("userId") Integer userId);

    /**
     * 查询预约详情（含门店、员工信息）
     *
     * @param apptId 预约ID
     * @return 预约详情
     */
    AppointmentDetailVO selectAppointmentDetail(@Param("apptId") Integer apptId);
}
