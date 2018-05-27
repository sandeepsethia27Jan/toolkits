package com.interviewtest.config;

import static com.fasterxml.jackson.core.JsonFactory.Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.node.JsonNodeFactory.withExactBigDecimals;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan("com.interviewtest")
public class WebSpringConfig {

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder() {
            @Override
            public void configure(final ObjectMapper objectMapper) {
                super.configure(objectMapper);
                objectMapper
                        .setVisibility(PropertyAccessor.ALL, Visibility.NONE)
                        .setVisibility(PropertyAccessor.FIELD, Visibility.ANY)
                        .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
                        .disableDefaultTyping()
                        .setNodeFactory(withExactBigDecimals(true))
                        .getFactory()
                        .configure(FAIL_ON_SYMBOL_HASH_OVERFLOW, true);
            }
        };
    }
}
