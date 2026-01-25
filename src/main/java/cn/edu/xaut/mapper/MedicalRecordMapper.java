package cn.edu.xaut.mapper;

import cn.edu.xaut.domain.entity.medicalrecord.MedicalRecordDO;
import cn.edu.xaut.domain.vo.medical.MedicalServiceVO;
import cn.edu.xaut.domain.vo.medicalrecord.MedicalRecordDetailVO;
import cn.edu.xaut.domain.vo.medicalrecord.MedicalRecordVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 医疗记录Mapper接口
 */
@Mapper
public interface MedicalRecordMapper extends BaseMapper<MedicalRecordDO> {

    /**
     * 查询用户的医疗服务列表
     *
     * @param userId 用户ID
     * @return 医疗服务列表
     */
    List<MedicalServiceVO> selectMedicalServicesByUserId(@Param("userId") Integer userId);

    /**
     * 根据员工ID分页查询医疗记录
     *
     * @param page  分页对象
     * @param empId 员工ID
     * @return 分页结果
     */
    Page<MedicalRecordVO> selectMedicalRecordsByEmpId(Page<MedicalRecordVO> page, @Param("empId") Integer empId);

    /**
     * 分页查询所有医疗记录
     *
     * @param page 分页对象
     * @return 分页结果
     */
    Page<MedicalRecordVO> selectAllMedicalRecords(Page<MedicalRecordVO> page);

    /**
     * 查询医疗记录详情
     *
     * @param medicalId 医疗记录ID
     * @return 医疗记录详情
     */
    MedicalRecordDetailVO selectMedicalRecordDetail(@Param("medicalId") Integer medicalId);

    /**
     * 根据用户ID查询医疗记录列表
     *
     * @param userId 用户ID
     * @return 医疗记录列表
     */
    List<MedicalRecordVO> selectMedicalRecordsByUserId(@Param("userId") Integer userId);

    /**
     * 医生端-条件查询医疗记录
     */
    Page<MedicalRecordVO> selectDoctorMedicalRecords(Page<MedicalRecordVO> page,
            @Param("dto") cn.edu.xaut.domain.dto.medicalrecord.DoctorMedicalRecordQueryDTO dto);

    /**
     * 医生端-查询详情（带门店校验）
     */
    MedicalRecordDetailVO selectDoctorMedicalRecordDetail(@Param("medicalId") Integer medicalId,
            @Param("storeId") Integer storeId);

    /**
     * 用户端-查询指定宠物的诊疗记录
     */
    Page<MedicalRecordVO> selectUserPetMedicalRecords(Page<MedicalRecordVO> page,
            @Param("dto") cn.edu.xaut.domain.dto.medicalrecord.UserMedicalRecordQueryDTO dto);

    /**
     * 用户端-查询详情（带用户校验）
     */
    MedicalRecordDetailVO selectUserMedicalRecordDetail(@Param("medicalId") Integer medicalId,
            @Param("userId") Integer userId);
}
