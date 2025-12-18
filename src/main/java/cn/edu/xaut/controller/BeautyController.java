package cn.edu.xaut.controller;

import cn.edu.xaut.domain.dto.beauty.BeautyDTO;
import cn.edu.xaut.domain.entity.beauty.BeautyDO;
import cn.edu.xaut.service.beauty.BeautyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * 美容项目controller
 */
@RestController
@RequestMapping("/api/beauties")
@Api(tags = "美容项目管理相关接口")
public class BeautyController {

    @Autowired
    private BeautyService beautyService;

    @ApiOperation("获取所有美容项目")
    @GetMapping
    public List<BeautyDO> getAllBeauties() {
        return beautyService.getAllBeauties();
    }

    @ApiOperation("根据ID查询美容项目")
    @GetMapping("/{id}")
    public BeautyDO getBeautyById(@PathVariable("id") Integer beautyId) {
        return beautyService.getBeautyById(beautyId);
    }

    @ApiOperation("根据类型查询美容项目")
    @GetMapping("/type/{type}")
    public List<BeautyDO> getBeautiesByType(@PathVariable("type") String beautyType) {
        return beautyService.getBeautiesByType(beautyType);
    }

    @ApiOperation("新增美容项目")
    @PostMapping
    public Integer createBeauty(@RequestBody @Validated BeautyDTO beautyDTO) {
        return beautyService.createBeauty(beautyDTO);
    }

    @ApiOperation("修改美容项目")
    @PutMapping("/{id}")
    public Integer updateBeauty(@PathVariable("id") Integer beautyId, @RequestBody @Validated BeautyDTO beautyDTO) {
        return beautyService.updateBeauty(beautyId, beautyDTO);
    }

    @ApiOperation("删除美容项目")
    @DeleteMapping("/{id}")
    public Integer deleteBeauty(@PathVariable("id") Integer beautyId) {
        return beautyService.deleteBeauty(beautyId);
    }

    @ApiOperation("分页获取所有美容服务")
    @GetMapping("/page")
    public cn.edu.xaut.domain.vo.PageResultVO<BeautyDO> getBeautiesPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        return beautyService.getBeautiesPage(pageNum, pageSize);
    }

    @ApiOperation("按类型分页获取美容服务")
    @GetMapping("/type/{type}/page")
    public cn.edu.xaut.domain.vo.PageResultVO<BeautyDO> getBeautiesByTypePage(@PathVariable("type") String beautyType, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        return beautyService.getBeautiesByTypePage(beautyType, pageNum, pageSize);
    }
}