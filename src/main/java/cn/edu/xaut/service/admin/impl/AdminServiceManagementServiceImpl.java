package cn.edu.xaut.service.admin.impl;

import cn.edu.xaut.domain.dto.admin.FosterDailyUpdateDTO;
import cn.edu.xaut.domain.dto.admin.MedicalRecordCreateDTO;
import cn.edu.xaut.domain.entity.appointment.AppointmentDO;
import cn.edu.xaut.domain.entity.fosterrecord.FosterRecordDO;
import cn.edu.xaut.domain.entity.medicalrecord.MedicalRecordDO;
import cn.edu.xaut.exception.BusinessException;
import cn.edu.xaut.mapper.AppointmentMapper;
import cn.edu.xaut.mapper.ApptMedicalMapper;
import cn.edu.xaut.mapper.FosterRecordMapper;
import cn.edu.xaut.mapper.MedicalRecordMapper;
import cn.edu.xaut.service.admin.AdminServiceManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 管理员专项服务管理Service实现
 * 创建时间：2025-12-18
 */
@Service
@RequiredArgsConstructor
public class AdminServiceManagementServiceImpl implements AdminServiceManagementService {

    private final AppointmentMapper appointmentMapper;
    private final FosterRecordMapper fosterRecordMapper;
    private final MedicalRecordMapper medicalRecordMapper;
    private final ApptMedicalMapper apptMedicalMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completeBeautyService(Integer apptId, Integer storeId) {
        // 查询预约信息
        AppointmentDO appointment = appointmentMapper.selectById(apptId);
        if (appointment == null) {
            throw new BusinessException("预约不存在");
        }

        // 验证门店权限
        if (!appointment.getStoreId().equals(storeId)) {
            throw new BusinessException("无权限操作其他门店的预约");
        }

        // 验证预约状态
        if (!"待服务".equals(appointment.getApptStatus())) {
            throw new BusinessException("预约状态不是'待服务'，无法完成服务");
        }

        // 更新预约状态为已完成
        appointment.setApptStatus("已完成");
        appointmentMapper.updateById(appointment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFosterDailyStatus(Integer fosterId, FosterDailyUpdateDTO updateDTO, Integer storeId) {
        // 查询寄养记录
        FosterRecordDO fosterRecord = fosterRecordMapper.selectById(fosterId);
        if (fosterRecord == null) {
            throw new BusinessException("寄养记录不存在");
        }

        // 验证门店权限
        if (!fosterRecord.getStoreId().equals(storeId)) {
            throw new BusinessException("无权限操作其他门店的寄养记录");
        }

        // 验证寄养状态
        if (!"进行中".equals(fosterRecord.getFosterStatus())) {
            throw new BusinessException("寄养状态不是'进行中'，无法更新");
        }

        // 追加每日状态（格式：原有内容\n新内容）
        String currentStatus = fosterRecord.getDailyStatus();
        String newStatus = currentStatus == null || currentStatus.trim().isEmpty() 
            ? updateDTO.getDailyStatus() 
            : currentStatus + "\n" + updateDTO.getDailyStatus();
        
        fosterRecord.setDailyStatus(newStatus);
        fosterRecordMapper.updateById(fosterRecord);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createMedicalRecord(MedicalRecordCreateDTO createDTO, Integer storeId) {
        // 查询预约信息
        AppointmentDO appointment = appointmentMapper.selectById(createDTO.getApptId());
        if (appointment == null) {
            throw new BusinessException("预约不存在");
        }

        // 验证门店权限
        if (!appointment.getStoreId().equals(storeId)) {
            throw new BusinessException("无权限操作其他门店的预约");
        }

        // 验证是否已有医疗记录
        Integer existingMedicalId = apptMedicalMapper.selectMedicalIdByApptId(createDTO.getApptId());
        if (existingMedicalId != null) {
            throw new BusinessException("该预约已有医疗记录，无法重复创建");
        }

        // 创建医疗记录
        MedicalRecordDO medicalRecord = new MedicalRecordDO();
        medicalRecord.setPetId(appointment.getPetId());
        medicalRecord.setEmpId(appointment.getEmpId() != null ? appointment.getEmpId() : 1); // 默认员工ID
        medicalRecord.setStoreId(storeId);
        medicalRecord.setMedicalTime(new Date());
        medicalRecord.setDiagnosis(createDTO.getDiagnosis());
        medicalRecord.setMedication(createDTO.getMedication());
        medicalRecord.setMedicalFee(createDTO.getMedicalFee());
        medicalRecord.setFollowUpAdvice(createDTO.getFollowUpAdvice());

        medicalRecordMapper.insert(medicalRecord);

        // 关联预约和医疗记录
        apptMedicalMapper.insertApptMedical(createDTO.getApptId(), medicalRecord.getMedicalId());

        return medicalRecord.getMedicalId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void appendMedicalRemark(Integer medicalId, String remark, Integer storeId) {
        // 查询医疗记录
        MedicalRecordDO medicalRecord = medicalRecordMapper.selectById(medicalId);
        if (medicalRecord == null) {
            throw new BusinessException("医疗记录不存在");
        }

        // 验证门店权限
        if (!medicalRecord.getStoreId().equals(storeId)) {
            throw new BusinessException("无权限操作其他门店的医疗记录");
        }

        // 追加备注到复诊建议字段（格式：原有内容\n补充：新内容）
        String currentAdvice = medicalRecord.getFollowUpAdvice();
        String newAdvice = currentAdvice == null || currentAdvice.trim().isEmpty() 
            ? "补充：" + remark 
            : currentAdvice + "\n补充：" + remark;
        
        medicalRecord.setFollowUpAdvice(newAdvice);
        medicalRecordMapper.updateById(medicalRecord);
    }
}
