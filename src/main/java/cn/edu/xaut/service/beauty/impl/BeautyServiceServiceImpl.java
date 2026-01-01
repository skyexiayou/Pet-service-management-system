package cn.edu.xaut.service.beauty.impl;

import cn.edu.xaut.domain.dto.beauty.BeautyAppointmentDTO;
import cn.edu.xaut.domain.entity.apptbeauty.ApptBeautyDO;
import cn.edu.xaut.domain.entity.appointment.AppointmentDO;
import cn.edu.xaut.domain.entity.beauty.BeautyDO;
import cn.edu.xaut.domain.entity.pet.PetDO;
import cn.edu.xaut.domain.vo.appointment.BeautyDetailVO;
import cn.edu.xaut.domain.vo.beauty.BeautyServiceDetailVO;
import cn.edu.xaut.domain.vo.beauty.BeautyServiceVO;
import cn.edu.xaut.exception.BusinessException;
import cn.edu.xaut.mapper.ApptBeautyMapper;
import cn.edu.xaut.mapper.AppointmentMapper;
import cn.edu.xaut.mapper.BeautyMapper;
import cn.edu.xaut.mapper.PetMapper;
import cn.edu.xaut.service.beauty.BeautyServiceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 美容服务Service实现类
 */
@Service
public class BeautyServiceServiceImpl implements BeautyServiceService {

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private ApptBeautyMapper apptBeautyMapper;

    @Autowired
    private BeautyMapper beautyMapper;

    @Autowired
    private PetMapper petMapper;

