package cn.edu.xaut.service.petproduct.impl;

import cn.edu.xaut.domain.dto.admin.ProductDTO;
import cn.edu.xaut.domain.entity.petproduct.PetProductDO;
import cn.edu.xaut.domain.vo.PageResultVO;
import cn.edu.xaut.exception.BusinessException;
import cn.edu.xaut.mapper.PetProductMapper;
import cn.edu.xaut.service.petproduct.PetProductAdminService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 宠物用品管理Service实现类
 * @date 2025-12-18
 */
@Service
public class PetProductAdminServiceImpl implements PetProductAdminService {

    @Autowired
    private PetProductMapper petProductMapper;

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
}
