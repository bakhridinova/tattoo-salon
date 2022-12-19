import com.example.demo.exception.DaoException;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.dao.dao.ImageDao;
import com.example.demo.model.dao.dao.OrderDao;
import com.example.demo.model.dao.impl.ImageDaoImpl;
import com.example.demo.model.dao.impl.OrderDaoImpl;
import com.example.demo.model.entity.Image;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.dto.Order;
import com.example.demo.service.OrderService;
import com.example.demo.service.impl.OrderServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static com.example.demo.model.entity.enumerator.OrderStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class OrderServiceTest {
    private Order order;
    @Mock
    private OrderDao orderDao;
    @Mock
    private ImageDao imageDao;
    @InjectMocks
    private OrderService orderService;

    @Before
    public void setup() {
        orderDao = mock(OrderDaoImpl.class);
        imageDao = mock(ImageDaoImpl.class);
        orderService = new OrderServiceImpl(orderDao, imageDao);
        order = new Order(0L, 0L, 0L, PENDING, false, 0.0, Timestamp.valueOf("1111-11-11 00:00:00"));
    }

    @Test
    public void shouldNotCreateAnyOrderWhenDaoExceptionIsThrown() throws DaoException {
        doThrow(DaoException.class).when(orderDao).insert(order);
        assertThrows(ServiceException.class, () -> {orderService.create(order);});
    }


    @Test
    public void shouldCreateOrderWhenNothingIsThrown() throws DaoException, ServiceException {
        doNothing().when(orderDao).insert(order);
        assertEquals(Optional.of(order), orderService.create(order));
    }

    @Test
    public void shouldNotProceedOrderWhenDaoExceptionIsThrown() throws DaoException {
        doThrow(DaoException.class).when(orderDao).insert(order);
        order.setStatus(COMPLETED);
        assertThrows(ServiceException.class, () -> {orderService.create(order);});
        order.setStatus(PENDING);
    }


    @Test
    public void shouldProceedOrderWhenNothingIsThrown() throws DaoException, ServiceException {
        doNothing().when(orderDao).insert(order);
        order.setStatus(COMPLETED);
        assertEquals(Optional.of(order), orderService.create(order));
        order.setStatus(PENDING);
    }

    @Test
    public void shouldNotCancelOrderWhenDaoExceptionIsThrown() throws DaoException {
        doThrow(DaoException.class).when(orderDao).insert(order);
        order.setStatus(CANCELLED);
        assertThrows(ServiceException.class, () -> {orderService.create(order);});
        order.setStatus(PENDING);
    }


    @Test
    public void shouldCancelOrderWhenNothingIsThrown() throws DaoException, ServiceException {
        doNothing().when(orderDao).insert(order);
        order.setStatus(CANCELLED);
        assertEquals(Optional.of(order), orderService.create(order));
        order.setStatus(PENDING);
    }
    @Test
    public void shouldNotReturnAnyOrderListWhenDaoExceptionIsThrown() throws DaoException {
        when(orderDao.findAll()).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> {orderService.findAllOrders();});
    }

    @Test
    public void shouldReturnOrderListWhenNothingIsThrown() throws DaoException, ServiceException {
        List<Order> orders = List.of(order);
        when(orderDao.findAll()).thenReturn(orders);
        assertEquals(orders, orderService.findAllOrders());
    }

    @Test
    public void shouldNotReturnAnyImageOrderListWhenDaoExceptionIsThrown() throws DaoException {
        when(orderDao.findAll()).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> {orderService.findAllForImage(new Image());});
    }

    @Test
    public void shouldNotReturnAnyUserOrderListWhenDaoExceptionIsThrown() throws DaoException {
        when(orderDao.findAll()).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> {orderService.findAllUserOrders(new User());});
    }
}
