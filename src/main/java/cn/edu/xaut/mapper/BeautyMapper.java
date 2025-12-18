package cn.edu.xaut.mapper;

import cn.edu.xaut.domain.entity.beauty.BeautyDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BeautyMapper extends BaseMapper<BeautyDO> {
    BeautyDO selectBeautyById(@Param("beautyId") Integer beautyId);
    List<BeautyDO> selectBeautiesByType(@Param("beautyType") String beautyType);
    List<BeautyDO> selectAllBeauties();
}