package junit;

import com.example.demo.util.validator.PatternValidator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ValidationFormTest {
    PatternValidator patternValidator = PatternValidator.getInstance();

    @DataProvider(name = "usernameProvider")
    public Object[][] createUsername() {
        return new Object[][] {
                {"plain text", false},
                {"textwithspecialchar*", false},
                {"textWithNumber3", true}
        };
    }

    @DataProvider(name = "passwordProvider")
    public Object[][] createPassword() {
        return new Object[][] {
                {"plain text", false},
                {"S&dLEqR85&0@", true}
        };
    }

    @DataProvider(name = "emailAddressProvider")
    public Object[][] createEmailAddress() {
        return new Object[][] {
                {"plain text", false},
                {"@example.com", false},
                {"email.example.com", false},
                {"email@example", false},
                {"email@example.com", true},
                {"firstname.lastname@example.com", true},
                {"email@subdomain.example.com", true}
        };
    }

    @DataProvider(name = "imageUrlProvider")
    public Object[][] createImageUrl() {
        return new Object[][] {
                {"plain text", false},
                {"https://sometext", false},
                {"https://sometext.png", true}
        };
    }

    @Test(dataProvider = "usernameProvider")
    public void testUsernameValidation(String username, boolean expected) {
        boolean actual = patternValidator.validUsername(username);
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "passwordProvider")
    public void testPasswordValidation(String password, boolean expected) {
        boolean actual = patternValidator.validPassword(password);
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "emailAddressProvider")
    public void testEmailAddressValidation(String emailAddress, boolean expected) {
        boolean actual = patternValidator.validEmailAddress(emailAddress);
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "imageUrlProvider")
    public void testImageUrlValidation(String url, boolean expected) {
        boolean actual = patternValidator.validImageUrl(url);
        assertEquals(actual, expected);
    }
}
