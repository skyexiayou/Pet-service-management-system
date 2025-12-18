package cn.edu.xaut.service.petproduct.impl;

/**
 * 宠物用品服务实现类
 */

import cn.edu.xaut.domain.entity.petproduct.PetProductDO;
import cn.edu.xaut.domain.vo.PageResultVO;
import cn.edu.xaut.domain.vo.petproduct.PetProductVO;
import cn.edu.xaut.mapper.PetProductMapper;
import cn.edu.xaut.service.petproduct.PetProductService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetProductServiceImpl implements PetProductService {

    @Autowired
    private PetProductMapper petProductMapper;

    @Override
    public PetProductDO getProductById(Integer productId) {
        return petProductMapper.selectById(productId);
    }

    @Override
    public List<PetProductDO> getProductsByType(String productType) {
        return petProductMapper.selectProductsByType(productType);
    }

    @Override
    public List<PetProductDO> getAllProducts() {
        return petProductMapper.selectList(null);
    }

    @Override
    public Integer createProduct(PetProductDO petProduct) {
        return petProductMapper.insert(petProduct);
    }

    @Override
    public Integer updateProduct(Integer productId, PetProductDO petProduct) {
        petProduct.setProductId(productId);
        return petProductMapper.updateById(petProduct);
    }

    @Override
    public Integer deleteProduct(Integer productId) {
        return petProductMapper.deleteById(productId);
    }

    @Override
    public PageResultVO<PetProductVO> getProducts(Integer pageNum, Integer pageSize) {
        Page<PetProductDO> page = new Page<>(pageNum, pageSize);
        Page<PetProductDO> resultPage = petProductMapper.selectPage(page, null);
        List<PetProductVO> productVOs = resultPage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        return PageResultVO.<PetProductVO>builder()
                .total(resultPage.getTotal())
                .list(productVOs)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();
    }

    @Override
    public PageResultVO<PetProductVO> getProductsByType(String productType, Integer pageNum, Integer pageSize) {
        Page<PetProductDO> page = new Page<>(pageNum, pageSize);
        Page<PetProductDO> resultPage = petProductMapper.selectProductsByType(page, productType);
        List<PetProductVO> productVOs = resultPage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        return PageResultVO.<PetProductVO>builder()
                .total(resultPage.getTotal())
                .list(productVOs)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();
    }

    @Override
    public PageResultVO<PetProductVO> getProductsByStoreId(Integer storeId, Integer pageNum, Integer pageSize) {
        Page<PetProductDO> page = new Page<>(pageNum, pageSize);
        Page<PetProductDO> resultPage = petProductMapper.selectProductsByStoreId(page, storeId);
        List<PetProductVO> productVOs = resultPage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        return PageResultVO.<PetProductVO>builder()
                .total(resultPage.getTotal())
                .list(productVOs)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();
    }

    private PetProductVO convertToVO(PetProductDO petProductDO) {
        PetProductVO petProductVO = new PetProductVO();
        BeanUtils.copyProperties(petProductDO, petProductVO);
        return petProductVO;
    }
}