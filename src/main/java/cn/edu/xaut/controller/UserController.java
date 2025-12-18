    package cn.edu.xaut.controller;

import cn.edu.xaut.domain.dto.user.UserDTO;
import cn.edu.xaut.domain.dto.user.UserUpdateInfoDTO;
import cn.edu.xaut.domain.entity.user.UserDO;
import cn.edu.xaut.domain.vo.ResponseVO;
import cn.edu.xaut.domain.vo.user.UserVO;
import cn.edu.xaut.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * 用户controller
 */
@RestController
@RequestMapping("/api/users")
@Api(tags = "用户管理")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("获取所有用户")
    @GetMapping
    public ResponseVO<List<UserVO>> getAllUsers() {
        return ResponseVO.success(userService.getAllUsers());
    }

    @ApiOperation("根据ID获取用户")
    @GetMapping("/{id}")
    public ResponseVO<UserVO> getUserById(@PathVariable("id") Integer userId) {
        UserDO user = userService.getUserById(userId);
        if (user == null) {
            return ResponseVO.businessError("用户不存在");
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return ResponseVO.success(userVO);
    }

    @ApiOperation("创建用户")
    @PostMapping
    public ResponseVO<Integer> createUser(@Validated @RequestBody UserDTO userDTO) {
        return ResponseVO.success(userService.createUser(userDTO));
    }

    @ApiOperation("更新用户")
    @PutMapping("/{id}")
    public ResponseVO<Integer> updateUser(@PathVariable("id") Integer userId, @Validated @RequestBody UserDTO userDTO) {
        return ResponseVO.success(userService.updateUser(userId, userDTO));
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/{id}")
    public ResponseVO<Integer> deleteUser(@PathVariable("id") Integer userId) {
        return ResponseVO.success(userService.deleteUser(userId));
    }

    @ApiOperation("修改个人信息")
    @PutMapping("/info")
    public ResponseVO<UserVO> updateUserInfo(@RequestParam Integer userId, @Validated @RequestBody UserUpdateInfoDTO updateInfoDTO) {
        return userService.updateUserInfo(userId, updateInfoDTO);
    }

    @ApiOperation("查询个人信息")
    @GetMapping("/info")
    public ResponseVO<UserVO> getUserInfo(@RequestParam Integer userId) {
        return userService.getUserInfo(userId);
    }
}