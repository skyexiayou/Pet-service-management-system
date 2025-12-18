package cn.edu.xaut.controller;

import cn.edu.xaut.domain.dto.medical.MedicalDTO;
import cn.edu.xaut.domain.entity.medical.MedicalDO;
import cn.edu.xaut.service.medical.MedicalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 医疗服务controller
 */
@RestController
@RequestMapping("/api/medicals")
@Api(tags = "医疗服务管理相关接口")
public class MedicalController {

    @Autowired
    private MedicalService medicalService;

    @ApiOperation("获取所有医疗服务")
    @GetMapping
    public List<MedicalDO> getAllMedicals() {
        return medicalService.getAllMedicals();
    }

    @ApiOperation("根据ID查询医疗服务")
    @GetMapping("/{id}")
    public MedicalDO getMedicalById(@PathVariable("id") Integer medicalId) {
        return medicalService.getMedicalById(medicalId);
    }

    @ApiOperation("根据类型查询医疗服务")
    @GetMapping("/type/{type}")
    public List<MedicalDO> getMedicalsByType(@PathVariable("type") String medicalType) {
        return medicalService.getMedicalsByType(medicalType);
    }

    @ApiOperation("分页获取所有医疗服务")
    @GetMapping("/page")
    public cn.edu.xaut.domain.vo.PageResultVO<MedicalDO> getMedicalsPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        return medicalService.getMedicalsPage(pageNum, pageSize);
    }

    @ApiOperation("根据类型分页获取医疗服务")
    @GetMapping("/page/type/{type}")
    public cn.edu.xaut.domain.vo.PageResultVO<MedicalDO> getMedicalsByTypePage(@PathVariable("type") String medicalType, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        return medicalService.getMedicalsByTypePage(medicalType, pageNum, pageSize);
    }

    @ApiOperation("新增医疗服务")
    @PostMapping
    public Integer createMedical(@RequestBody @Validated MedicalDTO medicalDTO) {
        return medicalService.createMedical(medicalDTO);
    }

    @ApiOperation("修改医疗服务")
    @PutMapping("/{id}")
    public Integer updateMedical(@PathVariable("id") Integer medicalId, @RequestBody @Validated MedicalDTO medicalDTO) {
        return medicalService.updateMedical(medicalId, medicalDTO);
    }

    @ApiOperation("删除医疗服务")
    @DeleteMapping("/{id}")
    public Integer deleteMedical(@PathVariable("id") Integer medicalId) {
        return medicalService.deleteMedical(medicalId);
    }
}