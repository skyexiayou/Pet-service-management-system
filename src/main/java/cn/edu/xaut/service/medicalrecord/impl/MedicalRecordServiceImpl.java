package cn.edu.xaut.service.medicalrecord.impl;

import cn.edu.xaut.domain.dto.medicalrecord.MedicalAppointmentDTO;
import cn.edu.xaut.domain.dto.medicalrecord.MedicalRecordDTO;
import cn.edu.xaut.domain.entity.appointment.AppointmentDO;
import cn.edu.xaut.domain.entity.employee.EmployeeDO;
import cn.edu.xaut.domain.entity.medicalrecord.MedicalRecordDO;
import cn.edu.xaut.domain.entity.pet.PetDO;
import cn.edu.xaut.domain.entity.store.StoreDO;
import cn.edu.xaut.domain.vo.PageResultVO;
import cn.edu.xaut.domain.vo.medicalrecord.MedicalRecordDetailVO;
import cn.edu.xaut.domain.vo.medicalrecord.MedicalRecordVO;
import cn.edu.xaut.exception.BusinessException;
import cn.edu.xaut.mapper.AppointmentMapper;
import cn.edu.xaut.mapper.ApptMedicalMapper;
import cn.edu.xaut.mapper.EmployeeMapper;
import cn.edu.xaut.mapper.MedicalRecordMapper;
import cn.edu.xaut.mapper.PetMapper;
import cn.edu.xaut.mapper.StoreMapper;
import cn.edu.xaut.service.medicalrecord.MedicalRecordService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 医疗记录Service实现类
 */
