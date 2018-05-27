package com.interviewtest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Spring security configuration file
 *
 * @author ssethia
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers(HttpMethod.GET, "/v1/info");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                // we don't need CSRF guard
                .csrf().disable()

                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                // authorize all the requests
                .authorizeRequests()

                // allow only GET and POST
                .antMatchers(HttpMethod.GET, "/**").permitAll()
                .antMatchers(HttpMethod.POST, "/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").denyAll()
                .antMatchers(HttpMethod.PUT, "/**").denyAll()
                .antMatchers(HttpMethod.HEAD, "/**").denyAll()
                .antMatchers(HttpMethod.PATCH, "/**").denyAll()
                .antMatchers(HttpMethod.TRACE, "/**").denyAll();

        // disable page caching
        http.headers().cacheControl();
    }

}
