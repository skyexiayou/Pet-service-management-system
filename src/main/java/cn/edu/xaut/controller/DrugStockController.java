package cn.edu.xaut.controller;

import cn.edu.xaut.domain.vo.ResponseVO;
import cn.edu.xaut.domain.vo.petdrug.DrugVO;
import cn.edu.xaut.service.petdrug.DrugService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 药品库存Controller
 */
@RestController
@RequestMapping("/api/drug-stock")
@Api(tags = "药品库存管理")
public class DrugStockController {

    @Autowired
    private DrugService drugService;

    @ApiOperation("获取药品库存列表")
    @GetMapping("/list")
    public ResponseVO<List<DrugVO>> getDrugStockList(
            @ApiParam(value = "门店ID", required = true) @RequestParam Integer storeId,
            @ApiParam(value = "搜索关键词：药品名、规格") @RequestParam(required = false) String keyword) {
        List<DrugVO> drugList = drugService.getDrugStockList(storeId, keyword);
        return ResponseVO.success(drugList);
    }
}