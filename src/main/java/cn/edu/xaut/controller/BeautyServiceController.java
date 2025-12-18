package cn.edu.xaut.controller;

import cn.edu.xaut.domain.vo.ResponseVO;
import cn.edu.xaut.domain.vo.beauty.BeautyServiceDetailVO;
import cn.edu.xaut.domain.vo.beauty.BeautyServiceVO;
import cn.edu.xaut.service.beauty.BeautyServiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
