package com.example.demo.model.dao.impl;

import com.example.demo.exception.DaoException;
import com.example.demo.model.connection.ConnectionPool;
import com.example.demo.model.dao.dao.OrderDao;
import com.example.demo.model.entity.dto.Order;
import com.example.demo.model.entity.enumerator.OrderStatus;
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

public class OrderDaoImpl implements OrderDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String SELECT_ALL_ORDERS =
            "select * from orders order by created_at desc;";
    private static final String SELECT_ORDER_BY_ID =
            "select * from orders where id = ?";
    private static final String INSERT_INTO_ORDERS =
            "insert into orders (user_id, image_id, status, with_discount, amount) values (?, ?, ?, ?, ?);";
    private static final String UPDATE_ORDER_STATUS =
            "update orders set status = ? where id = ?;";
    private static final OrderDao instance = new OrderDaoImpl();

    public static OrderDao getInstance() {
        return instance;
    }

    private OrderDaoImpl() {
    }

    @Override
    public Optional<Order> find(Long id) throws DaoException {
        Optional<Order> order = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                order = Optional.of(getAllOrderParametersFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            logger.error("failed to find order by id: {} {}", id, e.getMessage());
            throw new DaoException("failed to find order: ", e);
        }
        return order;
    }

    @Override
    public List<Order> findAll() throws DaoException {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ORDERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(getAllOrderParametersFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            logger.error("failed to find orders {}", e.getMessage());
            throw new DaoException("failed to find orders", e);
        }
        return orders;
    }

    @Override
    public void update(Order order) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER_STATUS)) {
            preparedStatement.setString(1, order.getStatus().toString().toLowerCase());
            preparedStatement.setLong(2, order.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("failed to update user: {}", e.getMessage());
            throw new DaoException("failed to update user: ", e);
        }
    }

    @Override
    public void insert(Order order) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_ORDERS)) {
            setAllOrderParametersToPreparedStatement(preparedStatement, order);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("failed to insert new order: {}", e.getMessage());
            throw new DaoException("failed to insert new order: ", e);
        }
    }

    @Override
    public void delete(Order order) throws DaoException {
    }

    private Order getAllOrderParametersFromResultSet(ResultSet resultSet) throws SQLException {
        return new Order(
                resultSet.getLong(ORDERS_TABLE_PK_COLUMN),
                resultSet.getLong(ORDERS_TABLE_FK_USER_ID_COLUMN),
                resultSet.getLong(ORDERS_TABLE_FK_IMAGE_ID_COLUMN),
                OrderStatus.valueOf(resultSet.getString(ORDERS_TABLE_STATUS_COLUMN).toUpperCase()),
                resultSet.getBoolean(ORDERS_TABLE_WITH_DISCOUNT_COLUMN),
                resultSet.getDouble(ORDERS_TABLE_AMOUNT_COLUMN),
                resultSet.getTimestamp(ORDERS_TABLE_CREATED_AT_COLUMN));
    }

    private void setAllOrderParametersToPreparedStatement(PreparedStatement preparedStatement, Order order) throws SQLException {
        preparedStatement.setLong(1, order.getUserId());
        preparedStatement.setLong(2, order.getImageId());
        preparedStatement.setString(3, order.getStatus().toString().toLowerCase());
        preparedStatement.setBoolean(4, order.isWithDiscount());
        preparedStatement.setDouble(5, order.getAmount());
    }
}
