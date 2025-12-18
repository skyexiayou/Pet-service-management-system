package cn.edu.xaut.controller.admin;

import cn.edu.xaut.domain.dto.admin.ProductStoreDTO;
import cn.edu.xaut.domain.dto.admin.StockAdjustmentDTO;
import cn.edu.xaut.domain.entity.petproductstore.PetProductStoreDO;
import cn.edu.xaut.domain.vo.PageResultVO;
import cn.edu.xaut.domain.vo.ResponseVO;
import cn.edu.xaut.service.petproductstore.ProductStoreAdminService;
import cn.edu.xaut.utils.AdminAuthUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员-门店铺货管理Controller
 * @date 2025-12-18
 */
@RestController
@RequestMapping("/api/admin/product-store")
@Api(tags = "管理员-门店铺货管理")
public class AdminProductStoreController {

    @Autowired
    private ProductStoreAdminService productStoreAdminService;

    @Autowired
    private AdminAuthUtil adminAuthUtil;

    @ApiOperation("分页查询门店铺货列表")
    @GetMapping
    public ResponseVO<PageResultVO<PetProductStoreDO>> getProductStoreList(
            @ApiParam("用户名") @RequestParam(required = false) String userName,
            @ApiParam("手机号") @RequestParam(required = false) String phone,
            @ApiParam("页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam("每页大小") @RequestParam(defaultValue = "10") Integer pageSize) {
        // 验证管理员身份并获取门店ID
        Integer storeId = adminAuthUtil.getAdminStoreId(userName, phone);
        
        return ResponseVO.success(productStoreAdminService.getProductStoreList(storeId, pageNum, pageSize));
    }

    @ApiOperation("根据ID查询铺货信息")
    @GetMapping("/{relId}")
    public ResponseVO<PetProductStoreDO> getProductStoreById(
            @ApiParam("关联ID") @PathVariable Integer relId,
            @ApiParam("用户名") @RequestParam(required = false) String userName,
            @ApiParam("手机号") @RequestParam(required = false) String phone) {
        // 验证管理员身份
        adminAuthUtil.validateAdmin(userName, phone);
        
        return ResponseVO.success(productStoreAdminService.getProductStoreById(relId));
    }

    @ApiOperation("创建或更新门店铺货")
    @PostMapping
    public ResponseVO<Integer> upsertProductStore(
            @ApiParam("用户名") @RequestParam(required = false) String userName,
            @ApiParam("手机号") @RequestParam(required = false) String phone,
            @Validated @RequestBody ProductStoreDTO productStoreDTO) {
        // 验证管理员身份并获取门店ID
        Integer storeId = adminAuthUtil.getAdminStoreId(userName, phone);
        
        return ResponseVO.success(productStoreAdminService.upsertProductStore(storeId, productStoreDTO));
    }

    @ApiOperation("调整库存")
    @PutMapping("/{relId}/stock")
    public ResponseVO<Integer> adjustStock(
            @ApiParam("关联ID") @PathVariable Integer relId,
            @ApiParam("用户名") @RequestParam(required = false) String userName,
            @ApiParam("手机号") @RequestParam(required = false) String phone,
            @Validated @RequestBody StockAdjustmentDTO stockAdjustmentDTO) {
        // 验证管理员身份并获取门店ID
        Integer storeId = adminAuthUtil.getAdminStoreId(userName, phone);
        
        return ResponseVO.success(productStoreAdminService.adjustStock(relId, storeId, stockAdjustmentDTO));
    }
}
