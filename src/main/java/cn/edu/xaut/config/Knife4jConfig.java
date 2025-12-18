package cn.edu.xaut.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Knife4j配置类，用于生成API接口文档
 */
@Configuration
public class Knife4jConfig {

    /**
     * 配置OpenAPI信息
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("宠物服务管理系统API文档")
                        .description("宠物服务管理系统的RESTful API接口文档，包含宠物档案、美容服务、医疗服务、寄养服务、门店管理和宠物用品等模块")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("宠物服务管理系统开发团队")
                                .email("dev@pet-service.com")
                                .url("http://localhost:8080"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
