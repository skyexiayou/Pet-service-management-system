package cn.edu.xaut.mapper;

import cn.edu.xaut.domain.vo.admin.MonthlyReportVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 管理员预约管理Mapper接口
 * 创建时间：2025-12-18
 */
@Mapper
public interface AdminAppointmentMapper extends BaseMapper<Map<String, Object>> {

    /**
     * 查询门店的待审核预约列表
     * @param storeId 门店ID
     * @return 预约列表
     */
    List<Map<String, Object>> selectPendingAppointments(@Param("storeId") Integer storeId);

    /**
     * 查询门店月报数据
     * @param storeId 门店ID
     * @param statMonth 统计月份（格式：YYYY-MM）
     * @return 月报数据列表
     */
    List<MonthlyReportVO> selectMonthlyReport(@Param("storeId") Integer storeId, 
                                               @Param("statMonth") String statMonth);

    /**
     * 查询门店订单列表
     * @param storeId 门店ID
     * @return 订单列表
     */
    List<Map<String, Object>> selectStoreOrders(@Param("storeId") Integer storeId);
}
