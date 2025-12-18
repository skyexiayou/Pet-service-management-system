package cn.edu.xaut.controller.admin;

import cn.edu.xaut.domain.dto.admin.CityDTO;
import cn.edu.xaut.domain.entity.city.CityDO;
import cn.edu.xaut.domain.vo.ResponseVO;
import cn.edu.xaut.service.city.CityService;
import cn.edu.xaut.utils.AdminAuthUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员-城市管理Controller
 * @date 2025-12-18
 */
@RestController
@RequestMapping("/api/admin/cities")
@Api(tags = "管理员-城市管理")
public class AdminCityController {

    @Autowired
    private CityService cityService;

    @Autowired
    private AdminAuthUtil adminAuthUtil;

    @ApiOperation("查询所有城市")
    @GetMapping
    public ResponseVO<List<CityDO>> getAllCities(
            @ApiParam("用户名") @RequestParam(required = false) String userName,
            @ApiParam("手机号") @RequestParam(required = false) String phone) {
        // 验证管理员身份
        adminAuthUtil.validateAdmin(userName, phone);
        
        return ResponseVO.success(cityService.getAllCities());
    }

    @ApiOperation("根据ID查询城市")
    @GetMapping("/{cityId}")
    public ResponseVO<CityDO> getCityById(
            @ApiParam("城市ID") @PathVariable Integer cityId,
            @ApiParam("用户名") @RequestParam(required = false) String userName,
            @ApiParam("手机号") @RequestParam(required = false) String phone) {
        // 验证管理员身份
        adminAuthUtil.validateAdmin(userName, phone);
        
        return ResponseVO.success(cityService.getCityById(cityId));
    }

    @ApiOperation("创建城市")
    @PostMapping
    public ResponseVO<Integer> createCity(
            @ApiParam("用户名") @RequestParam(required = false) String userName,
            @ApiParam("手机号") @RequestParam(required = false) String phone,
            @Validated @RequestBody CityDTO cityDTO) {
        // 验证管理员身份
        adminAuthUtil.validateAdmin(userName, phone);
        
        return ResponseVO.success(cityService.createCity(cityDTO));
    }

    @ApiOperation("更新城市")
    @PutMapping("/{cityId}")
    public ResponseVO<Integer> updateCity(
            @ApiParam("城市ID") @PathVariable Integer cityId,
            @ApiParam("用户名") @RequestParam(required = false) String userName,
            @ApiParam("手机号") @RequestParam(required = false) String phone,
            @Validated @RequestBody CityDTO cityDTO) {
        // 验证管理员身份
        adminAuthUtil.validateAdmin(userName, phone);
        
        return ResponseVO.success(cityService.updateCity(cityId, cityDTO));
    }

    @ApiOperation("删除城市")
    @DeleteMapping("/{cityId}")
    public ResponseVO<Integer> deleteCity(
            @ApiParam("城市ID") @PathVariable Integer cityId,
            @ApiParam("用户名") @RequestParam(required = false) String userName,
            @ApiParam("手机号") @RequestParam(required = false) String phone) {
        // 验证管理员身份
        adminAuthUtil.validateAdmin(userName, phone);
        
        return ResponseVO.success(cityService.deleteCity(cityId));
    }
}
