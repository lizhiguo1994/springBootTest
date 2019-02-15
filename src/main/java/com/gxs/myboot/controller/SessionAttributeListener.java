package com.gxs.myboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener
public class SessionAttributeListener implements HttpSessionAttributeListener {

    private final Logger logger = LoggerFactory.getLogger(SessionAttributeListener.class);

    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {
        logger.info("增加了sessionAttribute");
    }


    @Override
    public void attributeRemoved(HttpSessionBindingEvent se) {
        logger.info("删除了sessionAttribute");
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent se) {
        logger.info("修改了sessionAttribute");
    }
}
