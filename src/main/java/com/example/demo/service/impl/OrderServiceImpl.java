package com.example.demo.service.impl;

import com.example.demo.exception.DaoException;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.dao.dao.ImageDao;
import com.example.demo.model.dao.dao.OrderDao;
import com.example.demo.model.dao.impl.ImageDaoImpl;
import com.example.demo.model.dao.impl.OrderDaoImpl;
import com.example.demo.model.entity.Image;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.dto.Order;
import com.example.demo.service.OrderService;
import com.example.demo.util.OrderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo.model.entity.enumerator.OrderStatus.CANCELLED;
import static com.example.demo.model.entity.enumerator.OrderStatus.PENDING;


public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LogManager.getLogger();
    private static final OrderService instance = new OrderServiceImpl();
    private final OrderDao orderDao;
    private final ImageDao imageDao;
    private OrderServiceImpl() {
        orderDao = OrderDaoImpl.getInstance();
        imageDao = ImageDaoImpl.getInstance();
    }
    public OrderServiceImpl(OrderDao orderDao, ImageDao imageDao) { // for testing purposes
        this.orderDao = orderDao;
        this.imageDao = imageDao;
    }
    public static OrderService getInstance() {
        return instance;
    }

    @Override
    public Optional<Order> create(Order order) throws ServiceException {
        Optional<Order> optionalOrder;
        try {
            orderDao.insert(order);
            optionalOrder = Optional.of(order);
        } catch (DaoException e) {
            logger.error("failed to create new order, cause: {}", e.getMessage());
            throw new ServiceException(e);
        }
        return optionalOrder;
    }

    @Override
    public List<Order> findAllForImage(Image image) throws ServiceException {
        List<Order> imageOrders = new ArrayList<>();
        try {
            List<Order> allOrders = orderDao.findAll();
            for (Order order: allOrders) {
                if (order.getImageId().equals(image.getId())) {
                    imageOrders.add(order);
                }
            }
        } catch (DaoException e) {
            logger.error("failed to find imageOrders");
            throw new ServiceException(e);
        }
        return imageOrders;
    }

    @Override
    public List<Order> findAllOrders() throws ServiceException {
        List<Order> orders;
        try {
            orders = orderDao.findAll();
        } catch (DaoException e) {
            logger.error("failed to find orders");
            throw new ServiceException(e);
        }
        return orders;
    }

    @Override
    public void proceed(long id) throws ServiceException {
        try {
            Optional<Order> optionalOrder = orderDao.find(id);
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                order.setStatus(PENDING);
                orderDao.update(order);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void cancel(Long id) throws ServiceException {
        try {
            Optional<Order> optionalOrder = orderDao.find(id);
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                order.setStatus(CANCELLED);
                orderDao.update(order);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<com.example.demo.model.entity.Order> findAllUserOrders(User user) throws ServiceException {
        List<com.example.demo.model.entity.Order> userOrders = new ArrayList<>();
        Long id = user.getId();
        try {
            List<com.example.demo.model.entity.dto.Order> allOrders = orderDao.findAll();
            for (com.example.demo.model.entity.dto.Order order: allOrders) {
                if (order.getUserId().equals(id)) {
                    Optional<Image> optionalImage = imageDao.find(order.getImageId());
                    optionalImage.ifPresent(image -> userOrders.add(OrderUtil.dtoToOrder(order, user, image)));
                }
            }
        } catch (DaoException e) {
            logger.error("failed to find orders of user with id: {}, cause: {}", user.getId(), e.getMessage());
            throw new ServiceException(e);
        }
        return userOrders;
    }
}
