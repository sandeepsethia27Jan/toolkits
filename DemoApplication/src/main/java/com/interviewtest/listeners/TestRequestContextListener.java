package com.interviewtest.listeners;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.annotation.WebListener;


/**
 * created by ssethia on 05/27/2018
 *
 * Request context listen
 */
@Configuration
@WebListener
public class TestRequestContextListener extends RequestContextListener {

    @Override
    public void requestDestroyed(ServletRequestEvent requestEvent) {
        super.requestDestroyed(requestEvent);
    }
}
