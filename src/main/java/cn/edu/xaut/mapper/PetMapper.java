package cn.edu.xaut.mapper;

import cn.edu.xaut.domain.entity.pet.PetDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PetMapper extends BaseMapper<PetDO> {
    PetDO selectPetById(@Param("petId") Integer petId);
    List<PetDO> selectPetsByUserId(@Param("userId") Integer userId);
    List<PetDO> selectAllPets();
    List<Map<String, Object>> selectPetServiceRecords(@Param("petId") Integer petId);
}