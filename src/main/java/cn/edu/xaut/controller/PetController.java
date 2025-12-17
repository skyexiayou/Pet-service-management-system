package cn.edu.xaut.controller;

import cn.edu.xaut.domain.dto.PetDTO;
import cn.edu.xaut.domain.vo.PetVO;
import cn.edu.xaut.service.pet.PetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
@Api(tags = "????")
public class PetController {

    @Autowired
    private PetService petService;

    @ApiOperation("??????")
    @GetMapping
    public List<PetVO> getAllPets() {
        return petService.getAllPets();
    }

    @ApiOperation("??ID????")
    @GetMapping("/{id}")
    public PetVO getPetById(@PathVariable("id") Integer petId) {
        return petService.getPetById(petId);
    }

    @ApiOperation("????ID????")
    @GetMapping("/user/{userId}")
    public List<PetVO> getPetsByUserId(@PathVariable("userId") Integer userId) {
        return petService.getPetsByUserId(userId);
    }

    @ApiOperation("????")
    @PostMapping
    public Integer createPet(@RequestBody PetDTO petDTO) {
        return petService.createPet(petDTO);
    }

    @ApiOperation("????")
    @PutMapping("/{id}")
    public Integer updatePet(@PathVariable("id") Integer petId, @RequestBody PetDTO petDTO) {
        return petService.updatePet(petId, petDTO);
    }

    @ApiOperation("????")
    @DeleteMapping("/{id}")
    public Integer deletePet(@PathVariable("id") Integer petId) {
        return petService.deletePet(petId);
    }
}