    @Override
    public List<BeautyServiceVO> getBeautyServicesByUserId(Integer userId) {
        // 查询用户的所有预约
        List<cn.edu.xaut.domain.vo.appointment.AppointmentVO> appointments =
                appointmentMapper.selectAppointmentsByUserId(userId);

        List<BeautyServiceVO> result = new ArrayList<>();

        for (cn.edu.xaut.domain.vo.appointment.AppointmentVO appt : appointments) {
            // 查询该预约的美容服务明细
            List<BeautyDetailVO> beautyDetails = apptBeautyMapper.selectBeautyDetailsByApptId(appt.getApptId());

            if (beautyDetails != null && !beautyDetails.isEmpty()) {
                BeautyServiceVO vo = new BeautyServiceVO();
                vo.setApptId(appt.getApptId());
                vo.setPetId(appt.getPetId());
                vo.setPetName(appt.getPetName());
                vo.setApptTime(appt.getApptTime());
                vo.setApptStatus(appt.getApptStatus());
                vo.setStoreName(appt.getStoreName());

                // 提取美容项目名称列表
                List<String> beautyNames = beautyDetails.stream()
                        .map(BeautyDetailVO::getBeautyName)
                        .collect(Collectors.toList());
                vo.setBeautyNames(beautyNames);

                // 计算总价格
                BigDecimal totalPrice = beautyDetails.stream()
                        .map(BeautyDetailVO::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                vo.setTotalPrice(totalPrice);

                result.add(vo);
            }
        }

        return result;
    }

    @Override
    public BeautyServiceDetailVO getBeautyServiceDetail(Integer apptId) {
        // 查询预约基本信息
        AppointmentDO appointment = appointmentMapper.selectById(apptId);
        if (appointment == null) {
            throw new BusinessException(BusinessException.APPOINTMENT_NOT_FOUND);
        }

        // 查询美容服务明细
        List<BeautyDetailVO> beautyDetails = apptBeautyMapper.selectBeautyDetailsByApptId(apptId);

        // 组装详情VO
        cn.edu.xaut.domain.vo.appointment.AppointmentDetailVO apptDetail =
                appointmentMapper.selectAppointmentDetail(apptId);

        BeautyServiceDetailVO detailVO = new BeautyServiceDetailVO();
        detailVO.setApptId(apptId);
        detailVO.setPetId(apptDetail.getPetId());
        detailVO.setPetName(apptDetail.getPetName());
        detailVO.setApptTime(apptDetail.getApptTime());
        detailVO.setApptStatus(apptDetail.getApptStatus());
        detailVO.setStoreName(apptDetail.getStoreName());
        detailVO.setStoreAddress(apptDetail.getStoreAddress());
        detailVO.setEmpName(apptDetail.getEmpName());
        detailVO.setBeautyDetails(beautyDetails);

        return detailVO;
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
    public Integer createBeautyAppointment(BeautyAppointmentDTO dto) {
        // 1. 验证用户是否有宠物
        if (!checkUserHasPets(dto.getUserId())) {
            throw new BusinessException("请先注册宠物信息");
        }

        // 2. 验证宠物是否属于该用户
        PetDO pet = petMapper.selectById(dto.getPetId());
        if (pet == null || !pet.getUserId().equals(dto.getUserId())) {
            throw new BusinessException("宠物信息不存在或不属于当前用户");
        }

        // 3. 验证美容项目是否存在
        if (dto.getBeautyIds() == null || dto.getBeautyIds().isEmpty()) {
            throw new BusinessException("请选择至少一个美容项目");
        }

        // 4. 创建预约记录
        AppointmentDO appointment = new AppointmentDO();
        appointment.setUserId(dto.getUserId());
        appointment.setPetId(dto.getPetId());
        appointment.setStoreId(dto.getStoreId());
        appointment.setEmpId(dto.getEmpId());
        appointment.setApptTime(dto.getApptTime());
        appointment.setApptStatus("待服务");
        appointmentMapper.insert(appointment);

        // 5. 创建预约-美容关联
        List<ApptBeautyDO> apptBeautyList = new ArrayList<>();
        for (Integer beautyId : dto.getBeautyIds()) {
            ApptBeautyDO apptBeauty = new ApptBeautyDO();
            apptBeauty.setApptId(appointment.getApptId());
            apptBeauty.setBeautyId(beautyId);
            apptBeautyList.add(apptBeauty);
        }
        apptBeautyMapper.batchInsert(apptBeautyList);

        return appointment.getApptId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateBeautyAppointment(Integer apptId, BeautyAppointmentDTO dto) {
        // 1. 检查预约是否存在
        AppointmentDO appointment = appointmentMapper.selectById(apptId);
        if (appointment == null) {
            throw new BusinessException("预约记录不存在");
        }

        // 2. 检查预约状态
        if (!"待服务".equals(appointment.getApptStatus())) {
            throw new BusinessException("当前状态不允许修改");
        }

        // 3. 更新预约信息
        LambdaUpdateWrapper<AppointmentDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(AppointmentDO::getApptId, apptId)
                .set(AppointmentDO::getPetId, dto.getPetId())
                .set(AppointmentDO::getStoreId, dto.getStoreId())
                .set(AppointmentDO::getEmpId, dto.getEmpId())
                .set(AppointmentDO::getApptTime, dto.getApptTime());
        appointmentMapper.update(null, updateWrapper);

        // 4. 更新美容项目关联
        if (dto.getBeautyIds() != null && !dto.getBeautyIds().isEmpty()) {
            // 删除旧的关联
            LambdaQueryWrapper<ApptBeautyDO> deleteWrapper = new LambdaQueryWrapper<>();
            deleteWrapper.eq(ApptBeautyDO::getApptId, apptId);
            apptBeautyMapper.delete(deleteWrapper);

            // 创建新的关联
            List<ApptBeautyDO> apptBeautyList = new ArrayList<>();
            for (Integer beautyId : dto.getBeautyIds()) {
                ApptBeautyDO apptBeauty = new ApptBeautyDO();
                apptBeauty.setApptId(apptId);
                apptBeauty.setBeautyId(beautyId);
                apptBeautyList.add(apptBeauty);
            }
            apptBeautyMapper.batchInsert(apptBeautyList);
        }

        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer cancelBeautyAppointment(Integer apptId) {
        // 1. 检查预约是否存在
        AppointmentDO appointment = appointmentMapper.selectById(apptId);
        if (appointment == null) {
            throw new BusinessException("预约记录不存在");
        }

        // 2. 检查预约状态
        if (!"待服务".equals(appointment.getApptStatus())) {
            throw new BusinessException("当前状态不允许取消");
        }

        // 3. 更新预约状态为已取消
        LambdaUpdateWrapper<AppointmentDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(AppointmentDO::getApptId, apptId)
                .set(AppointmentDO::getApptStatus, "已取消");
        return appointmentMapper.update(null, updateWrapper);
    }

    @Override
    public List<BeautyDO> getAllBeautyItems() {
        return beautyMapper.selectAllBeauties();
    }
}
