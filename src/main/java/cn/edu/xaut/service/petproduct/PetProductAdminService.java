package cn.edu.xaut.service.petproduct;

import cn.edu.xaut.domain.dto.admin.ProductDTO;
import cn.edu.xaut.domain.dto.admin.ProductCreateDTO;
import cn.edu.xaut.domain.entity.petproduct.PetProductDO;
import cn.edu.xaut.domain.vo.PageResultVO;
import cn.edu.xaut.domain.vo.petproduct.ProductWithStoreVO;

import java.util.List;

/**
 * Pet Product Admin Service Interface
 * 
 * @author Kiro AI Assistant
 * @date 2025-12-18
 */
public interface PetProductAdminService {
    
    /**
     * Get product list with pagination
     * 
     * @param pageNum page number
     * @param pageSize page size
     * @return product list
     */
    PageResultVO<PetProductDO> getProductList(Integer pageNum, Integer pageSize);
    
    /**
     * Get all products with store info
     * 
     * @return product list with store info
     */
    List<ProductWithStoreVO> getAllProductsWithStore();
    
    /**
     * Get product by ID
     * 
     * @param productId product ID
     * @return product info
     */
    PetProductDO getProductById(Integer productId);
    
    /**
     * Create product
     * 
     * @param productDTO product DTO
     * @return product ID
     */
    Integer createProduct(ProductDTO productDTO);
    
    /**
     * Create product with store info
     * 
     * @param productCreateDTO product create DTO with store info
     * @return product ID
     */
    Integer createProductWithStore(ProductCreateDTO productCreateDTO);
    
    /**
     * Update product
     * 
     * @param productId product ID
     * @param productDTO product DTO
     * @return update result
     */
    Integer updateProduct(Integer productId, ProductDTO productDTO);
    
    /**
     * Update product with store info
     * 
     * @param productId product ID
     * @param productCreateDTO product DTO with store info
     * @return update result
     */
    Integer updateProductWithStore(Integer productId, ProductCreateDTO productCreateDTO);
    
    /**
     * Delete product
     * 
     * @param productId product ID
     * @return delete result
     */
    Integer deleteProduct(Integer productId);
}
