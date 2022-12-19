package com.example.demo.controller.command.impl.rating;

import com.example.demo.controller.command.Command;
import com.example.demo.controller.navigation.Router;
import com.example.demo.exception.CommandException;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.entity.Image;
import com.example.demo.model.entity.Rating;
import com.example.demo.model.entity.User;
import com.example.demo.service.ImageService;
import com.example.demo.service.RatingService;
import com.example.demo.service.impl.ImageServiceImpl;
import com.example.demo.service.impl.RatingServiceImpl;
import com.example.demo.util.validator.FormValidator;
import com.example.demo.util.validator.impl.NewRatingFormValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.demo.controller.navigation.AttributeParameterHolder.*;
import static com.example.demo.controller.navigation.PageNavigation.IMAGE_DETAILS;
import static com.example.demo.controller.navigation.Router.Type.FORWARD;
import static com.example.demo.model.entity.enumerator.RatingStatus.NEW;


public class FinishCreateNewRatingCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        List<Rating> ratings;
        String page = IMAGE_DETAILS;
        HttpSession session = request.getSession();
        ImageService imageService = ImageServiceImpl.getInstance();
        RatingService ratingService = RatingServiceImpl.getInstance();

        FormValidator validator = NewRatingFormValidator.getInstance();
        Map<String, String[]> requestParameters = request.getParameterMap();
        List<String> validationFeedback = validator.validateForm(requestParameters);

        if (validationFeedback.isEmpty()) {
            try {
                User user = (User) session.getAttribute(SESSION_ATTRIBUTE_USER);
                Long imageId = Long.parseLong(request.getParameter(PARAMETER_IMAGE_ID));
                double rating = Double.parseDouble(request.getParameter(PARAMETER_RATING_RATING));
                String review = request.getParameter(PARAMETER_RATING_REVIEW);
                Optional<Image> optionalImage = imageService.findById(imageId);
                optionalImage.ifPresent(image -> request.setAttribute(PARAMETER_IMAGE_ID, image));
                com.example.demo.model.entity.dto.Rating ratingdto = new com.example.demo.model.entity.dto.Rating(
                        0L, user.getId(), imageId, rating, review, NEW, Timestamp.valueOf(LocalDateTime.now()));
                ratingService.create(ratingdto);
                ratings = ratingService.findAllForImage(imageId);
                request.setAttribute(SESSION_ATTRIBUTE_ALL_IMAGE_DETAILS, ratings);
                request.setAttribute(PARAMETER_IMAGE_IS_ORDERED, true);
                request.setAttribute(PARAMETER_IMAGE_IS_RATED, true);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        } else {
            request.setAttribute(REQUEST_ATTRIBUTE_ERROR_MESSAGE, validationFeedback.get(0));
        }
        session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, page);
        request.setAttribute(PARAMETER_CATALOG_CURRENT, 1);
        return new Router(page, FORWARD);
    }
}
