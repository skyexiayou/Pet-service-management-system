package cn.edu.xaut.service.pet;

import cn.edu.xaut.domain.dto.PetDTO;
import cn.edu.xaut.domain.vo.PetVO;

import java.util.List;

public interface PetService {
    PetVO getPetById(Integer petId);
    List<PetVO> getPetsByUserId(Integer userId);
    List<PetVO> getAllPets();
    Integer createPet(PetDTO petDTO);
    Integer updatePet(Integer petId, PetDTO petDTO);
    Integer deletePet(Integer petId);
}