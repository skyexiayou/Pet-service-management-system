package cn.edu.xaut.service.employee;

import cn.edu.xaut.domain.dto.admin.EmployeeDTO;
import cn.edu.xaut.domain.entity.employee.EmployeeDO;
import cn.edu.xaut.domain.vo.PageResultVO;

/**
 * 员工Service接口
 * @date 2025-12-18
 */
public interface EmployeeService {
    
    /**
     * 分页查询门店员工列表
     * 
     * @param storeId 门店ID
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 员工列表
     */
    PageResultVO<EmployeeDO> getEmployeesByStoreId(Integer storeId, Integer pageNum, Integer pageSize);
    
    /**
     * 根据ID查询员工
     * 
     * @param empId 员工ID
     * @return 员工信息
     */
    EmployeeDO getEmployeeById(Integer empId);
    
    /**
     * 创建员工
     * 
     * @param storeId 门店ID
     * @param employeeDTO 员工DTO
     * @return 员工ID
     */
    Integer createEmployee(Integer storeId, EmployeeDTO employeeDTO);
    
    /**
     * 更新员工
     * 
     * @param empId 员工ID
     * @param storeId 门店ID（权限校验）
     * @param employeeDTO 员工DTO
     * @return 更新结果
     */
    Integer updateEmployee(Integer empId, Integer storeId, EmployeeDTO employeeDTO);
    
    /**
     * 删除员工
     * 
     * @param empId 员工ID
     * @param storeId 门店ID（权限校验）
     * @return 删除结果
     */
    Integer deleteEmployee(Integer empId, Integer storeId);
}
