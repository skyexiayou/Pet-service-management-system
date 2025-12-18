package cn.edu.xaut.mapper;

/**
 * 宠物用品Mapper接口

 */

import cn.edu.xaut.domain.entity.petproduct.PetProductDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PetProductMapper extends BaseMapper<PetProductDO> {
    PetProductDO selectProductById(@Param("productId") Integer productId);
    List<PetProductDO> selectProductsByType(@Param("productType") String productType);
    List<PetProductDO> selectAllProducts();
    
    /**
     * 查询所有宠物用品（分页）
     * @param page 分页参数
     * @return 分页宠物用品列表
     */
    Page<PetProductDO> selectProducts(Page<PetProductDO> page);
    
    /**
     * 根据类型查询宠物用品（分页）
     * @param page 分页参数
     * @param productType 商品类型
     * @return 分页宠物用品列表
     */
    Page<PetProductDO> selectProductsByType(Page<PetProductDO> page, @Param("productType") String productType);
    
    /**
     * 根据门店ID查询宠物用品（分页）
     * @param page 分页参数
     * @param storeId 门店ID
     * @return 分页宠物用品列表
     */
    Page<PetProductDO> selectProductsByStoreId(Page<PetProductDO> page, @Param("storeId") Integer storeId);
}