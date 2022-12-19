package com.example.demo.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.demo.controller.command.CommandType.DEFAULT;
import static com.example.demo.controller.navigation.PageNavigation.URL_REDIRECT_BASE_PATTERN;

/**
 * Filter for redirecting user uncommon JSP-page requests (fragment and errors packages) to default page.
 */
@WebFilter(filterName = "ForbidUncommonUrlFilter",
        urlPatterns = {"/view/fragment/*", "/view/errors/*"})
public class ForbidUncommonUrlFilter implements Filter {

    private static final String DEFAULT_PAGE_URL = String.format(URL_REDIRECT_BASE_PATTERN, DEFAULT.name());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.sendRedirect(request.getContextPath() + DEFAULT_PAGE_URL);
    }
}
