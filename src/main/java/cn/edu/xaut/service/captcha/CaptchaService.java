package cn.edu.xaut.service.captcha;

import cn.edu.xaut.domain.vo.auth.CaptchaVO;

/**
 * 验证码服务接口
 */
public interface CaptchaService {

    /**
     * 生成验证码
     * @return 验证码信息（包含令牌和图片）
     */
    CaptchaVO generateCaptcha();

    /**
     * 验证验证码
     * @param captchaToken 验证码令牌
     * @param captchaCode 用户输入的验证码
     * @return 是否验证成功
     */
    boolean verifyCaptcha(String captchaToken, String captchaCode);
}
