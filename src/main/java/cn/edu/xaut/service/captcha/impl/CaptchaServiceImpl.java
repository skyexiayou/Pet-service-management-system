package cn.edu.xaut.service.captcha.impl;

import cn.edu.xaut.domain.vo.auth.CaptchaVO;
import cn.edu.xaut.service.captcha.CaptchaService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 验证码服务实现类
 */
@Slf4j
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Value("${captcha.expiration:300000}")
    private Long captchaExpiration;

    // 验证码缓存：key为令牌，value为验证码信息
    private final Map<String, CaptchaEntry> captchaCache = new ConcurrentHashMap<>();

    private final DefaultKaptcha defaultKaptcha;

    public CaptchaServiceImpl(DefaultKaptcha defaultKaptcha) {
        this.defaultKaptcha = defaultKaptcha;
    }

    @Override
    public CaptchaVO generateCaptcha() {
        // 生成验证码文本
        String captchaText = defaultKaptcha.createText();
        
        // 生成验证码图片
        BufferedImage captchaImage = defaultKaptcha.createImage(captchaText);
        
        // 生成唯一令牌
        String captchaToken = UUID.randomUUID().toString();
        
        // 将验证码存入缓存
        captchaCache.put(captchaToken, new CaptchaEntry(captchaText, System.currentTimeMillis()));
        
        // 将图片转换为Base64
        String base64Image = convertImageToBase64(captchaImage);
        
        log.info("生成验证码，令牌: {}", captchaToken);
        
        return CaptchaVO.builder()
                .captchaToken(captchaToken)
                .captchaImage("data:image/png;base64," + base64Image)
                .build();
    }

    @Override
    public boolean verifyCaptcha(String captchaToken, String captchaCode) {
        if (captchaToken == null || captchaCode == null) {
            log.warn("验证码令牌或验证码为空");
            return false;
        }
        
        CaptchaEntry entry = captchaCache.get(captchaToken);
        if (entry == null) {
            log.warn("验证码令牌不存在: {}", captchaToken);
            return false;
        }
        
        // 验证是否过期
        if (System.currentTimeMillis() - entry.getCreateTime() > captchaExpiration) {
            captchaCache.remove(captchaToken);
            log.warn("验证码已过期: {}", captchaToken);
            return false;
        }
        
        // 验证码比较（不区分大小写）
        boolean matches = entry.getCode().equalsIgnoreCase(captchaCode);
        
        // 验证成功后移除验证码（一次性使用）
        if (matches) {
            captchaCache.remove(captchaToken);
            log.info("验证码验证成功: {}", captchaToken);
        } else {
            log.warn("验证码验证失败: {}, 输入: {}, 正确: {}", captchaToken, captchaCode, entry.getCode());
        }
        
        return matches;
    }

    /**
     * 定时清理过期验证码（每分钟执行一次）
     */
    @Scheduled(fixedRate = 60000)
    public void cleanExpiredCaptcha() {
        long now = System.currentTimeMillis();
        int removedCount = 0;
        
        for (Map.Entry<String, CaptchaEntry> entry : captchaCache.entrySet()) {
            if (now - entry.getValue().getCreateTime() > captchaExpiration) {
                captchaCache.remove(entry.getKey());
                removedCount++;
            }
        }
        
        if (removedCount > 0) {
            log.info("清理过期验证码: {} 个", removedCount);
        }
    }

    /**
     * 将图片转换为Base64字符串
     */
    private String convertImageToBase64(BufferedImage image) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", baos);
            byte[] imageBytes = baos.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            log.error("图片转换Base64失败", e);
            throw new RuntimeException("验证码图片生成失败");
        }
    }

    /**
     * 验证码缓存条目
     */
    private static class CaptchaEntry {
        private final String code;
        private final long createTime;

        public CaptchaEntry(String code, long createTime) {
            this.code = code;
            this.createTime = createTime;
        }

        public String getCode() {
            return code;
        }

        public long getCreateTime() {
            return createTime;
        }
    }
}
