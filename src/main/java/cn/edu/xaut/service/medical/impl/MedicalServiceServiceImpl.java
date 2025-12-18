package cn.edu.xaut.service.medical.impl;

import cn.edu.xaut.domain.entity.apptmedical.ApptMedicalDO;
import cn.edu.xaut.domain.entity.medicalrecord.MedicalRecordDO;
import cn.edu.xaut.domain.vo.medical.MedicalServiceDetailVO;
import cn.edu.xaut.domain.vo.medical.MedicalServiceVO;
import cn.edu.xaut.exception.BusinessException;
import cn.edu.xaut.mapper.ApptMedicalMapper;
import cn.edu.xaut.mapper.AppointmentMapper;
import cn.edu.xaut.mapper.MedicalRecordMapper;
import cn.edu.xaut.service.medical.MedicalServiceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 医疗服务Service实现类
 */
@Service
public class MedicalServiceServiceImpl implements MedicalServiceService {

    @Autowired
    private MedicalRecordMapper medicalRecordMapper;

    @Autowired
    private ApptMedicalMapper apptMedicalMapper;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Override
    public List<MedicalServiceVO> getMedicalServicesByUserId(Integer userId) {
        return medicalRecordMapper.selectMedicalServicesByUserId(userId);
    }

    @Override
    public MedicalServiceDetailVO getMedicalServiceDetail(Integer medicalId) {
        // 查询医疗记录
        MedicalRecordDO medicalRecord = medicalRecordMapper.selectById(medicalId);
        if (medicalRecord == null) {
            throw new BusinessException(BusinessException.MEDICAL_NOT_FOUND);
        }

        // 查询关联的预约信息
        ApptMedicalDO apptMedical = apptMedicalMapper.selectOne(
                new LambdaQueryWrapper<ApptMedicalDO>().eq(ApptMedicalDO::getMedicalId, medicalId)
        );

        // 组装详情VO
        MedicalServiceDetailVO detailVO = new MedicalServiceDetailVO();
        BeanUtils.copyProperties(medicalRecord, detailVO);

        if (apptMedical != null) {
            detailVO.setApptId(apptMedical.getApptId());

            // 查询预约详情获取宠物、门店和员工信息
            cn.edu.xaut.domain.vo.appointment.AppointmentDetailVO apptDetail =
                    appointmentMapper.selectAppointmentDetail(apptMedical.getApptId());
            if (apptDetail != null) {
                detailVO.setPetName(apptDetail.getPetName());
                detailVO.setStoreName(apptDetail.getStoreName());
                detailVO.setStoreAddress(apptDetail.getStoreAddress());
                detailVO.setEmpName(apptDetail.getEmpName());
                detailVO.setEmpPosition(apptDetail.getEmpPosition());
            }
        }

        return detailVO;
    }
}
