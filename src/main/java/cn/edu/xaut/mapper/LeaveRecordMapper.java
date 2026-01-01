package cn.edu.xaut.mapper;

import cn.edu.xaut.domain.entity.leaverecord.LeaveRecordDO;
import cn.edu.xaut.domain.vo.admin.LeaveRecordVO;
import cn.edu.xaut.domain.vo.admin.MonthlyReportVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 请假记录Mapper接口
 */
@Mapper
public interface LeaveRecordMapper extends BaseMapper<LeaveRecordDO> {

    /**
     * 查询员工在指定时间段内的请假记录
     *
     * @param empId     员工ID
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 请假记录列表
     */
    List<LeaveRecordDO> selectLeaveRecordsByEmpIdAndTime(
            @Param("empId") Integer empId,
            @Param("startTime") Date startTime,
            @Param("endTime") Date endTime
    );

    /**
     * 分页查询所有请假记录（带员工和门店详情）
     *
     * @param page 分页参数
     * @param approveStatus 审批状态（可选）
     * @param leaveType 请假类型（可选）
     * @param empName 员工姓名（可选，模糊查询）
     * @return 请假记录分页结果
     */
    Page<LeaveRecordVO> selectAllLeaveRecordsWithDetail(
            Page<LeaveRecordVO> page,
            @Param("approveStatus") String approveStatus,
            @Param("leaveType") String leaveType,
            @Param("empName") String empName
    );

    /**
     * 根据ID查询请假记录详情
     *
     * @param leaveId 请假记录ID
     * @return 请假记录详情
     */
    LeaveRecordVO selectLeaveRecordDetailById(@Param("leaveId") Integer leaveId);

    /**
     * 查询指定月份区间的月度报表
     *
     * @param startMonth 起始月份（格式：YYYY-MM）
     * @param endMonth 终止月份（格式：YYYY-MM）
     * @param storeId 门店ID（可选）
     * @return 月度报表列表
     */
    List<MonthlyReportVO> selectMonthlyReportByRange(
            @Param("startMonth") String startMonth,
            @Param("endMonth") String endMonth,
            @Param("storeId") Integer storeId
    );
}
