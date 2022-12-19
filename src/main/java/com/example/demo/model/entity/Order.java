package com.example.demo.model.entity;

import com.example.demo.model.entity.enumerator.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order extends Entity {
    private Long id;
    private User user;
    private Image image;

    private OrderStatus status;
    private boolean withDiscount;// if true 15% off

    private Double amount;
    private Timestamp createdAt;

    @Override
    public String toString() {
        return "\nOrder{" +
                "id: " + id +
                ", user id: " + user.getId() +
                ", image id: " + image.getId() +
                ", status: " + status +
                ", with discount: " + withDiscount +
                ", amount: " + amount +
                ", created at: " + createdAt +
                '}';
    }
}
