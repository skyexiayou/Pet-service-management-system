package cn.edu.xaut.controller.admin;

import cn.edu.xaut.domain.dto.admin.FosterRecordDTO;
import cn.edu.xaut.domain.vo.PageResultVO;
import cn.edu.xaut.domain.vo.ResponseVO;
import cn.edu.xaut.domain.vo.admin.AdminFosterRecordDetailVO;
import cn.edu.xaut.domain.vo.admin.AdminFosterRecordVO;
import cn.edu.xaut.service.admin.AdminFosterRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * 管理员寄养记录控制器
 */
@RestController
@RequestMapping("/api/admin/foster-records")
@Api(tags = "管理员寄养记录管理")
public class AdminFosterRecordController {

    @Autowired
    private AdminFosterRecordService adminFosterRecordService;

    @ApiOperation("获取当前管理员负责的寄养记录")
    @GetMapping("/my")
    public ResponseVO<PageResultVO<AdminFosterRecordVO>> getMyFosterRecords(
            @ApiParam(value = "员工ID", required = true) @RequestParam Integer empId,
            @ApiParam(value = "页码", defaultValue = "1") @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页大小", defaultValue = "10") @RequestParam(defaultValue = "10") Integer pageSize) {
        PageResultVO<AdminFosterRecordVO> result = adminFosterRecordService.getMyFosterRecords(empId, pageNum, pageSize);
        return ResponseVO.success(result);
    }

    @ApiOperation("获取所有寄养记录")
    @GetMapping
    public ResponseVO<PageResultVO<AdminFosterRecordVO>> getAllFosterRecords(
            @ApiParam(value = "页码", defaultValue = "1") @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页大小", defaultValue = "10") @RequestParam(defaultValue = "10") Integer pageSize) {
        PageResultVO<AdminFosterRecordVO> result = adminFosterRecordService.getAllFosterRecords(pageNum, pageSize);
        return ResponseVO.success(result);
    }

    @ApiOperation("获取寄养记录详情")
    @GetMapping("/{fosterId}")
    public ResponseVO<AdminFosterRecordDetailVO> getFosterRecordDetail(
            @ApiParam(value = "寄养ID", required = true) @PathVariable Integer fosterId) {
        AdminFosterRecordDetailVO detail = adminFosterRecordService.getFosterRecordDetail(fosterId);
        return ResponseVO.success(detail);
    }

    @ApiOperation("创建寄养记录")
    @PostMapping
    public ResponseVO<Integer> createFosterRecord(
            @ApiParam(value = "寄养记录数据", required = true) @Valid @RequestBody FosterRecordDTO dto,
            @ApiParam(value = "当前管理员ID") @RequestParam(required = false) Integer empId) {
        Integer fosterId = adminFosterRecordService.createFosterRecord(dto, empId);
        return ResponseVO.success(fosterId);
    }

    @ApiOperation("更新寄养记录")
    @PutMapping("/{fosterId}")
    public ResponseVO<Integer> updateFosterRecord(
            @ApiParam(value = "寄养ID", required = true) @PathVariable Integer fosterId,
            @ApiParam(value = "寄养记录数据", required = true) @Valid @RequestBody FosterRecordDTO dto) {
        Integer result = adminFosterRecordService.updateFosterRecord(fosterId, dto);
        return ResponseVO.success(result);
    }

    @ApiOperation("删除寄养记录")
    @DeleteMapping("/{fosterId}")
    public ResponseVO<Integer> deleteFosterRecord(
            @ApiParam(value = "寄养ID", required = true) @PathVariable Integer fosterId) {
        Integer result = adminFosterRecordService.deleteFosterRecord(fosterId);
        return ResponseVO.success(result);
    }
}
