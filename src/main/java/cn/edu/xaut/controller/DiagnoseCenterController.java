package cn.edu.xaut.controller;

import cn.edu.xaut.domain.vo.diagnose.DiagnoseWorkOrderVO;
import cn.edu.xaut.domain.vo.diagnose.MedicalRecordDetailVO;
import cn.edu.xaut.mapper.DiagnoseCenterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 诊断中心控制器
 */
@RestController
@RequestMapping("/api/diagnose-center")
public class DiagnoseCenterController {

    @Autowired
    private DiagnoseCenterMapper diagnoseCenterMapper;

    /**
     * 获取诊断工单列表
     * 
     * @param status  诊断状态
     * @param keyword 关键字
     * @param empId   员工ID
     * @return 诊断工单列表
     */
    @GetMapping("/work-orders")
    public List<DiagnoseWorkOrderVO> getDiagnoseWorkOrders(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer empId) {
        return diagnoseCenterMapper.selectDiagnoseWorkOrderList(status, keyword, empId);
    }

    /**
     * 获取待诊断工单数量
     * 
     * @return 待诊断工单数量
     */
    @GetMapping("/pending-count")
    public Integer getPendingCount() {
        return diagnoseCenterMapper.selectPendingCount();
    }

    /**
     * 根据预约ID获取病历详情
     * 
     * @param apptId 预约ID
     * @return 病历详情
     */
    @GetMapping("/medical-record/{apptId}")
    public MedicalRecordDetailVO getMedicalRecordDetail(@PathVariable Long apptId) {
        return diagnoseCenterMapper.selectMedicalRecordDetailByApptId(apptId);
    }
}