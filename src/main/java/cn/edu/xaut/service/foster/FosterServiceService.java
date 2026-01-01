package cn.edu.xaut.service.foster;

import cn.edu.xaut.domain.dto.foster.FosterAppointmentDTO;
import cn.edu.xaut.domain.vo.foster.FosterServiceDetailVO;
import cn.edu.xaut.domain.vo.foster.FosterServiceVO;

import java.util.List;

/**
 * 寄养服务Service接口
 */
public interface FosterServiceService {

    /**
     * 查询用户的寄养服务列表
     *
     * @param userId 用户ID
     * @return 寄养服务列表
     */
    List<FosterServiceVO> getFosterServicesByUserId(Integer userId);

    /**
     * 查询寄养服务详情
     *
     * @param fosterId 寄养ID
     * @return 寄养服务详情
     */
    FosterServiceDetailVO getFosterServiceDetail(Integer fosterId);

    /**
     * 接领确认
     *
     * @param fosterId 寄养ID
     */
    void confirmPickup(Integer fosterId);

    /**
     * 检查用户是否有注册宠物
     *
     * @param userId 用户ID
     * @return true-有宠物，false-无宠物
     */
    boolean checkUserHasPets(Integer userId);

    /**
     * 创建寄养预约
     *
     * @param dto 寄养预约数据
     * @return 寄养记录ID
     */
    Integer createFosterAppointment(FosterAppointmentDTO dto);
}
