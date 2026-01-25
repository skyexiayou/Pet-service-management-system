package cn.edu.xaut.service.medicalrecord;

import cn.edu.xaut.domain.dto.medicalrecord.MedicalAppointmentDTO;
import cn.edu.xaut.domain.dto.medicalrecord.MedicalRecordDTO;
import cn.edu.xaut.domain.vo.PageResultVO;
import cn.edu.xaut.domain.vo.medicalrecord.MedicalRecordDetailVO;
import cn.edu.xaut.domain.vo.medicalrecord.MedicalRecordVO;

import java.util.List;

/**
 * 医疗记录Service接口
 */
public interface MedicalRecordService {

    // 管理员功能

    /**
     * 获取当前管理员负责的医疗记录（分页）
     *
     * @param empId    员工ID
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageResultVO<MedicalRecordVO> getMyMedicalRecords(Integer empId, Integer pageNum, Integer pageSize);

    /**
     * 获取所有医疗记录（分页）
     *
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageResultVO<MedicalRecordVO> getAllMedicalRecords(Integer pageNum, Integer pageSize);

    /**
     * 获取医疗记录详情
     *
     * @param medicalId 医疗记录ID
     * @return 医疗记录详情
     */
    MedicalRecordDetailVO getMedicalRecordDetail(Integer medicalId);

    /**
     * 创建医疗记录
     *
     * @param dto 医疗记录DTO
     * @return 新创建的医疗记录ID
     */
    Integer createMedicalRecord(MedicalRecordDTO dto);

    /**
     * 更新医疗记录
     *
     * @param medicalId 医疗记录ID
     * @param dto       医疗记录DTO
     */
    void updateMedicalRecord(Integer medicalId, MedicalRecordDTO dto);

    /**
     * 删除医疗记录
     *
     * @param medicalId 医疗记录ID
     */
    void deleteMedicalRecord(Integer medicalId);

    // ==================== 用户功能 ====================

    /**
     * 获取用户的医疗记录列表
     *
     * @param userId 用户ID
     * @return 医疗记录列表
     */
    List<MedicalRecordVO> getUserMedicalRecords(Integer userId);

    /**
     * 创建医疗预约
     *
     * @param dto 医疗预约DTO
     * @return 新创建的医疗记录ID
     */
    Integer createMedicalAppointment(MedicalAppointmentDTO dto);

    /**
     * 检查用户是否有注册宠物
     *
     * @param userId 用户ID
     * @return 是否有宠物
     */
    boolean checkUserHasPets(Integer userId);

    /**
     * 医生端-分页查询医疗记录（带筛选）
     */
    PageResultVO<MedicalRecordVO> getDoctorMedicalRecords(
            cn.edu.xaut.domain.dto.medicalrecord.DoctorMedicalRecordQueryDTO dto);

    /**
     * 医生端-查询详情
     */
    MedicalRecordDetailVO getDoctorMedicalRecordDetail(Integer medicalId, Integer storeId);

    /**
     * 用户端-分页查询指定宠物诊疗记录
     */
    PageResultVO<MedicalRecordVO> getUserPetMedicalRecords(
            cn.edu.xaut.domain.dto.medicalrecord.UserMedicalRecordQueryDTO dto);

    /**
     * 用户端-查询详情（带权限校验）
     */
    MedicalRecordDetailVO getUserMedicalRecordDetail(Integer medicalId, Integer userId);
}
