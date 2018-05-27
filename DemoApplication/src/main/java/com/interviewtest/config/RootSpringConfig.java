package com.interviewtest.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * This class contains the root configuration
 */

@Configuration
@ComponentScan(basePackages = {
        "com.interviewtest.*"
})
@Import({ SecurityConfig.class })
public class RootSpringConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorParameter(false)
                .favorPathExtension(false);
    }

    // removed as soon as we have a real solution.
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/v1/**")
                .allowedOrigins("http://localhost:9000");
    }
}