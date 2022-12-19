package com.example.demo.service.impl;

import com.example.demo.exception.DaoException;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.dao.dao.ImageDao;
import com.example.demo.model.dao.impl.ImageDaoImpl;
import com.example.demo.model.entity.Image;
import com.example.demo.model.entity.User;
import com.example.demo.service.ImageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ImageServiceImpl implements ImageService {
    private static final Logger logger = LogManager.getLogger();
    private static final ImageServiceImpl instance = new ImageServiceImpl();
    private final ImageDao imageDao;
    private ImageServiceImpl() {
        imageDao = ImageDaoImpl.getInstance();
    }
    public ImageServiceImpl(ImageDao imageDao) { // for testing purposes
        this.imageDao = imageDao;
    }
    public static ImageServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Image> findAllImages() throws ServiceException {
        List<Image> images;
        List<String> urls = new ArrayList<>();
        try {
            images = imageDao.findAll();
        } catch (DaoException e) {
            logger.error("failed to find images, cause: {}", e.getMessage());
            throw new ServiceException(e);
        }
        return images;
    }

    @Override
    public Optional<Image> create(Image image) throws ServiceException {
        Optional<Image> optionalImage;
        try {
            imageDao.insert(image);
            optionalImage = Optional.of(image);
        } catch (DaoException e) {
            logger.error("failed to create new image, cause: {}", e.getMessage());
            throw new ServiceException(e);
        }
        return optionalImage;
    }

    @Override
    public Optional<Image> edit(Image image) throws ServiceException {
        Optional<Image> optionalImage;
        try {
            imageDao.update(image);
            optionalImage = Optional.of(image);
        } catch (DaoException e) {
            logger.error("failed to edit image, cause: {}", e.getMessage());
            throw new ServiceException(e);
        }
        return optionalImage;
    }

    @Override
    public Optional<Image> findById(Long id) throws ServiceException {
        Optional<Image> image;
        try {
            image = imageDao.find(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return image;
    }

    @Override
    public List<Image> findAllUserImages(User user) throws ServiceException {
        List<Image> userImages = new ArrayList<>();
        Long id = user.getId();
        try {
            List<Image> allImages = imageDao.findAll();
            for (Image image: allImages) {
                if (image.getUserId().equals(id)) {
                    userImages.add(image);
                }
            }
        } catch (DaoException e) {
            logger.error("failed to find images of user with id: {}, cause: {}", user.getId(), e.getMessage());
            throw new ServiceException(e);
        }
        return userImages;
    }
}