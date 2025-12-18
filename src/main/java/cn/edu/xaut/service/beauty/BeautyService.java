package cn.edu.xaut.service.beauty;

import cn.edu.xaut.domain.dto.beauty.BeautyDTO;
import cn.edu.xaut.domain.entity.beauty.BeautyDO;
import cn.edu.xaut.domain.vo.PageResultVO;

import java.util.List;

public interface BeautyService {

    BeautyDO getBeautyById(Integer beautyId);

    List<BeautyDO> getBeautiesByType(String beautyType);

    List<BeautyDO> getAllBeauties();

    PageResultVO<BeautyDO> getBeautiesPage(Integer pageNum, Integer pageSize);

    PageResultVO<BeautyDO> getBeautiesByTypePage(String beautyType, Integer pageNum, Integer pageSize);

    Integer createBeauty(BeautyDTO beautyDTO);

    Integer updateBeauty(Integer beautyId, BeautyDTO beautyDTO);

    Integer deleteBeauty(Integer beautyId);
}