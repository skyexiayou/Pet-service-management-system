package cn.edu.xaut.controller;

import cn.edu.xaut.domain.dto.boarding.BoardingDTO;
import cn.edu.xaut.domain.entity.boarding.BoardingDO;
import cn.edu.xaut.service.boarding.BoardingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 寄养服务controller
 */
@RestController
@RequestMapping("/api/boardings")
@Api(tags = "寄养服务管理相关接口")
public class BoardingController {

    @Autowired
    private BoardingService boardingService;

    @ApiOperation("获取所有寄养服务")
    @GetMapping
    public List<BoardingDO> getAllBoardings() {
        return boardingService.getAllBoardings();
    }

    @ApiOperation("根据ID查询寄养服务")
    @GetMapping("/{id}")
    public BoardingDO getBoardingById(@PathVariable("id") Integer boardingId) {
        return boardingService.getBoardingById(boardingId);
    }

    @ApiOperation("根据类型查询寄养服务")
    @GetMapping("/type/{type}")
    public List<BoardingDO> getBoardingsByType(@PathVariable("type") String boardingType) {
        return boardingService.getBoardingsByType(boardingType);
    }

    @ApiOperation("分页获取所有寄养服务")
    @GetMapping("/page")
    public cn.edu.xaut.domain.vo.PageResultVO<BoardingDO> getBoardingsPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        return boardingService.getBoardingsPage(pageNum, pageSize);
    }

    @ApiOperation("根据类型分页获取寄养服务")
    @GetMapping("/page/type/{type}")
    public cn.edu.xaut.domain.vo.PageResultVO<BoardingDO> getBoardingsByTypePage(@PathVariable("type") String boardingType, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        return boardingService.getBoardingsByTypePage(boardingType, pageNum, pageSize);
    }

    @ApiOperation("新增寄养服务")
    @PostMapping
    public Integer createBoarding(@RequestBody @Validated BoardingDTO boardingDTO) {
        return boardingService.createBoarding(boardingDTO);
    }

    @ApiOperation("修改寄养服务")
    @PutMapping("/{id}")
    public Integer updateBoarding(@PathVariable("id") Integer boardingId, @RequestBody @Validated BoardingDTO boardingDTO) {
        return boardingService.updateBoarding(boardingId, boardingDTO);
    }

    @ApiOperation("删除寄养服务")
    @DeleteMapping("/{id}")
    public Integer deleteBoarding(@PathVariable("id") Integer boardingId) {
        return boardingService.deleteBoarding(boardingId);
    }
}