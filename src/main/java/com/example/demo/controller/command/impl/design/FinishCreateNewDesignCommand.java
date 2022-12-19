package com.example.demo.controller.command.impl.design;

import com.example.demo.controller.command.Command;
import com.example.demo.controller.navigation.Router;
import com.example.demo.exception.CommandException;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.entity.Image;
import com.example.demo.model.entity.User;
import com.example.demo.util.validator.FormValidator;
import com.example.demo.util.validator.impl.NewDesignFormValidator;
import com.example.demo.service.ImageService;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.ImageServiceImpl;
import com.example.demo.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.example.demo.controller.navigation.AttributeParameterHolder.*;
import static com.example.demo.controller.navigation.PageNavigation.HOME;
import static com.example.demo.controller.navigation.Router.Type.FORWARD;
import static com.example.demo.model.entity.enumerator.ImageStatus.PENDING;
import static com.example.demo.model.entity.enumerator.UserStatus.PRO;


public class FinishCreateNewDesignCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        UserService userService = UserServiceImpl.getInstance();
        ImageService imageService = ImageServiceImpl.getInstance();

        FormValidator validator = NewDesignFormValidator.getInstance();
        Map<String, String[]> requestParameters = request.getParameterMap();
        List<String> validationFeedback = validator.validateForm(requestParameters);
        User user = (User) session.getAttribute(SESSION_ATTRIBUTE_USER);

        if (validationFeedback.isEmpty()) {
            String url = request.getParameter(PARAMETER_IMAGE_URL);
            String shortDescription = request.getParameter(PARAMETER_IMAGE_SHORT_DESC);
            String longDescription = request.getParameter(PARAMETER_IMAGE_LONG_DESC);

            try {
                user.setStatus(PRO);
                userService.edit(user);
                Image image = new Image(0L, user.getId(), PENDING, shortDescription, longDescription, Timestamp.valueOf(LocalDateTime.now()), 0, 0.0, 0.0, url);
                imageService.create(image);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        } else {
            request.setAttribute(REQUEST_ATTRIBUTE_ERROR_MESSAGE, validationFeedback.get(0));
        }
        return new Router(HOME, FORWARD);
    }
}
