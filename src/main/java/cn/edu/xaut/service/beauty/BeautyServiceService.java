package cn.edu.xaut.service.beauty;

import cn.edu.xaut.domain.dto.beauty.BeautyAppointmentDTO;
import cn.edu.xaut.domain.entity.beauty.BeautyDO;
import cn.edu.xaut.domain.vo.beauty.BeautyServiceDetailVO;
import cn.edu.xaut.domain.vo.beauty.BeautyServiceVO;

import java.util.List;

/**
 * 美容服务Service接口
 */
public interface BeautyServiceService {

    /**
     * 查询用户的美容服务列表
     *
     * @param userId 用户ID
     * @return 美容服务列表
     */
    List<BeautyServiceVO> getBeautyServicesByUserId(Integer userId);

    /**
     * 查询美容服务详情
     *
     * @param apptId 预约ID
     * @return 美容服务详情
     */
    BeautyServiceDetailVO getBeautyServiceDetail(Integer apptId);

    /**
     * 检查用户是否有注册宠物
     *
     * @param userId 用户ID
     * @return true-有宠物，false-无宠物
     */
    boolean checkUserHasPets(Integer userId);

    /**
     * 创建美容预约
     *
     * @param dto 美容预约数据
     * @return 预约ID
     */
    Integer createBeautyAppointment(BeautyAppointmentDTO dto);

    /**
     * 更新美容预约
     *
     * @param apptId 预约ID
     * @param dto    美容预约数据
     * @return 更新结果
     */
    Integer updateBeautyAppointment(Integer apptId, BeautyAppointmentDTO dto);

    /**
     * 取消美容预约
     *
     * @param apptId 预约ID
     * @return 取消结果
     */
    Integer cancelBeautyAppointment(Integer apptId);

    /**
     * 获取所有美容项目（用户预览）
     *
     * @return 美容项目列表
     */
    List<BeautyDO> getAllBeautyItems();
}
