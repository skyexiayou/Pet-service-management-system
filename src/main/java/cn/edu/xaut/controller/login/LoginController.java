package cn.edu.xaut.controller.login;

import cn.edu.xaut.domain.dto.user.UserLoginDTO;
import cn.edu.xaut.domain.entity.user.UserDO;
import cn.edu.xaut.domain.vo.ResponseVO;
import cn.edu.xaut.domain.vo.auth.LoginSuccessVO;
import cn.edu.xaut.exception.BusinessException;
import cn.edu.xaut.service.captcha.CaptchaService;
import cn.edu.xaut.service.login.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户登录控制器
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "用户登录", description = "用户登录相关接口")
public class LoginController {

    private final LoginService loginService;
    private final CaptchaService captchaService;

    @Operation(summary = "用户登录", description = "用户登录接口。错误码：4-请先获取验证码，5-验证码错误，7-账号或密码错误，8-用户账户已被封禁")
    @PostMapping("/login")
    public ResponseVO<LoginSuccessVO> login(@Valid @RequestBody UserLoginDTO loginDTO) {
        log.info("登录请求，账号: {}", loginDTO.getAccount());

        try {
            // 1. 验证验证码
            if (loginDTO.getCaptchaToken() == null || loginDTO.getCaptchaToken().isEmpty()) {
                log.warn("验证码令牌为空");
                throw new BusinessException(4, "请先获取验证码");
            }

            boolean captchaValid = captchaService.verifyCaptcha(
                    loginDTO.getCaptchaToken(), 
                    loginDTO.getCaptchaCode()
            );

            if (!captchaValid) {
                log.warn("验证码验证失败，账号: {}", loginDTO.getAccount());
                throw new BusinessException(5, "验证码错误");
            }

            log.info("验证码验证成功");

            // 2. 用户认证
            UserDO user = loginService.authenticate(loginDTO.getAccount(), loginDTO.getPassword());

            // 3. 生成JWT令牌
            String token = loginService.generateToken(user);

            // 4. 构建响应
            LoginSuccessVO successVO = LoginSuccessVO.builder()
                    .token(token)
                    .build();

            log.info("用户 {} 登录成功", loginDTO.getAccount());
            return ResponseVO.success(successVO);
        } catch (Exception e) {
            log.error("用户 {} 登录失败", loginDTO.getAccount(), e);
            throw e;
        }
    }
}
