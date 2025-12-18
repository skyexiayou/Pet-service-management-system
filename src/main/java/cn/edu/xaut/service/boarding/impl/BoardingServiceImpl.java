package cn.edu.xaut.service.boarding.impl;

import cn.edu.xaut.domain.dto.boarding.BoardingDTO;
import cn.edu.xaut.domain.entity.boarding.BoardingDO;
import cn.edu.xaut.domain.vo.PageResultVO;
import cn.edu.xaut.mapper.BoardingMapper;
import cn.edu.xaut.service.boarding.BoardingService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 寄养业务层实现类
 * 实现BoardingService接口，处理寄养信息的CRUD、分页查询等核心业务逻辑
 *
 * @date 2025.12.18
 */
@Service
public class BoardingServiceImpl implements BoardingService {

    /** 寄养数据访问层接口 */
    @Autowired
    private BoardingMapper boardingMapper;

    /**
     * 根据寄养ID查询单个寄养信息
     *
     * @param boardingId 寄养ID（主键）
     * @return 寄养信息实体类
     */
    @Override
    public BoardingDO getBoardingById(Integer boardingId) {
        return boardingMapper.selectBoardingById(boardingId);
    }

    /**
     * 根据寄养类型查询寄养信息列表
     *
     * @param boardingType 寄养类型（如：短期寄养、长期寄养）
     * @return 符合条件的寄养信息列表
     */
    @Override
    public List<BoardingDO> getBoardingsByType(String boardingType) {
        return boardingMapper.selectBoardingsByType(boardingType);
    }

    /**
     * 查询所有寄养信息
     *
     * @return 全部寄养信息列表
     */
    @Override
    public List<BoardingDO> getAllBoardings() {
        return boardingMapper.selectAllBoardings();
    }

    /**
     * 分页查询所有寄养信息
     * 基于MyBatis-Plus内置分页插件实现自动分页
     *
     * @param pageNum  当前页码（从1开始）
     * @param pageSize 每页显示条数
     * @return 分页结果VO（包含总条数、当前页数据、页码/页大小）
     */
    @Override
    public PageResultVO<BoardingDO> getBoardingsPage(Integer pageNum, Integer pageSize) {
        Page<BoardingDO> page = new Page<>(pageNum, pageSize);
        Page<BoardingDO> resultPage = boardingMapper.selectPage(page, null);
        return PageResultVO.<BoardingDO>builder()
                .total(resultPage.getTotal())
                .list(resultPage.getRecords())
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();
    }

    /**
     * 根据寄养类型分页查询寄养信息
     * 先查询全量数据再手动分页（适用于数据量较小的场景）
     *
     * @param boardingType 寄养类型（如：短期寄养、长期寄养）
     * @param pageNum      当前页码（从1开始）
     * @param pageSize     每页显示条数
     * @return 分页结果VO（包含总条数、当前页数据、页码/页大小）
     */
    @Override
    public PageResultVO<BoardingDO> getBoardingsByTypePage(String boardingType, Integer pageNum, Integer pageSize) {
        Page<BoardingDO> page = new Page<>(pageNum, pageSize);
        List<BoardingDO> boardings = boardingMapper.selectBoardingsByType(boardingType);
        // 手动分页：计算起始/结束下标，避免数组越界
        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, boardings.size());
        List<BoardingDO> pageList = boardings.subList(start, end);
        return PageResultVO.<BoardingDO>builder()
                .total((long) boardings.size())
                .list(pageList)
                .pageNum(pageNum)
                .pageSize(pageSize)
                .build();
    }

    /**
     * 新增寄养信息
     * 将DTO转换为DO后插入数据库
     *
     * @param boardingDTO 寄养信息入参DTO（前端传入的新增数据）
     * @return 受影响的行数（1=新增成功，0=新增失败）
     */
    @Override
    public Integer createBoarding(BoardingDTO boardingDTO) {
        BoardingDO boarding = new BoardingDO();
        BeanUtils.copyProperties(boardingDTO, boarding);
        return boardingMapper.insert(boarding);
    }

    /**
     * 修改寄养信息
     * 根据寄养ID更新对应信息，DTO转换为DO后执行更新
     *
     * @param boardingId  寄养ID（主键，指定要修改的记录）
     * @param boardingDTO 寄养信息入参DTO（前端传入的修改数据）
     * @return 受影响的行数（1=修改成功，0=修改失败/无数据更新）
     */
    @Override
    public Integer updateBoarding(Integer boardingId, BoardingDTO boardingDTO) {
        BoardingDO boarding = new BoardingDO();
        BeanUtils.copyProperties(boardingDTO, boarding);
        boarding.setBoardingId(boardingId);
        return boardingMapper.updateById(boarding);
    }

    /**
     * 删除寄养信息
     * 根据寄养ID删除对应记录（物理删除）
     *
     * @param boardingId 寄养ID（主键，指定要删除的记录）
     * @return 受影响的行数（1=删除成功，0=删除失败/无此记录）
     */
    @Override
    public Integer deleteBoarding(Integer boardingId) {
        return boardingMapper.deleteById(boardingId);
    }
}