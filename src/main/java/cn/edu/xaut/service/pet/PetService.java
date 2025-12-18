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
}