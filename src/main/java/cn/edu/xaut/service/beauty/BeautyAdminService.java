package cn.edu.xaut.service.beauty;

import cn.edu.xaut.domain.dto.admin.BeautyServiceDTO;
import cn.edu.xaut.domain.entity.beauty.BeautyDO;
import cn.edu.xaut.domain.vo.PageResultVO;

/**
 * 美容服务管理Service接口
 * @date 2025-12-18
 */
public interface BeautyAdminService {
    
    /**
     * 分页查询美容服务列表
     * 
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 美容服务列表
     */
    PageResultVO<BeautyDO> getBeautyList(Integer pageNum, Integer pageSize);
    
    /**
     * 根据ID查询美容服务
     * 
     * @param beautyId 美容服务ID
     * @return 美容服务信息
     */
    BeautyDO getBeautyById(Integer beautyId);
    
    /**
     * 创建美容服务
     * 
     * @param beautyServiceDTO 美容服务DTO
     * @return 美容服务ID
     */
    Integer createBeauty(BeautyServiceDTO beautyServiceDTO);
    
    /**
     * 更新美容服务
     * 
     * @param beautyId 美容服务ID
     * @param beautyServiceDTO 美容服务DTO
     * @return 更新结果
     */
    Integer updateBeauty(Integer beautyId, BeautyServiceDTO beautyServiceDTO);
    
    /**
     * 删除美容服务（软删除，实际是下架）
     * 
     * @param beautyId 美容服务ID
     * @return 删除结果
     */
    Integer deleteBeauty(Integer beautyId);
}
