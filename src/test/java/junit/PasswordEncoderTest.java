package junit;

import com.example.demo.util.encoder.PasswordEncoder;
import org.testng.annotations.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PasswordEncoderTest {
    PasswordEncoder encoder = PasswordEncoder.getInstance();
    public static String PASSWORD = "&W@J5W9?eWusPapHib=E";
    @Test
    public void testPasswordEncoding() throws NoSuchAlgorithmException {
        String expected = "2e9ca036b891fd9cc2d5b40682896a0133ba67c5";
        String actual = encoder.encode(PASSWORD);
        assertEquals(expected, actual);
    }
}
