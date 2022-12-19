package com.example.demo.service;

import com.example.demo.exception.ServiceException;
import com.example.demo.model.entity.Image;
import com.example.demo.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    List<Image> findAllImages() throws ServiceException;
    Optional<Image> create(Image image) throws ServiceException;
    Optional<Image> edit(Image image) throws ServiceException;
    Optional<Image> findById(Long id) throws ServiceException;
    List<Image> findAllUserImages(User user) throws ServiceException;
}
