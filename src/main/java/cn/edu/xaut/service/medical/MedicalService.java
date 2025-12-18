package cn.edu.xaut.service.medical;

import cn.edu.xaut.domain.dto.medical.MedicalDTO;
import cn.edu.xaut.domain.entity.medical.MedicalDO;
import cn.edu.xaut.domain.vo.PageResultVO;

import java.util.List;

public interface MedicalService {

    MedicalDO getMedicalById(Integer medicalId);

    List<MedicalDO> getMedicalsByType(String medicalType);

    List<MedicalDO> getAllMedicals();

    PageResultVO<MedicalDO> getMedicalsPage(Integer pageNum, Integer pageSize);

    PageResultVO<MedicalDO> getMedicalsByTypePage(String medicalType, Integer pageNum, Integer pageSize);

    Integer createMedical(MedicalDTO medicalDTO);

    Integer updateMedical(Integer medicalId, MedicalDTO medicalDTO);

    Integer deleteMedical(Integer medicalId);
}