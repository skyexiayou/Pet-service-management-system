package cn.edu.xaut.controller.admin;

import cn.edu.xaut.domain.dto.admin.BeautyServiceDTO;
import cn.edu.xaut.domain.entity.beauty.BeautyDO;
import cn.edu.xaut.domain.vo.PageResultVO;
import cn.edu.xaut.domain.vo.ResponseVO;
import cn.edu.xaut.domain.vo.admin.AdminBeautyServiceDetailVO;
import cn.edu.xaut.domain.vo.admin.AdminBeautyServiceVO;
import cn.edu.xaut.service.admin.AdminBeautyServiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * 管理员美容服务记录控制器
 */
@RestController
@RequestMapping("/api/admin/beauty-services")
@Api(tags = "管理员美容服务记录管理")
public class AdminBeautyServiceController {

    @Autowired
    private AdminBeautyServiceService adminBeautyServiceService;

    @ApiOperation("获取当前管理员负责的美容服务记录")
    @GetMapping("/my")
    public ResponseVO<PageResultVO<AdminBeautyServiceVO>> getMyBeautyServices(
            @ApiParam(value = "员工ID", required = true) @RequestParam Integer empId,
            @ApiParam(value = "页码", defaultValue = "1") @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页大小", defaultValue = "10") @RequestParam(defaultValue = "10") Integer pageSize) {
        PageResultVO<AdminBeautyServiceVO> result = adminBeautyServiceService.getMyBeautyServices(empId, pageNum, pageSize);
        return ResponseVO.success(result);
    }

    @ApiOperation("获取所有美容服务记录")
    @GetMapping
    public ResponseVO<PageResultVO<AdminBeautyServiceVO>> getAllBeautyServices(
            @ApiParam(value = "页码", defaultValue = "1") @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页大小", defaultValue = "10") @RequestParam(defaultValue = "10") Integer pageSize) {
        PageResultVO<AdminBeautyServiceVO> result = adminBeautyServiceService.getAllBeautyServices(pageNum, pageSize);
        return ResponseVO.success(result);
    }

    @ApiOperation("获取美容服务记录详情")
    @GetMapping("/{apptId}")
    public ResponseVO<AdminBeautyServiceDetailVO> getBeautyServiceDetail(
            @ApiParam(value = "预约ID", required = true) @PathVariable Integer apptId) {
        AdminBeautyServiceDetailVO detail = adminBeautyServiceService.getBeautyServiceDetail(apptId);
        return ResponseVO.success(detail);
    }

    @ApiOperation("删除美容服务记录（预约）")
    @DeleteMapping("/{apptId}")
    public ResponseVO<Integer> deleteBeautyService(
            @ApiParam(value = "预约ID", required = true) @PathVariable Integer apptId) {
        Integer result = adminBeautyServiceService.deleteBeautyService(apptId);
        return ResponseVO.success(result);
    }

    @ApiOperation("获取所有美容项目")
    @GetMapping("/items")
    public ResponseVO<PageResultVO<BeautyDO>> getAllBeautyItems(
            @ApiParam(value = "页码", defaultValue = "1") @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页大小", defaultValue = "10") @RequestParam(defaultValue = "10") Integer pageSize) {
        PageResultVO<BeautyDO> result = adminBeautyServiceService.getAllBeautyItems(pageNum, pageSize);
        return ResponseVO.success(result);
    }

    @ApiOperation("创建美容项目")
    @PostMapping("/items")
    public ResponseVO<Integer> createBeautyItem(
            @ApiParam(value = "美容项目数据", required = true) @Valid @RequestBody BeautyServiceDTO dto) {
        Integer beautyId = adminBeautyServiceService.createBeautyService(dto, null);
        return ResponseVO.success(beautyId);
    }

    @ApiOperation("更新美容项目")
    @PutMapping("/items/{beautyId}")
    public ResponseVO<Integer> updateBeautyItem(
            @ApiParam(value = "美容项目ID", required = true) @PathVariable Integer beautyId,
            @ApiParam(value = "美容项目数据", required = true) @Valid @RequestBody BeautyServiceDTO dto) {
        Integer result = adminBeautyServiceService.updateBeautyService(beautyId, dto);
        return ResponseVO.success(result);
    }

    @ApiOperation("删除美容项目")
    @DeleteMapping("/items/{beautyId}")
    public ResponseVO<Integer> deleteBeautyItem(
            @ApiParam(value = "美容项目ID", required = true) @PathVariable Integer beautyId) {
        Integer result = adminBeautyServiceService.deleteBeautyService(beautyId);
        return ResponseVO.success(result);
    }
}
