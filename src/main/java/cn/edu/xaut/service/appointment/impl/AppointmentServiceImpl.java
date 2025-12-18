package cn.edu.xaut.service.appointment.impl;

import cn.edu.xaut.domain.dto.appointment.AppointmentCreateDTO;
import cn.edu.xaut.domain.dto.appointment.FosterParamDTO;
import cn.edu.xaut.domain.dto.appointment.MedicalParamDTO;
import cn.edu.xaut.domain.entity.appointment.AppointmentDO;
import cn.edu.xaut.domain.entity.apptbeauty.ApptBeautyDO;
import cn.edu.xaut.domain.entity.apptfoster.ApptFosterDO;
import cn.edu.xaut.domain.entity.apptmedical.ApptMedicalDO;
import cn.edu.xaut.domain.entity.fosterrecord.FosterRecordDO;
import cn.edu.xaut.domain.entity.leaverecord.LeaveRecordDO;
import cn.edu.xaut.domain.entity.medicalrecord.MedicalRecordDO;
import cn.edu.xaut.domain.entity.pet.PetDO;
import cn.edu.xaut.domain.entity.store.StoreDO;
import cn.edu.xaut.domain.vo.appointment.AppointmentDetailVO;
import cn.edu.xaut.domain.vo.appointment.AppointmentVO;
import cn.edu.xaut.domain.vo.appointment.BeautyDetailVO;
import cn.edu.xaut.domain.vo.appointment.FosterDetailVO;
import cn.edu.xaut.domain.vo.appointment.MedicalDetailVO;
import cn.edu.xaut.exception.BusinessException;
import cn.edu.xaut.mapper.*;
import cn.edu.xaut.service.appointment.AppointmentService;
import cn.edu.xaut.utils.TimeValidationUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 预约服务实现类
 */
