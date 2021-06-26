package com.lft.rabbitmq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Class Name:      SwaggerConfig
 * Package Name:    com.lft.rabbitmq.config
 * <p>
 * Function: 		A {@code SwaggerConfig} object With Some FUNCTION.
 * Date:            2021-06-26 9:19
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    
    @Bean
    public Docket webApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                .build();
    }
    
    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder()
                .title("RabbitMQ接口文档")
                .description("本文档描述了 RabbitMQ 微服务接口定义")
                .version("1.0.0")
                .contact(new Contact("Laifengting", "http://vi7p.com", "laifengting@foxmail.com"))
                .build();
        
    }
    
}
