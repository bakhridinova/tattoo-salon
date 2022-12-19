package com.example.demo.controller.filter;

import com.example.demo.controller.navigation.PageNavigation;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebFilter(filterName = "PreIndexFilter", urlPatterns = PageNavigation.HOME)
public class PreIndexFilter implements Filter {
    static Logger logger = LogManager.getLogger();
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession(false);
        logger.info("---------> " + (session != null ? session.getId() : "session was not created"));
        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}
