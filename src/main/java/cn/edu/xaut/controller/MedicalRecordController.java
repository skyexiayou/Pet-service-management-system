package cn.edu.xaut.controller;

import cn.edu.xaut.domain.dto.medicalrecord.MedicalAppointmentDTO;
import cn.edu.xaut.domain.vo.ResponseVO;
import cn.edu.xaut.domain.vo.medicalrecord.MedicalRecordDetailVO;
import cn.edu.xaut.domain.vo.medicalrecord.MedicalRecordVO;
import cn.edu.xaut.service.medicalrecord.MedicalRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户医疗记录控制器
 */
@RestController
@RequestMapping("/api/medical-records")
@Api(tags = "用户-医疗记录接口")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @ApiOperation("获取用户的医疗记录列表")
    @GetMapping("/user/{userId}")
    public ResponseVO<List<MedicalRecordVO>> getUserMedicalRecords(
            @ApiParam(value = "用户ID", required = true) @PathVariable Integer userId) {
        List<MedicalRecordVO> records = medicalRecordService.getUserMedicalRecords(userId);
        return ResponseVO.success(records);
    }

    @ApiOperation("获取医疗记录详情")
    @GetMapping("/{medicalId}")
    public ResponseVO<MedicalRecordDetailVO> getMedicalRecordDetail(
            @ApiParam(value = "医疗记录ID", required = true) @PathVariable Integer medicalId) {
        MedicalRecordDetailVO detail = medicalRecordService.getMedicalRecordDetail(medicalId);
        return ResponseVO.success(detail);
    }

    @ApiOperation("用户预约医疗服务")
    @PostMapping("/appointment")
    public ResponseVO<Integer> createMedicalAppointment(
            @ApiParam(value = "预约信息", required = true) @RequestBody @Validated MedicalAppointmentDTO dto) {
        Integer medicalId = medicalRecordService.createMedicalAppointment(dto);
        return ResponseVO.success(medicalId);
    }

    @ApiOperation("检查用户是否有注册宠物")
    @GetMapping("/check-pets/{userId}")
    public ResponseVO<Boolean> checkUserHasPets(
            @ApiParam(value = "用户ID", required = true) @PathVariable Integer userId) {
        boolean hasPets = medicalRecordService.checkUserHasPets(userId);
        return ResponseVO.success(hasPets);
    }
}
