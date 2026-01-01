package cn.edu.xaut.service.admin;

import cn.edu.xaut.domain.dto.admin.BeautyServiceDTO;
import cn.edu.xaut.domain.entity.beauty.BeautyDO;
import cn.edu.xaut.domain.vo.PageResultVO;
import cn.edu.xaut.domain.vo.admin.AdminBeautyServiceDetailVO;
import cn.edu.xaut.domain.vo.admin.AdminBeautyServiceVO;

/**
 * 管理员美容服务Service接口
 */
public interface AdminBeautyServiceService {

    /**
     * 获取当前管理员负责的美容服务记录（分页）
     *
     * @param empId    员工ID
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageResultVO<AdminBeautyServiceVO> getMyBeautyServices(Integer empId, Integer pageNum, Integer pageSize);

    /**
     * 获取所有美容服务记录（分页）
     *
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageResultVO<AdminBeautyServiceVO> getAllBeautyServices(Integer pageNum, Integer pageSize);

    /**
     * 获取美容服务记录详情
     *
     * @param apptId 预约ID
     * @return 美容服务记录详情
     */
    AdminBeautyServiceDetailVO getBeautyServiceDetail(Integer apptId);

    /**
     * 创建美容服务记录
     *
     * @param dto   美容服务数据
     * @param empId 当前管理员ID
     * @return 新创建的预约ID
     */
    Integer createBeautyService(BeautyServiceDTO dto, Integer empId);

    /**
     * 更新美容服务记录
     *
     * @param apptId 预约ID
     * @param dto    美容服务数据
     * @return 更新结果
     */
    Integer updateBeautyService(Integer apptId, BeautyServiceDTO dto);

    /**
     * 删除美容服务记录
     *
     * @param apptId 预约ID
     * @return 删除结果
     */
    Integer deleteBeautyService(Integer apptId);

    /**
     * 获取所有美容项目（分页）
     *
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageResultVO<BeautyDO> getAllBeautyItems(Integer pageNum, Integer pageSize);
}
