package cn.edu.xaut.service.petproductstore.impl;

import cn.edu.xaut.domain.dto.admin.ProductStoreDTO;
import cn.edu.xaut.domain.dto.admin.StockAdjustmentDTO;
import cn.edu.xaut.domain.entity.petproductstore.PetProductStoreDO;
import cn.edu.xaut.domain.vo.PageResultVO;
import cn.edu.xaut.exception.BusinessException;
import cn.edu.xaut.mapper.PetProductStoreMapper;
import cn.edu.xaut.service.petproductstore.ProductStoreAdminService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 门店铺货管理Service实现类
 * @date 2025-12-18
 */
@Service
public class ProductStoreAdminServiceImpl implements ProductStoreAdminService {

    @Autowired
    private PetProductStoreMapper petProductStoreMapper;

    @Override
    public PageResultVO<PetProductStoreDO> getProductStoreList(Integer storeId, Integer pageNum, Integer pageSize) {
        Page<PetProductStoreDO> page = new Page<>(pageNum, pageSize);
        QueryWrapper<PetProductStoreDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("StoreID", storeId);
        
        Page<PetProductStoreDO> resultPage = petProductStoreMapper.selectPage(page, queryWrapper);
        
        return PageResultVO.<PetProductStoreDO>builder()
                .total(resultPage.getTotal())
                .list(resultPage.getRecords())
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();
    }

    @Override
    public PetProductStoreDO getProductStoreById(Integer relId) {
        PetProductStoreDO productStore = petProductStoreMapper.selectById(relId);
        if (productStore == null) {
            throw new BusinessException("铺货记录不存在");
        }
        return productStore;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer upsertProductStore(Integer storeId, ProductStoreDTO productStoreDTO) {
        // 查询是否已存在该商品的铺货记录
        QueryWrapper<PetProductStoreDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ProductID", productStoreDTO.getProductId())
                   .eq("StoreID", storeId);
        PetProductStoreDO existingRecord = petProductStoreMapper.selectOne(queryWrapper);
        
        if (existingRecord != null) {
            // 更新现有记录
            BeanUtils.copyProperties(productStoreDTO, existingRecord);
            existingRecord.setStoreId(storeId);
            petProductStoreMapper.updateById(existingRecord);
            return existingRecord.getRelId();
        } else {
            // 创建新记录
            PetProductStoreDO productStore = new PetProductStoreDO();
            BeanUtils.copyProperties(productStoreDTO, productStore);
            productStore.setStoreId(storeId);
            petProductStoreMapper.insert(productStore);
            return productStore.getRelId();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer adjustStock(Integer relId, Integer storeId, StockAdjustmentDTO stockAdjustmentDTO) {
        // 校验铺货记录是否存在
        PetProductStoreDO productStore = getProductStoreById(relId);
        
        // 权限校验：只能调整本门店的库存
        if (!productStore.getStoreId().equals(storeId)) {
            throw new BusinessException("无权限调整其他门店的库存");
        }
        
        Integer currentStock = productStore.getStoreStock();
        Integer adjustmentQuantity = stockAdjustmentDTO.getQuantity();
        Integer newStock;
        
        if ("ADD".equals(stockAdjustmentDTO.getAdjustmentType())) {
            // 补货
            newStock = currentStock + adjustmentQuantity;
        } else if ("REDUCE".equals(stockAdjustmentDTO.getAdjustmentType())) {
            // 减库存
            newStock = currentStock - adjustmentQuantity;
            if (newStock < 0) {
                throw new BusinessException("库存不足，无法减少");
            }
        } else {
            throw new BusinessException("无效的调整类型");
        }
        
        productStore.setStoreStock(newStock);
        
        // 根据库存自动更新上架状态
        if (newStock == 0) {
            productStore.setShelfStatus("缺货");
        } else if (newStock >= 1 && "缺货".equals(productStore.getShelfStatus())) {
            productStore.setShelfStatus("在售");
        }
        
        return petProductStoreMapper.updateById(productStore);
    }
}
