package cn.edu.xaut.controller;

import cn.edu.xaut.domain.vo.PageResultVO;
import cn.edu.xaut.domain.vo.StoreVO;
import cn.edu.xaut.service.store.StoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 门店controller
 * @date 2025.12.18
 */
@RestController
@RequestMapping("/api/stores")
@Api(tags = "门店管理相关接口")
public class StoreController {
    
    @Autowired
    private StoreService storeService;
    
    @ApiOperation("查询所有门店（分页）")
    @GetMapping
    public PageResultVO<StoreVO> getAllStores(
            @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return storeService.getAllStores(pageNum, pageSize);
    }
    
    @ApiOperation("根据城市ID查询门店（分页）")
    @GetMapping("/city/{cityId}")
    public PageResultVO<StoreVO> getStoresByCityId(
            @PathVariable("cityId") Integer cityId,
            @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return storeService.getStoresByCityId(cityId, pageNum, pageSize);
    }
    
    @ApiOperation("根据门店ID查询门店详情")
    @GetMapping("/{id}")
    public StoreVO getStoreById(@PathVariable("id") Integer storeId) {
        return storeService.getStoreById(storeId);
    }
}