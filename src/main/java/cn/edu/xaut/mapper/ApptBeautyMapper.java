package cn.edu.xaut.mapper;

import cn.edu.xaut.domain.entity.apptbeauty.ApptBeautyDO;
import cn.edu.xaut.domain.vo.appointment.BeautyDetailVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 预约-美容中间表Mapper接口
 */
@Mapper
public interface ApptBeautyMapper extends BaseMapper<ApptBeautyDO> {

    /**
     * 批量插入预约-美容关联记录
     *
     * @param list 关联记录列表
     * @return 插入数量
     */
    int batchInsert(@Param("list") List<ApptBeautyDO> list);

    /**
     * 查询预约的美容服务明细
     *
     * @param apptId 预约ID
     * @return 美容服务明细列表
     */
    List<BeautyDetailVO> selectBeautyDetailsByApptId(@Param("apptId") Integer apptId);
}
