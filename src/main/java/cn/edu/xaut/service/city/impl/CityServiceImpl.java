package cn.edu.xaut.service.city.impl;

import cn.edu.xaut.domain.dto.admin.CityDTO;
import cn.edu.xaut.domain.entity.city.CityDO;
import cn.edu.xaut.exception.BusinessException;
import cn.edu.xaut.mapper.CityMapper;
import cn.edu.xaut.service.city.CityService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 城市Service实现类
 * @date 2025-12-18
 */
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityMapper cityMapper;

    @Override
    public List<CityDO> getAllCities() {
        return cityMapper.selectList(null);
    }

    @Override
    public CityDO getCityById(Integer cityId) {
        CityDO city = cityMapper.selectById(cityId);
        if (city == null) {
            throw new BusinessException("城市不存在");
        }
        return city;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createCity(CityDTO cityDTO) {
        // 校验城市名+省份唯一性
        CityDO existingCity = cityMapper.selectByCityNameAndProvince(cityDTO.getCityName(), cityDTO.getProvince());
        if (existingCity != null) {
            throw new BusinessException("该省份下已存在同名城市");
        }

        CityDO city = new CityDO();
        BeanUtils.copyProperties(cityDTO, city);
        
        cityMapper.insert(city);
        return city.getCityId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateCity(Integer cityId, CityDTO cityDTO) {
        // 校验城市是否存在
        CityDO city = getCityById(cityId);

        // 校验城市名+省份唯一性（排除自己）
        CityDO existingCity = cityMapper.selectByCityNameAndProvince(cityDTO.getCityName(), cityDTO.getProvince());
        if (existingCity != null && !existingCity.getCityId().equals(cityId)) {
            throw new BusinessException("该省份下已存在同名城市");
        }

        BeanUtils.copyProperties(cityDTO, city);
        return cityMapper.updateById(city);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteCity(Integer cityId) {
        // 校验城市是否存在
        getCityById(cityId);
        
        // TODO: 可以添加校验，检查是否有门店关联此城市
        
        return cityMapper.deleteById(cityId);
    }
}
