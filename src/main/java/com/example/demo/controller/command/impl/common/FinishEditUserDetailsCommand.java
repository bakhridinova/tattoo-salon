package com.example.demo.controller.command.impl.common;

import com.example.demo.controller.command.Command;
import com.example.demo.controller.navigation.Router;
import com.example.demo.exception.CommandException;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.enumerator.UserGender;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.util.validator.FormValidator;
import com.example.demo.util.validator.impl.EditAccountFormValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.demo.controller.navigation.AttributeParameterHolder.*;
import static com.example.demo.controller.navigation.PageNavigation.EDIT_ACCOUNT;
import static com.example.demo.controller.navigation.PageNavigation.USER_ACCOUNT;
import static com.example.demo.controller.navigation.Router.Type.FORWARD;


public class FinishEditUserDetailsCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = EDIT_ACCOUNT;
        HttpSession session = request.getSession();
        UserService userService = UserServiceImpl.getInstance();

        FormValidator validator = EditAccountFormValidator.getInstance();
        Map<String, String[]> requestParameters = request.getParameterMap();
        List<String> validationFeedback = validator.validateForm(requestParameters);

        if (validationFeedback.isEmpty()) {
            String gender = request.getParameter(PARAMETER_USER_GENDER);
            String fullName = request.getParameter(PARAMETER_USER_FULL_NAME);
            String emailAddress = request.getParameter(PARAMETER_USER_EMAIL_ADDRESS);
            String contactNumber = request.getParameter(PARAMETER_USER_CONTACT_NUMBER);
            String birthDate = request.getParameter(PARAMETER_USER_BIRTH_DATE);


            try {
                User user = (User) session.getAttribute(SESSION_ATTRIBUTE_USER);
                user.setGender(UserGender.valueOf(gender.toUpperCase()));
                user.setFullName(fullName);
                user.setEmailAddress(emailAddress);
                user.setContactNumber(contactNumber);
                user.setBirthDate(Timestamp.valueOf(birthDate + " 00:00:00"));
                Optional<User> optionalUser = userService.edit(user);
                if (optionalUser.isPresent()) {
                    session.setAttribute(SESSION_ATTRIBUTE_USER, optionalUser.get());
                    session.setAttribute(SESSION_ATTRIBUTE_USER, optionalUser.get()); // for logs
                    page = USER_ACCOUNT;
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
