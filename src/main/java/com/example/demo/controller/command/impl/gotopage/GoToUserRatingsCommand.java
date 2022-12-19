package com.example.demo.controller.command.impl.gotopage;

import com.example.demo.controller.command.Command;
import com.example.demo.controller.navigation.Router;
import com.example.demo.exception.CommandException;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.entity.Rating;
import com.example.demo.model.entity.User;
import com.example.demo.service.RatingService;
import com.example.demo.service.impl.RatingServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import static com.example.demo.controller.navigation.AttributeParameterHolder.*;
import static com.example.demo.controller.navigation.PageNavigation.USER_RATINGS;
import static com.example.demo.controller.navigation.Router.Type.FORWARD;


public class GoToUserRatingsCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        RatingService ratingService = RatingServiceImpl.getInstance();
        String localisation = request.getParameter(PARAMETER_LANG);
        HttpSession session = request.getSession();
        List<Rating> ratings;
        String page;
        try {
            User user = (User) session.getAttribute(SESSION_ATTRIBUTE_USER);
            ratings = ratingService.findAllUserRatings(user);
            page = USER_RATINGS;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        if (localisation != null) {
            session.setAttribute(SESSION_ATTRIBUTE_PARAMETER_LOCALIZATION, localisation);
        }
        session.setAttribute(SESSION_ATTRIBUTE_ALL_USER_RATINGS, ratings);
        session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, page);
        return new Router(page, FORWARD);
    }
}
