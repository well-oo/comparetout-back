package com.pepit.compareTout.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Optional;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

        @Bean
        public Docket api() {
            return new Docket(DocumentationType.SWAGGER_2)//
                    .select()//
                    .apis(RequestHandlerSelectors.basePackage("com.pepit.compareTout.controller"))
                    .paths(regex("(/api/products.*|/users/supplier.*)"))
                    .build()//
                    .apiInfo(metadata())//
                    .useDefaultResponseMessages(false)//
                    .securitySchemes(Arrays.asList(apiKey()))
                    .genericModelSubstitutes(Optional.class);

        }

    private ApiKey apiKey() {
        return new ApiKey("apiKey", "Authorization", "header");
    }


    private ApiInfo metadata() {
            return new ApiInfoBuilder()
                    .title("API SUPPLIER")
                    .description(
                            "This is an api used by suppliers to manipulate products ! You should click on the right top button `Authorize` and introduce your token with the prefix \"Bearer\" with first letter in uppercase and other in lowercase with a space between bearer and your token.")//
                    .version("1.0.0")
                    .license("MIT License").licenseUrl("http://opensource.org/licenses/MIT")//
                    .contact(new Contact("CompareTout", "https://www.comparetout.org", "admin@comparetout.org"))
                    .build();
        }


}
