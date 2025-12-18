package cn.edu.xaut.service.beauty.impl;

import cn.edu.xaut.domain.dto.beauty.BeautyDTO;
import cn.edu.xaut.domain.entity.beauty.BeautyDO;
import cn.edu.xaut.domain.vo.PageResultVO;
import cn.edu.xaut.mapper.BeautyMapper;
import cn.edu.xaut.service.beauty.BeautyService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 宠物美容业务层实现类
 * 实现BeautyService接口，处理宠物美容信息的CRUD、分页查询等核心业务逻辑
 *
 * @date 2025.12.18
 */
@Service
public class BeautyServiceImpl implements BeautyService {

    /** 宠物美容数据访问层接口 */
    @Autowired
    private BeautyMapper beautyMapper;

    /**
     * 根据美容ID查询单个宠物美容信息
     *
     * @param beautyId 美容ID（主键）
     * @return 宠物美容信息实体类
     */
    @Override
    public BeautyDO getBeautyById(Integer beautyId) {
        return beautyMapper.selectBeautyById(beautyId);
    }

    /**
     * 根据美容类型查询宠物美容信息列表
     *
     * @param beautyType 美容类型（如：毛发修剪、洗澡美容、造型设计）
     * @return 符合条件的宠物美容信息列表
     */
    @Override
    public List<BeautyDO> getBeautiesByType(String beautyType) {
        return beautyMapper.selectBeautiesByType(beautyType);
    }

    /**
     * 查询所有宠物美容信息
     *
     * @return 全部宠物美容信息列表
     */
    @Override
    public List<BeautyDO> getAllBeauties() {
        return beautyMapper.selectAllBeauties();
    }

    /**
     * 分页查询所有宠物美容信息
     * 基于MyBatis-Plus内置分页插件实现自动分页
     *
     * @param pageNum  当前页码（从1开始）
     * @param pageSize 每页显示条数
     * @return 分页结果VO（包含总条数、当前页数据、页码/页大小）
     */
    @Override
    public PageResultVO<BeautyDO> getBeautiesPage(Integer pageNum, Integer pageSize) {
        Page<BeautyDO> page = new Page<>(pageNum, pageSize);
        Page<BeautyDO> resultPage = beautyMapper.selectPage(page, null);
        return PageResultVO.<BeautyDO>builder()
                .total(resultPage.getTotal())
                .list(resultPage.getRecords())
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();
    }

    /**
     * 根据美容类型分页查询宠物美容信息
     * 先查询全量数据再手动分页（适用于数据量较小的场景）
     *
     * @param beautyType 美容类型（如：毛发修剪、洗澡美容、造型设计）
     * @param pageNum    当前页码（从1开始）
     * @param pageSize   每页显示条数
     * @return 分页结果VO（包含总条数、当前页数据、页码/页大小）
     */
    @Override
    public PageResultVO<BeautyDO> getBeautiesByTypePage(String beautyType, Integer pageNum, Integer pageSize) {
        Page<BeautyDO> page = new Page<>(pageNum, pageSize);
        List<BeautyDO> beauties = beautyMapper.selectBeautiesByType(beautyType);
        // 手动分页：计算起始/结束下标，避免数组越界
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, beauties.size());
        List<BeautyDO> pageList = beauties.subList(start, end);
        return PageResultVO.<BeautyDO>builder()
                .total((long) beauties.size())
                .list(pageList)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();
    }

    /**
     * 新增宠物美容信息
     * 将前端传入的DTO转换为DO后插入数据库
     *
     * @param beautyDTO 宠物美容信息入参DTO（前端传入的新增数据）
     * @return 受影响的行数（1=新增成功，0=新增失败）
     */
    @Override
    public Integer createBeauty(BeautyDTO beautyDTO) {
        BeautyDO beauty = new BeautyDO();
        BeanUtils.copyProperties(beautyDTO, beauty);
        return beautyMapper.insert(beauty);
    }

    /**
     * 修改宠物美容信息
     * 根据美容ID更新对应信息，DTO转换为DO后执行更新
     *
     * @param beautyId  美容ID（主键，指定要修改的记录）
     * @param beautyDTO 宠物美容信息入参DTO（前端传入的修改数据）
     * @return 受影响的行数（1=修改成功，0=修改失败/无数据更新）
     */
    @Override
    public Integer updateBeauty(Integer beautyId, BeautyDTO beautyDTO) {
        BeautyDO beauty = new BeautyDO();
        BeanUtils.copyProperties(beautyDTO, beauty);
        beauty.setBeautyId(beautyId);
        return beautyMapper.updateById(beauty);
    }

    /**
     * 删除宠物美容信息
     * 根据美容ID删除对应记录（物理删除）
     *
     * @param beautyId 美容ID（主键，指定要删除的记录）
     * @return 受影响的行数（1=删除成功，0=删除失败/无此记录）
     */
    @Override
    public Integer deleteBeauty(Integer beautyId) {
        return beautyMapper.deleteById(beautyId);
    }
}