@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private ApptBeautyMapper apptBeautyMapper;

    @Autowired
    private ApptFosterMapper apptFosterMapper;

    @Autowired
    private ApptMedicalMapper apptMedicalMapper;

    @Autowired
    private FosterRecordMapper fosterRecordMapper;

    @Autowired
    private MedicalRecordMapper medicalRecordMapper;

    @Autowired
    private LeaveRecordMapper leaveRecordMapper;

    @Autowired
    private cn.edu.xaut.mapper.PetMapper petMapper;

    @Autowired
    private cn.edu.xaut.mapper.StoreMapper storeMapper;

    /**
     * 创建多服务组合预约
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createAppointment(AppointmentCreateDTO dto) {
        // 1. 校验宠物是否存在
        PetDO pet = petMapper.selectById(dto.getPetId());
        if (pet == null) {
            throw new BusinessException(BusinessException.PET_NOT_FOUND);
        }

        // 2. 校验门店是否存在并获取营业时间
        StoreDO store = storeMapper.selectById(dto.getStoreId());
        if (store == null) {
            throw new BusinessException(BusinessException.STORE_NOT_FOUND);
        }

        // 3. 校验预约时间在营业时间内
        if (!TimeValidationUtil.isWithinBusinessHours(dto.getApptTime(), store.getBusinessHours())) {
            throw new BusinessException(BusinessException.APPT_TIME_NOT_IN_BUSINESS_HOURS);
        }

        // 4. 校验员工排班（如果分配了员工）
        if (dto.getEmpId() != null) {
            List<LeaveRecordDO> leaveRecords = leaveRecordMapper.selectLeaveRecordsByEmpIdAndTime(
                    dto.getEmpId(), dto.getApptTime(), dto.getApptTime()
            );
            if (TimeValidationUtil.isEmployeeOnLeave(dto.getEmpId(), dto.getApptTime(), leaveRecords)) {
                throw new BusinessException(BusinessException.EMPLOYEE_ON_LEAVE);
            }
        }

        // 5. 校验寄养日期（如果选择了寄养服务）
        if (dto.getFosterParam() != null) {
            FosterParamDTO fosterParam = dto.getFosterParam();
            if (!fosterParam.getStartDate().before(fosterParam.getEndDate())) {
                throw new BusinessException(BusinessException.FOSTER_DATE_INVALID);
            }
        }

        // 6. 校验医疗症状（如果选择了医疗服务）
        if (dto.getMedicalParam() != null) {
            MedicalParamDTO medicalParam = dto.getMedicalParam();
            if (medicalParam.getSymptom() == null || medicalParam.getSymptom().trim().isEmpty()) {
                throw new BusinessException(BusinessException.MEDICAL_SYMPTOM_EMPTY);
            }
        }

        // 7. 创建Appointment主记录
        AppointmentDO appointment = new AppointmentDO();
        appointment.setUserId(pet.getUserId());
        appointment.setPetId(dto.getPetId());
        appointment.setStoreId(dto.getStoreId());
        appointment.setEmpId(dto.getEmpId());
        appointment.setApptTime(dto.getApptTime());
        appointment.setApptStatus("待服务");
        appointment.setCreateTime(TimeValidationUtil.getServerTime());
        appointmentMapper.insert(appointment);

        Integer apptId = appointment.getApptId();

        // 8. 创建美容服务关联（如果选择了美容服务）
        if (dto.getBeautyIds() != null && !dto.getBeautyIds().isEmpty()) {
            List<ApptBeautyDO> apptBeautyList = new ArrayList<>();
            for (Integer beautyId : dto.getBeautyIds()) {
                ApptBeautyDO apptBeauty = new ApptBeautyDO();
                apptBeauty.setApptId(apptId);
                apptBeauty.setBeautyId(beautyId);
                apptBeautyList.add(apptBeauty);
            }
            apptBeautyMapper.batchInsert(apptBeautyList);
        }

        // 9. 创建寄养服务记录（如果选择了寄养服务）
        if (dto.getFosterParam() != null) {
            FosterParamDTO fosterParam = dto.getFosterParam();

            // 计算寄养天数和费用
            long days = ChronoUnit.DAYS.between(
                    fosterParam.getStartDate().toInstant(),
                    fosterParam.getEndDate().toInstant()
            );
            BigDecimal fosterFee = BigDecimal.valueOf(days).multiply(new BigDecimal("100"));

            // 创建寄养记录
            FosterRecordDO fosterRecord = new FosterRecordDO();
            fosterRecord.setPetId(dto.getPetId());
            fosterRecord.setStoreId(dto.getStoreId());
            fosterRecord.setEmpId(dto.getEmpId());
            fosterRecord.setStartDate(fosterParam.getStartDate());
            fosterRecord.setEndDate(fosterParam.getEndDate());
            fosterRecord.setFosterFee(fosterFee);
            fosterRecord.setFosterStatus("进行中");
            fosterRecord.setFosterRemarks(fosterParam.getFosterRemarks());
            fosterRecordMapper.insert(fosterRecord);

            // 创建预约-寄养关联
            ApptFosterDO apptFoster = new ApptFosterDO();
            apptFoster.setApptId(apptId);
            apptFoster.setFosterId(fosterRecord.getFosterId());
            apptFosterMapper.insert(apptFoster);
        }

        // 10. 创建医疗服务记录（如果选择了医疗服务）
        if (dto.getMedicalParam() != null) {
            MedicalParamDTO medicalParam = dto.getMedicalParam();

            // 创建医疗记录（初始状态，诊断结果等由管理员后续完善）
            MedicalRecordDO medicalRecord = new MedicalRecordDO();
            medicalRecord.setPetId(dto.getPetId());
            medicalRecord.setEmpId(dto.getEmpId());
            medicalRecord.setStoreId(dto.getStoreId());
            medicalRecord.setMedicalTime(dto.getApptTime());
            medicalRecord.setDiagnosis("待诊断：" + medicalParam.getSymptom());
            medicalRecord.setMedicalFee(new BigDecimal("0"));
            medicalRecordMapper.insert(medicalRecord);

            // 创建预约-医疗关联
            ApptMedicalDO apptMedical = new ApptMedicalDO();
            apptMedical.setApptId(apptId);
            apptMedical.setMedicalId(medicalRecord.getMedicalId());
            apptMedicalMapper.insert(apptMedical);
        }

        return apptId;
    }

    /**
     * 查询用户的预约列表
     */
    @Override
    public List<AppointmentVO> getAppointmentsByUserId(Integer userId) {
        return appointmentMapper.selectAppointmentsByUserId(userId);
    }

    /**
     * 查询预约详情（含服务明细）
     */
    @Override
    public AppointmentDetailVO getAppointmentDetail(Integer apptId) {
        // 查询预约基本信息
        AppointmentDetailVO detailVO = appointmentMapper.selectAppointmentDetail(apptId);
        if (detailVO == null) {
            throw new BusinessException(BusinessException.APPOINTMENT_NOT_FOUND);
        }

        // 查询美容服务明细
        List<BeautyDetailVO> beautyServices = apptBeautyMapper.selectBeautyDetailsByApptId(apptId);
        detailVO.setBeautyServices(beautyServices);

        // 查询寄养服务明细
        FosterDetailVO fosterService = apptFosterMapper.selectFosterDetailByApptId(apptId);
        detailVO.setFosterService(fosterService);

        // 查询医疗服务明细
        MedicalDetailVO medicalService = apptMedicalMapper.selectMedicalDetailByApptId(apptId);
        detailVO.setMedicalService(medicalService);

        return detailVO;
    }

    /**
     * 取消预约
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelAppointment(Integer apptId) {
        // 1. 查询预约信息
        AppointmentDO appointment = appointmentMapper.selectById(apptId);
        if (appointment == null) {
            throw new BusinessException(BusinessException.APPOINTMENT_NOT_FOUND);
        }

        // 2. 校验预约状态
        if (!"待服务".equals(appointment.getApptStatus())) {
            throw new BusinessException(BusinessException.APPT_STATUS_INVALID);
        }

        // 3. 校验24小时取消限制
        if (!TimeValidationUtil.canCancelWithin24Hours(appointment.getApptTime())) {
            throw new BusinessException(BusinessException.CANCEL_TIME_LIMIT);
        }

        // 4. 更新预约状态为"已取消"
        LambdaUpdateWrapper<AppointmentDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(AppointmentDO::getApptId, apptId)
                .set(AppointmentDO::getApptStatus, "已取消");
        appointmentMapper.update(null, updateWrapper);

        // 5. 如果包含寄养服务，同步更新寄养状态
        ApptFosterDO apptFoster = apptFosterMapper.selectOne(
                new LambdaQueryWrapper<ApptFosterDO>().eq(ApptFosterDO::getApptId, apptId)
        );
        if (apptFoster != null) {
            LambdaUpdateWrapper<FosterRecordDO> fosterUpdateWrapper = new LambdaUpdateWrapper<>();
            fosterUpdateWrapper.eq(FosterRecordDO::getFosterId, apptFoster.getFosterId())
                    .set(FosterRecordDO::getFosterStatus, "已取消");
            fosterRecordMapper.update(null, fosterUpdateWrapper);
        }
    }
}
