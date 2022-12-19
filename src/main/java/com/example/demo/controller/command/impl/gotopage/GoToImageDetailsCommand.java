package com.example.demo.controller.command.impl.gotopage;

import com.example.demo.controller.command.Command;
import com.example.demo.controller.navigation.Router;
import com.example.demo.exception.CommandException;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.entity.Image;
import com.example.demo.model.entity.Rating;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.dto.Order;
import com.example.demo.service.ImageService;
import com.example.demo.service.OrderService;
import com.example.demo.service.RatingService;
import com.example.demo.service.impl.ImageServiceImpl;
import com.example.demo.service.impl.OrderServiceImpl;
import com.example.demo.service.impl.RatingServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

import static com.example.demo.controller.navigation.AttributeParameterHolder.*;
import static com.example.demo.controller.navigation.PageNavigation.IMAGE_DETAILS;
import static com.example.demo.controller.navigation.Router.Type.FORWARD;


public class GoToImageDetailsCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        RatingService ratingService = RatingServiceImpl.getInstance();
        ImageService imageService = ImageServiceImpl.getInstance();
        OrderService orderService = OrderServiceImpl.getInstance();
        HttpSession session = request.getSession();
        List<Rating> ratings;
        String page;
        try {
            User user = (User) session.getAttribute(SESSION_ATTRIBUTE_USER);
            Long imageId = Long.parseLong(request.getParameter(PARAMETER_IMAGE_ID));
            ratings = ratingService.findAllForImage(imageId);
            Optional<Image> optionalImage = imageService.findById(imageId);
            if (optionalImage.isPresent()) {
                Image image = optionalImage.get();
                request.setAttribute(PARAMETER_IMAGE_ID, image);
                List<Order> imageOrders = orderService.findAllForImage(image);
                boolean ordered = false;
                boolean rated = false;
                for (Order order: imageOrders) {
                    if (order.getUserId().equals(user.getId())) {
                        ordered = true;
                        break;
                    }
                }
                for (Rating rating: ratings) {
                    if (rating.getUser().getId().equals(user.getId())) {
                        rated = true;
                        break;
                    }
                }
                request.setAttribute(PARAMETER_IMAGE_IS_ORDERED, ordered);
                request.setAttribute(PARAMETER_IMAGE_IS_RATED, rated);
            }
            request.setAttribute(SESSION_ATTRIBUTE_ALL_IMAGE_DETAILS, ratings);
            page = IMAGE_DETAILS;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, page);
        request.setAttribute(PARAMETER_CATALOG_CURRENT, 1);
        return new Router(page, FORWARD);
    }
}
