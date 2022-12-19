package com.example.demo.controller.filter;

import com.example.demo.controller.command.CommandType;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.example.demo.controller.navigation.PageNavigation.*;
import static com.example.demo.controller.navigation.PageNavigation.URL_REDIRECT_BASE_PATTERN;

/**
 * Filter for redirecting user JSP-page requests to corresponding command with sequential checks.
 */
@WebFilter(filterName = "ForbidUserUrlFilter",
        urlPatterns = {"/view/pages/*"})
public class ForbidUserUrlFilter implements Filter {

    private static Map<String, String> redirectUrl;

    public void init(FilterConfig config) {

        redirectUrl = new HashMap<>(18);

        // Common pages
        redirectUrl.put(HOME, String.format(URL_REDIRECT_BASE_PATTERN, CommandType.HOME));
        redirectUrl.put(DEFAULT, String.format(URL_REDIRECT_BASE_PATTERN, CommandType.DEFAULT));
        redirectUrl.put(SIGN_UP, String.format(URL_REDIRECT_BASE_PATTERN, CommandType.START_SIGN_UP));
        redirectUrl.put(SIGN_IN, String.format(URL_REDIRECT_BASE_PATTERN, CommandType.START_SIGN_IN));
        redirectUrl.put(STATISTICS, String.format(URL_REDIRECT_BASE_PATTERN, CommandType.STATISTICS));
        redirectUrl.put(USER_ACCOUNT , String.format(URL_REDIRECT_BASE_PATTERN, CommandType.ACCOUNT_DETAILS));
        redirectUrl.put(USER_RATINGS , String.format(URL_REDIRECT_BASE_PATTERN, CommandType.ACCOUNT_RATINGS));
        redirectUrl.put(USER_ORDERS, String.format(URL_REDIRECT_BASE_PATTERN, CommandType.ACCOUNT_ORDERS));
        redirectUrl.put(USER_IMAGES, String.format(URL_REDIRECT_BASE_PATTERN, CommandType.ACCOUNT_IMAGES));
        redirectUrl.put(EDIT_ACCOUNT, String.format(URL_REDIRECT_BASE_PATTERN, CommandType.START_EDIT_ACCOUNT_DETAILS));
        redirectUrl.put(DELETE_ACCOUNT, String.format(URL_REDIRECT_BASE_PATTERN, CommandType.START_DELETE_ACCOUNT));
        redirectUrl.put(CATALOG, String.format(URL_REDIRECT_BASE_PATTERN, CommandType.CATALOG));
        redirectUrl.put(ALL_TASKS, String.format(URL_REDIRECT_BASE_PATTERN, CommandType.ALL_TASKS));
        redirectUrl.put(ALL_USERS, String.format(URL_REDIRECT_BASE_PATTERN, CommandType.ALL_USERS));
        redirectUrl.put(ALL_IMAGES, String.format(URL_REDIRECT_BASE_PATTERN, CommandType.ALL_IMAGES));
        redirectUrl.put(ALL_RATINGS, String.format(URL_REDIRECT_BASE_PATTERN, CommandType.ALL_RATINGS));
        redirectUrl.put(ALL_ORDERS, String.format(URL_REDIRECT_BASE_PATTERN, CommandType.ALL_ORDERS));
        redirectUrl.put(IMAGE_DETAILS, String.format(URL_REDIRECT_BASE_PATTERN, CommandType.IMAGE_DETAILS));
        redirectUrl.put(NEW_IMAGE, String.format(URL_REDIRECT_BASE_PATTERN, CommandType.START_CREATE_DESIGN));
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();
        if (redirectUrl.containsKey(uri)){
            String url = redirectUrl.get(uri);
            request.getRequestDispatcher(url).forward(request, response);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
