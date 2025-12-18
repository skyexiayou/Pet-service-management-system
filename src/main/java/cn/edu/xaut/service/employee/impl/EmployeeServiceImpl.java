package cn.edu.xaut.service.employee.impl;

import cn.edu.xaut.domain.dto.admin.EmployeeDTO;
import cn.edu.xaut.domain.entity.employee.EmployeeDO;
import cn.edu.xaut.domain.vo.PageResultVO;
import cn.edu.xaut.exception.BusinessException;
import cn.edu.xaut.mapper.EmployeeMapper;
import cn.edu.xaut.service.employee.EmployeeService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 员工Service实现类
 * @date 2025-12-18
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public PageResultVO<EmployeeDO> getEmployeesByStoreId(Integer storeId, Integer pageNum, Integer pageSize) {
        Page<EmployeeDO> page = new Page<>(pageNum, pageSize);
        Page<EmployeeDO> resultPage = employeeMapper.selectEmployeesByStoreId(page, storeId);
        
        return PageResultVO.<EmployeeDO>builder()
                .total(resultPage.getTotal())
                .list(resultPage.getRecords())
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();
    }

    @Override
    public EmployeeDO getEmployeeById(Integer empId) {
        EmployeeDO employee = employeeMapper.selectById(empId);
        if (employee == null) {
            throw new BusinessException("员工不存在");
        }
        return employee;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createEmployee(Integer storeId, EmployeeDTO employeeDTO) {
        // 校验手机号唯一性
        EmployeeDO existingEmployee = employeeMapper.selectByPhone(employeeDTO.getEmpPhone());
        if (existingEmployee != null) {
            throw new BusinessException("该手机号已被使用");
        }

        EmployeeDO employee = new EmployeeDO();
        BeanUtils.copyProperties(employeeDTO, employee);
        employee.setStoreId(storeId);
        
        employeeMapper.insert(employee);
        return employee.getEmpId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateEmployee(Integer empId, Integer storeId, EmployeeDTO employeeDTO) {
        // 校验员工是否存在
        EmployeeDO employee = getEmployeeById(empId);
        
        // 权限校验：只能修改本门店员工
        if (!employee.getStoreId().equals(storeId)) {
            throw new BusinessException("无权限修改其他门店员工信息");
        }

        // 校验手机号唯一性（排除自己）
        EmployeeDO existingEmployee = employeeMapper.selectByPhone(employeeDTO.getEmpPhone());
        if (existingEmployee != null && !existingEmployee.getEmpId().equals(empId)) {
            throw new BusinessException("该手机号已被使用");
        }

        BeanUtils.copyProperties(employeeDTO, employee);
        return employeeMapper.updateById(employee);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteEmployee(Integer empId, Integer storeId) {
        // 校验员工是否存在
        EmployeeDO employee = getEmployeeById(empId);
        
        // 权限校验：只能删除本门店员工
        if (!employee.getStoreId().equals(storeId)) {
            throw new BusinessException("无权限删除其他门店员工");
        }
        
        return employeeMapper.deleteById(empId);
    }
}
