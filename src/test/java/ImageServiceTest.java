import com.example.demo.exception.DaoException;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.dao.dao.ImageDao;
import com.example.demo.model.dao.impl.ImageDaoImpl;
import com.example.demo.model.entity.Image;
import com.example.demo.model.entity.User;
import com.example.demo.service.ImageService;
import com.example.demo.service.impl.ImageServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static com.example.demo.model.entity.enumerator.ImageStatus.PENDING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

public class ImageServiceTest {
    private Image image;
    @Mock
    private ImageDao imageDao;
    @InjectMocks
    private ImageService imageService;

    @Before
    public void setup() {
        imageDao = mock(ImageDaoImpl.class);
        imageService = new ImageServiceImpl(imageDao);
        image = new Image(0L, 0L, PENDING, "short_description", "long_description", Timestamp.valueOf("1111-11-11 00:00:00"), 0, 0.0, 0.0, "url");
    }

    @Test
    public void shouldNotReturnAnyImageListWhenDaoExceptionIsThrown() throws DaoException {
        when(imageDao.findAll()).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> {imageService.findAllImages();});
    }

    @Test
    public void shouldReturnImageListWhenNothingIsThrown() throws DaoException, ServiceException {
        List<Image> images = List.of(image);
        when(imageDao.findAll()).thenReturn(images);
        assertEquals(images, imageService.findAllImages());
    }

    @Test
    public void shouldNotEditImageWhenDaoExceptionIsThrown() throws DaoException {
        doThrow(DaoException.class).when(imageDao).update(image);
        assertThrows(ServiceException.class, () -> {imageService.edit(image); });
    }

    @Test
    public void shouldEditImageWhenNothingIsThrown() throws DaoException, ServiceException {
        doNothing().when(imageDao).update(image);
        assertEquals(Optional.of(image), imageService.edit(image));
    }

    @Test
    public void shouldNotReturnAnyImageWhenDaoExceptionIsThrown() throws DaoException {
        when(imageDao.find(0L)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> {imageService.findById(0L);});
    }

    @Test
    public void shouldNotReturnAnyImageWhenImageIsNotFoundAndDaoExceptionIsThrown() throws DaoException, ServiceException {
        when(imageDao.find(0L)).thenReturn(Optional.empty());
        assertEquals(Optional.empty(), imageService.findById(0L));
    }

    @Test
    public void shouldReturnImageWhenImageIsFoundAndNothingIsThrown() throws DaoException, ServiceException {
        when(imageDao.find(0L)).thenReturn(Optional.of(image));
        assertEquals(Optional.of(image), imageService.findById(0L));
    }

    @Test
    public void shouldNotReturnAnyUserImageListWhenDaoExceptionIsThrown() throws DaoException {
        when(imageDao.findAll()).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> {imageService.findAllUserImages(new User());});
    }
}
