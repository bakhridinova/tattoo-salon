package com.example.demo.controller.command.impl.rating;

import com.example.demo.controller.command.Command;
import com.example.demo.controller.navigation.Router;
import com.example.demo.exception.CommandException;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.entity.Image;
import com.example.demo.model.entity.Rating;
import com.example.demo.service.ImageService;
import com.example.demo.service.RatingService;
import com.example.demo.service.impl.ImageServiceImpl;
import com.example.demo.service.impl.RatingServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

import static com.example.demo.controller.navigation.AttributeParameterHolder.*;
import static com.example.demo.controller.navigation.PageNavigation.IMAGE_DETAILS;
import static com.example.demo.controller.navigation.Router.Type.FORWARD;


public class StartEditRatingCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        RatingService ratingService = RatingServiceImpl.getInstance();
        ImageService imageService = ImageServiceImpl.getInstance();
        HttpSession session = request.getSession();
        List<Rating> ratings;
        String page;
        try {
            Long imageId = Long.parseLong(request.getParameter(PARAMETER_IMAGE_ID));
            Long ratingId = Long.parseLong(request.getParameter(PARAMETER_RATING_ID));
            ratings = ratingService.findAllForImage(imageId);
            Optional<Image> optionalImage = imageService.findById(imageId);
            Optional<com.example.demo.model.entity.dto.Rating> optionalRating = ratingService.findById(ratingId);
            optionalImage.ifPresent(image -> request.setAttribute(PARAMETER_IMAGE_ID, image));
            optionalRating.ifPresent(rating -> request.setAttribute(PARAMETER_RATING_ID, rating));
            request.setAttribute(SESSION_ATTRIBUTE_ALL_IMAGE_DETAILS, ratings);
            request.setAttribute(PARAMETER_IMAGE_IS_ORDERED, true);
            request.setAttribute(PARAMETER_IMAGE_IS_RATED, true);
            page = IMAGE_DETAILS;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, page);
        request.setAttribute(PARAMETER_CATALOG_CURRENT, 1);
        return new Router(page, FORWARD);
    }
}
