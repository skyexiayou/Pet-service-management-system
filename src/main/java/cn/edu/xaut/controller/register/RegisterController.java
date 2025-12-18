package cn.edu.xaut.controller.register;

import cn.edu.xaut.domain.dto.user.UserRegisterDTO;
import cn.edu.xaut.domain.entity.user.UserDO;
import cn.edu.xaut.domain.vo.ResponseVO;
import cn.edu.xaut.domain.vo.auth.RegisterSuccessVO;
import cn.edu.xaut.service.register.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 用户注册控制器
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "用户注册", description = "用户注册相关接口")
public class RegisterController {

    private final RegisterService registerService;

    @Operation(summary = "用户注册", description = "支持账号、用户名、密码、手机号、邮箱注册。错误码：11-账号已存在，12-手机号已被注册，13-邮箱已被注册")
    @PostMapping("/register")
    public ResponseVO<RegisterSuccessVO> register(@Valid @RequestBody UserRegisterDTO registerDTO) {
        log.info("用户注册请求，账号: {}", registerDTO.getAccount());

        try {
            // 执行注册
            UserDO newUser = registerService.register(registerDTO);

            // 构建成功响应
            RegisterSuccessVO successVO = RegisterSuccessVO.builder()
                    .id(newUser.getUserId())
                    .account(newUser.getAccount())
                    .userName(newUser.getUserName())
                    .phone(newUser.getPhone())
                    .email(newUser.getEmail())
                    .createTime(newUser.getRegisterTime() != null ? 
                            LocalDateTime.ofInstant(newUser.getRegisterTime().toInstant(), ZoneId.systemDefault()) : 
                            LocalDateTime.now())
                    .build();

            log.info("用户 {} 注册成功", registerDTO.getAccount());
            return ResponseVO.success(successVO);
        } catch (Exception e) {
            log.error("用户 {} 注册失败", registerDTO.getAccount(), e);
            throw e;
        }
    }
}
