package com.robben.config;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${server.servlet.context-path}")
    private String projectName;

    //---------------------------------默认的指定空间--------------------------------------------
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("defalut1")  //默认模块的名称不需要写,不然会报错
                .enable(true)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))  //只是扫描带注解的
                .paths(PathSelectors.regex("/.*"))  //对根下所有路径进行监控
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //标题
                .title(projectName.split("/")[1] + "接口文档")
                //描述
                .description("lalala")
                .version("1.0.0")
                .contact(new Contact("robben", null, "EX-robbenOO1@pingan.com.cn"))
                .build();
    }

}
