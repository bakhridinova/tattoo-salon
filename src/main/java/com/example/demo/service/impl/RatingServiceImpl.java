package com.example.demo.service.impl;

import com.example.demo.exception.DaoException;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.dao.dao.ImageDao;
import com.example.demo.model.dao.dao.RatingDao;
import com.example.demo.model.dao.dao.UserDao;
import com.example.demo.model.dao.impl.ImageDaoImpl;
import com.example.demo.model.dao.impl.RatingDaoImpl;
import com.example.demo.model.dao.impl.UserDaoImpl;
import com.example.demo.model.entity.Image;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.dto.Rating;
import com.example.demo.model.entity.enumerator.RatingStatus;
import com.example.demo.service.RatingService;
import com.example.demo.util.RatingUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RatingServiceImpl implements RatingService {
    private static final Logger logger = LogManager.getLogger();
    private static final RatingServiceImpl instance = new RatingServiceImpl();
    private final RatingDao ratingDao;
    private final ImageDao imageDao;
    private final UserDao userDao;
    private RatingServiceImpl() {
        ratingDao = RatingDaoImpl.getInstance();
        imageDao = ImageDaoImpl.getInstance();
        userDao = UserDaoImpl.getInstance();
    }
    public RatingServiceImpl(RatingDao ratingDao, ImageDao imageDao, UserDao userDao) { // for testing purposes
        this.ratingDao = ratingDao;
        this.imageDao = imageDao;
        this.userDao = userDao;
    }
    public static RatingService getInstance() {
        return instance;
    }

    @Override
    public Optional<Rating> create(Rating rating) throws ServiceException {
        Optional<Rating> optionalRating;
        try {
            ratingDao.insert(rating);
            optionalRating = Optional.of(rating);
        } catch (DaoException e) {
            logger.error("failed to create new rating, cause: {}", e.getMessage());
            throw new ServiceException(e);
        }
        return optionalRating;
    }

    @Override
    public List<Rating> findAllRatings() throws ServiceException {
        List<Rating> ratings;
        try {
            ratings = ratingDao.findAll();
        } catch (DaoException e) {
            logger.error("failed to find ratings, cause: {}", e.getMessage());
            throw new ServiceException(e);
        }
        return ratings;
    }

    @Override
    public Optional<Rating> findById(Long id) throws ServiceException {
        Optional<Rating> optionalRating;
        try {
            optionalRating = ratingDao.find(id);
        } catch (DaoException e) {
            logger.error("failed to find ratings by id: {}, cause: {}", id, e.getMessage());
            throw new ServiceException(e);
        }
        return optionalRating;
    }

    @Override
    public List<com.example.demo.model.entity.Rating> findAllForImage(Long id) throws ServiceException {
        List<com.example.demo.model.entity.Rating> imageRatings = new ArrayList<>();
        try {
            List<Rating> allRatings = ratingDao.findAll();
            for (Rating ratingWithUserId: allRatings) {
                com.example.demo.model.entity.Rating rating =
                        new com.example.demo.model.entity.Rating(
                                ratingWithUserId.getId(),
                                userDao.find(ratingWithUserId.getUserId()).get(),
                                imageDao.find(ratingWithUserId.getImageId()).get(),
                                ratingWithUserId.getRating(),
                                ratingWithUserId.getReview(),
                                ratingWithUserId.getStatus(),
                                ratingWithUserId.getCreatedAt()
                        );
                if (rating.getImage().getId().equals(id)) {
                    imageRatings.add(rating);
                }
            }
        } catch (DaoException e) {
            logger.error("failed to find ratings to image id: {}, cause: {}", id, e.getMessage());
            throw new ServiceException(e);
        }
        return imageRatings;
    }

    @Override
    public Optional<Rating> edit(Rating rating) throws ServiceException {
        Optional<Rating> optionalRating;
        rating.setStatus(RatingStatus.EDITED);
        try {
            ratingDao.update(rating);
            optionalRating = Optional.of(rating);
        } catch (DaoException e) {
            logger.error("failed to edit rating, cause: {}", e.getMessage());
            throw new ServiceException(e);
        }
        return optionalRating;
    }

    @Override
    public List<com.example.demo.model.entity.Rating> findAllUserRatings(User user) throws ServiceException {
        List<com.example.demo.model.entity.Rating> userRatings = new ArrayList<>();
        Long id = user.getId();
        try {
            List<com.example.demo.model.entity.dto.Rating> allRatings = ratingDao.findAll();
            for (com.example.demo.model.entity.dto.Rating rating: allRatings) {
                if (rating.getUserId().equals(id)) {
                    Optional<Image> optionalImage = imageDao.find(rating.getImageId());
                    optionalImage.ifPresent(image -> userRatings.add(RatingUtil.dtoToRating(rating, user, image)));
                }
            }
        } catch (DaoException e) {
            logger.error("failed to find ratings of user with id: {}, cause: {}", user.getId(), e.getMessage());
            throw new ServiceException(e);
        }
        return userRatings;
    }
}
