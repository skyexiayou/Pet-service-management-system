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
     * 根据用户ID查询预约列表
     *
     * @param userId 用户ID
     * @return 预约列表
     */
    List<AppointmentVO> selectAppointmentsByUserId(@Param("userId") Integer userId);

    /**
     * 根据预约ID查询预约详情（含关联信息）
     *
     * @param apptId 预约ID
     * @return 预约详情
     */
    AppointmentDetailVO selectAppointmentDetail(@Param("apptId") Integer apptId);

    /**
     * 查询待诊断预约列表（分页）
     *
     * @param storeId  门店ID
     * @param keyword  搜索关键词
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @param offset   分页偏移量
     * @return 待诊断预约列表
     */
    List<AppointmentDetailVO> selectWaitDiagnoseAppointments(
            @Param("storeId") Integer storeId,
            @Param("keyword") String keyword,
            @Param("pageNum") Integer pageNum,
            @Param("pageSize") Integer pageSize,
            @Param("offset") Integer offset);

    /**
     * 查询待诊断预约数量
     *
     * @return 待诊断预约总数
     */
    int selectPendingDiagnoseCount();

    /**
     * 查询诊断预约列表
     *
     * @param diagnoseStatus 诊断状态（待诊断/已诊断/已取消）
     * @param keyword        搜索关键词（宠物名/主人名/诊断描述等）
     * @return 诊断预约列表
     */
    List<AppointmentDetailVO> selectDiagnoseList(@Param("diagnoseStatus") String diagnoseStatus,
            @Param("keyword") String keyword);

    /**
     * 查询某门店下待处理预约最少的医生
     * 
     * @param storeId 门店ID
     * @return 医生ID
     */
    Integer selectDoctorWithLeastAppointments(@Param("storeId") Integer storeId);
}