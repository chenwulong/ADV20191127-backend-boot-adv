package com.techprimers.security.securitydbexample;

import com.techprimers.security.securitydbexample.resource.UsuarioResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableSwagger2
public class SecurityDbExampleApplication /*extends SpringBootServletInitializer*/ {

    Logger logger = LoggerFactory.getLogger(UsuarioResource.class);

    /*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SecurityDbExampleApplication.class);
    }*/

    public static void main(String[] args) {
        SpringApplication.run(SecurityDbExampleApplication.class, args);
    } // main

    @Bean
    public Docket swaggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                //.paths(PathSelectors.ant("/*"))
                .apis(RequestHandlerSelectors.basePackage("com.techprimers.security.securitydbexample"))
                .build()
                .apiInfo(this.apiDetails());
    } // swaggerConfiguration

    private ApiInfo apiDetails() {
        return new ApiInfo(
                "Adv APP API",
                "API for Adv Client System",
                "1.0",
                "Free to use",
                new springfox.documentation.service.Contact("Cristiano de Souza", "http://google.com", "wulongcs@gmail.com"),
                "API License",
                "http://google.com",
                Collections.emptyList());
    } // apiDetails
} // SecurityDbExampleApplication
