package com.example.demo.controller.command.impl.common;

import com.example.demo.controller.command.Command;
import com.example.demo.controller.navigation.Router;
import com.example.demo.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

import static com.example.demo.controller.navigation.PageNavigation.SIGN_UP;
import static com.example.demo.controller.navigation.Router.Type.FORWARD;

public class StartSignUpUserCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        return new Router(SIGN_UP, FORWARD);
    }
}
