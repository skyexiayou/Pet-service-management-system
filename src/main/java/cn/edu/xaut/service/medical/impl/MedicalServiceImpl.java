package cn.edu.xaut.service.medical.impl;

import cn.edu.xaut.domain.dto.medical.MedicalDTO;
import cn.edu.xaut.domain.entity.medical.MedicalDO;
import cn.edu.xaut.domain.vo.PageResultVO;
import cn.edu.xaut.mapper.MedicalMapper;
import cn.edu.xaut.service.medical.MedicalService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 宠物医疗业务层实现类
 * 实现MedicalService接口，处理宠物医疗信息的CRUD、分页查询等核心业务逻辑
 *
 * @date 2025-12-18
 */
@Service
public class MedicalServiceImpl implements MedicalService {

    @Autowired
    private MedicalMapper medicalMapper;

    /**
     * 根据医疗ID查询单个宠物医疗信息
     */
    @Override
    public MedicalDO getMedicalById(Integer medicalId) {
        return medicalMapper.selectMedicalById(medicalId);
    }

    /**
     * 根据医疗类型查询宠物医疗信息列表
     */
    @Override
    public List<MedicalDO> getMedicalsByType(String medicalType) {
        return medicalMapper.selectMedicalsByType(medicalType);
    }

    /**
     * 查询所有宠物医疗信息
     */
    @Override
    public List<MedicalDO> getAllMedicals() {
        return medicalMapper.selectAllMedicals();
    }

    /**
     * 分页查询所有宠物医疗信息
     */
    @Override
    public PageResultVO<MedicalDO> getMedicalsPage(Integer pageNum, Integer pageSize) {
        Page<MedicalDO> page = new Page<>(pageNum, pageSize);
        Page<MedicalDO> resultPage = medicalMapper.selectPage(page, null);
        return PageResultVO.<MedicalDO>builder()
                .total(resultPage.getTotal())
                .list(resultPage.getRecords())
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();
    }

    /**
     * 根据医疗类型分页查询宠物医疗信息
     */
    @Override
    public PageResultVO<MedicalDO> getMedicalsByTypePage(String medicalType, Integer pageNum, Integer pageSize) {
        Page<MedicalDO> page = new Page<>(pageNum, pageSize);
        List<MedicalDO> medicals = medicalMapper.selectMedicalsByType(medicalType);
        // 手动分页：计算起始/结束下标，避免数组越界
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, medicals.size());
        List<MedicalDO> pageList = medicals.subList(start, end);
        return PageResultVO.<MedicalDO>builder()
                .total((long) medicals.size())
                .list(pageList)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();
    }

    /**
     * 新增宠物医疗信息
     */
    @Override
    public Integer createMedical(MedicalDTO medicalDTO) {
        MedicalDO medical = new MedicalDO();
        BeanUtils.copyProperties(medicalDTO, medical);
        return medicalMapper.insert(medical);
    }

    /**
     * 修改宠物医疗信息
     */
    @Override
    public Integer updateMedical(Integer medicalId, MedicalDTO medicalDTO) {
        MedicalDO medical = new MedicalDO();
        BeanUtils.copyProperties(medicalDTO, medical);
        medical.setMedId(medicalId);
        return medicalMapper.updateById(medical);
    }

    /**
     * 删除宠物医疗信息
     */
    @Override
    public Integer deleteMedical(Integer medicalId) {
        return medicalMapper.deleteById(medicalId);
    }
}