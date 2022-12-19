package com.example.demo.controller.command.impl.design;

import com.example.demo.controller.command.Command;
import com.example.demo.controller.navigation.Router;
import com.example.demo.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

import static com.example.demo.controller.navigation.AttributeParameterHolder.PARAMETER_CATALOG_CURRENT;
import static com.example.demo.controller.navigation.AttributeParameterHolder.PARAMETER_CATALOG_PAGINATION;
import static com.example.demo.controller.navigation.PageNavigation.NEW_IMAGE;
import static com.example.demo.controller.navigation.Router.Type.FORWARD;


public class StartCreateNewDesignCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String pagination = request.getParameter(PARAMETER_CATALOG_PAGINATION);
        int index;
        if (pagination != null) {
            index = Integer.parseInt(pagination);
        } else {
            index = 1;
        }
        request.setAttribute(PARAMETER_CATALOG_CURRENT, index);
        return new Router(NEW_IMAGE, FORWARD);
    }
}
