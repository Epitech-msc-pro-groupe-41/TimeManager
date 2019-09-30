package com.timemanager.api.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
/**
 * Config for swagger documentation : http://localhost:4000/rest/swagger-ui.html#/
 */
public class SwaggerConfig {

    @Autowired
    private Environment env;

    @Bean
    /**
     * Init where to check folder for routes
     */
    public Docket api() {
        List<SecurityScheme> schemeList = new ArrayList<>();
        schemeList.add(new ApiKey("Bearer", HttpHeaders.AUTHORIZATION, "header"));
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //.apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.timemanager.core"))  //
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(schemeList);
    }

    /**
     * Init swagger data infos and contact infos
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder() .title(env.getProperty("swagger.info.title"))
                                    .description(env.getProperty("swagger.info.desc"))
                                    .contact(new Contact(   env.getProperty("swagger.info.contact.name"),
                                                            env.getProperty(""), 
                                                            env.getProperty("swagger.info.contact.email")))
                                    .version(env.getProperty("swagger.info.version"))
                                    .build();
    }
}