package cn.edu.xaut.controller.captcha;

import cn.edu.xaut.domain.vo.ResponseVO;
import cn.edu.xaut.domain.vo.auth.CaptchaVO;
import cn.edu.xaut.service.captcha.CaptchaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码控制器
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "验证码管理", description = "验证码生成相关接口")
public class CaptchaController {

    private final CaptchaService captchaService;

    @Operation(summary = "获取验证码", description = "生成验证码图片和令牌")
    @GetMapping("/captcha")
    public ResponseVO<CaptchaVO> generateCaptcha() {
        log.info("请求生成验证码");
        CaptchaVO captchaVO = captchaService.generateCaptcha();
        return ResponseVO.success(captchaVO);
    }
}
