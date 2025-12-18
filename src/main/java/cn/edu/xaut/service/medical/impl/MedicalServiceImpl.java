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

    /** 宠物医疗数据访问层接口，负责医疗相关数据库操作 */
    @Autowired
    private MedicalMapper medicalMapper;

    /**
     * 根据医疗ID查询单个宠物医疗信息
     *
     * @param medicalId 医疗ID（主键）
     * @return 宠物医疗信息实体类
     */
    @Override
    public MedicalDO getMedicalById(Integer medicalId) {
        return medicalMapper.selectMedicalById(medicalId);
    }

    /**
     * 根据医疗类型查询宠物医疗信息列表
     *
     * @param medicalType 医疗类型（如：疫苗接种、疾病治疗、体检）
     * @return 符合条件的宠物医疗信息列表
     */
    @Override
    public List<MedicalDO> getMedicalsByType(String medicalType) {
        return medicalMapper.selectMedicalsByType(medicalType);
    }

    /**
     * 查询所有宠物医疗信息
     *
     * @return 全部宠物医疗信息列表
     */
    @Override
    public List<MedicalDO> getAllMedicals() {
        return medicalMapper.selectAllMedicals();
    }

    /**
     * 分页查询所有宠物医疗信息
     * 基于MyBatis-Plus内置分页插件实现自动分页
     *
     * @param pageNum  当前页码（从1开始）
     * @param pageSize 每页显示条数
     * @return 分页结果VO（包含总条数、当前页数据、页码/页大小）
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
     * 先查询全量数据再手动分页（适用于数据量较小的场景）
     *
     * @param medicalType 医疗类型（如：疫苗接种、疾病治疗、体检）
     * @param pageNum     当前页码（从1开始）
     * @param pageSize    每页显示条数
     * @return 分页结果VO（包含总条数、当前页数据、页码/页大小）
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
     * 将前端传入的DTO转换为DO后插入数据库
     *
     * @param medicalDTO 宠物医疗信息入参DTO（前端传入的新增数据）
     * @return 受影响的行数（1=新增成功，0=新增失败）
     */
    @Override
    public Integer createMedical(MedicalDTO medicalDTO) {
        MedicalDO medical = new MedicalDO();
        BeanUtils.copyProperties(medicalDTO, medical);
        return medicalMapper.insert(medical);
    }

    /**
     * 修改宠物医疗信息
     * 根据医疗ID更新对应信息，DTO转换为DO后执行更新
     *
     * @param medicalId  医疗ID（主键，指定要修改的记录）
     * @param medicalDTO 宠物医疗信息入参DTO（前端传入的修改数据）
     * @return 受影响的行数（1=修改成功，0=修改失败/无数据更新）
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
     * 根据医疗ID删除对应记录（物理删除）
     *
     * @param medicalId 医疗ID（主键，指定要删除的记录）
     * @return 受影响的行数（1=删除成功，0=删除失败/无此记录）
     */
    @Override
    public Integer deleteMedical(Integer medicalId) {
        return medicalMapper.deleteById(medicalId);
    }
}