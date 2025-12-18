package cn.edu.xaut.controller;

import cn.edu.xaut.domain.dto.pet.PetDTO;
import cn.edu.xaut.domain.vo.pet.PetDetailVO;
import cn.edu.xaut.domain.vo.pet.PetVO;
import cn.edu.xaut.service.pet.PetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * 宠物controller
 */
@RestController
@RequestMapping("/api/pets")
@Api(tags = "宠物管理相关接口")
public class PetController {

    @Autowired
    private PetService petService;

    @ApiOperation("获取所有宠物信息")
    @GetMapping
    public List<PetVO> getAllPets() {
        return petService.getAllPets();
    }

    @ApiOperation("分页获取所有宠物信息")
    @GetMapping("/page")
    public cn.edu.xaut.domain.vo.PageResultVO<PetVO> getAllPetsPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return petService.getAllPetsPage(pageNum, pageSize);
    }

    @ApiOperation("根据ID查询宠物信息")
    @GetMapping("/{id}")
    public PetVO getPetById(@PathVariable("id") Integer petId) {
        return petService.getPetById(petId);
    }

    @ApiOperation("根据用户ID查询宠物列表")
    @GetMapping("/user/{userId}")
    public List<PetVO> getPetsByUserId(@PathVariable("userId") Integer userId) {
        return petService.getPetsByUserId(userId);
    }

    @ApiOperation("根据用户ID分页查询宠物列表")
    @GetMapping("/user/{userId}/page")
    public cn.edu.xaut.domain.vo.PageResultVO<PetVO> getPetsByUserIdPage(
            @PathVariable("userId") Integer userId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return petService.getPetsByUserIdPage(userId, pageNum, pageSize);
    }

    @ApiOperation("新增宠物")
    @PostMapping
    public Integer createPet(@RequestBody @Validated PetDTO petDTO) {
        return petService.createPet(petDTO);
    }

    @ApiOperation("修改宠物信息")
    @PutMapping("/{id}")
    public Integer updatePet(@PathVariable("id") Integer petId, @RequestBody @Validated PetDTO petDTO) {
        return petService.updatePet(petId, petDTO);
    }

    @ApiOperation("获取宠物详情（含服务记录）")
    @GetMapping("/{id}/detail")
    public PetDetailVO getPetDetail(@PathVariable("id") Integer petId) {
        return petService.getPetDetail(petId);
    }


}