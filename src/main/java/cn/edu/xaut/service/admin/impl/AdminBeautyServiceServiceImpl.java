package cn.edu.xaut.service.admin.impl;

import cn.edu.xaut.domain.dto.admin.AdminBeautyAppointmentDTO;
import cn.edu.xaut.domain.dto.admin.BeautyServiceDTO;
import cn.edu.xaut.domain.entity.apptbeauty.ApptBeautyDO;
import cn.edu.xaut.domain.entity.appointment.AppointmentDO;
import cn.edu.xaut.domain.entity.beauty.BeautyDO;
import cn.edu.xaut.domain.vo.PageResultVO;
import cn.edu.xaut.domain.vo.admin.AdminBeautyServiceDetailVO;
import cn.edu.xaut.domain.vo.admin.AdminBeautyServiceVO;
import cn.edu.xaut.domain.vo.appointment.BeautyDetailVO;
import cn.edu.xaut.exception.BusinessException;
import cn.edu.xaut.mapper.ApptBeautyMapper;
import cn.edu.xaut.mapper.AppointmentMapper;
import cn.edu.xaut.mapper.BeautyMapper;
import cn.edu.xaut.service.admin.AdminBeautyServiceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 管理员美容服务Service实现类
 */
@Service
public class AdminBeautyServiceServiceImpl implements AdminBeautyServiceService {

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private ApptBeautyMapper apptBeautyMapper;

    @Autowired
    private BeautyMapper beautyMapper;

    @Override
    public PageResultVO<AdminBeautyServiceVO> getMyBeautyServices(Integer empId, Integer pageNum, Integer pageSize) {
        // 通过中间表查询该员工负责的所有美容预约
        // 先获取所有有美容项目的预约ID
        List<ApptBeautyDO> apptBeauties = apptBeautyMapper.selectList(null);
        List<Integer> beautyApptIds = apptBeauties.stream()
                .map(ApptBeautyDO::getApptId)
                .distinct()
                .collect(Collectors.toList());
        
        if (beautyApptIds.isEmpty()) {
            return paginate(new ArrayList<>(), pageNum, pageSize);
        }
        
        // 查询该员工负责的美容预约
        LambdaQueryWrapper<AppointmentDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AppointmentDO::getEmpId, empId)
               .in(AppointmentDO::getApptId, beautyApptIds)
               .orderByDesc(AppointmentDO::getApptTime);
        
        List<AppointmentDO> appointments = appointmentMapper.selectList(wrapper);
        List<AdminBeautyServiceVO> allRecords = convertToAdminBeautyServiceVOList(appointments);
        
