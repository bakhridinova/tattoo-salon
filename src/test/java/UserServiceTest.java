import com.example.demo.exception.DaoException;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.dao.dao.ImageDao;
import com.example.demo.model.dao.dao.OrderDao;
import com.example.demo.model.dao.dao.RatingDao;
import com.example.demo.model.dao.dao.UserDao;
import com.example.demo.model.dao.impl.ImageDaoImpl;
import com.example.demo.model.dao.impl.OrderDaoImpl;
import com.example.demo.model.dao.impl.RatingDaoImpl;
import com.example.demo.model.dao.impl.UserDaoImpl;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.enumerator.UserGender;
import com.example.demo.model.entity.enumerator.UserRole;
import com.example.demo.model.entity.enumerator.UserStatus;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.util.encoder.PasswordEncoder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest{
    private User user;
    @Mock
    private UserDao userDao;
    @Mock
    private OrderDao orderDao;
    @Mock
    private ImageDao imageDao;
    @Mock
    private RatingDao ratingDao;
    private PasswordEncoder encoder;

    @InjectMocks
    private UserService userService;

    @Before
    public void setup() {
        userDao = mock(UserDaoImpl.class);
        orderDao = mock(OrderDaoImpl.class);
        imageDao = mock(ImageDaoImpl.class);
        ratingDao = mock(RatingDaoImpl.class);
        encoder = PasswordEncoder.getInstance();
        userService = new UserServiceImpl(userDao, orderDao, imageDao, ratingDao, encoder);
        user = new User(0L, UserRole.GUEST, UserStatus.ACTIVE, "username", "password", UserGender.OTHER,
                Timestamp.valueOf("1111-11-11 00:00:00"), "full name", "email address", "contact number");
    }

    @Test
    public void shouldNotDeleteWhenDaoExceptionIsThrown() throws NoSuchAlgorithmException, DaoException {
        user.setPassword(encoder.encode(user.getPassword()));
        doThrow(DaoException.class).when(userDao).delete(user);
        assertThrows(ServiceException.class, () -> {userService.delete(user); });
    }

    @Test
    public void shouldNotEditUserWhenDaoExceptionIsThrown() throws NoSuchAlgorithmException, DaoException {
        user.setPassword(encoder.encode(user.getPassword()));
        doThrow(DaoException.class).when(userDao).update(user);
        assertThrows(ServiceException.class, () -> {userService.edit(user); });
    }

    @Test
    public void shouldEditUserWhenNothingIsThrown() throws DaoException, ServiceException, NoSuchAlgorithmException {
        user.setPassword(encoder.encode(user.getPassword()));
        doNothing().when(userDao).update(user);
        assertEquals(Optional.of(user), userService.edit(user));
    }

    @Test
    public void shouldNotCreateAnyUserWhenDaoExceptionIsThrown() throws DaoException, NoSuchAlgorithmException {
        user.setPassword(encoder.encode(user.getPassword()));
        doThrow(DaoException.class).when(userDao).insert(user);
        assertThrows(ServiceException.class, () -> {userService.create(user);});
    }


    @Test
    public void shouldCreateUserWhenNothingIsThrown() throws DaoException, ServiceException, NoSuchAlgorithmException {
        user.setPassword(encoder.encode(user.getPassword()));
        doNothing().when(userDao).insert(user);
        assertEquals(Optional.of(user), userService.create(user));
    }

    @Test
    public void shouldNotReturnAnyBooleanWhenDaoExceptionIsThrown() throws DaoException {
        String someUsername = "some_username";
        when(userDao.findAllUsernames()).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> {userService.usernameExists(someUsername);});
    }

    @Test
    public void shouldReturnTrueWhenListContainsUsername() throws DaoException, ServiceException {
        List<String> usernames = List.of("some_username");
        String username = "some_username";
        when(userDao.findAllUsernames()).thenReturn(usernames);
        assertTrue(userService.usernameExists(username));
    }

    @Test
    public void shouldReturnFalseWhenListDoesNotContainUsername() throws DaoException, ServiceException {
        List<String> usernames = List.of("other_username");
        String username = "some_username";
        when(userDao.findAllUsernames()).thenReturn(usernames);
        assertFalse(userService.usernameExists(username));
    }

    @Test
    public void shouldNotAuthenticateWhenDaoExceptionIsThrown() throws NoSuchAlgorithmException, DaoException {
        String someUsername = "some_username";
        String somePassword = "some_password";
        String encodedPassword = encoder.encode(somePassword);
        when(userDao.authenticate(someUsername, encodedPassword)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> {userService.authenticate(someUsername, somePassword); });
    }

    @Test
    public void shouldNotAuthenticateWhenUsernameAndPasswordAreInCorrect() throws DaoException, ServiceException, NoSuchAlgorithmException {
        String incorrectUsername = "incorrect_username";
        String incorrectPassword = "incorrect_password";
        String encodedPassword = encoder.encode(incorrectPassword);
        when(userDao.authenticate(incorrectUsername, encodedPassword)).thenReturn(Optional.empty());
        assertEquals(Optional.empty(), userService.authenticate(incorrectUsername, incorrectPassword));
    }

    @Test
    public void shouldAuthenticateWhenUsernameAndPasswordAreCorrect() throws DaoException, ServiceException, NoSuchAlgorithmException {
        String correctUsername = "correct_username";
        String correctPassword = "correct_password";
        String encodedPassword = encoder.encode(correctPassword);
        Optional<User> expected = Optional.of(user);
        when(userDao.authenticate(correctUsername, encodedPassword)).thenReturn(expected);
        assertEquals(expected, userService.authenticate(correctUsername, correctPassword));
    }

    @Test
    public void shouldNotReturnAnyUserListWhenDaoExceptionIsThrown() throws DaoException {
        when(userDao.findAll()).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> {userService.findAllUsers();});
    }

    @Test
    public void shouldReturnUserListWhenNothingIsThrown() throws DaoException, ServiceException {
        List<User> users = List.of(user);
        when(userDao.findAll()).thenReturn(users);
        assertEquals(users, userService.findAllUsers());
    }
}
