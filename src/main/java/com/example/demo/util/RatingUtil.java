package com.example.demo.util;


import com.example.demo.model.entity.Image;
import com.example.demo.model.entity.Rating;
import com.example.demo.model.entity.User;

public class RatingUtil {
    public static Rating dtoToRating(com.example.demo.model.entity.dto.Rating rating, User user, Image image) {
        return new Rating(
                rating.getId(),
                user,
                image,
                rating.getRating(),
                rating.getReview(),
                rating.getStatus(),
                rating.getCreatedAt());
    }

    public static com.example.demo.model.entity.dto.Rating ratingToDto(Rating rating) {
        return new com.example.demo.model.entity.dto.Rating(
                rating.getId(),
                rating.getUser().getId(),
                rating.getImage().getId(),
                rating.getRating(),
                rating.getReview(),
                rating.getStatus(),
                rating.getCreatedAt());
    }
}
