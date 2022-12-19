package com.example.demo.controller.command;

import com.example.demo.controller.navigation.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.example.demo.controller.navigation.AttributeParameterHolder.*;
import static com.example.demo.controller.navigation.PageNavigation.HOME;
import static com.example.demo.controller.navigation.Router.Type.FORWARD;

public class DefaultCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String localisation = request.getParameter(PARAMETER_LANG);
        if (localisation != null) {
            session.setAttribute(SESSION_ATTRIBUTE_PARAMETER_LOCALIZATION, localisation);
        }
        String page = HOME;
        session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, page);
        return new Router(page, FORWARD);
    }
}
