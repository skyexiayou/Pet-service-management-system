package cn.edu.xaut.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * Web配置类，用于设置字符编码等Web相关配置
 */
@Configuration
public class WebConfig {

    /**
     * 设置字符编码过滤器，确保所有响应使用UTF-8编码
     */
    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }
}