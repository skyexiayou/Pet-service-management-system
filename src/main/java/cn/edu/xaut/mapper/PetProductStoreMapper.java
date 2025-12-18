package cn.edu.xaut.mapper;

/**
 * 宠物用品门店关联Mapper接口
 */

import cn.edu.xaut.domain.entity.petproductstore.PetProductStoreDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PetProductStoreMapper extends BaseMapper<PetProductStoreDO> {
    PetProductStoreDO selectPetProductStoreByRelId(@Param("relId") Integer relId);
    List<PetProductStoreDO> selectPetProductStoresByProductId(@Param("productId") Integer productId);
    List<PetProductStoreDO> selectPetProductStoresByStoreId(@Param("storeId") Integer storeId);
    List<PetProductStoreDO> selectAllPetProductStores();
    
    /**
     * 扣减库存（带行锁）
     * @param relId 用品关联ID
     * @param quantity 扣减数量
     * @return 更新行数
     */
    int decreaseStock(@Param("relId") Integer relId, @Param("quantity") Integer quantity);
    
    /**
     * 查询用品信息（带行锁）
     * @param relId 用品关联ID
     * @return 用品信息
     */
    PetProductStoreDO selectByRelIdForUpdate(@Param("relId") Integer relId);

    /**
     * 增加库存（退款时恢复库存）
     * @param relId 用品关联ID
     * @param quantity 增加数量
     * @return 更新行数
     */
    int increaseStock(@Param("relId") Integer relId, @Param("quantity") Integer quantity);
}