package cn.edu.xaut.service.petproductstore;

import cn.edu.xaut.domain.dto.admin.ProductStoreDTO;
import cn.edu.xaut.domain.dto.admin.StockAdjustmentDTO;
import cn.edu.xaut.domain.entity.petproductstore.PetProductStoreDO;
import cn.edu.xaut.domain.vo.PageResultVO;

/**
 * 门店铺货管理Service接口
 * @date 2025-12-18
 */
public interface ProductStoreAdminService {
    
    /**
     * 分页查询门店铺货列表
     * 
     * @param storeId 门店ID
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 铺货列表
     */
    PageResultVO<PetProductStoreDO> getProductStoreList(Integer storeId, Integer pageNum, Integer pageSize);
    
    /**
     * 根据ID查询铺货信息
     * 
     * @param relId 关联ID
     * @return 铺货信息
     */
    PetProductStoreDO getProductStoreById(Integer relId);
    
    /**
     * 创建或更新门店铺货（使用ON DUPLICATE KEY UPDATE）
     * 
     * @param storeId 门店ID
     * @param productStoreDTO 铺货DTO
     * @return 关联ID
     */
    Integer upsertProductStore(Integer storeId, ProductStoreDTO productStoreDTO);
    
    /**
     * 调整库存
     * 
     * @param relId 关联ID
     * @param storeId 门店ID（权限校验）
     * @param stockAdjustmentDTO 库存调整DTO
     * @return 更新结果
     */
    Integer adjustStock(Integer relId, Integer storeId, StockAdjustmentDTO stockAdjustmentDTO);
}
