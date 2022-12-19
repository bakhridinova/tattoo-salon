package com.example.demo.service;

import com.example.demo.exception.ServiceException;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.dto.Rating;

import java.util.List;
import java.util.Optional;

public interface RatingService {
    Optional<Rating> create(Rating rating) throws ServiceException;
    List<Rating> findAllRatings() throws ServiceException;
    Optional<Rating> findById(Long id) throws ServiceException;
    Optional<Rating> edit(Rating rating) throws ServiceException;
    List<com.example.demo.model.entity.Rating> findAllForImage(Long id) throws ServiceException;
    List<com.example.demo.model.entity.Rating> findAllUserRatings(User user) throws ServiceException;
}
