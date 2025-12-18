package cn.edu.xaut.service.petproduct;

/**
 * 宠物用品服务接口
 */

import cn.edu.xaut.domain.entity.petproduct.PetProductDO;
import cn.edu.xaut.domain.vo.PageResultVO;
import cn.edu.xaut.domain.vo.petproduct.PetProductVO;

import java.util.List;

public interface PetProductService {
    PetProductDO getProductById(Integer productId);
    List<PetProductDO> getProductsByType(String productType);
    List<PetProductDO> getAllProducts();
    Integer createProduct(PetProductDO petProduct);
    Integer updateProduct(Integer productId, PetProductDO petProduct);
    Integer deleteProduct(Integer productId);
    
    /**
     * 查询所有宠物用品（分页）
     * @param pageNum 当前页码
     * @param pageSize 每页记录数
     * @return 分页宠物用品列表
     */
    PageResultVO<PetProductVO> getProducts(Integer pageNum, Integer pageSize);
    
    /**
     * 根据类型查询宠物用品（分页）
     * @param productType 商品类型
     * @param pageNum 当前页码
     * @param pageSize 每页记录数
     * @return 分页宠物用品列表
     */
    PageResultVO<PetProductVO> getProductsByType(String productType, Integer pageNum, Integer pageSize);
    
    /**
     * 根据门店ID查询宠物用品（分页）
     * @param storeId 门店ID
     * @param pageNum 当前页码
     * @param pageSize 每页记录数
     * @return 分页宠物用品列表
     */
    PageResultVO<PetProductVO> getProductsByStoreId(Integer storeId, Integer pageNum, Integer pageSize);
}