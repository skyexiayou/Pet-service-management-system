package cn.edu.xaut.service.pet;

import cn.edu.xaut.domain.dto.pet.PetDTO;
import cn.edu.xaut.domain.vo.pet.PetDetailVO;
import cn.edu.xaut.domain.vo.pet.PetVO;

import java.util.List;

import cn.edu.xaut.domain.vo.PageResultVO;

public interface PetService {
    PetVO getPetById(Integer petId);
    PetDetailVO getPetDetail(Integer petId);
    List<PetVO> getPetsByUserId(Integer userId);
    List<PetVO> getAllPets();
    PageResultVO<PetVO> getAllPetsPage(Integer pageNum, Integer pageSize);
    PageResultVO<PetVO> getPetsByUserIdPage(Integer userId, Integer pageNum, Integer pageSize);
    Integer createPet(PetDTO petDTO);
    Integer updatePet(Integer petId, PetDTO petDTO);
    
    /**
     * 搜索宠物列表
     * @param petName 宠物姓名
     * @param petId 宠物ID
     * @param breed 宠物品种
     * @param gender 宠物性别
     * @param birthDateStart 出生日期开始
     * @param birthDateEnd 出生日期结束
     * @return 符合条件的宠物列表
     */
    List<PetVO> searchPets(String petName, Integer petId, String breed, String gender, String birthDateStart, String birthDateEnd);
    
    /**
     * 删除宠物
     * @param petId 宠物ID
     * @return 删除结果
     */
    Integer deletePet(Integer petId);
}