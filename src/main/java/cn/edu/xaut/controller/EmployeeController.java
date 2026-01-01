package cn.edu.xaut.controller;

import cn.edu.xaut.domain.entity.employee.EmployeeDO;
import cn.edu.xaut.domain.vo.PageResultVO;
import cn.edu.xaut.domain.vo.ResponseVO;
import cn.edu.xaut.service.employee.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 员工查询Controller（公开接口）
 * 供普通用户查询门店员工信息，用于预约服务时选择服务人员
 */
@RestController
@RequestMapping("/api/employees")
@Api(tags = "员工查询")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @ApiOperation("根据门店ID查询员工列表")
    @GetMapping
    public ResponseVO<PageResultVO<EmployeeDO>> getEmployeesByStoreId(
            @ApiParam("门店ID") @RequestParam Integer storeId,
            @ApiParam("页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam("每页大小") @RequestParam(defaultValue = "100") Integer pageSize) {
        return ResponseVO.success(employeeService.getEmployeesByStoreId(storeId, pageNum, pageSize));
    }

    @ApiOperation("根据ID查询员工详情")
    @GetMapping("/{empId}")
    public ResponseVO<EmployeeDO> getEmployeeById(
            @ApiParam("员工ID") @PathVariable Integer empId) {
        return ResponseVO.success(employeeService.getEmployeeById(empId));
    }
}