        return paginate(allRecords, pageNum, pageSize);
    }

    @Override
    public PageResultVO<AdminBeautyServiceVO> getAllBeautyServices(Integer pageNum, Integer pageSize) {
        // 通过中间表查询所有美容预约
        List<ApptBeautyDO> apptBeauties = apptBeautyMapper.selectList(null);
        List<Integer> beautyApptIds = apptBeauties.stream()
                .map(ApptBeautyDO::getApptId)
                .distinct()
                .collect(Collectors.toList());
        
        if (beautyApptIds.isEmpty()) {
            return paginate(new ArrayList<>(), pageNum, pageSize);
        }
        
        // 查询所有美容预约
        LambdaQueryWrapper<AppointmentDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(AppointmentDO::getApptId, beautyApptIds)
               .orderByDesc(AppointmentDO::getApptTime);
        
        List<AppointmentDO> appointments = appointmentMapper.selectList(wrapper);
        List<AdminBeautyServiceVO> allRecords = convertToAdminBeautyServiceVOList(appointments);
        
        return paginate(allRecords, pageNum, pageSize);
    }

    private List<AdminBeautyServiceVO> convertToAdminBeautyServiceVOList(List<AppointmentDO> appointments) {
        List<AdminBeautyServiceVO> result = new ArrayList<>();
        
        for (AppointmentDO appt : appointments) {
            // 查询预约详情
            cn.edu.xaut.domain.vo.appointment.AppointmentDetailVO apptDetail = 
                    appointmentMapper.selectAppointmentDetail(appt.getApptId());
            
            if (apptDetail != null) {
                AdminBeautyServiceVO vo = new AdminBeautyServiceVO();
                vo.setApptId(appt.getApptId());
                vo.setPetId(apptDetail.getPetId());
                vo.setPetName(apptDetail.getPetName());
                vo.setUserId(apptDetail.getUserId());
                vo.setUserName(apptDetail.getUserName());
                vo.setStoreId(appt.getStoreId());
                vo.setStoreName(apptDetail.getStoreName());
                vo.setEmpId(appt.getEmpId());
                vo.setEmpName(apptDetail.getEmpName());
                vo.setApptTime(appt.getApptTime());
                vo.setApptStatus(appt.getApptStatus());
                
                // 查询美容项目
                List<BeautyDetailVO> beautyDetails = apptBeautyMapper.selectBeautyDetailsByApptId(appt.getApptId());
                if (beautyDetails != null && !beautyDetails.isEmpty()) {
                    vo.setBeautyNames(beautyDetails.stream()
                            .map(BeautyDetailVO::getBeautyName)
                            .collect(Collectors.toList()));
                    vo.setTotalPrice(beautyDetails.stream()
                            .map(BeautyDetailVO::getPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add));
                }
                
                result.add(vo);
            }
        }
        
        return result;
    }

    @Override
    public AdminBeautyServiceDetailVO getBeautyServiceDetail(Integer apptId) {
        // 查询预约详情
        cn.edu.xaut.domain.vo.appointment.AppointmentDetailVO apptDetail = 
                appointmentMapper.selectAppointmentDetail(apptId);
        
        if (apptDetail == null) {
            throw new BusinessException("美容服务记录不存在");
        }
        
        AdminBeautyServiceDetailVO detailVO = new AdminBeautyServiceDetailVO();
        detailVO.setApptId(apptId);
        detailVO.setPetId(apptDetail.getPetId());
        detailVO.setPetName(apptDetail.getPetName());
        detailVO.setBreed(apptDetail.getBreed());
        detailVO.setGender(apptDetail.getGender());
        detailVO.setUserId(apptDetail.getUserId());
        detailVO.setUserName(apptDetail.getUserName());
        detailVO.setUserPhone(apptDetail.getUserPhone());
        detailVO.setStoreId(apptDetail.getStoreId());
        detailVO.setStoreName(apptDetail.getStoreName());
        detailVO.setStoreAddress(apptDetail.getStoreAddress());
        detailVO.setStorePhone(apptDetail.getStorePhone());
        detailVO.setEmpId(apptDetail.getEmpId());
        detailVO.setEmpName(apptDetail.getEmpName());
        detailVO.setEmpPhone(apptDetail.getEmpPhone());
        detailVO.setEmpPosition(apptDetail.getEmpPosition());
        detailVO.setApptTime(apptDetail.getApptTime());
        detailVO.setApptStatus(apptDetail.getApptStatus());
        detailVO.setRemarks(apptDetail.getRemarks());
        
        // 查询美容项目明细
        List<BeautyDetailVO> beautyDetails = apptBeautyMapper.selectBeautyDetailsByApptId(apptId);
        detailVO.setBeautyDetails(beautyDetails);
        
        if (beautyDetails != null && !beautyDetails.isEmpty()) {
            detailVO.setTotalPrice(beautyDetails.stream()
                    .map(BeautyDetailVO::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
        }
        
        return detailVO;
    }

    @Override
    @Transactional
    public Integer createBeautyService(BeautyServiceDTO dto, Integer empId) {
        // 这个方法用于创建美容项目（Beauty表），不是预约
        BeautyDO beauty = new BeautyDO();
        BeanUtils.copyProperties(dto, beauty);
        beautyMapper.insert(beauty);
        return beauty.getBeautyId();
    }

    @Override
    @Transactional
    public Integer updateBeautyService(Integer apptId, BeautyServiceDTO dto) {
        // 这个方法用于更新美容项目（Beauty表）
        BeautyDO existing = beautyMapper.selectById(apptId);
        if (existing == null) {
            throw new BusinessException("美容项目不存在");
        }
        
        BeautyDO beauty = new BeautyDO();
        BeanUtils.copyProperties(dto, beauty);
        beauty.setBeautyId(apptId);
        return beautyMapper.updateById(beauty);
    }

    @Override
    @Transactional
    public Integer deleteBeautyService(Integer apptId) {
        // 检查是否是预约ID还是美容项目ID
        AppointmentDO appointment = appointmentMapper.selectById(apptId);
        if (appointment != null) {
            // 删除预约-美容关联
            LambdaQueryWrapper<ApptBeautyDO> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ApptBeautyDO::getApptId, apptId);
            apptBeautyMapper.delete(wrapper);
            
            // 删除预约
            return appointmentMapper.deleteById(apptId);
        }
        
        // 如果不是预约，尝试删除美容项目
        BeautyDO beauty = beautyMapper.selectById(apptId);
        if (beauty == null) {
            throw new BusinessException("记录不存在");
        }
        return beautyMapper.deleteById(apptId);
    }

    @Override
    public PageResultVO<BeautyDO> getAllBeautyItems(Integer pageNum, Integer pageSize) {
        List<BeautyDO> allItems = beautyMapper.selectAllBeauties();
        
        int total = allItems.size();
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, total);
        
        List<BeautyDO> pageItems = start < total ? allItems.subList(start, end) : List.of();
        
        PageResultVO<BeautyDO> result = new PageResultVO<>();
        result.setList(pageItems);
        result.setTotal((long) total);
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);
        
        return result;
    }

    private <T> PageResultVO<T> paginate(List<T> allRecords, Integer pageNum, Integer pageSize) {
        int total = allRecords.size();
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, total);
        
        List<T> pageRecords = start < total ? allRecords.subList(start, end) : List.of();
        
        PageResultVO<T> result = new PageResultVO<>();
        result.setList(pageRecords);
        result.setTotal((long) total);
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);
        
        return result;
    }
}
