package com.example.demo.model.entity.dto;

import com.example.demo.model.entity.Entity;
import com.example.demo.model.entity.enumerator.RatingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rating extends Entity {
    private Long id;
    private Long userId;
    private Long imageId;

    private double rating;
    private String review;
    private RatingStatus status;
    private Timestamp createdAt;

    @Override
    public String toString() {
        return "\nRating{" +
                "id: " + id +
                ", user id: " + userId +
                ", image id: " + imageId +
                ", rating: " + rating +
                ", review: " + review +
                ", status: " + status +
                ", created at: " + createdAt +
                '}';
    }
}
