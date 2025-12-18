package cn.edu.xaut.service.foster.impl;

import cn.edu.xaut.domain.entity.apptfoster.ApptFosterDO;
import cn.edu.xaut.domain.entity.appointment.AppointmentDO;
import cn.edu.xaut.domain.entity.fosterrecord.FosterRecordDO;
import cn.edu.xaut.domain.vo.foster.FosterServiceDetailVO;
import cn.edu.xaut.domain.vo.foster.FosterServiceVO;
import cn.edu.xaut.exception.BusinessException;
import cn.edu.xaut.mapper.ApptFosterMapper;
import cn.edu.xaut.mapper.AppointmentMapper;
import cn.edu.xaut.mapper.FosterRecordMapper;
import cn.edu.xaut.service.foster.FosterServiceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
