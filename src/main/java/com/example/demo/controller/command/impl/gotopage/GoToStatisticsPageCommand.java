package com.example.demo.controller.command.impl.gotopage;

import com.example.demo.controller.command.Command;
import com.example.demo.controller.navigation.Router;
import com.example.demo.exception.CommandException;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.entity.Image;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.dto.Order;
import com.example.demo.model.entity.dto.Rating;
import com.example.demo.service.ImageService;
import com.example.demo.service.OrderService;
import com.example.demo.service.RatingService;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.ImageServiceImpl;
import com.example.demo.service.impl.OrderServiceImpl;
import com.example.demo.service.impl.RatingServiceImpl;
import com.example.demo.service.impl.UserServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import static com.example.demo.controller.navigation.AttributeParameterHolder.*;
import static com.example.demo.controller.navigation.PageNavigation.STATISTICS;
import static com.example.demo.controller.navigation.Router.Type.FORWARD;


public class GoToStatisticsPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String localisation = request.getParameter(PARAMETER_LANG);
        RatingService ratingService = RatingServiceImpl.getInstance();
        ImageService imageService = ImageServiceImpl.getInstance();
        OrderService orderService = OrderServiceImpl.getInstance();
        UserService userService = UserServiceImpl.getInstance();
        String userData, orderData, imageData, ratingData;
        HttpSession session = request.getSession();
        Gson gsonObj = new Gson();
        String page = STATISTICS;
        try {
            List<User> users = userService.findAllUsers();
            List<Image> images = imageService.findAllImages();
            List<Order> orders = orderService.findAllOrders();
            List<Rating> ratings = ratingService.findAllRatings();

            userData = gsonObj.toJson(users);
            imageData = gsonObj.toJson(images);
            orderData = gsonObj.toJson(orders);
            ratingData = gsonObj.toJson(ratings);
        }
        catch(ServiceException e){
            throw new CommandException(e);
        }
        if (localisation != null) {
            session.setAttribute(SESSION_ATTRIBUTE_PARAMETER_LOCALIZATION, localisation);
        }
        session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, page);
        session.setAttribute(SESSION_ATTRIBUTE_USER_DATA, userData);
        session.setAttribute(SESSION_ATTRIBUTE_IMAGE_DATA, imageData);
        session.setAttribute(SESSION_ATTRIBUTE_ORDER_DATA, orderData);
        session.setAttribute(SESSION_ATTRIBUTE_RATING_DATA, ratingData);
        return new Router(page, FORWARD);
    }
}
