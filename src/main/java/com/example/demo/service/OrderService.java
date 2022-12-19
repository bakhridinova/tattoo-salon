package com.example.demo.service;

import com.example.demo.exception.ServiceException;
import com.example.demo.model.entity.Image;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.dto.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<Order> create(Order order) throws ServiceException;
    void proceed(long id) throws ServiceException;
    void cancel(Long id) throws ServiceException;
    List<Order> findAllOrders() throws ServiceException;
    List<Order> findAllForImage(Image image) throws ServiceException;
    List<com.example.demo.model.entity.Order> findAllUserOrders(User user) throws ServiceException;
}
