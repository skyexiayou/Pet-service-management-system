package cn.edu.xaut.mapper;

import cn.edu.xaut.domain.entity.city.CityDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 城市Mapper接口
 * @date 2025-12-18
 */
@Mapper
public interface CityMapper extends BaseMapper<CityDO> {
    
    /**
     * 根据城市名和省份查询城市
     * 
     * @param cityName 城市名
     * @param province 省份
     * @return 城市信息
     */
    CityDO selectByCityNameAndProvince(@Param("cityName") String cityName, @Param("province") String province);
}
