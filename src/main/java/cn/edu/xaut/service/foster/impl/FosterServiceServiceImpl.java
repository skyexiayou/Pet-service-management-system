package cn.edu.xaut.service.foster.impl;

import cn.edu.xaut.domain.dto.foster.FosterAppointmentDTO;
import cn.edu.xaut.domain.entity.apptfoster.ApptFosterDO;
import cn.edu.xaut.domain.entity.appointment.AppointmentDO;
import cn.edu.xaut.domain.entity.fosterrecord.FosterRecordDO;
import cn.edu.xaut.domain.entity.pet.PetDO;
import cn.edu.xaut.domain.vo.foster.FosterServiceDetailVO;
import cn.edu.xaut.domain.vo.foster.FosterServiceVO;
import cn.edu.xaut.exception.BusinessException;
import cn.edu.xaut.mapper.ApptFosterMapper;
import cn.edu.xaut.mapper.AppointmentMapper;
import cn.edu.xaut.mapper.FosterRecordMapper;
import cn.edu.xaut.mapper.PetMapper;
import cn.edu.xaut.service.foster.FosterServiceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 寄养服务Service实现类
 */
@Service
public class FosterServiceServiceImpl implements FosterServiceService {

    @Autowired
    private FosterRecordMapper fosterRecordMapper;

    @Autowired
    private ApptFosterMapper apptFosterMapper;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private PetMapper petMapper;

    @Override
    public List<FosterServiceVO> getFosterServicesByUserId(Integer userId) {
        return fosterRecordMapper.selectFosterServicesByUserId(userId);
    }

    @Override
    public FosterServiceDetailVO getFosterServiceDetail(Integer fosterId) {
        // 查询寄养记录
        FosterRecordDO fosterRecord = fosterRecordMapper.selectById(fosterId);
        if (fosterRecord == null) {
            throw new BusinessException(BusinessException.FOSTER_NOT_FOUND);
        }

        // 查询关联的预约信息
        ApptFosterDO apptFoster = apptFosterMapper.selectOne(
                new LambdaQueryWrapper<ApptFosterDO>().eq(ApptFosterDO::getFosterId, fosterId)
        );

        // 组装详情VO
        FosterServiceDetailVO detailVO = new FosterServiceDetailVO();
        BeanUtils.copyProperties(fosterRecord, detailVO);

        if (apptFoster != null) {
            detailVO.setApptId(apptFoster.getApptId());

            // 查询预约详情获取门店和员工信息
            cn.edu.xaut.domain.vo.appointment.AppointmentDetailVO apptDetail =
                    appointmentMapper.selectAppointmentDetail(apptFoster.getApptId());
            if (apptDetail != null) {
                detailVO.setStoreName(apptDetail.getStoreName());
                detailVO.setStoreAddress(apptDetail.getStoreAddress());
                detailVO.setEmpName(apptDetail.getEmpName());
            }
        }

        return detailVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmPickup(Integer fosterId) {
        // 1. 查询寄养记录
        FosterRecordDO fosterRecord = fosterRecordMapper.selectById(fosterId);
        if (fosterRecord == null) {
            throw new BusinessException(BusinessException.FOSTER_NOT_FOUND);
        }

        // 2. 校验寄养状态
        if (!"进行中".equals(fosterRecord.getFosterStatus())) {
            throw new BusinessException(BusinessException.FOSTER_STATUS_INVALID);
        }

        // 3. 更新寄养状态为"已结束"
        LambdaUpdateWrapper<FosterRecordDO> fosterUpdateWrapper = new LambdaUpdateWrapper<>();
        fosterUpdateWrapper.eq(FosterRecordDO::getFosterId, fosterId)
                .set(FosterRecordDO::getFosterStatus, "已结束");
        fosterRecordMapper.update(null, fosterUpdateWrapper);

        // 4. 同步更新预约状态为"已完成"
        ApptFosterDO apptFoster = apptFosterMapper.selectOne(
                new LambdaQueryWrapper<ApptFosterDO>().eq(ApptFosterDO::getFosterId, fosterId)
        );
        if (apptFoster != null) {
            LambdaUpdateWrapper<AppointmentDO> apptUpdateWrapper = new LambdaUpdateWrapper<>();
            apptUpdateWrapper.eq(AppointmentDO::getApptId, apptFoster.getApptId())
                    .set(AppointmentDO::getApptStatus, "已完成");
            appointmentMapper.update(null, apptUpdateWrapper);
        }
    }

    @Override
    public boolean checkUserHasPets(Integer userId) {
        LambdaQueryWrapper<PetDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PetDO::getUserId, userId);
        Long count = petMapper.selectCount(wrapper);
        return count != null && count > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createFosterAppointment(FosterAppointmentDTO dto) {
        // 1. 验证用户是否有宠物
        if (!checkUserHasPets(dto.getUserId())) {
            throw new BusinessException("请先注册宠物信息");
        }

        // 2. 验证宠物是否属于该用户
        PetDO pet = petMapper.selectById(dto.getPetId());
        if (pet == null || !pet.getUserId().equals(dto.getUserId())) {
            throw new BusinessException("宠物信息不存在或不属于当前用户");
        }

        // 3. 验证日期
        if (dto.getEndDate().before(dto.getStartDate())) {
            throw new BusinessException("结束日期必须晚于开始日期");
        }

        // 4. 计算寄养费用（按天计算，每天100元）
        long diffInMillies = dto.getEndDate().getTime() - dto.getStartDate().getTime();
        long days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        if (days < 1) days = 1;
        BigDecimal fosterFee = BigDecimal.valueOf(days * 100);

        // 5. 创建预约记录
        AppointmentDO appointment = new AppointmentDO();
        appointment.setUserId(dto.getUserId());
        appointment.setPetId(dto.getPetId());
        appointment.setStoreId(dto.getStoreId());
        appointment.setEmpId(dto.getEmpId());
        appointment.setApptTime(dto.getStartDate());
        appointment.setApptStatus("待服务");
        appointmentMapper.insert(appointment);

        // 6. 创建寄养记录
        FosterRecordDO fosterRecord = new FosterRecordDO();
        fosterRecord.setPetId(dto.getPetId());
        fosterRecord.setStoreId(dto.getStoreId());
        fosterRecord.setEmpId(dto.getEmpId());
        fosterRecord.setStartDate(dto.getStartDate());
        fosterRecord.setEndDate(dto.getEndDate());
        fosterRecord.setFosterFee(fosterFee);
        fosterRecord.setFosterStatus("进行中");
        fosterRecord.setFosterRemarks(dto.getRemarks());
        fosterRecordMapper.insert(fosterRecord);

        // 7. 创建预约-寄养关联
        ApptFosterDO apptFoster = new ApptFosterDO();
        apptFoster.setApptId(appointment.getApptId());
        apptFoster.setFosterId(fosterRecord.getFosterId());
        apptFosterMapper.insert(apptFoster);

        return fosterRecord.getFosterId();
    }
}
