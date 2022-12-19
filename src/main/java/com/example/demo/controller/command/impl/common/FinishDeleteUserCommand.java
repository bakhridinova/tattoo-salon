package com.example.demo.controller.command.impl.common;

import com.example.demo.controller.command.Command;
import com.example.demo.controller.navigation.Router;
import com.example.demo.exception.CommandException;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.entity.User;
import com.example.demo.util.encoder.PasswordEncoder;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.security.NoSuchAlgorithmException;

import static com.example.demo.controller.navigation.AttributeParameterHolder.*;
import static com.example.demo.controller.navigation.PageNavigation.*;
import static com.example.demo.controller.navigation.Router.Type.FORWARD;

public class FinishDeleteUserCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String password = request.getParameter(PARAMETER_USER_PASSWORD);
        PasswordEncoder encoder = PasswordEncoder.getInstance();
        UserService userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        String page;
        try {
            User user = (User) session.getAttribute(SESSION_ATTRIBUTE_USER);
            password = encoder.encode(password);
            if (password.equals(user.getPassword())) {
                userService.delete(user);
                page = HOME;
                String localization = (String) session.getAttribute(PARAMETER_LANG);
                session.invalidate();
                session = request.getSession();
                session.setAttribute(SESSION_ATTRIBUTE_PARAMETER_LOCALIZATION, localization);
            } else {
                request.setAttribute(REQUEST_ATTRIBUTE_ERROR_MESSAGE, PARAMETER_USER_PASSWORD);
                page = ERROR_404;
            }
        } catch (ServiceException | NoSuchAlgorithmException e) {
            throw new CommandException(e);
        }
        session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, page);
        return new Router(page, FORWARD);
    }
}
