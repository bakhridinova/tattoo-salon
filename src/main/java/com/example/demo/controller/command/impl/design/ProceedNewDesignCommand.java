package com.example.demo.controller.command.impl.design;


import com.example.demo.controller.command.Command;
import com.example.demo.controller.navigation.Router;
import com.example.demo.exception.CommandException;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.entity.Image;
import com.example.demo.service.ImageService;
import com.example.demo.service.impl.ImageServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo.controller.navigation.AttributeParameterHolder.*;
import static com.example.demo.controller.navigation.PageNavigation.ALL_TASKS;
import static com.example.demo.controller.navigation.Router.Type.FORWARD;
import static com.example.demo.model.entity.enumerator.ImageStatus.APPROVED;

public class ProceedNewDesignCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page;
        HttpSession session = request.getSession();
        ImageService imageService = ImageServiceImpl.getInstance();
        try {
            double price = Double.parseDouble(request.getParameter(PARAMETER_IMAGE_PRICE));
            Long imageId = Long.parseLong(request.getParameter(PARAMETER_IMAGE_ID));
            Optional<Image> optionalImage = imageService.findById(imageId);
            if (optionalImage.isPresent()) {
                Image image = optionalImage.get();
                image.setPrice(price);
                image.setStatus(APPROVED);
                imageService.edit(image);
            }
            List<Image> imagesToProceed = new ArrayList<>();
            List<Image> allImages = imageService.findAllImages();
            for (Image image: allImages) {
                if (!image.getStatus().equals(APPROVED)) {
                    imagesToProceed.add(image);
                }
            }
            session.setAttribute(SESSION_ATTRIBUTE_ALL_TASKS, imagesToProceed);
            page = ALL_TASKS;
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

        return new Router(page, FORWARD);
    }
}
