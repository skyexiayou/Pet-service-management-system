package cn.edu.xaut.mapper;

import cn.edu.xaut.domain.entity.leaverecord.LeaveRecordDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
}
