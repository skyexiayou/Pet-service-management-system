package cn.edu.xaut.mapper;

import cn.edu.xaut.domain.entity.employee.EmployeeDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 员工Mapper接口
 * @date 2025-12-18
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<EmployeeDO> {
    
    /**
     * 根据门店ID分页查询员工列表
     * 
     * @param page 分页参数
     * @param storeId 门店ID
     * @return 员工列表
     */
    Page<EmployeeDO> selectEmployeesByStoreId(Page<EmployeeDO> page, @Param("storeId") Integer storeId);
    
    /**
     * 根据手机号查询员工
     * 
     * @param empPhone 员工手机号
     * @return 员工信息
     */
    EmployeeDO selectByPhone(@Param("empPhone") String empPhone);
}
