package com.example.demo.model.entity;

import com.example.demo.model.entity.enumerator.ImageStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Image extends Entity {
    private Long id;
    private Long userId;
    private ImageStatus status;
    private String shortDescription;
    private String longDescription;
    private Timestamp createdAt;
    private Integer orders;
    private Double rating;
    private Double price;
    private String url;

    @Override
    public String toString() {
        return "\nImage{" +
                "id: " + id +
                ", user id: " + userId +
                ", short description: " + shortDescription +
                ", long description: " + longDescription + "," +
                ", created at: " + createdAt +
                ", orders: " + orders +
                ", rating: " + rating +
                ", price: " + price +
                ", url: " + url +
                "}";
    }
}