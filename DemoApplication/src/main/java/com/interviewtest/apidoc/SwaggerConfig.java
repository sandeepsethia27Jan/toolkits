package com.interviewtest.apidoc;

import com.fasterxml.classmate.TypeResolver;
import com.interviewtest.config.profiles.DocsProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.WildcardType;
import java.time.LocalDate;
import java.util.ArrayList;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

/**
 * Created by ssethia on 05/26/2018.
 */
@DocsProfile
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Autowired
    private TypeResolver typeResolver;

    private final ApiInfo apiInfo = new ApiInfo("Currency exchange dummy app", "", "V1.0.0", "[TODO]", new Contact("[TODO]", "[TODO]", "[TODO]"), "[TODO]", "[TODO]", new ArrayList<>());

    @Bean
    public Docket adminApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(
                        newRule(typeResolver.resolve(DeferredResult.class,
                                typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                                typeResolver.resolve(WildcardType.class)))
                .useDefaultResponseMessages(true)
                .globalResponseMessage(RequestMethod.GET,
                        newArrayList(new ResponseMessageBuilder()
                                .code(500)
                                .message("Internal Server Error")
                                .responseModel(new ModelRef("Error"))
                                .build()))
                .securitySchemes(newArrayList(apiKey()))
                .enableUrlTemplating(true);
    }

    private static ApiKey apiKey() {
        return new ApiKey("mykey", "api_key", "header");
    }
}