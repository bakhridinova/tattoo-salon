package com.example.demo.controller.listener;

import com.example.demo.model.connection.ConnectionPool;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class ServletContextListener implements jakarta.servlet.ServletContextListener {
    static Logger logger = LogManager.getLogger();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionPool.getInstance();
        logger.info("---------> contextInitialized: " + sce.getServletContext().getServerInfo());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("---------> contextDestroyed: " + sce.getServletContext().getServerInfo());
        ConnectionPool.getInstance().destroyPool();
    }
}
