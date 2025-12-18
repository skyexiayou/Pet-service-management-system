package cn.edu.xaut.service.register.impl;

import cn.edu.xaut.domain.dto.user.UserRegisterDTO;
import cn.edu.xaut.domain.entity.user.UserDO;
import cn.edu.xaut.exception.BusinessException;
import cn.edu.xaut.mapper.UserMapper;
import cn.edu.xaut.service.register.RegisterService;
import cn.edu.xaut.utils.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDO register(UserRegisterDTO registerDTO) {
        log.info("开始用户注册，账号：{}", registerDTO.getAccount());

        UserDO existingUser = userMapper.selectUserByAccount(registerDTO.getAccount());
        if (existingUser != null) {
            log.warn("账号已存在：{}", registerDTO.getAccount());
            throw new BusinessException(11, "账号已存在");
        }

        existingUser = userMapper.selectUserByPhone(registerDTO.getPhone());
        if (existingUser != null) {
            log.warn("手机号已注册：{}", registerDTO.getPhone());
            throw new BusinessException(12, "手机号已注册");
        }

        if (registerDTO.getEmail() != null && !registerDTO.getEmail().isEmpty()) {
            existingUser = userMapper.selectUserByEmail(registerDTO.getEmail());
            if (existingUser != null) {
                log.warn("邮箱已注册：{}", registerDTO.getEmail());
                throw new BusinessException(13, "邮箱已注册");
            }
        }

        UserDO newUser = new UserDO();
        newUser.setAccount(registerDTO.getAccount());
        newUser.setUserName(registerDTO.getUserName());
        newUser.setPhone(registerDTO.getPhone());
        newUser.setEmail(registerDTO.getEmail());
        newUser.setAddress(registerDTO.getAddress());

        String encodedPassword = PasswordEncoder.encode(registerDTO.getPassword());
        newUser.setPassword(encodedPassword);

        newUser.setIsAdmin(0);
        newUser.setIsBanned(0);
        newUser.setRegisterTime(new Date());

        int result = userMapper.insert(newUser);
        if (result <= 0) {
            log.error("用户注册失败，数据库插入操作失败");
            throw new BusinessException("用户注册失败");
        }

        log.info("用户注册成功，账号：{}，用户ID：{}", newUser.getAccount(), newUser.getUserId());
        return newUser;
    }
}