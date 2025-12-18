package cn.edu.xaut.service.medical;

import cn.edu.xaut.domain.vo.medical.MedicalServiceDetailVO;
import cn.edu.xaut.domain.vo.medical.MedicalServiceVO;

import java.util.List;

/**
 * 医疗服务Service接口
 */
public interface MedicalServiceService {

    /**
     * 查询用户的医疗服务列表
     *
     * @param userId 用户ID
     * @return 医疗服务列表
     */
    List<MedicalServiceVO> getMedicalServicesByUserId(Integer userId);

    /**
     * 查询医疗服务详情
     *
     * @param medicalId 医疗记录ID
     * @return 医疗服务详情
     */
    MedicalServiceDetailVO getMedicalServiceDetail(Integer medicalId);
}
