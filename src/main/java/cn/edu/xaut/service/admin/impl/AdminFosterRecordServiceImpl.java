package cn.edu.xaut.service.admin.impl;

import cn.edu.xaut.domain.dto.admin.FosterRecordDTO;
import cn.edu.xaut.domain.entity.fosterrecord.FosterRecordDO;
import cn.edu.xaut.domain.vo.PageResultVO;
import cn.edu.xaut.domain.vo.admin.AdminFosterRecordDetailVO;
import cn.edu.xaut.domain.vo.admin.AdminFosterRecordVO;
import cn.edu.xaut.exception.BusinessException;
import cn.edu.xaut.mapper.ApptFosterMapper;
import cn.edu.xaut.mapper.FosterRecordMapper;
import cn.edu.xaut.service.admin.AdminFosterRecordService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 管理员寄养记录服务实现类
 */
@Service
public class AdminFosterRecordServiceImpl implements AdminFosterRecordService {

    @Autowired
    private FosterRecordMapper fosterRecordMapper;

    @Autowired
    private ApptFosterMapper apptFosterMapper;

    @Override
    public PageResultVO<AdminFosterRecordVO> getMyFosterRecords(Integer empId, Integer pageNum, Integer pageSize) {
        // 获取所有记录
        List<AdminFosterRecordVO> allRecords = fosterRecordMapper.selectFosterRecordsByEmpId(empId);
        
        // 手动分页
        int total = allRecords.size();
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, total);
        
        List<AdminFosterRecordVO> pageRecords = start < total ? allRecords.subList(start, end) : List.of();
        
        PageResultVO<AdminFosterRecordVO> result = new PageResultVO<>();
        result.setList(pageRecords);
        result.setTotal((long) total);
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);
        
        return result;
    }

    @Override
    public PageResultVO<AdminFosterRecordVO> getAllFosterRecords(Integer pageNum, Integer pageSize) {
        // 获取所有记录
        List<AdminFosterRecordVO> allRecords = fosterRecordMapper.selectAllFosterRecords();
        
        // 手动分页
        int total = allRecords.size();
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, total);
        
        List<AdminFosterRecordVO> pageRecords = start < total ? allRecords.subList(start, end) : List.of();
        
        PageResultVO<AdminFosterRecordVO> result = new PageResultVO<>();
        result.setList(pageRecords);
        result.setTotal((long) total);
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);
        
        return result;
    }

    @Override
    public AdminFosterRecordDetailVO getFosterRecordDetail(Integer fosterId) {
        AdminFosterRecordDetailVO detail = fosterRecordMapper.selectFosterRecordDetail(fosterId);
        if (detail == null) {
            throw new BusinessException("寄养记录不存在");
        }
        return detail;
    }

    @Override
    @Transactional
    public Integer createFosterRecord(FosterRecordDTO dto, Integer empId) {
        // 验证日期
        if (dto.getEndDate() != null && dto.getStartDate() != null 
                && dto.getEndDate().before(dto.getStartDate())) {
            throw new BusinessException("结束日期必须晚于开始日期");
        }
        
        FosterRecordDO fosterRecord = new FosterRecordDO();
        BeanUtils.copyProperties(dto, fosterRecord);
        
        // 如果没有指定员工ID，使用当前管理员ID
        if (fosterRecord.getEmpId() == null && empId != null) {
            fosterRecord.setEmpId(empId);
        }
        
        // 设置默认状态
        if (fosterRecord.getFosterStatus() == null) {
            fosterRecord.setFosterStatus("进行中");
        }
        
        fosterRecordMapper.insert(fosterRecord);
        return fosterRecord.getFosterId();
    }

    @Override
    @Transactional
    public Integer updateFosterRecord(Integer fosterId, FosterRecordDTO dto) {
        // 检查记录是否存在
        FosterRecordDO existing = fosterRecordMapper.selectById(fosterId);
        if (existing == null) {
            throw new BusinessException("寄养记录不存在");
        }
        
        // 验证日期
        if (dto.getEndDate() != null && dto.getStartDate() != null 
                && dto.getEndDate().before(dto.getStartDate())) {
            throw new BusinessException("结束日期必须晚于开始日期");
        }
        
        FosterRecordDO fosterRecord = new FosterRecordDO();
        BeanUtils.copyProperties(dto, fosterRecord);
        fosterRecord.setFosterId(fosterId);
        
        return fosterRecordMapper.updateById(fosterRecord);
    }

    @Override
    @Transactional
    public Integer deleteFosterRecord(Integer fosterId) {
        // 检查记录是否存在
        FosterRecordDO existing = fosterRecordMapper.selectById(fosterId);
        if (existing == null) {
            throw new BusinessException("寄养记录不存在");
        }
        
        // 删除关联的预约-寄养中间表记录
        LambdaQueryWrapper<cn.edu.xaut.domain.entity.apptfoster.ApptFosterDO> wrapper = 
                new LambdaQueryWrapper<>();
        wrapper.eq(cn.edu.xaut.domain.entity.apptfoster.ApptFosterDO::getFosterId, fosterId);
        apptFosterMapper.delete(wrapper);
        
        // 删除寄养记录
        return fosterRecordMapper.deleteById(fosterId);
    }
}
