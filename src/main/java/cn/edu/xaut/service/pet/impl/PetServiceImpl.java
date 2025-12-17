package cn.edu.xaut.service.pet.impl;

import cn.edu.xaut.domain.dto.PetDTO;
import cn.edu.xaut.domain.entity.Pet;
import cn.edu.xaut.domain.entity.User;
import cn.edu.xaut.domain.vo.PetVO;
import cn.edu.xaut.domain.vo.UserVO;
import cn.edu.xaut.mapper.PetMapper;
import cn.edu.xaut.service.pet.PetService;
import cn.edu.xaut.service.user.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetMapper petMapper;

    @Autowired
    private UserService userService;

    @Override
    public PetVO getPetById(Integer petId) {
        Pet pet = petMapper.selectPetById(petId);
        if (pet == null) {
            return null;
        }
        return convertToPetVO(pet);
    }

    @Override
    public List<PetVO> getPetsByUserId(Integer userId) {
        List<Pet> pets = petMapper.selectPetsByUserId(userId);
        return pets.stream().map(this::convertToPetVO).collect(Collectors.toList());
    }

    @Override
    public List<PetVO> getAllPets() {
        List<Pet> pets = petMapper.selectAllPets();
        return pets.stream().map(this::convertToPetVO).collect(Collectors.toList());
    }

    @Override
    public Integer createPet(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return petMapper.insertPet(pet);
    }

    @Override
    public Integer updatePet(Integer petId, PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        pet.setPetId(petId);
        return petMapper.updatePet(pet);
    }

    @Override
    public Integer deletePet(Integer petId) {
        return petMapper.deletePet(petId);
    }

    private PetVO convertToPetVO(Pet pet) {
        PetVO petVO = new PetVO();
        BeanUtils.copyProperties(pet, petVO);

        User user = userService.getUserById(pet.getUserId());
        if (user != null) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            petVO.setUser(userVO);
        }

        return petVO;
    }
}
