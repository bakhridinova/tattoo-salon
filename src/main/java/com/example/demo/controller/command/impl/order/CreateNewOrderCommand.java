package com.example.demo.controller.command.impl.order;

import com.example.demo.controller.command.Command;
import com.example.demo.controller.navigation.Router;
import com.example.demo.exception.CommandException;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.entity.Image;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.dto.Order;
import com.example.demo.service.ImageService;
import com.example.demo.service.OrderService;
import com.example.demo.service.impl.ImageServiceImpl;
import com.example.demo.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.example.demo.controller.navigation.AttributeParameterHolder.*;
import static com.example.demo.controller.navigation.PageNavigation.USER_ORDERS;
import static com.example.demo.controller.navigation.Router.Type.FORWARD;
import static com.example.demo.model.entity.enumerator.OrderStatus.AWAITING_PAYMENT;


public class CreateNewOrderCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        OrderService orderService = OrderServiceImpl.getInstance();
        ImageService imageService = ImageServiceImpl.getInstance();
        HttpSession session = request.getSession();
        List<com.example.demo.model.entity.Order> orders;
        String page;
        try {
            User user = (User) session.getAttribute(SESSION_ATTRIBUTE_USER);
            Long imageId = Long.parseLong(request.getParameter(PARAMETER_IMAGE_ID));
            Optional<Image> optionalImage = imageService.findById(imageId);
            if (optionalImage.isPresent()) {
                Image image = optionalImage.get();
                double amount = image.getPrice();
                boolean wihDiscount = image.getUserId().equals(user.getId());
                Order order = new Order(0L, user.getId(), imageId, AWAITING_PAYMENT, wihDiscount, wihDiscount ? amount * 0.85 : amount, Timestamp.valueOf(LocalDateTime.now()));
                orderService.create(order);
            }

            orders = orderService.findAllUserOrders(user);
            page = USER_ORDERS;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        session.setAttribute(SESSION_ATTRIBUTE_ALL_USER_ORDERS, orders);
        session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, page);
        return new Router(page, FORWARD);
    }
}