@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    @Autowired
    private MedicalRecordMapper medicalRecordMapper;

    @Autowired
    private PetMapper petMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private ApptMedicalMapper apptMedicalMapper;

    // ==================== 管理员功能 ====================

    @Override
    public PageResultVO<MedicalRecordVO> getMyMedicalRecords(Integer empId, Integer pageNum, Integer pageSize) {
        Page<MedicalRecordVO> page = new Page<>(pageNum, pageSize);
        Page<MedicalRecordVO> result = medicalRecordMapper.selectMedicalRecordsByEmpId(page, empId);
        return PageResultVO.<MedicalRecordVO>builder()
                .list(result.getRecords())
                .total(result.getTotal())
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();
    }

    @Override
    public PageResultVO<MedicalRecordVO> getAllMedicalRecords(Integer pageNum, Integer pageSize) {
        Page<MedicalRecordVO> page = new Page<>(pageNum, pageSize);
        Page<MedicalRecordVO> result = medicalRecordMapper.selectAllMedicalRecords(page);
        return PageResultVO.<MedicalRecordVO>builder()
                .list(result.getRecords())
                .total(result.getTotal())
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();
    }

    @Override
    public MedicalRecordDetailVO getMedicalRecordDetail(Integer medicalId) {
        MedicalRecordDetailVO detail = medicalRecordMapper.selectMedicalRecordDetail(medicalId);
        if (detail == null) {
            throw new BusinessException(1001, "医疗记录不存在");
        }
        return detail;
    }

    @Override
    @Transactional
    public Integer createMedicalRecord(MedicalRecordDTO dto) {
        // 验证外键
        validateForeignKeys(dto.getPetId(), dto.getEmpId(), dto.getStoreId());

        MedicalRecordDO record = new MedicalRecordDO();
        BeanUtils.copyProperties(dto, record);
        // 注意：status 字段在数据库中可能不存在，已设置 exist=false
        medicalRecordMapper.insert(record);
        return record.getMedicalId();
    }

    @Override
    @Transactional
    public void updateMedicalRecord(Integer medicalId, MedicalRecordDTO dto) {
        // 检查记录是否存在
        MedicalRecordDO existing = medicalRecordMapper.selectById(medicalId);
        if (existing == null) {
            throw new BusinessException(1001, "医疗记录不存在");
        }

        // 验证外键
        validateForeignKeys(dto.getPetId(), dto.getEmpId(), dto.getStoreId());

        MedicalRecordDO record = new MedicalRecordDO();
        BeanUtils.copyProperties(dto, record);
        record.setMedicalId(medicalId);
        medicalRecordMapper.updateById(record);
    }

    @Override
    @Transactional
    public void deleteMedicalRecord(Integer medicalId) {
        MedicalRecordDO existing = medicalRecordMapper.selectById(medicalId);
        if (existing == null) {
            throw new BusinessException(1001, "医疗记录不存在");
        }
        medicalRecordMapper.deleteById(medicalId);
    }

    // ==================== 用户功能 ====================

    @Override
    public List<MedicalRecordVO> getUserMedicalRecords(Integer userId) {
        return medicalRecordMapper.selectMedicalRecordsByUserId(userId);
    }

    @Override
    @Transactional
    public Integer createMedicalAppointment(MedicalAppointmentDTO dto) {
        // 检查用户是否有宠物
        if (!checkUserHasPets(dto.getUserId())) {
            throw new BusinessException(1005, "请先注册宠物信息");
        }

        // 验证宠物是否属于该用户
        PetDO pet = petMapper.selectById(dto.getPetId());
        if (pet == null) {
            throw new BusinessException(1002, "指定的宠物不存在");
        }
        if (!pet.getUserId().equals(dto.getUserId())) {
            throw new BusinessException(1007, "无权限操作该宠物");
        }

        // 验证医生和门店
        validateForeignKeys(dto.getPetId(), dto.getEmpId(), dto.getStoreId());

        // 1. 创建预约记录
        AppointmentDO appointment = new AppointmentDO();
        appointment.setUserId(dto.getUserId());
        appointment.setPetId(dto.getPetId());
        appointment.setStoreId(dto.getStoreId());
        appointment.setEmpId(dto.getEmpId());
        appointment.setApptTime(dto.getAppointmentTime());
        appointment.setApptStatus("待服务");
        appointment.setCreateTime(new Date());
        appointmentMapper.insert(appointment);

        // 2. 创建医疗记录
        MedicalRecordDO record = new MedicalRecordDO();
        record.setPetId(dto.getPetId());
        record.setEmpId(dto.getEmpId());
        record.setStoreId(dto.getStoreId());
        record.setMedicalTime(dto.getAppointmentTime());
        record.setDiagnosis(dto.getSymptoms() != null ? "症状描述: " + dto.getSymptoms() : "待诊断");
        record.setMedicalFee(new java.math.BigDecimal("0.00"));
        medicalRecordMapper.insert(record);

        // 3. 创建预约-医疗关联
        apptMedicalMapper.insertApptMedical(appointment.getApptId(), record.getMedicalId());

        return record.getMedicalId();
    }

    @Override
    public boolean checkUserHasPets(Integer userId) {
        LambdaQueryWrapper<PetDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PetDO::getUserId, userId);
        return petMapper.selectCount(wrapper) > 0;
    }

    // ==================== 医生端新增功能 ====================

    @Override
    public PageResultVO<MedicalRecordVO> getDoctorMedicalRecords(
            cn.edu.xaut.domain.dto.medicalrecord.DoctorMedicalRecordQueryDTO dto) {
        Page<MedicalRecordVO> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        Page<MedicalRecordVO> result = medicalRecordMapper.selectDoctorMedicalRecords(page, dto);

        return PageResultVO.<MedicalRecordVO>builder()
                .list(result.getRecords())
                .total(result.getTotal())
                .pageNum(dto.getPageNum())
                .pageSize(dto.getPageSize())
                .build();
    }

    @Override
    public MedicalRecordDetailVO getDoctorMedicalRecordDetail(Integer medicalId, Integer storeId) {
        MedicalRecordDetailVO detail = medicalRecordMapper.selectDoctorMedicalRecordDetail(medicalId, storeId);
        if (detail == null) {
            throw new BusinessException(1001, "医疗记录不存在或无权访问");
        }
        return detail;
    }

    // ==================== 用户端新增功能 ====================

    @Override
    public PageResultVO<MedicalRecordVO> getUserPetMedicalRecords(
            cn.edu.xaut.domain.dto.medicalrecord.UserMedicalRecordQueryDTO dto) {
        Page<MedicalRecordVO> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        Page<MedicalRecordVO> result = medicalRecordMapper.selectUserPetMedicalRecords(page, dto);

        return PageResultVO.<MedicalRecordVO>builder()
                .list(result.getRecords())
                .total(result.getTotal())
                .pageNum(dto.getPageNum())
                .pageSize(dto.getPageSize())
                .build();
    }

    @Override
    public MedicalRecordDetailVO getUserMedicalRecordDetail(Integer medicalId, Integer userId) {
        MedicalRecordDetailVO detail = medicalRecordMapper.selectUserMedicalRecordDetail(medicalId, userId);
        if (detail == null) {
            throw new BusinessException(1001, "医疗记录不存在或无权访问");
        }
        return detail;
    }

    // ==================== 私有方法 ====================

    private void validateForeignKeys(Integer petId, Integer empId, Integer storeId) {
        // 验证宠物
        PetDO pet = petMapper.selectById(petId);
        if (pet == null) {
            throw new BusinessException(1002, "指定的宠物不存在");
        }

        // 验证员工
        EmployeeDO emp = employeeMapper.selectById(empId);
        if (emp == null) {
            throw new BusinessException(1003, "指定的医生不存在");
        }

        // 验证门店
        StoreDO store = storeMapper.selectById(storeId);
        if (store == null) {
            throw new BusinessException(1004, "指定的门店不存在");
        }
    }
}
