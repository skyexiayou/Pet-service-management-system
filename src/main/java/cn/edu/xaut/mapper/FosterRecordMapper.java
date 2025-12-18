package cn.edu.xaut.mapper;

import cn.edu.xaut.domain.entity.fosterrecord.FosterRecordDO;
import cn.edu.xaut.domain.vo.foster.FosterServiceVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 寄养记录Mapper接口
 */
@Mapper
public interface FosterRecordMapper extends BaseMapper<FosterRecordDO> {

    /**
     * 查询用户的寄养服务列表
     *
     * @param userId 用户ID
     * @return 寄养服务列表
     */
    List<FosterServiceVO> selectFosterServicesByUserId(@Param("userId") Integer userId);
}
