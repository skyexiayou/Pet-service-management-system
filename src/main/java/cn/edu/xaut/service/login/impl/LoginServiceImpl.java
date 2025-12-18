package cn.edu.xaut.service.login.impl;

import cn.edu.xaut.domain.entity.user.UserDO;
import cn.edu.xaut.exception.BusinessException;
import cn.edu.xaut.mapper.UserMapper;
import cn.edu.xaut.service.login.LoginService;
import cn.edu.xaut.utils.JwtUtils;
import cn.edu.xaut.utils.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserMapper userMapper;

    @Override
    public UserDO authenticate(String account, String password) {
        log.info("用户认证，账号: {}", account);

        // 1. 根据账号查询用户
        UserDO user = userMapper.selectUserByAccount(account);
        if (user == null) {
            log.warn("账号不存在: {}", account);
            throw new BusinessException(7, "账号或密码错误");
        }

        // 2. 验证密码
        if (!PasswordEncoder.matches(password, user.getPassword())) {
            log.warn("密码错误，账号: {}", account);
            throw new BusinessException(7, "账号或密码错误");
        }

        // 3. 检查用户是否被封禁
        if (user.getIsBanned() != null && user.getIsBanned() == 1) {
            log.warn("用户已被封禁，账号: {}", account);
            throw new BusinessException(8, "用户账户已被封禁");
        }

        log.info("用户认证成功，账号: {}", account);
        return user;
    }

    @Override
    public String generateToken(UserDO user) {
        log.info("生成JWT令牌，账号: {}", user.getAccount());

        // 构建JWT声明
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getAccount());
        claims.put("isAdmin", user.getIsAdmin() != null ? user.getIsAdmin() : 0);

        // 生成令牌
        String token = JwtUtils.generateToken(claims);
        
        log.info("JWT令牌生成成功，账号: {}", user.getAccount());
        return token;
    }
}
