package cn.edu.xaut.service.admin;

import cn.edu.xaut.domain.dto.admin.FosterRecordDTO;
import cn.edu.xaut.domain.vo.PageResultVO;
import cn.edu.xaut.domain.vo.admin.AdminFosterRecordDetailVO;
import cn.edu.xaut.domain.vo.admin.AdminFosterRecordVO;

/**
 * 管理员寄养记录服务接口
 */
public interface AdminFosterRecordService {

    /**
     * 获取当前管理员负责的寄养记录（分页）
     *
     * @param empId    员工ID
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageResultVO<AdminFosterRecordVO> getMyFosterRecords(Integer empId, Integer pageNum, Integer pageSize);

    /**
     * 获取所有寄养记录（分页）
     *
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageResultVO<AdminFosterRecordVO> getAllFosterRecords(Integer pageNum, Integer pageSize);

    /**
     * 获取寄养记录详情
     *
     * @param fosterId 寄养ID
     * @return 寄养记录详情
     */
    AdminFosterRecordDetailVO getFosterRecordDetail(Integer fosterId);

    /**
     * 创建寄养记录
     *
     * @param dto   寄养记录数据
     * @param empId 当前管理员ID（用于"我的记录"创建时自动设置）
     * @return 新创建的寄养ID
     */
    Integer createFosterRecord(FosterRecordDTO dto, Integer empId);

    /**
     * 更新寄养记录
     *
     * @param fosterId 寄养ID
     * @param dto      寄养记录数据
     * @return 更新结果
     */
    Integer updateFosterRecord(Integer fosterId, FosterRecordDTO dto);

    /**
     * 删除寄养记录
     *
     * @param fosterId 寄养ID
     * @return 删除结果
     */
    Integer deleteFosterRecord(Integer fosterId);
}
