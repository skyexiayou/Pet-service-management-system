package cn.edu.xaut.service.petproductstore;

/**
 * 宠物用品门店关联服务接口
 */

import cn.edu.xaut.domain.entity.petproductstore.PetProductStoreDO;

import java.util.List;

public interface PetProductStoreService {
    PetProductStoreDO getPetProductStoreByRelId(Integer relId);
    List<PetProductStoreDO> getPetProductStoresByProductId(Integer productId);
    List<PetProductStoreDO> getPetProductStoresByStoreId(Integer storeId);
    List<PetProductStoreDO> getAllPetProductStores();
    Integer createPetProductStore(PetProductStoreDO petProductStore);
    Integer updatePetProductStore(Integer relId, PetProductStoreDO petProductStore);
    Integer deletePetProductStore(Integer relId);
}