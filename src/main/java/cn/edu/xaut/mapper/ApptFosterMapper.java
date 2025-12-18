package cn.edu.xaut.mapper;

import cn.edu.xaut.domain.entity.apptfoster.ApptFosterDO;
import cn.edu.xaut.domain.vo.appointment.FosterDetailVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 预约-寄养中间表Mapper接口
 */
@Mapper
public interface ApptFosterMapper extends BaseMapper<ApptFosterDO> {

    /**
     * 查询预约的寄养服务明细
     *
     * @param apptId 预约ID
     * @return 寄养服务明细
     */
    FosterDetailVO selectFosterDetailByApptId(@Param("apptId") Integer apptId);
}
