package com.example.demo.controller.command.impl.common;

import com.example.demo.controller.command.Command;
import com.example.demo.controller.navigation.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.example.demo.controller.navigation.AttributeParameterHolder.*;
import static com.example.demo.controller.navigation.PageNavigation.HOME;
import static com.example.demo.controller.navigation.Router.Type.FORWARD;

public class SignOutUserCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String page = HOME;

        String localization = (String) session.getAttribute(SESSION_ATTRIBUTE_PARAMETER_LOCALIZATION);
        session.invalidate();

        session = request.getSession();
        session.setAttribute(SESSION_ATTRIBUTE_PARAMETER_LOCALIZATION, localization);

        session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, page);
        return new Router(page, FORWARD);
    }
}
