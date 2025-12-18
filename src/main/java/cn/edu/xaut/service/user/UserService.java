package cn.edu.xaut.service.user;

import cn.edu.xaut.domain.dto.user.UserDTO;
import cn.edu.xaut.domain.dto.user.UserUpdateInfoDTO;
import cn.edu.xaut.domain.entity.user.UserDO;
import cn.edu.xaut.domain.vo.user.UserVO;
import cn.edu.xaut.domain.vo.ResponseVO;

import java.util.List;

public interface UserService {

    UserDO getUserById(Integer userId);

    List<UserVO> getAllUsers();

    Integer createUser(UserDTO userDTO);

    Integer updateUser(Integer userId, UserDTO userDTO);

    Integer deleteUser(Integer userId);

    UserDO getUserByPhone(String phone);

    /**
     * 修改个人信息
     */
    ResponseVO<UserVO> updateUserInfo(Integer userId, UserUpdateInfoDTO updateInfoDTO);

    /**
     * 查询个人信息
     */
    ResponseVO<UserVO> getUserInfo(Integer userId);

    /**
     * 根据邮箱查询用户
     */
    UserDO getUserByEmail(String email);
}
