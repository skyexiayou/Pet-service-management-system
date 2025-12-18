package cn.edu.xaut.mapper;

import cn.edu.xaut.domain.entity.boarding.BoardingDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardingMapper extends BaseMapper<BoardingDO> {
    BoardingDO selectBoardingById(@Param("boardingId") Integer boardingId);
    List<BoardingDO> selectBoardingsByType(@Param("boardingType") String boardingType);
    List<BoardingDO> selectAllBoardings();
}