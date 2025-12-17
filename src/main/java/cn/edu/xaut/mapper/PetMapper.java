package cn.edu.xaut.mapper;

import cn.edu.xaut.domain.entity.Pet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PetMapper {
    Pet selectPetById(@Param("petId") Integer petId);
    List<Pet> selectPetsByUserId(@Param("userId") Integer userId);
    List<Pet> selectAllPets();
    int insertPet(Pet pet);
    int updatePet(Pet pet);
    int deletePet(@Param("petId") Integer petId);
}