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
        log.info("认证开始，账号: {}", account);
        log.info("输入的密码: {}", password);

        // 1. 根据账号查询用户
        UserDO user = userMapper.selectUserByAccount(account);
        log.info("查询到的用户信息: {}", user);
        if (user == null) {
            log.warn("账号不存在: {}", account);
            throw new BusinessException(7, "账号或密码错误");
        }

        // 2. 验证密码
        log.info("存储的密码: {}", user.getPassword());
        log.info("密码长度: {}", user.getPassword().length());
        boolean passwordMatches = PasswordEncoder.matches(password, user.getPassword());
        log.info("密码匹配结果: {}", passwordMatches);
        if (!passwordMatches) {
            log.warn("密码不匹配，账号: {}", account);
            throw new BusinessException(7, "账号或密码错误");
        }

        // 3. 检查用户是否被封禁
        log.info("用户封禁状态: {}", user.getIsBanned());
        if (user.getIsBanned() != null && user.getIsBanned() == 1) {
            log.warn("用户已被封禁，账号: {}", account);
            throw new BusinessException(8, "用户账户已被封禁");
        }

        log.info("认证成功，账号: {}", account);
        return user;
    }

    @Override
    public String generateToken(UserDO user) {
        log.info("生成JWT令牌，账号: {}", user.getAccount());

        // 构建JWT载荷
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getAccount());
        claims.put("isAdmin", user.getIsAdmin() != null ? user.getIsAdmin() : 0);

        // 生成令牌
        String token = JwtUtils.generateToken(claims);

        log.info("JWT令牌生成成功，账号: {}", user.getAccount());
        return token;
    }
}