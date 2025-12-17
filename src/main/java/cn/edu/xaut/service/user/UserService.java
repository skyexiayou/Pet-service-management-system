package cn.edu.xaut.service.user;

import cn.edu.xaut.domain.dto.UserDTO;
import cn.edu.xaut.domain.entity.User;
import cn.edu.xaut.domain.vo.UserVO;

import java.util.List;

public interface UserService {

    User getUserById(Integer userId);

    List<UserVO> getAllUsers();

    Integer createUser(UserDTO userDTO);

    Integer updateUser(Integer userId, UserDTO userDTO);

    Integer deleteUser(Integer userId);

    User getUserByPhone(String phone);
}
