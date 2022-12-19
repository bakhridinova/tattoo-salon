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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.demo.controller.navigation.AttributeParameterHolder.*;
import static com.example.demo.controller.navigation.PageNavigation.CATALOG;
import static com.example.demo.controller.navigation.Router.Type.FORWARD;

public class GoToImageCatalogCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        ImageService imageService = ImageServiceImpl.getInstance();
        HttpSession session = request.getSession();
        List<Image> images;
        String page;
        try {
            String localisation = request.getParameter(PARAMETER_LANG);
            String sort = request.getParameter(PARAMETER_CATALOG_SORT);
            String order = request.getParameter(PARAMETER_CATALOG_SORT_ORDER);
            String pagination = request.getParameter(PARAMETER_CATALOG_PAGINATION);
            images = imageService.findAllImages();
            int size = images.size();
            session.setAttribute(PARAMETER_CATALOG_SIZE, size);
            if (sort != null) {
                if (sort.equals("rating")) {
                    images.sort(new SortCatalogByRatingComparator());

                } else if (sort.equals("price")) {
                    images.sort(new SortCatalogByPriceComparator());
                }

                if (order != null && order.equals("desc")) {
                    Collections.reverse(images);
                } else {
                    order = "asc";
                }

                request.setAttribute(PARAMETER_CATALOG_SORT, sort);
                request.setAttribute(PARAMETER_CATALOG_SORT_ORDER, order);
            }

            int index;
            if (pagination != null) {
                index = Integer.parseInt(pagination);
            } else {
                index = 1;
            }

            if (localisation != null) {
                session.setAttribute(SESSION_ATTRIBUTE_PARAMETER_LOCALIZATION, localisation);
            }
            images = images.subList((index - 1) * 20, Math.min(index * 20, size));
            request.setAttribute(PARAMETER_CATALOG_CURRENT, index);
            page = CATALOG;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        session.setAttribute(SESSION_ATTRIBUTE_CATALOG, images);
        session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, page);
        return new Router(page, FORWARD);
    }

    private static class SortCatalogByRatingComparator implements Comparator<Image> {
        @Override
        public int compare(Image image1, Image image2) {
            return image1.getRating().compareTo(image2.getRating());
        }
    }

    private static class SortCatalogByPriceComparator implements Comparator<Image> {
        @Override
        public int compare(Image image1, Image image2) {
            return image1.getPrice().compareTo(image2.getPrice());
        }
    }
}
