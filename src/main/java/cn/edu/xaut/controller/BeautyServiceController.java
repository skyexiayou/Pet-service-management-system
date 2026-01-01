package cn.edu.xaut.controller;

import cn.edu.xaut.domain.dto.beauty.BeautyAppointmentDTO;
import cn.edu.xaut.domain.entity.beauty.BeautyDO;
import cn.edu.xaut.domain.vo.ResponseVO;
import cn.edu.xaut.domain.vo.beauty.BeautyServiceDetailVO;
import cn.edu.xaut.domain.vo.beauty.BeautyServiceVO;
import cn.edu.xaut.service.beauty.BeautyServiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 美容服务Controller
 */
@RestController
@RequestMapping("/api/beauty-services")
@Api(tags = "美容服务管理")
public class BeautyServiceController {

    @Autowired
    private BeautyServiceService beautyServiceService;

    @ApiOperation("查询用户的美容服务列表")
    @GetMapping("/user/{userId}")
    public ResponseVO<List<BeautyServiceVO>> getBeautyServicesByUserId(
            @ApiParam(value = "用户ID", required = true) @PathVariable Integer userId) {
        List<BeautyServiceVO> services = beautyServiceService.getBeautyServicesByUserId(userId);
        return ResponseVO.success(services);
    }

    @ApiOperation("查询美容服务详情")
    @GetMapping("/{apptId}")
    public ResponseVO<BeautyServiceDetailVO> getBeautyServiceDetail(
            @ApiParam(value = "预约ID", required = true) @PathVariable Integer apptId) {
        BeautyServiceDetailVO detail = beautyServiceService.getBeautyServiceDetail(apptId);
        return ResponseVO.success(detail);
    }

    @ApiOperation("检查用户是否有注册宠物")
    @GetMapping("/check-pets/{userId}")
    public ResponseVO<Boolean> checkUserHasPets(
            @ApiParam(value = "用户ID", required = true) @PathVariable Integer userId) {
        boolean hasPets = beautyServiceService.checkUserHasPets(userId);
        return ResponseVO.success(hasPets);
    }

    @ApiOperation("创建美容预约")
    @PostMapping("/appointment")
    public ResponseVO<Integer> createBeautyAppointment(
            @ApiParam(value = "美容预约数据", required = true) @Valid @RequestBody BeautyAppointmentDTO dto) {
        Integer apptId = beautyServiceService.createBeautyAppointment(dto);
        return ResponseVO.success(apptId);
    }

    @ApiOperation("更新美容预约")
    @PutMapping("/{apptId}")
    public ResponseVO<Integer> updateBeautyAppointment(
            @ApiParam(value = "预约ID", required = true) @PathVariable Integer apptId,
            @ApiParam(value = "美容预约数据", required = true) @Valid @RequestBody BeautyAppointmentDTO dto) {
        Integer result = beautyServiceService.updateBeautyAppointment(apptId, dto);
        return ResponseVO.success(result);
    }

    @ApiOperation("取消美容预约")
    @DeleteMapping("/{apptId}")
    public ResponseVO<Integer> cancelBeautyAppointment(
            @ApiParam(value = "预约ID", required = true) @PathVariable Integer apptId) {
        Integer result = beautyServiceService.cancelBeautyAppointment(apptId);
        return ResponseVO.success(result);
    }

    @ApiOperation("获取所有美容项目（用户预览）")
    @GetMapping("/items")
    public ResponseVO<List<BeautyDO>> getAllBeautyItems() {
        List<BeautyDO> items = beautyServiceService.getAllBeautyItems();
        return ResponseVO.success(items);
    }
}
