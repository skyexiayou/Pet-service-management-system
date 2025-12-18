package cn.edu.xaut.service.store;

import cn.edu.xaut.domain.dto.store.StoreDTO;
import cn.edu.xaut.domain.vo.PageResultVO;
import cn.edu.xaut.domain.vo.StoreVO;

import java.util.List;

/**
 * 门店服务接口
 * @date 2025.12.18
 */
public interface StoreService {
    
    /**
     * 查询所有门店（分页）
     * @param pageNum 当前页码
     * @param pageSize 每页记录数
     * @return 分页门店列表
     */
    PageResultVO<StoreVO> getAllStores(Integer pageNum, Integer pageSize);
    
    /**
     * 根据城市ID查询门店（分页）
     * @param cityId 城市ID
     * @param pageNum 当前页码
     * @param pageSize 每页记录数
     * @return 分页门店列表
     */
    PageResultVO<StoreVO> getStoresByCityId(Integer cityId, Integer pageNum, Integer pageSize);
    
    /**
     * 根据门店ID查询门店详情
     * @param storeId 门店ID
     * @return 门店详情
     */
    StoreVO getStoreById(Integer storeId);
}