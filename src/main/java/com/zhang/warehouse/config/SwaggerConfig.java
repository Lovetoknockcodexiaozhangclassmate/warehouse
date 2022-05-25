package com.zhang.warehouse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 1. swagger配置类
 * @author 1张伟严
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * http://localhost:8082/swagger-ui.html
     * @return :
     */
    @Bean
    public Docket buildDocket() {
        return  new Docket(DocumentationType.SWAGGER_2)
                //调用下面apiInfo()方法
                .apiInfo(apiInfo())
                .select()
                //Controller所在路径
                .apis(RequestHandlerSelectors.basePackage("com.zhang.warehouse.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    public ApiInfo apiInfo() {
        return  new ApiInfoBuilder()
                .title("SpringBoot结合swagger2构建Restful API")
                .description("swagger2调试文档")
                .version("0.0.1")
                .build();
    }
}
