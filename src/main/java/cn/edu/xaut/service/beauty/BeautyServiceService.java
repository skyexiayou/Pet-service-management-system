package cn.edu.xaut.service.beauty;

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
}
