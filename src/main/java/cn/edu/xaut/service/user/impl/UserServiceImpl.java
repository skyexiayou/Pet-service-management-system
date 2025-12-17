package cn.edu.xaut.service.user.impl;

import cn.edu.xaut.domain.dto.UserDTO;
import cn.edu.xaut.domain.entity.User;
import cn.edu.xaut.domain.vo.UserVO;
import cn.edu.xaut.mapper.UserMapper;
import cn.edu.xaut.service.user.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(Integer userId) {
        return userMapper.selectUserById(userId);
    }

    @Override
    public List<UserVO> getAllUsers() {
        List<User> users = userMapper.selectAllUsers();
        return users.stream().map(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            return userVO;
        }).collect(Collectors.toList());
    }

    @Override
    public Integer createUser(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setRegisterTime(new Date());
        return userMapper.insertUser(user);
    }

    @Override
    public Integer updateUser(Integer userId, UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setUserId(userId);
        return userMapper.updateUser(user);
    }

    @Override
    public Integer deleteUser(Integer userId) {
        return userMapper.deleteUser(userId);
    }

    @Override
    public User getUserByPhone(String phone) {
        return userMapper.selectUserByPhone(phone);
    }
}
