package cn.edu.xaut.controller.admin;

import cn.edu.xaut.domain.dto.admin.FosterDailyUpdateDTO;
import cn.edu.xaut.domain.dto.admin.MedicalRecordCreateDTO;
import cn.edu.xaut.domain.vo.ResponseVO;
import cn.edu.xaut.service.admin.AdminServiceManagementService;
import cn.edu.xaut.utils.AdminAuthUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员专项服务管理Controller
 * 创建时间：2025-12-18
 */
@RestController
@RequestMapping("/api/admin/services")
@Api(tags = "管理员-专项服务管理")
@RequiredArgsConstructor
public class AdminServiceController {

    private final AdminServiceManagementService adminServiceManagementService;
    private final AdminAuthUtil adminAuthUtil;

    @PutMapping("/beauty/{apptId}/complete")
    @ApiOperation("完成美容服务")
    public ResponseVO<Void> completeBeautyService(
            @ApiParam("预约ID") @PathVariable Integer apptId,
            @ApiParam("管理员用户名") @RequestParam(required = false) String userName,
            @ApiParam("管理员手机号") @RequestParam(required = false) String phone) {
        
        Integer storeId = adminAuthUtil.getAdminStoreId(userName, phone);
        adminServiceManagementService.completeBeautyService(apptId, storeId);
        return ResponseVO.success(null);
    }

    @PutMapping("/foster/{fosterId}/daily-status")
    @ApiOperation("更新寄养每日状态")
    public ResponseVO<Void> updateFosterDailyStatus(
            @ApiParam("寄养ID") @PathVariable Integer fosterId,
            @ApiParam("更新信息") @Valid @RequestBody FosterDailyUpdateDTO updateDTO,
            @ApiParam("管理员用户名") @RequestParam(required = false) String userName,
            @ApiParam("管理员手机号") @RequestParam(required = false) String phone) {
        
        Integer storeId = adminAuthUtil.getAdminStoreId(userName, phone);
        adminServiceManagementService.updateFosterDailyStatus(fosterId, updateDTO, storeId);
        return ResponseVO.success(null);
    }

    @PostMapping("/medical")
    @ApiOperation("创建医疗记录")
    public ResponseVO<Integer> createMedicalRecord(
            @ApiParam("医疗记录信息") @Valid @RequestBody MedicalRecordCreateDTO createDTO,
            @ApiParam("管理员用户名") @RequestParam(required = false) String userName,
            @ApiParam("管理员手机号") @RequestParam(required = false) String phone) {
        
        Integer storeId = adminAuthUtil.getAdminStoreId(userName, phone);
        Integer medicalId = adminServiceManagementService.createMedicalRecord(createDTO, storeId);
        return ResponseVO.success(medicalId);
    }

    @PutMapping("/medical/{medicalId}/remark")
    @ApiOperation("补充医疗记录备注")
    public ResponseVO<Void> appendMedicalRemark(
            @ApiParam("医疗记录ID") @PathVariable Integer medicalId,
            @ApiParam("备注内容") @RequestParam String remark,
            @ApiParam("管理员用户名") @RequestParam(required = false) String userName,
            @ApiParam("管理员手机号") @RequestParam(required = false) String phone) {
        
        Integer storeId = adminAuthUtil.getAdminStoreId(userName, phone);
        adminServiceManagementService.appendMedicalRemark(medicalId, remark, storeId);
        return ResponseVO.success(null);
    }
}
