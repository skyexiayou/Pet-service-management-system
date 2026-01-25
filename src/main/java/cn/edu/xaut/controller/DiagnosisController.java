package cn.edu.xaut.controller;

import cn.edu.xaut.domain.dto.diagnosis.DiagnosisSaveDTO;
import cn.edu.xaut.domain.vo.ResponseVO;
import cn.edu.xaut.domain.vo.appointment.AppointmentDetailVO;
import cn.edu.xaut.service.appointment.AppointmentService;
import cn.edu.xaut.service.diagnosis.DiagnosisService;
import cn.edu.xaut.service.order.PetOrderService;
import cn.edu.xaut.utils.OrderNoGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 诊断相关接口控制器
 */
@RestController
@RequestMapping("/api/diagnosis")
@Api(tags = "诊断相关接口")
public class DiagnosisController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DiagnosisService diagnosisService;
    
    @Autowired
    private PetOrderService petOrderService;

    @ApiOperation("待诊断预约列表接口")
    @GetMapping("/appointment/list")
    public ResponseVO<List<AppointmentDetailVO>> getWaitDiagnoseAppointments(
            @ApiParam(value = "门店ID", required = true) @RequestParam Integer storeId,
            @ApiParam(value = "搜索关键词", required = false) @RequestParam(required = false) String keyword,
            @ApiParam(value = "页码", required = false, defaultValue = "1") @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页条数", required = false, defaultValue = "10") @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        List<AppointmentDetailVO> list = appointmentService.getWaitDiagnoseAppointments(storeId, keyword, pageNum, pageSize);
        return ResponseVO.success(list);
    }

    @ApiOperation("生成处方接口")
    @PostMapping("/prescription/create")
    public ResponseVO<String> createPrescription(
            @ApiParam(value = "诊断和处方数据", required = true) @RequestBody DiagnosisSaveDTO diagnosisSaveDTO) {
        diagnosisService.saveDiagnosisWithPrescription(diagnosisSaveDTO);
        return ResponseVO.success("处方生成成功");
    }
    
    @ApiOperation("生成订单接口")
    @PostMapping("/order/create")
    public ResponseVO<java.util.Map<String, Object>> createOrder(
            @ApiParam(value = "处方ID和诊疗费用", required = true) @RequestBody java.util.Map<String, Object> requestBody) {
        Integer prescriptionId = (Integer) requestBody.get("prescriptionId");
        java.math.BigDecimal medicalFee = new java.math.BigDecimal(requestBody.get("medicalFee").toString());
        
        Integer orderId = petOrderService.generateOrderByPrescription(prescriptionId, medicalFee);
        
        java.util.Map<String, Object> result = new java.util.HashMap<>();
        result.put("orderId", orderId);
        result.put("orderNo", OrderNoGenerator.generate());
        
        return ResponseVO.success(result);
    }
}
