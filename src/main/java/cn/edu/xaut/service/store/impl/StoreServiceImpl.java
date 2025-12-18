package cn.edu.xaut.service.store.impl;

import cn.edu.xaut.domain.entity.store.StoreDO;
import cn.edu.xaut.domain.vo.PageResultVO;
import cn.edu.xaut.domain.vo.StoreVO;
import cn.edu.xaut.mapper.StoreMapper;
import cn.edu.xaut.service.store.StoreService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * 门店服务实现类
 * @date 2025.12.18
 */
@Service
public class StoreServiceImpl implements StoreService {
    
    @Autowired
    private StoreMapper storeMapper;
    
    @Override
    public PageResultVO<StoreVO> getAllStores(Integer pageNum, Integer pageSize) {
        // 参数校验与默认值设置
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize <= 0 || pageSize > 100) {
            pageSize = 10;
        }
        
        // 执行分页查询
        Page<StoreDO> page = new Page<>(pageNum, pageSize);
        Page<StoreDO> storePage = storeMapper.selectAllStores(page);
        
        // 转换为VO列表
        return convertToPageResult(storePage);
    }
    
    @Override
    public PageResultVO<StoreVO> getStoresByCityId(Integer cityId, Integer pageNum, Integer pageSize) {
        // 参数校验与默认值设置
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize <= 0 || pageSize > 100) {
            pageSize = 10;
        }
        
        // 执行分页查询
        Page<StoreDO> page = new Page<>(pageNum, pageSize);
        Page<StoreDO> storePage = storeMapper.selectStoresByCityId(page, cityId);
        
        // 转换为VO列表
        return convertToPageResult(storePage);
    }
    
    @Override
    public StoreVO getStoreById(Integer storeId) {
        StoreDO store = storeMapper.selectStoreById(storeId);
        if (store == null) {
            return null;
        }
        return convertToStoreVO(store);
    }
    
    /**
     * 将StoreDO转换为StoreVO
     * @param store StoreDO对象
     * @return StoreVO对象
     */
    private StoreVO convertToStoreVO(StoreDO store) {
        StoreVO storeVO = new StoreVO();
        BeanUtils.copyProperties(store, storeVO);
        storeVO.setCityName(store.getCityName());
        return storeVO;
    }
    
    /**
     * 将Page<StoreDO>转换为PageResultVO<StoreVO>
     * @param storePage Page<StoreDO>对象
     * @return PageResultVO<StoreVO>对象
     */
    private PageResultVO<StoreVO> convertToPageResult(Page<StoreDO> storePage) {
        return PageResultVO.<StoreVO>builder()
                .total(storePage.getTotal())
                .list(storePage.getRecords().stream().map(this::convertToStoreVO).collect(Collectors.toList()))
                .pageNum((int) storePage.getCurrent())
                .pageSize((int) storePage.getSize())
                .build();
    }
}