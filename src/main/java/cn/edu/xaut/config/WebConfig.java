package cn.edu.xaut.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类，用于设置字符编码、CORS等Web相关配置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

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

    /**
     * 配置CORS跨域请求
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // 允许所有/api/**路径的跨域请求
                .allowedOrigins("http://localhost:5173", "http://localhost:5174", "http://localhost:3000")  // 允许的前端地址
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 允许的HTTP方法
                .allowedHeaders("*")  // 允许所有请求头
                .allowCredentials(true)  // 允许携带凭证
                .maxAge(3600);  // 预检请求的缓存时间（秒）
    }
}