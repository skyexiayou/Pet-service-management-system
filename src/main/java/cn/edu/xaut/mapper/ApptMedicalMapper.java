package cn.edu.xaut.mapper;

import cn.edu.xaut.domain.entity.apptmedical.ApptMedicalDO;
import cn.edu.xaut.domain.vo.appointment.MedicalDetailVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 预约-医疗中间表Mapper接口
 */
@Mapper
public interface ApptMedicalMapper extends BaseMapper<ApptMedicalDO> {

    /**
     * 查询预约的医疗服务明细
     *
     * @param apptId 预约ID
     * @return 医疗服务明细
     */
    MedicalDetailVO selectMedicalDetailByApptId(@Param("apptId") Integer apptId);

    /**
     * 根据预约ID查询医疗记录ID
     * @param apptId 预约ID
     * @return 医疗记录ID
     */
    Integer selectMedicalIdByApptId(@Param("apptId") Integer apptId);

    /**
     * 插入预约-医疗关联记录
     * @param apptId 预约ID
     * @param medicalId 医疗记录ID
     * @return 插入行数
     */
    int insertApptMedical(@Param("apptId") Integer apptId, @Param("medicalId") Integer medicalId);
}
