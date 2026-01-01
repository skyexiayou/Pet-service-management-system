package cn.edu.xaut.controller.admin;

import cn.edu.xaut.domain.dto.medicalrecord.MedicalRecordDTO;
import cn.edu.xaut.domain.vo.PageResultVO;
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

/**
 * 管理员医疗记录控制器
 */
@RestController
@RequestMapping("/api/admin/medical-records")
@Api(tags = "管理员-医疗记录管理接口")
public class AdminMedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @ApiOperation("获取当前管理员负责的医疗记录（分页）")
    @GetMapping("/my")
    public ResponseVO<PageResultVO<MedicalRecordVO>> getMyMedicalRecords(
            @ApiParam(value = "员工ID", required = true) @RequestParam Integer empId,
            @ApiParam(value = "页码", defaultValue = "1") @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页大小", defaultValue = "10") @RequestParam(defaultValue = "10") Integer pageSize) {
        PageResultVO<MedicalRecordVO> result = medicalRecordService.getMyMedicalRecords(empId, pageNum, pageSize);
        return ResponseVO.success(result);
    }

    @ApiOperation("获取所有医疗记录（分页）")
    @GetMapping
    public ResponseVO<PageResultVO<MedicalRecordVO>> getAllMedicalRecords(
            @ApiParam(value = "页码", defaultValue = "1") @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页大小", defaultValue = "10") @RequestParam(defaultValue = "10") Integer pageSize) {
        PageResultVO<MedicalRecordVO> result = medicalRecordService.getAllMedicalRecords(pageNum, pageSize);
        return ResponseVO.success(result);
    }

    @ApiOperation("获取医疗记录详情")
    @GetMapping("/{medicalId}")
    public ResponseVO<MedicalRecordDetailVO> getMedicalRecordDetail(
            @ApiParam(value = "医疗记录ID", required = true) @PathVariable Integer medicalId) {
        MedicalRecordDetailVO detail = medicalRecordService.getMedicalRecordDetail(medicalId);
        return ResponseVO.success(detail);
    }

    @ApiOperation("创建医疗记录")
    @PostMapping
    public ResponseVO<Integer> createMedicalRecord(
            @ApiParam(value = "医疗记录信息", required = true) @RequestBody @Validated MedicalRecordDTO dto) {
        Integer medicalId = medicalRecordService.createMedicalRecord(dto);
        return ResponseVO.success(medicalId);
    }

    @ApiOperation("更新医疗记录")
    @PutMapping("/{medicalId}")
    public ResponseVO<Void> updateMedicalRecord(
            @ApiParam(value = "医疗记录ID", required = true) @PathVariable Integer medicalId,
            @ApiParam(value = "医疗记录信息", required = true) @RequestBody @Validated MedicalRecordDTO dto) {
        medicalRecordService.updateMedicalRecord(medicalId, dto);
        return ResponseVO.success(null);
    }

    @ApiOperation("删除医疗记录")
    @DeleteMapping("/{medicalId}")
    public ResponseVO<Void> deleteMedicalRecord(
            @ApiParam(value = "医疗记录ID", required = true) @PathVariable Integer medicalId) {
        medicalRecordService.deleteMedicalRecord(medicalId);
        return ResponseVO.success(null);
    }
}
