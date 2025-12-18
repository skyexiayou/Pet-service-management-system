package cn.edu.xaut.controller;

import cn.edu.xaut.domain.vo.ResponseVO;
import cn.edu.xaut.domain.vo.medical.MedicalServiceDetailVO;
import cn.edu.xaut.domain.vo.medical.MedicalServiceVO;
import cn.edu.xaut.service.medical.MedicalServiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 医疗服务Controller
 */
@RestController
@RequestMapping("/api/medical-services")
@Api(tags = "医疗服务管理")
public class MedicalServiceController {

    @Autowired
    private MedicalServiceService medicalServiceService;

    @ApiOperation("查询用户的医疗服务列表")
    @GetMapping("/user/{userId}")
    public ResponseVO<List<MedicalServiceVO>> getMedicalServicesByUserId(
            @ApiParam(value = "用户ID", required = true) @PathVariable Integer userId) {
        List<MedicalServiceVO> services = medicalServiceService.getMedicalServicesByUserId(userId);
        return ResponseVO.success(services);
    }

    @ApiOperation("查询医疗服务详情")
    @GetMapping("/{medicalId}")
    public ResponseVO<MedicalServiceDetailVO> getMedicalServiceDetail(
            @ApiParam(value = "医疗记录ID", required = true) @PathVariable Integer medicalId) {
        MedicalServiceDetailVO detail = medicalServiceService.getMedicalServiceDetail(medicalId);
        return ResponseVO.success(detail);
    }
}
