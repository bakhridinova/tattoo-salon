package com.example.demo.controller.command.impl.order;


import com.example.demo.controller.command.Command;
import com.example.demo.controller.navigation.Router;
import com.example.demo.exception.CommandException;
import com.example.demo.exception.ServiceException;
import com.example.demo.service.OrderService;
import com.example.demo.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import static com.example.demo.controller.navigation.AttributeParameterHolder.PARAMETER_CATALOG_ORDER_ID;
import static com.example.demo.controller.navigation.PageNavigation.USER_ORDERS;
import static com.example.demo.controller.navigation.Router.Type.FORWARD;

public class CancelOrderCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        OrderService orderService = OrderServiceImpl.getInstance();
        String page;
        try {
            long id = Long.parseLong(request.getParameter(PARAMETER_CATALOG_ORDER_ID));
            orderService.cancel(id);
            page = USER_ORDERS;
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return new Router(page, FORWARD);
    }
}
