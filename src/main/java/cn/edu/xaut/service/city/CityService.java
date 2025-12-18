package cn.edu.xaut.service.city;

import cn.edu.xaut.domain.dto.admin.CityDTO;
import cn.edu.xaut.domain.entity.city.CityDO;

import java.util.List;

/**
 * 城市Service接口
 * @date 2025-12-18
 */
public interface CityService {
    
    /**
     * 查询所有城市
     * 
     * @return 城市列表
     */
    List<CityDO> getAllCities();
    
    /**
     * 根据ID查询城市
     * 
     * @param cityId 城市ID
     * @return 城市信息
     */
    CityDO getCityById(Integer cityId);
    
    /**
     * 创建城市
     * 
     * @param cityDTO 城市DTO
     * @return 城市ID
     */
    Integer createCity(CityDTO cityDTO);
    
    /**
     * 更新城市
     * 
     * @param cityId 城市ID
     * @param cityDTO 城市DTO
     * @return 更新结果
     */
    Integer updateCity(Integer cityId, CityDTO cityDTO);
    
    /**
     * 删除城市
     * 
     * @param cityId 城市ID
     * @return 删除结果
     */
    Integer deleteCity(Integer cityId);
}
