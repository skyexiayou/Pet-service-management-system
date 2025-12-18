package cn.edu.xaut.service.petproductstore.impl;

/**
 * 宠物用品门店关联服务实现类
 */

import cn.edu.xaut.domain.entity.petproductstore.PetProductStoreDO;
import cn.edu.xaut.mapper.PetProductStoreMapper;
import cn.edu.xaut.service.petproductstore.PetProductStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetProductStoreServiceImpl implements PetProductStoreService {

    @Autowired
    private PetProductStoreMapper petProductStoreMapper;

    @Override
    public PetProductStoreDO getPetProductStoreByRelId(Integer relId) {
        return petProductStoreMapper.selectById(relId);
    }

    @Override
    public List<PetProductStoreDO> getPetProductStoresByProductId(Integer productId) {
        return petProductStoreMapper.selectPetProductStoresByProductId(productId);
    }

    @Override
    public List<PetProductStoreDO> getPetProductStoresByStoreId(Integer storeId) {
        return petProductStoreMapper.selectPetProductStoresByStoreId(storeId);
    }

    @Override
    public List<PetProductStoreDO> getAllPetProductStores() {
        return petProductStoreMapper.selectList(null);
    }

    @Override
    public Integer createPetProductStore(PetProductStoreDO petProductStore) {
        return petProductStoreMapper.insert(petProductStore);
    }

    @Override
    public Integer updatePetProductStore(Integer relId, PetProductStoreDO petProductStore) {
        petProductStore.setRelId(relId);
        return petProductStoreMapper.updateById(petProductStore);
    }

    @Override
    public Integer deletePetProductStore(Integer relId) {
        return petProductStoreMapper.deleteById(relId);
    }
}