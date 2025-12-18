package cn.edu.xaut.controller;

import cn.edu.xaut.domain.vo.ResponseVO;
import cn.edu.xaut.domain.vo.foster.FosterServiceDetailVO;
import cn.edu.xaut.domain.vo.foster.FosterServiceVO;
import cn.edu.xaut.service.foster.FosterServiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 寄养服务Controller
 */
@RestController
@RequestMapping("/api/foster-services")
@Api(tags = "寄养服务管理")
public class FosterServiceController {

    @Autowired
    private FosterServiceService fosterServiceService;

    @ApiOperation("查询用户的寄养服务列表")
    @GetMapping("/user/{userId}")
    public ResponseVO<List<FosterServiceVO>> getFosterServicesByUserId(
            @ApiParam(value = "用户ID", required = true) @PathVariable Integer userId) {
        List<FosterServiceVO> services = fosterServiceService.getFosterServicesByUserId(userId);
        return ResponseVO.success(services);
    }

    @ApiOperation("查询寄养服务详情")
    @GetMapping("/{fosterId}")
    public ResponseVO<FosterServiceDetailVO> getFosterServiceDetail(
            @ApiParam(value = "寄养ID", required = true) @PathVariable Integer fosterId) {
        FosterServiceDetailVO detail = fosterServiceService.getFosterServiceDetail(fosterId);
        return ResponseVO.success(detail);
    }

    @ApiOperation("接领确认")
    @PutMapping("/{fosterId}/pickup")
    public ResponseVO<Void> confirmPickup(
            @ApiParam(value = "寄养ID", required = true) @PathVariable Integer fosterId) {
        fosterServiceService.confirmPickup(fosterId);
        return ResponseVO.success(null);
    }
}
