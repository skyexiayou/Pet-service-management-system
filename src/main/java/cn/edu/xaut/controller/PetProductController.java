package cn.edu.xaut.controller;

/**
 * 宠物用品controller
 */

import cn.edu.xaut.domain.entity.petproduct.PetProductDO;
import cn.edu.xaut.service.petproduct.PetProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Api(tags = "宠物用品管理")
public class PetProductController {

    @Autowired
    private PetProductService petProductService;

    @ApiOperation("获取所有宠物用品")
    @GetMapping
    public List<PetProductDO> getAllProducts() {
        return petProductService.getAllProducts();
    }

    @ApiOperation("根据ID获取宠物用品")
    @GetMapping("/{id}")
    public PetProductDO getProductById(@PathVariable("id") Integer productId) {
        return petProductService.getProductById(productId);
    }

    @ApiOperation("根据类型获取宠物用品")
    @GetMapping("/type/{type}")
    public List<PetProductDO> getProductsByType(@PathVariable("type") String productType) {
        return petProductService.getProductsByType(productType);
    }

    @ApiOperation("创建宠物用品")
    @PostMapping
    public Integer createProduct(@RequestBody PetProductDO petProduct) {
        return petProductService.createProduct(petProduct);
    }

    @ApiOperation("更新宠物用品")
    @PutMapping("/{id}")
    public Integer updateProduct(@PathVariable("id") Integer productId, @RequestBody PetProductDO petProduct) {
        return petProductService.updateProduct(productId, petProduct);
    }

    @ApiOperation("删除宠物用品")
    @DeleteMapping("/{id}")
    public Integer deleteProduct(@PathVariable("id") Integer productId) {
        return petProductService.deleteProduct(productId);
    }

    @ApiOperation("分页获取所有宠物用品")
    @GetMapping("/page")
    public cn.edu.xaut.domain.vo.PageResultVO<cn.edu.xaut.domain.vo.petproduct.PetProductVO> getProducts(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return petProductService.getProducts(pageNum, pageSize);
    }

    @ApiOperation("按类型分页获取宠物用品")
    @GetMapping("/type/{type}/page")
    public cn.edu.xaut.domain.vo.PageResultVO<cn.edu.xaut.domain.vo.petproduct.PetProductVO> getProductsByType(
            @PathVariable("type") String productType,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return petProductService.getProductsByType(productType, pageNum, pageSize);
    }

    @ApiOperation("按门店ID分页获取宠物用品")
    @GetMapping("/store/{storeId}/page")
    public cn.edu.xaut.domain.vo.PageResultVO<cn.edu.xaut.domain.vo.petproduct.PetProductVO> getProductsByStoreId(
            @PathVariable("storeId") Integer storeId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return petProductService.getProductsByStoreId(storeId, pageNum, pageSize);
    }
}