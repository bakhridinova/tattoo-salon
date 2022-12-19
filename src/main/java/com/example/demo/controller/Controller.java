package com.example.demo.controller;

import com.example.demo.controller.command.Command;
import com.example.demo.controller.command.CommandType;
import com.example.demo.controller.navigation.Router;
import com.example.demo.exception.CommandException;
import com.example.demo.model.connection.ConnectionPool;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.example.demo.controller.navigation.AttributeParameterHolder.PARAMETER_COMMAND;
import static com.example.demo.controller.navigation.AttributeParameterHolder.REQUEST_ATTRIBUTE_ERROR_MESSAGE;
import static com.example.demo.controller.navigation.PageNavigation.ERROR_500;
import static com.example.demo.controller.navigation.Router.Type.REDIRECT;

@WebServlet(name = "helloServlet", urlPatterns ={"/home"})
public class Controller extends HttpServlet {
    static Logger logger = LogManager.getLogger();

    public void init() {
        logger.info("---------> servletInit: " + this.getServletInfo());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    public void destroy() {
        ConnectionPool.getInstance().destroyPool();
        logger.info("---------> servletDestroy: " + this.getServletName());
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String commandStr = request.getParameter(PARAMETER_COMMAND);
        Command command = CommandType.define(commandStr);
        String page;
        try {
            Router router = command.execute(request);
            page = router.getPage();
            if (router.getType().equals(REDIRECT)) {
                response.sendRedirect(page);
            } else {
                request.getRequestDispatcher(page).forward(request, response);
            }
        } catch (CommandException | IOException | ServletException e) {
            // response.sendError(500); // 1
            // throw new ServletException(e); // 2
            request.setAttribute(REQUEST_ATTRIBUTE_ERROR_MESSAGE, e.getCause()); // 3
            request.getRequestDispatcher(ERROR_500).forward(request, response); // 3
        }
    }
}
