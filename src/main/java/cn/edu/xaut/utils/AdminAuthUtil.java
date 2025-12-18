package cn.edu.xaut.utils;

import cn.edu.xaut.domain.entity.employee.EmployeeDO;
import cn.edu.xaut.domain.entity.user.UserDO;
import cn.edu.xaut.exception.BusinessException;
import cn.edu.xaut.mapper.EmployeeMapper;
import cn.edu.xaut.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 管理员身份验证工具类
 * 用于验证用户是否为管理员，并获取管理员所属门店ID
 * @date 2025-12-18
 */
@Component
public class AdminAuthUtil {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 验证管理员身份并返回员工信息
     * 
     * @param userName 用户名（可选）
     * @param phone 手机号（可选）
     * @return 员工信息
     * @throws BusinessException 如果不是管理员或参数无效
     */
    public EmployeeDO validateAdmin(String userName, String phone) {
        // 参数校验
        if ((userName == null || userName.trim().isEmpty()) && 
            (phone == null || phone.trim().isEmpty())) {
            throw new BusinessException("用户名或手机号不能为空");
        }

        // 查询用户信息
        final UserDO user;
        if (phone != null && !phone.trim().isEmpty()) {
            user = userMapper.selectUserByPhone(phone);
        } else if (userName != null && !userName.trim().isEmpty()) {
            QueryWrapper<UserDO> userQuery = new QueryWrapper<>();
            userQuery.eq("UserName", userName);
            user = userMapper.selectOne(userQuery);
        } else {
            user = null;
        }

        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 查询员工信息（通过姓名或手机号匹配）
        final String finalUserName = user.getUserName();
        final String finalPhone = user.getPhone();
        
        QueryWrapper<EmployeeDO> empQuery = new QueryWrapper<>();
        empQuery.and(wrapper -> wrapper
                .eq("EmpName", finalUserName)
                .or()
                .eq("EmpPhone", finalPhone)
        );
        
        EmployeeDO employee = employeeMapper.selectOne(empQuery);
        
        if (employee == null) {
            throw new BusinessException("该用户不是管理员");
        }

        return employee;
    }

    /**
     * 获取管理员所属门店ID
     * 
     * @param userName 用户名（可选）
     * @param phone 手机号（可选）
     * @return 门店ID
     */
    public Integer getAdminStoreId(String userName, String phone) {
        EmployeeDO employee = validateAdmin(userName, phone);
        return employee.getStoreId();
    }
}
