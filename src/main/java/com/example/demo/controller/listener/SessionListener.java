package com.example.demo.controller.listener;

import com.example.demo.model.entity.User;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.example.demo.controller.navigation.AttributeParameterHolder.*;
import static com.example.demo.controller.navigation.PageNavigation.HOME;
import static com.example.demo.model.entity.enumerator.UserRole.GUEST;

@WebListener
public class SessionListener implements HttpSessionListener {
    static Logger logger = LogManager.getLogger();
    private static final String DEFAULT_LOCALIZATION = "en";
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        User user = new User(); user.setRole(GUEST);
        session.setAttribute(SESSION_ATTRIBUTE_PARAMETER_LOCALIZATION, DEFAULT_LOCALIZATION);
        session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, HOME);
        session.setAttribute(SESSION_ATTRIBUTE_USER, user);

        logger.info("---------> sessionCreated: " + se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.info("----------> sessionDestroyed: " + se.getSession().getId());
    }
}
