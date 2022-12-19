package com.example.demo.controller.listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class SessionAttributeListener implements HttpSessionAttributeListener {
    static Logger logger = LogManager.getLogger();

    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        String attributeName = sbe.getName();
        Object attributeValue = sbe.getValue();
        logger.info("---------> attributeAdded: " + attributeName + " : " + attributeValue);
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        String attributeName = sbe.getName();
        Object attributeValue = sbe.getValue();
        logger.info("---------> attributeRemoved: " + attributeName + " : " + attributeValue);
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        String attributeName = sbe.getName();
        Object attributeValue = sbe.getValue();
        logger.info("---------> attributeReplaced: " + attributeName + " : " + attributeValue);
    }
}
