package cn.edu.xaut.service.diagnosis.impl;

import cn.edu.xaut.domain.dto.diagnosis.DiagnosisSaveDTO;
import cn.edu.xaut.domain.entity.appointment.AppointmentDO;
import cn.edu.xaut.domain.entity.medicalrecord.MedicalRecordDO;
import cn.edu.xaut.domain.entity.petmedicalprescription.PetMedicalPrescriptionDO;
import cn.edu.xaut.domain.entity.prescriptiondrug.PrescriptionDrugDO;
import cn.edu.xaut.domain.entity.petdrugstore.PetDrugStoreDO;
import cn.edu.xaut.exception.BusinessException;
import cn.edu.xaut.mapper.*;
import cn.edu.xaut.service.diagnosis.DiagnosisService;
import cn.edu.xaut.utils.OrderNoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 诊断服务实现类
 */
@Service("xautDiagnosisService")
public class DiagnosisServiceImpl implements DiagnosisService {

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private MedicalRecordMapper medicalRecordMapper;

    @Autowired
    private PetMedicalPrescriptionMapper prescriptionMapper;

    @Autowired
    private PrescriptionDrugMapper prescriptionDrugMapper;

    @Autowired
    private PetDrugStoreMapper petDrugStoreMapper;

    @Autowired
    private ApptMedicalMapper apptMedicalMapper;

    /**
     * 保存诊断和处方
     * 
     * @param diagnosisSaveDTO 诊断和处方数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDiagnosisWithPrescription(DiagnosisSaveDTO diagnosisSaveDTO) {
        // 1. 校验预约是否存在且未诊断
        AppointmentDO appointment = appointmentMapper.selectById(diagnosisSaveDTO.getApptId());
        if (appointment == null) {
            throw new BusinessException("预约不存在");
        }
        if (!"待诊断".equals(appointment.getDiagnoseStatus())) {
            throw new BusinessException("该工单已诊断，不能重复提交");
        }

        // 2. 写入医疗记录
        MedicalRecordDO medicalRecord = new MedicalRecordDO();
        medicalRecord.setPetId(appointment.getPetId());
        medicalRecord.setEmpId(appointment.getEmpId());
        medicalRecord.setStoreId(appointment.getStoreId());
        medicalRecord.setMedicalTime(new Date());
        medicalRecord.setDiagnosis(diagnosisSaveDTO.getDiagnosis());
        medicalRecord.setDiagnoseDesc(diagnosisSaveDTO.getDiagnoseDesc());
        medicalRecord.setMedicalFee(new java.math.BigDecimal("0.00")); // 暂时设置为0，后续可扩展
        medicalRecordMapper.insert(medicalRecord);

        // 3. 写入处方主表
        PetMedicalPrescriptionDO prescription = new PetMedicalPrescriptionDO();
        prescription.setPetId(appointment.getPetId());
        prescription.setUserId(appointment.getUserId());
        prescription.setEmpId(appointment.getEmpId());
        prescription.setStoreId(appointment.getStoreId());
        prescription.setPrescriptionNo(OrderNoGenerator.generatePrescriptionNo());
        prescription.setDiagnosis(diagnosisSaveDTO.getDiagnosis());
        prescription.setIssueTime(new Date());
        // 设置处方有效期为7天
        Date validTime = new Date();
        validTime.setTime(validTime.getTime() + 7 * 24 * 60 * 60 * 1000);
        prescription.setValidTime(validTime);
        prescription.setPresStatus("已开具");
        prescriptionMapper.insert(prescription);

        // 4. 写入处方药品明细表并扣减库存
        List<DiagnosisSaveDTO.PrescriptionDrugDTO> drugs = diagnosisSaveDTO.getDrugs();
        for (DiagnosisSaveDTO.PrescriptionDrugDTO drug : drugs) {
            // 校验药品库存
            PetDrugStoreDO drugStore = petDrugStoreMapper.selectByStoreIdAndDrugId(appointment.getStoreId(),
                    drug.getDrugId());
            if (drugStore == null) {
                throw new BusinessException("药品不存在");
            }
            if (drugStore.getStoreStock() < drug.getPrescriptionNum()) {
                throw new BusinessException("药品库存不足");
            }

            // 写入处方药品
            PrescriptionDrugDO prescriptionDrug = new PrescriptionDrugDO();
            prescriptionDrug.setPrescriptionId(prescription.getPrescriptionId());
            prescriptionDrug.setDrugId(drug.getDrugId());
            prescriptionDrug.setDrugNum(drug.getPrescriptionNum());
            prescriptionDrug.setDrugDosage(drug.getDosePerTime() + "mg/次，" + drug.getFrequencyPerDay() + "次/天");
            prescriptionDrugMapper.insert(prescriptionDrug);

            // 扣减库存
            petDrugStoreMapper.decreaseStock(appointment.getStoreId(), drug.getDrugId(), drug.getPrescriptionNum());
        }

        // 5. 更新预约的诊断状态
        appointment.setDiagnoseStatus("已诊断");
        appointmentMapper.updateById(appointment);

        // 6. 关联预约和医疗记录
        apptMedicalMapper.insertApptMedical(diagnosisSaveDTO.getApptId(), medicalRecord.getMedicalId());
    }
}