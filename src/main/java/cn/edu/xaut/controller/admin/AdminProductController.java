package cn.edu.xaut.controller.admin;

import cn.edu.xaut.domain.dto.admin.ProductDTO;
import cn.edu.xaut.domain.dto.admin.ProductCreateDTO;
import cn.edu.xaut.domain.entity.petproduct.PetProductDO;
import cn.edu.xaut.domain.vo.PageResultVO;
import cn.edu.xaut.domain.vo.ResponseVO;
import cn.edu.xaut.domain.vo.petproduct.ProductWithStoreVO;
import cn.edu.xaut.service.petproduct.PetProductAdminService;
import cn.edu.xaut.utils.AdminAuthUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员-宠物用品管理Controller
 * @date 2025-12-18
 */
@RestController
@RequestMapping("/api/admin/products")
@Api(tags = "管理员-宠物用品管理")
public class AdminProductController {

    @Autowired
    private PetProductAdminService petProductAdminService;

    @Autowired
    private AdminAuthUtil adminAuthUtil;

    @ApiOperation("分页查询用品列表")
    @GetMapping
    public ResponseVO<PageResultVO<PetProductDO>> getProductList(
            @ApiParam("用户名") @RequestParam(required = false) String userName,
            @ApiParam("手机号") @RequestParam(required = false) String phone,
            @ApiParam("页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam("每页大小") @RequestParam(defaultValue = "10") Integer pageSize) {
        // 验证管理员身份
        adminAuthUtil.validateAdmin(userName, phone);
        
        return ResponseVO.success(petProductAdminService.getProductList(pageNum, pageSize));
    }
    
    @ApiOperation("获取所有商品（带门店信息）")
    @GetMapping("/with-store")
    public ResponseVO<List<ProductWithStoreVO>> getAllProductsWithStore() {
        return ResponseVO.success(petProductAdminService.getAllProductsWithStore());
    }

    @ApiOperation("根据ID查询用品")
    @GetMapping("/{productId}")
    public ResponseVO<PetProductDO> getProductById(
            @ApiParam("用品ID") @PathVariable Integer productId,
            @ApiParam("用户名") @RequestParam(required = false) String userName,
            @ApiParam("手机号") @RequestParam(required = false) String phone) {
        // 验证管理员身份
        adminAuthUtil.validateAdmin(userName, phone);
        
        return ResponseVO.success(petProductAdminService.getProductById(productId));
    }

    @ApiOperation("创建用品")
    @PostMapping
    public ResponseVO<Integer> createProduct(
            @ApiParam("用户名") @RequestParam(required = false) String userName,
            @ApiParam("手机号") @RequestParam(required = false) String phone,
            @Validated @RequestBody ProductDTO productDTO) {
        // 验证管理员身份
        adminAuthUtil.validateAdmin(userName, phone);
        
        return ResponseVO.success(petProductAdminService.createProduct(productDTO));
    }
    
    @ApiOperation("创建商品（带门店信息）")
    @PostMapping("/with-store")
    public ResponseVO<Integer> createProductWithStore(
            @Validated @RequestBody ProductCreateDTO productCreateDTO) {
        return ResponseVO.success(petProductAdminService.createProductWithStore(productCreateDTO));
    }

    @ApiOperation("更新用品")
    @PutMapping("/{productId}")
    public ResponseVO<Integer> updateProduct(
            @ApiParam("用品ID") @PathVariable Integer productId,
            @ApiParam("用户名") @RequestParam(required = false) String userName,
            @ApiParam("手机号") @RequestParam(required = false) String phone,
            @Validated @RequestBody ProductDTO productDTO) {
        // 验证管理员身份
        adminAuthUtil.validateAdmin(userName, phone);
        
        return ResponseVO.success(petProductAdminService.updateProduct(productId, productDTO));
    }
    
    @ApiOperation("更新商品（带门店信息）")
    @PutMapping("/{productId}/with-store")
    public ResponseVO<Integer> updateProductWithStore(
            @ApiParam("商品ID") @PathVariable Integer productId,
            @Validated @RequestBody ProductCreateDTO productCreateDTO) {
        return ResponseVO.success(petProductAdminService.updateProductWithStore(productId, productCreateDTO));
    }
    
    @ApiOperation("删除商品")
    @DeleteMapping("/{productId}")
    public ResponseVO<Integer> deleteProduct(
            @ApiParam("商品ID") @PathVariable Integer productId) {
        return ResponseVO.success(petProductAdminService.deleteProduct(productId));
    }
}
