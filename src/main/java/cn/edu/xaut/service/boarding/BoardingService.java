package cn.edu.xaut.service.boarding;

import cn.edu.xaut.domain.dto.boarding.BoardingDTO;
import cn.edu.xaut.domain.entity.boarding.BoardingDO;
import cn.edu.xaut.domain.vo.PageResultVO;

import java.util.List;

public interface BoardingService {

    BoardingDO getBoardingById(Integer boardingId);

    List<BoardingDO> getBoardingsByType(String boardingType);

    List<BoardingDO> getAllBoardings();

    PageResultVO<BoardingDO> getBoardingsPage(Integer pageNum, Integer pageSize);

    PageResultVO<BoardingDO> getBoardingsByTypePage(String boardingType, Integer pageNum, Integer pageSize);

    Integer createBoarding(BoardingDTO boardingDTO);

    Integer updateBoarding(Integer boardingId, BoardingDTO boardingDTO);

    Integer deleteBoarding(Integer boardingId);
}