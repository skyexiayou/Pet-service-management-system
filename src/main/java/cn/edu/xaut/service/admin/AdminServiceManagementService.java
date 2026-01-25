package cn.edu.xaut.service.admin;

import cn.edu.xaut.domain.dto.admin.MedicalRecordCreateDTO;

/**
 * 管理员专项服务管理Service接口
 * 创建时间：2025-12-18
 */
public interface AdminServiceManagementService {

    /**
     * 创建医疗记录
     * @param createDTO 医疗记录信息
     * @param storeId 门店ID
     * @return 医疗记录ID
     */
    Integer createMedicalRecord(MedicalRecordCreateDTO createDTO, Integer storeId);

    /**
     * 补充医疗记录备注
     * @param medicalId 医疗记录ID
     * @param remark 备注内容
     * @param storeId 门店ID
     */
    void appendMedicalRemark(Integer medicalId, String remark, Integer storeId);
}