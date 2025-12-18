package cn.edu.xaut.service.user.impl;

import cn.edu.xaut.domain.dto.user.UserDTO;
import cn.edu.xaut.domain.dto.user.UserUpdateInfoDTO;
import cn.edu.xaut.domain.entity.user.UserDO;
import cn.edu.xaut.domain.vo.ResponseVO;
import cn.edu.xaut.domain.vo.user.UserVO;
import cn.edu.xaut.mapper.UserMapper;
import cn.edu.xaut.service.user.UserService;
import cn.edu.xaut.utils.CommonUtils;
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
    public UserDO getUserById(Integer userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public List<UserVO> getAllUsers() {
        List<UserDO> users = userMapper.selectAllUsers();
        return users.stream().map(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            // 数据脱敏
            userVO.setPhone(CommonUtils.maskPhone(userVO.getPhone()));
            return userVO;
        }).collect(Collectors.toList());
    }

    @Override
    public Integer createUser(UserDTO userDTO) {
        UserDO user = new UserDO();
        BeanUtils.copyProperties(userDTO, user);
        user.setRegisterTime(new Date());
        return userMapper.insert(user);
    }

    @Override
    public Integer updateUser(Integer userId, UserDTO userDTO) {
        UserDO user = new UserDO();
        BeanUtils.copyProperties(userDTO, user);
        user.setUserId(userId);
        return userMapper.updateById(user);
    }

    @Override
    public Integer deleteUser(Integer userId) {
        return userMapper.deleteById(userId);
    }

    @Override
    public UserDO getUserByPhone(String phone) {
        return userMapper.selectUserByPhone(phone);
    }

    @Override
    public ResponseVO<UserVO> updateUserInfo(Integer userId, UserUpdateInfoDTO updateInfoDTO) {
        // 1. 查询用户是否存在
        UserDO user = getUserById(userId);
        if (user == null) {
            return ResponseVO.paramError("用户不存在");
        }

        // 2. 校验邮箱是否已被其他用户使用
        if (updateInfoDTO.getEmail() != null && !updateInfoDTO.getEmail().isEmpty()) {
            UserDO existingUser = getUserByEmail(updateInfoDTO.getEmail());
            if (existingUser != null && !existingUser.getUserId().equals(userId)) {
                return ResponseVO.paramError("邮箱已被其他用户使用");
            }
        }

        // 3. 更新用户信息
        BeanUtils.copyProperties(updateInfoDTO, user, "userId");

        int result = userMapper.updateById(user);
        if (result <= 0) {
            return ResponseVO.businessError("信息修改失败");
        }

        // 4. 返回更新后的用户信息（脱敏）
        UserVO userVO = convertToUserVO(user);
        return ResponseVO.success(userVO);
    }

    @Override
    public ResponseVO<UserVO> getUserInfo(Integer userId) {
        // 1. 查询用户是否存在
        UserDO user = getUserById(userId);
        if (user == null) {
            return ResponseVO.paramError("用户不存在");
        }

        // 2. 返回用户信息（脱敏）
        UserVO userVO = convertToUserVO(user);
        return ResponseVO.success(userVO);
    }

    @Override
    public UserDO getUserByEmail(String email) {
        return userMapper.selectUserByEmail(email);
    }

    /**
     * 将UserDO转换为UserVO，并进行数据脱敏
     */
    private UserVO convertToUserVO(UserDO user) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        // 数据脱敏：手机号脱敏
        userVO.setPhone(CommonUtils.maskPhone(userVO.getPhone()));
        return userVO;
    }
}
