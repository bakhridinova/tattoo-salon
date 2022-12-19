package com.example.demo.util;


import com.example.demo.model.entity.Image;
import com.example.demo.model.entity.Order;
import com.example.demo.model.entity.User;

public class OrderUtil {

    public static Order dtoToOrder(com.example.demo.model.entity.dto.Order order, User user, Image image) {
        return new Order(
                order.getId(),
                user,
                image,
                order.getStatus(),
                order.isWithDiscount(),
                order.getAmount(),
                order.getCreatedAt());
    }

    public static com.example.demo.model.entity.dto.Order orderToDto(Order order) {
        return new com.example.demo.model.entity.dto.Order(
                order.getId(),
                order.getUser().getId(),
                order.getImage().getId(),
                order.getStatus(),
                order.isWithDiscount(),
                order.getAmount(),
                order.getCreatedAt());
    }
}
