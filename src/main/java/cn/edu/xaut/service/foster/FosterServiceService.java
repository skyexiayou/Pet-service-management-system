package cn.edu.xaut.service.foster;

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
}
