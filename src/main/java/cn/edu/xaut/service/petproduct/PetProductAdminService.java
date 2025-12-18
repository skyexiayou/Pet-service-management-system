package cn.edu.xaut.service.petproduct;

import cn.edu.xaut.domain.dto.admin.ProductDTO;
import cn.edu.xaut.domain.entity.petproduct.PetProductDO;
import cn.edu.xaut.domain.vo.PageResultVO;

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
     * Update product
     * 
     * @param productId product ID
     * @param productDTO product DTO
     * @return update result
     */
    Integer updateProduct(Integer productId, ProductDTO productDTO);
}
