package cn.edu.xaut.service.beauty.impl;

import cn.edu.xaut.domain.dto.admin.BeautyServiceDTO;
import cn.edu.xaut.domain.entity.beauty.BeautyDO;
import cn.edu.xaut.domain.vo.PageResultVO;
import cn.edu.xaut.exception.BusinessException;
import cn.edu.xaut.mapper.BeautyMapper;
import cn.edu.xaut.service.beauty.BeautyAdminService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 美容服务管理Service实现类
 * @date 2025-12-18
 */
@Service
public class BeautyAdminServiceImpl implements BeautyAdminService {

    @Autowired
    private BeautyMapper beautyMapper;

    @Override
    public PageResultVO<BeautyDO> getBeautyList(Integer pageNum, Integer pageSize) {
        Page<BeautyDO> page = new Page<>(pageNum, pageSize);
        Page<BeautyDO> resultPage = beautyMapper.selectPage(page, null);
        
        return PageResultVO.<BeautyDO>builder()
                .total(resultPage.getTotal())
                .list(resultPage.getRecords())
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();
    }

    @Override
    public BeautyDO getBeautyById(Integer beautyId) {
        BeautyDO beauty = beautyMapper.selectById(beautyId);
        if (beauty == null) {
            throw new BusinessException("美容服务不存在");
        }
        return beauty;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createBeauty(BeautyServiceDTO beautyServiceDTO) {
        BeautyDO beauty = new BeautyDO();
        BeanUtils.copyProperties(beautyServiceDTO, beauty);
        
        beautyMapper.insert(beauty);
        return beauty.getBeautyId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateBeauty(Integer beautyId, BeautyServiceDTO beautyServiceDTO) {
        // 校验美容服务是否存在
        BeautyDO beauty = getBeautyById(beautyId);
        
        BeanUtils.copyProperties(beautyServiceDTO, beauty);
        return beautyMapper.updateById(beauty);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteBeauty(Integer beautyId) {
        // 校验美容服务是否存在
        getBeautyById(beautyId);
        
        // 注意：这里是物理删除，如果需要软删除（下架），需要添加ShelfStatus字段
        return beautyMapper.deleteById(beautyId);
    }
}
