package com.example.demo.controller.filter;

import com.example.demo.controller.command.CommandType;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.enumerator.UserRole;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Map;

import static com.example.demo.controller.command.CommandType.*;
import static com.example.demo.controller.navigation.AttributeParameterHolder.*;
import static com.example.demo.model.entity.enumerator.UserRole.*;

/**
 * Filter for checking user role access to commands.
 */
@WebFilter(
        filterName = "CheckUserRoleFilter",
        dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST},
        urlPatterns = {"/home"})
public class CheckUserRoleFilter implements Filter {

    private Map<UserRole, EnumSet<CommandType>> userCommands;

    @Override
    public void init(FilterConfig filterConfig) {
        userCommands = Map.of(
                ADMIN, EnumSet.of(
                        // common
                        HOME,
                        DEFAULT,
                        CATALOG,

                        // admin only
                        ALL_TASKS,
                        STATISTICS,
                        PROCEED_NEW_DESIGN,

                        ALL_USERS,
                        ALL_IMAGES,
                        ALL_ORDERS,
                        ALL_RATINGS,

                        // admin/user only
                        SIGN_OUT,
                        IMAGE_DETAILS,
                        ACCOUNT_DETAILS,
                        START_EDIT_ACCOUNT_DETAILS,
                        FINISH_EDIT_ACCOUNT_DETAILS),

                USER, EnumSet.of(
                        // common
                        HOME,
                        DEFAULT,
                        CATALOG,

                        // user only
                        ACCOUNT_ORDERS,
                        ACCOUNT_IMAGES,
                        ACCOUNT_RATINGS,

                        CANCEL_ORDER,
                        PROCEED_ORDER,
                        CREATE_NEW_ORDER,

                        START_EDIT_RATING,
                        FINISH_EDIT_RATING,
                        START_CREATE_RATING,
                        FINISH_CREATE_RATING,

                        START_CREATE_DESIGN,
                        FINISH_CREATE_DESIGN,

                        START_DELETE_ACCOUNT,
                        FINISH_DELETE_ACCOUNT,


                        // admin/user only
                        SIGN_OUT,
                        IMAGE_DETAILS,
                        ACCOUNT_DETAILS,

                        START_EDIT_ACCOUNT_DETAILS,
                        FINISH_EDIT_ACCOUNT_DETAILS
                ),

                GUEST, EnumSet.of(
                        // common
                        HOME,
                        DEFAULT,
                        CATALOG,

                        // guest only
                        START_SIGN_IN,
                        FINISH_SIGN_IN,
                        START_SIGN_UP,
                        FINISH_SIGN_UP
                )
        );
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String commandStr = request.getParameter(PARAMETER_COMMAND);

        User user = (User) session.getAttribute(SESSION_ATTRIBUTE_USER);
        UserRole userRole = user != null ? user.getRole() : GUEST;

        EnumSet<CommandType> allowedCommands = userCommands.get(userRole);
        CommandType command = CommandType.valueOf(commandStr);

        if (!allowedCommands.contains(command)) {
            session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, DEFAULT);
            response.sendRedirect(request.getContextPath() + DEFAULT);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
