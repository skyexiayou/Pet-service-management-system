package cn.edu.xaut.controller;

import cn.edu.xaut.domain.dto.UserDTO;
import cn.edu.xaut.domain.entity.User;
import cn.edu.xaut.domain.vo.UserVO;
import cn.edu.xaut.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Api(tags = "用户管理")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("获取所有用户")
    @GetMapping
    public List<UserVO> getAllUsers() {
        return userService.getAllUsers();
    }

    @ApiOperation("根据ID获取用户")
    @GetMapping("/{id}")
    public UserVO getUserById(@PathVariable("id") Integer userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @ApiOperation("创建用户")
    @PostMapping
    public Integer createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @ApiOperation("更新用户")
    @PutMapping("/{id}")
    public Integer updateUser(@PathVariable("id") Integer userId, @RequestBody UserDTO userDTO) {
        return userService.updateUser(userId, userDTO);
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/{id}")
    public Integer deleteUser(@PathVariable("id") Integer userId) {
        return userService.deleteUser(userId);
    }
}