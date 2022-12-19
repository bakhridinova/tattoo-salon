package com.example.demo.controller.filter;

import com.example.demo.controller.navigation.PageNavigation;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {"/view/*"}, initParams = { @WebInitParam(name="INDEX_PATH", value=PageNavigation.HOME)})
public class SecurityFilter implements Filter {
    private String indexPath;
    public void init(FilterConfig config) {
        indexPath = config.getInitParameter("INDEX_PATH");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + indexPath);
        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}
