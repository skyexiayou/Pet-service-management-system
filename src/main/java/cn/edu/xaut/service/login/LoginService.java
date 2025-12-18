package cn.edu.xaut.service.login;

import cn.edu.xaut.domain.entity.user.UserDO;

/**
 * 登录服务接口
 */
public interface LoginService {

    /**
     * 用户认证
     * @param account 账号
     * @param password 密码
     * @return 用户信息
     */
    UserDO authenticate(String account, String password);

    /**
     * 生成JWT令牌
     * @param user 用户信息
     * @return JWT令牌字符串
     */
    String generateToken(UserDO user);
}
