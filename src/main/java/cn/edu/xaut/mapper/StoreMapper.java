package cn.edu.xaut.mapper;

import cn.edu.xaut.domain.entity.store.StoreDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 门店Mapper接口
 * @date 2025.12.18
 */
@Mapper
public interface StoreMapper extends BaseMapper<StoreDO> {
    
    /**
     * 查询所有门店（分页）
     * @param page 分页参数
     * @return 分页门店列表
     */
    Page<StoreDO> selectAllStores(Page<StoreDO> page);
    
    /**
     * 根据城市ID查询门店（分页）
     * @param page 分页参数
     * @param cityId 城市ID
     * @return 分页门店列表
     */
    Page<StoreDO> selectStoresByCityId(Page<StoreDO> page, @Param("cityId") Integer cityId);
    
    /**
     * 根据门店ID查询门店详情
     * @param storeId 门店ID
     * @return 门店详情
     */
    StoreDO selectStoreById(@Param("storeId") Integer storeId);
}