package cn.edu.xaut.service.diagnosis;

import cn.edu.xaut.domain.dto.diagnosis.DiagnosisSaveDTO;

/**
 * 诊断服务接口
 */
public interface DiagnosisService {

    /**
     * 保存诊断和处方
     * @param diagnosisSaveDTO 诊断和处方数据
     */
    void saveDiagnosisWithPrescription(DiagnosisSaveDTO diagnosisSaveDTO);
}