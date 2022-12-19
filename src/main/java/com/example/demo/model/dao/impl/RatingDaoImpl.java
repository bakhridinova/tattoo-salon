package com.example.demo.model.dao.impl;


import com.example.demo.exception.DaoException;
import com.example.demo.model.connection.ConnectionPool;
import com.example.demo.model.dao.dao.RatingDao;
import com.example.demo.model.entity.dto.Rating;
import com.example.demo.model.entity.enumerator.RatingStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo.model.dao.DataBaseInfo.*;

public class RatingDaoImpl implements RatingDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String SELECT_RATING_BY_ID =
            "select * from ratings where id = ?;";
    private static final String SELECT_ALL_RATINGS =
            "select * from ratings join users u on u.id = ratings.user_id join images i on i.id = ratings.image_id order by ratings.created_at desc;";
    private static final String INSERT_INTO_RATINGS =
            "insert into ratings (user_id, image_id, rating, review, status) values (?, ?, ?, ?, ?);";
    private static final String UPDATE_RATINGS =
            "update ratings set user_id = ?, image_id = ?, rating = ?, review = ?, status = ? where id = ?";

    private static final RatingDao instance = new RatingDaoImpl();

    public static RatingDao getInstance() {
        return instance;
    }

    private RatingDaoImpl() {
    }

    @Override
    public Optional<Rating> find(Long id) throws DaoException {
        Optional<Rating> rating = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_RATING_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                rating = Optional.of(getAllRatingParametersFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            logger.error("failed to find rating by id: {} : {}", id, e.getMessage());
            throw new DaoException("Failed to find rating by id: " + id, e);
        }
        return rating;
    }

    @Override
    public List<Rating> findAll() throws DaoException {
        List<Rating> ratings = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_RATINGS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ratings.add(getAllRatingParametersFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            logger.error("failed to find ratings {}", e.getMessage());
            throw new DaoException("failed to find ratings", e);
        }
        return ratings;
    }

    @Override
    public void update(Rating rating) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_RATINGS)) {
            setAllRatingParametersToPreparedStatement(preparedStatement, rating);
            preparedStatement.setLong(6, rating.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("failed to update rating: {}", e.getMessage());
            throw new DaoException("failed to update rating: ", e);
        }
    }

    @Override
    public void insert(Rating rating) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_RATINGS)) {
            setAllRatingParametersToPreparedStatement(preparedStatement, rating);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("failed to insert new rating: {}", e.getMessage());
            throw new DaoException("failed to delete new image: ", e);
        }
    }

    @Override
    public void delete(Rating rating) throws DaoException {
    }

    private Rating getAllRatingParametersFromResultSet(ResultSet resultSet) throws SQLException {
        return new Rating(
                resultSet.getLong(RATINGS_TABLE_PK_COLUMN),
                resultSet.getLong(RATINGS_TABLE_FK_USER_ID_COLUMN),
                resultSet.getLong(RATINGS_TABLE_FK_IMAGE_ID_COLUMN),

                resultSet.getShort(RATINGS_TABLE_RATING_COLUMN),
                resultSet.getString(RATINGS_TABLE_REVIEW_COLUMN),
                RatingStatus.valueOf(resultSet.getString(RATINGS_TABLE_STATUS_COLUMN).toUpperCase()),
                resultSet.getTimestamp(RATINGS_TABLE_CREATED_AT_COLUMN));
    }

    private static void setAllRatingParametersToPreparedStatement(PreparedStatement preparedStatement, Rating rating) throws SQLException {
        preparedStatement.setLong(1, rating.getUserId());
        preparedStatement.setLong(2, rating.getImageId());

        preparedStatement.setDouble(3, rating.getRating());
        preparedStatement.setString(4, rating.getReview());
        preparedStatement.setString(5, rating.getStatus().toString().toLowerCase());
    }
}
