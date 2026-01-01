package cn.edu.xaut.service.petproduct.impl;

import cn.edu.xaut.domain.dto.admin.ProductDTO;
import cn.edu.xaut.domain.dto.admin.ProductCreateDTO;
import cn.edu.xaut.domain.entity.petproduct.PetProductDO;
import cn.edu.xaut.domain.entity.petproductstore.PetProductStoreDO;
import cn.edu.xaut.domain.vo.PageResultVO;
import cn.edu.xaut.domain.vo.petproduct.ProductWithStoreVO;
import cn.edu.xaut.exception.BusinessException;
import cn.edu.xaut.mapper.PetProductMapper;
import cn.edu.xaut.mapper.PetProductStoreMapper;
import cn.edu.xaut.mapper.OrderProductMapper;
import cn.edu.xaut.service.petproduct.PetProductAdminService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 宠物用品管理Service实现类
 * @date 2025-12-18
 */
@Service
public class PetProductAdminServiceImpl implements PetProductAdminService {

    @Autowired
    private PetProductMapper petProductMapper;
    
    @Autowired
    private PetProductStoreMapper petProductStoreMapper;
    
    @Autowired
    private OrderProductMapper orderProductMapper;

    @Override
    public PageResultVO<PetProductDO> getProductList(Integer pageNum, Integer pageSize) {
        Page<PetProductDO> page = new Page<>(pageNum, pageSize);
        Page<PetProductDO> resultPage = petProductMapper.selectPage(page, null);
        
        return PageResultVO.<PetProductDO>builder()
                .total(resultPage.getTotal())
                .list(resultPage.getRecords())
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();
    }

    @Override
    public PetProductDO getProductById(Integer productId) {
        PetProductDO product = petProductMapper.selectById(productId);
        if (product == null) {
            throw new BusinessException("用品不存在");
        }
        return product;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createProduct(ProductDTO productDTO) {
        PetProductDO product = new PetProductDO();
        BeanUtils.copyProperties(productDTO, product);
        
        petProductMapper.insert(product);
        return product.getProductId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateProduct(Integer productId, ProductDTO productDTO) {
        // 校验用品是否存在
        PetProductDO product = getProductById(productId);
        
        BeanUtils.copyProperties(productDTO, product);
        return petProductMapper.updateById(product);
    }
    
    @Override
    public List<ProductWithStoreVO> getAllProductsWithStore() {
        return petProductMapper.selectProductsWithStore();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createProductWithStore(ProductCreateDTO productCreateDTO) {
        // 1. 创建商品基础信息
        PetProductDO product = new PetProductDO();
        product.setProductName(productCreateDTO.getProductName());
        product.setProductType(productCreateDTO.getProductType());
        product.setSupplier(productCreateDTO.getSupplier());
        petProductMapper.insert(product);
        
        // 2. 创建商品-门店关联信息
        PetProductStoreDO productStore = new PetProductStoreDO();
        productStore.setProductId(product.getProductId());
        productStore.setStoreId(productCreateDTO.getStoreId());
        productStore.setPrice(productCreateDTO.getPrice());
        productStore.setStoreStock(productCreateDTO.getStoreStock());
        productStore.setShelfStatus(productCreateDTO.getShelfStatus() != null ? 
                productCreateDTO.getShelfStatus() : "在售");
        petProductStoreMapper.insert(productStore);
        
        return product.getProductId();
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateProductWithStore(Integer productId, ProductCreateDTO productCreateDTO) {
        // 1. 校验商品是否存在
        PetProductDO product = getProductById(productId);
        
        // 2. 更新商品基础信息
        product.setProductName(productCreateDTO.getProductName());
        product.setProductType(productCreateDTO.getProductType());
        product.setSupplier(productCreateDTO.getSupplier());
        petProductMapper.updateById(product);
        
        // 3. 更新商品-门店关联信息
        LambdaQueryWrapper<PetProductStoreDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PetProductStoreDO::getProductId, productId);
        if (productCreateDTO.getStoreId() != null) {
            wrapper.eq(PetProductStoreDO::getStoreId, productCreateDTO.getStoreId());
        }
        PetProductStoreDO productStore = petProductStoreMapper.selectOne(wrapper);
        
        if (productStore != null) {
            if (productCreateDTO.getPrice() != null) {
                productStore.setPrice(productCreateDTO.getPrice());
            }
            if (productCreateDTO.getStoreStock() != null) {
                productStore.setStoreStock(productCreateDTO.getStoreStock());
            }
            if (productCreateDTO.getShelfStatus() != null) {
                productStore.setShelfStatus(productCreateDTO.getShelfStatus());
            }
            petProductStoreMapper.updateById(productStore);
        }
        
        return 1;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteProduct(Integer productId) {
        // 1. 校验商品是否存在
        PetProductDO product = getProductById(productId);
        
        // 2. 检查是否有关联订单
        LambdaQueryWrapper<PetProductStoreDO> storeWrapper = new LambdaQueryWrapper<>();
        storeWrapper.eq(PetProductStoreDO::getProductId, productId);
        List<PetProductStoreDO> productStores = petProductStoreMapper.selectList(storeWrapper);
        
        for (PetProductStoreDO productStore : productStores) {
            // 检查该关联是否有订单
            Long orderCount = orderProductMapper.countByRelId(productStore.getRelId());
            if (orderCount != null && orderCount > 0) {
                throw new BusinessException("该商品有关联订单，无法删除");
            }
        }
        
        // 3. 删除商品-门店关联
        petProductStoreMapper.delete(storeWrapper);
        
        // 4. 删除商品
        return petProductMapper.deleteById(productId);
    }
}
