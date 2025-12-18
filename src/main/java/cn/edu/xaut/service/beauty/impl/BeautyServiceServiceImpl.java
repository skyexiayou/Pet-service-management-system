package cn.edu.xaut.service.beauty.impl;

import cn.edu.xaut.domain.entity.appointment.AppointmentDO;
import cn.edu.xaut.domain.vo.appointment.BeautyDetailVO;
import cn.edu.xaut.domain.vo.beauty.BeautyServiceDetailVO;
import cn.edu.xaut.domain.vo.beauty.BeautyServiceVO;
import cn.edu.xaut.exception.BusinessException;
import cn.edu.xaut.mapper.ApptBeautyMapper;
import cn.edu.xaut.mapper.AppointmentMapper;
import cn.edu.xaut.service.beauty.BeautyServiceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
