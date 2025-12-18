package cn.edu.xaut.controller.admin;

import cn.edu.xaut.domain.dto.admin.BeautyServiceDTO;
import cn.edu.xaut.domain.entity.beauty.BeautyDO;
import cn.edu.xaut.domain.vo.PageResultVO;
import cn.edu.xaut.domain.vo.ResponseVO;
import cn.edu.xaut.service.beauty.BeautyAdminService;
import cn.edu.xaut.utils.AdminAuthUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员-美容服务管理Controller
 * @date 2025-12-18
 */
@RestController
@RequestMapping("/api/admin/beauty")
@Api(tags = "管理员-美容服务管理")
public class AdminBeautyController {

    @Autowired
    private BeautyAdminService beautyAdminService;

    @Autowired
    private AdminAuthUtil adminAuthUtil;

    @ApiOperation("分页查询美容服务列表")
    @GetMapping
    public ResponseVO<PageResultVO<BeautyDO>> getBeautyList(
            @ApiParam("用户名") @RequestParam(required = false) String userName,
            @ApiParam("手机号") @RequestParam(required = false) String phone,
            @ApiParam("页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam("每页大小") @RequestParam(defaultValue = "10") Integer pageSize) {
        // 验证管理员身份
        adminAuthUtil.validateAdmin(userName, phone);
        
        return ResponseVO.success(beautyAdminService.getBeautyList(pageNum, pageSize));
    }

    @ApiOperation("根据ID查询美容服务")
    @GetMapping("/{beautyId}")
    public ResponseVO<BeautyDO> getBeautyById(
            @ApiParam("美容服务ID") @PathVariable Integer beautyId,
            @ApiParam("用户名") @RequestParam(required = false) String userName,
            @ApiParam("手机号") @RequestParam(required = false) String phone) {
        // 验证管理员身份
        adminAuthUtil.validateAdmin(userName, phone);
        
        return ResponseVO.success(beautyAdminService.getBeautyById(beautyId));
    }

    @ApiOperation("创建美容服务")
    @PostMapping
    public ResponseVO<Integer> createBeauty(
            @ApiParam("用户名") @RequestParam(required = false) String userName,
            @ApiParam("手机号") @RequestParam(required = false) String phone,
            @Validated @RequestBody BeautyServiceDTO beautyServiceDTO) {
        // 验证管理员身份
        adminAuthUtil.validateAdmin(userName, phone);
        
        return ResponseVO.success(beautyAdminService.createBeauty(beautyServiceDTO));
    }

    @ApiOperation("更新美容服务")
    @PutMapping("/{beautyId}")
    public ResponseVO<Integer> updateBeauty(
            @ApiParam("美容服务ID") @PathVariable Integer beautyId,
            @ApiParam("用户名") @RequestParam(required = false) String userName,
            @ApiParam("手机号") @RequestParam(required = false) String phone,
            @Validated @RequestBody BeautyServiceDTO beautyServiceDTO) {
        // 验证管理员身份
        adminAuthUtil.validateAdmin(userName, phone);
        
        return ResponseVO.success(beautyAdminService.updateBeauty(beautyId, beautyServiceDTO));
    }

    @ApiOperation("删除美容服务")
    @DeleteMapping("/{beautyId}")
    public ResponseVO<Integer> deleteBeauty(
            @ApiParam("美容服务ID") @PathVariable Integer beautyId,
            @ApiParam("用户名") @RequestParam(required = false) String userName,
            @ApiParam("手机号") @RequestParam(required = false) String phone) {
        // 验证管理员身份
        adminAuthUtil.validateAdmin(userName, phone);
        
        return ResponseVO.success(beautyAdminService.deleteBeauty(beautyId));
    }
}
