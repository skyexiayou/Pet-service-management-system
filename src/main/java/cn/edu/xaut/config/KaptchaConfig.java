package cn.edu.xaut.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 验证码配置类
 */
@Configuration
public class KaptchaConfig {

    @Value("${captcha.width:120}")
    private String width;

    @Value("${captcha.height:40}")
    private String height;

    @Value("${captcha.length:4}")
    private String length;

    @Bean
    public DefaultKaptcha defaultKaptcha() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        
        // 图片边框
        properties.setProperty("kaptcha.border", "no");
        // 图片宽度
        properties.setProperty("kaptcha.image.width", width);
        // 图片高度
        properties.setProperty("kaptcha.image.height", height);
        // 字符集
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        // 字符长度
        properties.setProperty("kaptcha.textproducer.char.length", length);
        // 字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        // 字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "black");
        // 干扰线颜色
        properties.setProperty("kaptcha.noise.color", "blue");
        
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        
        return defaultKaptcha;
    }
}
