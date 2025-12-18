package cn.edu.xaut.service.register;

import cn.edu.xaut.domain.dto.user.UserRegisterDTO;
import cn.edu.xaut.domain.entity.user.UserDO;

/**
 * 注册服务接口
 */
public interface RegisterService {

    /**
     * 用户注册
     * @param registerDTO 注册信息
     * @return 注册成功的用户信息
     */
    UserDO register(UserRegisterDTO registerDTO);
}
