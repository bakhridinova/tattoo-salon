package com.example.demo.controller.command.impl.common;

import com.example.demo.controller.command.Command;
import com.example.demo.controller.navigation.Router;
import com.example.demo.exception.CommandException;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.enumerator.UserGender;
import com.example.demo.util.validator.FormValidator;
import com.example.demo.util.validator.impl.SignUpFormValidator;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.demo.controller.navigation.AttributeParameterHolder.*;
import static com.example.demo.controller.navigation.PageNavigation.HOME;
import static com.example.demo.controller.navigation.PageNavigation.SIGN_UP;
import static com.example.demo.controller.navigation.Router.Type.FORWARD;
import static com.example.demo.model.entity.enumerator.UserRole.USER;
import static com.example.demo.model.entity.enumerator.UserStatus.ACTIVE;


public class FinishSignUpUserCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = SIGN_UP;
        HttpSession session = request.getSession();
        UserService userService = UserServiceImpl.getInstance();

        FormValidator validator = SignUpFormValidator.getInstance();
        Map<String, String[]> requestParameters = request.getParameterMap();
        List<String > validationFeedback = validator.validateForm(requestParameters);

        if (validationFeedback.isEmpty()) {
            String username = request.getParameter(PARAMETER_USER_NAME);
            String password = request.getParameter(PARAMETER_USER_PASSWORD);
            String gender = request.getParameter(PARAMETER_USER_GENDER);
            String fullName = request.getParameter(PARAMETER_USER_FULL_NAME);
            String emailAddress = request.getParameter(PARAMETER_USER_EMAIL_ADDRESS);
            String contactNumber = request.getParameter(PARAMETER_USER_CONTACT_NUMBER);
            String birthDate = request.getParameter(PARAMETER_USER_BIRTH_DATE);


            try {
                if (!userService.usernameExists(username)) {
                    User user =  new User(0L, USER, ACTIVE, username, password, UserGender.valueOf(gender.toUpperCase()), Timestamp.valueOf(birthDate), fullName, contactNumber, emailAddress);
                    Optional<User> optionalUser = userService.create(user);
                    if (optionalUser.isPresent()) {
                        session.setAttribute(SESSION_ATTRIBUTE_USER, optionalUser.get());
                        session.setAttribute(SESSION_ATTRIBUTE_USER, optionalUser.get()); // for logs
                        page = HOME;
                    }
                } else {
                    request.setAttribute(PARAMETER_USER_NAME, username);
                    request.setAttribute(PARAMETER_USER_PASSWORD, password);
                    request.setAttribute(PARAMETER_USER_GENDER, gender);
                    request.setAttribute(PARAMETER_USER_FULL_NAME, fullName);
                    request.setAttribute(PARAMETER_USER_EMAIL_ADDRESS, emailAddress);
                    request.setAttribute(PARAMETER_USER_CONTACT_NUMBER, contactNumber);
                    request.setAttribute(PARAMETER_USER_BIRTH_DATE, birthDate);
                    request.setAttribute(REQUEST_ATTRIBUTE_ERROR_MESSAGE, "user_name_taken");
                }
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        } else  {
            request.setAttribute(REQUEST_ATTRIBUTE_ERROR_MESSAGE, validationFeedback.get(0));
        }
        session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, page);
        return new Router(page, FORWARD);
    }
}
