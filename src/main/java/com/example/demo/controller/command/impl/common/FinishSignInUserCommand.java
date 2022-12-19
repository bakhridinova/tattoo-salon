package com.example.demo.controller.command.impl.common;

import com.example.demo.controller.command.Command;
import com.example.demo.controller.navigation.Router;
import com.example.demo.exception.CommandException;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.entity.User;
import com.example.demo.util.validator.FormValidator;
import com.example.demo.util.validator.impl.SignInFormValidator;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.demo.controller.navigation.AttributeParameterHolder.*;
import static com.example.demo.controller.navigation.PageNavigation.HOME;
import static com.example.demo.controller.navigation.PageNavigation.SIGN_IN;
import static com.example.demo.controller.navigation.Router.Type.FORWARD;
import static com.example.demo.model.entity.enumerator.UserStatus.DELETED;

public class FinishSignInUserCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = SIGN_IN;
        HttpSession session = request.getSession();
        UserService userService = UserServiceImpl.getInstance();

        FormValidator validator = SignInFormValidator.getInstance();
        Map<String, String[]> requestParameters = request.getParameterMap();
        List<String> validationFeedback = validator.validateForm(requestParameters);

        if (validationFeedback.isEmpty()) {
            String username = request.getParameter(PARAMETER_USER_NAME);
            String password = request.getParameter(PARAMETER_USER_PASSWORD);
            try {
                Optional<User> optionalUser = userService.authenticate(username, password);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    if (!user.getStatus().equals(DELETED)) {
                        session.setAttribute(SESSION_ATTRIBUTE_USER, user);
                        session.setAttribute(SESSION_ATTRIBUTE_USER, user); // for logs
                        page = HOME;
                    } else {
                        request.setAttribute(REQUEST_ATTRIBUTE_ERROR_MESSAGE, "user_deleted");
                    }
                } else {
                    request.setAttribute(REQUEST_ATTRIBUTE_ERROR_MESSAGE, "username_or_password");
                }
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        } else {
            request.setAttribute(REQUEST_ATTRIBUTE_ERROR_MESSAGE, validationFeedback.get(0));
        }
        session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, page);
        return new Router(page, FORWARD);
    }
}
