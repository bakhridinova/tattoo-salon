package com.example.demo.util.validator;

/**
 * Pattern holder and single value validator.
 */
public final class PatternValidator {

    public static final String USER_NAME_PATTERN =
            "^[A-Za-z0-9]*$";
    public static final String PASSWORD_PATTERN =
            "^[\\p{Alnum}\\p{Punct}]{8,20}$";
    public static final String EMAIL_PATTERN =
            "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String IMAGE_URL_PATTERN =
            "https?:\\/\\/.*\\.(?:png|jpg)";
    public static final String UNLIMITED_TEXT_PATTERN =
            "^[\\p{Alnum}\\p{Punct}А-яЁё\\s]*$";

    private static final PatternValidator instance = new PatternValidator();

    private PatternValidator() {
    }

    public static PatternValidator getInstance() {
        return instance;
    }

    public boolean validUsername(String name) {
        return name.matches(USER_NAME_PATTERN);
    }

    public boolean validPassword(String password) {
        return password.matches(PASSWORD_PATTERN);
    }
    public boolean validEmailAddress(String email) {
        return email.matches(EMAIL_PATTERN);
    }

    public boolean validImageUrl(String url) {
        return url.matches(IMAGE_URL_PATTERN);
    }

    public boolean validUnlimitedText(String text) {
        return text.matches(UNLIMITED_TEXT_PATTERN);
    }
}