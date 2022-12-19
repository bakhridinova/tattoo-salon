import com.example.demo.exception.DaoException;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.dao.dao.ImageDao;
import com.example.demo.model.dao.dao.RatingDao;
import com.example.demo.model.dao.dao.UserDao;
import com.example.demo.model.dao.impl.ImageDaoImpl;
import com.example.demo.model.dao.impl.RatingDaoImpl;
import com.example.demo.model.dao.impl.UserDaoImpl;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.dto.Rating;
import com.example.demo.service.RatingService;
import com.example.demo.service.impl.RatingServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static com.example.demo.model.entity.enumerator.RatingStatus.NEW;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RatingServiceTest {
    private Rating rating;
    @Mock
    private UserDao userDao;
    @Mock
    private ImageDao imageDao;
    @Mock
    private RatingDao ratingDao;

    @InjectMocks
    private RatingService ratingService;

    @Before
    public void setup() {
        userDao = mock(UserDaoImpl.class);
        imageDao = mock(ImageDaoImpl.class);
        ratingDao = mock(RatingDaoImpl.class);
        ratingService = new RatingServiceImpl(ratingDao, imageDao, userDao);
        rating = new Rating(0L, 0L, 0L, 0.0, "review", NEW, Timestamp.valueOf("1111-11-11 00:00:00"));
    }

    @Test
    public void shouldNotCreateAnyRatingWhenDaoExceptionIsThrown() throws DaoException {
        doThrow(DaoException.class).when(ratingDao).insert(rating);
        assertThrows(ServiceException.class, () -> {ratingService.create(rating);});
    }


    @Test
    public void shouldCreateRatingWhenNothingIsThrown() throws DaoException, ServiceException {
        doNothing().when(ratingDao).insert(rating);
        assertEquals(Optional.of(rating), ratingService.create(rating));
    }

    @Test
    public void shouldNotReturnAnyRatingListWhenDaoExceptionIsThrown() throws DaoException {
        when(ratingDao.findAll()).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> {ratingService.findAllRatings();});
    }

    @Test
    public void shouldReturnRatingListWhenNothingIsThrown() throws DaoException, ServiceException {
        List<Rating> ratings = List.of(rating);
        when(ratingDao.findAll()).thenReturn(ratings);
        assertEquals(ratings, ratingService.findAllRatings());
    }

    @Test
    public void shouldNotReturnAnyRatingWhenDaoExceptionIsThrown() throws DaoException {
        when(ratingDao.find(0L)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> {ratingService.findById(0L);});
    }

    @Test
    public void shouldNotReturnAnyRatingWhenRatingIsNotFoundAndDaoExceptionIsThrown() throws DaoException, ServiceException {
        when(ratingDao.find(0L)).thenReturn(Optional.empty());
        assertEquals(Optional.empty(), ratingService.findById(0L));
    }

    @Test
    public void shouldReturnRatingWhenRatingIsFoundAndNothingIsThrown() throws DaoException, ServiceException {
        when(ratingDao.find(0L)).thenReturn(Optional.of(rating));
        assertEquals(Optional.of(rating), ratingService.findById(0L));
    }


    @Test
    public void shouldNotEditRatingWhenDaoExceptionIsThrown() throws DaoException {
        doThrow(DaoException.class).when(ratingDao).update(rating);
        assertThrows(ServiceException.class, () -> {ratingService.edit(rating); });
    }

    @Test
    public void shouldEditRatingWhenNothingIsThrown() throws DaoException, ServiceException {
        doNothing().when(ratingDao).update(rating);
        assertEquals(Optional.of(rating), ratingService.edit(rating));
    }

    @Test
    public void shouldNotReturnAnyImageRatingListWhenDaoExceptionIsThrown() throws DaoException {
        when(ratingDao.findAll()).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> {ratingService.findAllForImage(0L);});
    }

    @Test
    public void shouldNotReturnAnyUserRatingListWhenDaoExceptionIsThrown() throws DaoException {
        when(ratingDao.findAll()).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> {ratingService.findAllUserRatings(new User());});
    }
}
