package com.interviewtest.config.initializers;

import com.interviewtest.config.RootSpringConfig;
import com.google.common.base.Strings;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import static org.springframework.core.env.AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME;

/**
 * created by ssethia on 05/27/2018
 */

public class WebServletConfiguration implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext ctx) throws ServletException {
        AnnotationConfigWebApplicationContext webCtx = new AnnotationConfigWebApplicationContext();
        webCtx.register(RootSpringConfig.class);
        webCtx.setServletContext(ctx);
        ServletRegistration.Dynamic servlet = ctx.addServlet("dispatcher", new DispatcherServlet(webCtx));
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/*");

        ctx.setInitParameter(ACTIVE_PROFILES_PROPERTY_NAME, activeProfiles(ACTIVE_PROFILES_PROPERTY_NAME));
    }

    private static String activeProfiles(final String systemProperty) {
        final String activeProfiles = System.getProperty(systemProperty);
        return Strings.isNullOrEmpty(activeProfiles)
                ? "development"
                : activeProfiles;
    }
}
