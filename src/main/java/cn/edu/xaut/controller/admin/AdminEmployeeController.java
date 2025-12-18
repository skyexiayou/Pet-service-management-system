package cn.edu.xaut.controller.admin;

import cn.edu.xaut.domain.dto.admin.EmployeeDTO;
import cn.edu.xaut.domain.entity.employee.EmployeeDO;
import cn.edu.xaut.domain.vo.PageResultVO;
import cn.edu.xaut.domain.vo.ResponseVO;
import cn.edu.xaut.service.employee.EmployeeService;
import cn.edu.xaut.utils.AdminAuthUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员-员工管理Controller
 * @date 2025-12-18
 */
@RestController
@RequestMapping("/api/admin/employees")
@Api(tags = "管理员-员工管理")
public class AdminEmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AdminAuthUtil adminAuthUtil;

    @ApiOperation("分页查询门店员工列表")
    @GetMapping
    public ResponseVO<PageResultVO<EmployeeDO>> getEmployees(
            @ApiParam("用户名") @RequestParam(required = false) String userName,
            @ApiParam("手机号") @RequestParam(required = false) String phone,
            @ApiParam("页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam("每页大小") @RequestParam(defaultValue = "10") Integer pageSize) {
        // 验证管理员身份并获取门店ID
        Integer storeId = adminAuthUtil.getAdminStoreId(userName, phone);
        
        return ResponseVO.success(employeeService.getEmployeesByStoreId(storeId, pageNum, pageSize));
    }

    @ApiOperation("根据ID查询员工")
    @GetMapping("/{empId}")
    public ResponseVO<EmployeeDO> getEmployeeById(
            @ApiParam("员工ID") @PathVariable Integer empId,
            @ApiParam("用户名") @RequestParam(required = false) String userName,
            @ApiParam("手机号") @RequestParam(required = false) String phone) {
        // 验证管理员身份
        adminAuthUtil.validateAdmin(userName, phone);
        
        return ResponseVO.success(employeeService.getEmployeeById(empId));
    }

    @ApiOperation("创建员工")
    @PostMapping
    public ResponseVO<Integer> createEmployee(
            @ApiParam("用户名") @RequestParam(required = false) String userName,
            @ApiParam("手机号") @RequestParam(required = false) String phone,
            @Validated @RequestBody EmployeeDTO employeeDTO) {
        // 验证管理员身份并获取门店ID
        Integer storeId = adminAuthUtil.getAdminStoreId(userName, phone);
        
        return ResponseVO.success(employeeService.createEmployee(storeId, employeeDTO));
    }

    @ApiOperation("更新员工")
    @PutMapping("/{empId}")
    public ResponseVO<Integer> updateEmployee(
            @ApiParam("员工ID") @PathVariable Integer empId,
            @ApiParam("用户名") @RequestParam(required = false) String userName,
            @ApiParam("手机号") @RequestParam(required = false) String phone,
            @Validated @RequestBody EmployeeDTO employeeDTO) {
        // 验证管理员身份并获取门店ID
        Integer storeId = adminAuthUtil.getAdminStoreId(userName, phone);
        
        return ResponseVO.success(employeeService.updateEmployee(empId, storeId, employeeDTO));
    }

    @ApiOperation("删除员工")
    @DeleteMapping("/{empId}")
    public ResponseVO<Integer> deleteEmployee(
            @ApiParam("员工ID") @PathVariable Integer empId,
            @ApiParam("用户名") @RequestParam(required = false) String userName,
            @ApiParam("手机号") @RequestParam(required = false) String phone) {
        // 验证管理员身份并获取门店ID
        Integer storeId = adminAuthUtil.getAdminStoreId(userName, phone);
        
        return ResponseVO.success(employeeService.deleteEmployee(empId, storeId));
    }
}
