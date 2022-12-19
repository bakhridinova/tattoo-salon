package com.example.demo.controller.command.impl.gotopage;


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

import static com.example.demo.controller.navigation.AttributeParameterHolder.*;
import static com.example.demo.controller.navigation.PageNavigation.ALL_TASKS;
import static com.example.demo.controller.navigation.Router.Type.FORWARD;
import static com.example.demo.model.entity.enumerator.ImageStatus.APPROVED;

public class GoToAllTasksCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page;
        HttpSession session = request.getSession();
        ImageService imageService = ImageServiceImpl.getInstance();
        String localisation = request.getParameter(PARAMETER_LANG);

        try {
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
            throw new CommandException(e);
        }
        if (localisation != null) {
            session.setAttribute(SESSION_ATTRIBUTE_PARAMETER_LOCALIZATION, localisation);
        }
        session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, page);
        return new Router(page, FORWARD);
    }
}
