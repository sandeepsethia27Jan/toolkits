package com.interviewtest.config.profiles;

import org.springframework.context.annotation.Profile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ssethia on 5/26/2017.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Profile("docs")
public @interface DocsProfile {
}
