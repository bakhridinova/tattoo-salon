package com.example.demo.util.validator.impl;

import com.example.demo.util.validator.FormValidator;
import com.example.demo.util.validator.PatternValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.demo.controller.navigation.AttributeParameterHolder.*;

/**
 * Sign up form validator. Checks username, email  and password validity and equality between password and repeated password.
 */
public class SignUpFormValidator implements FormValidator {

    private static FormValidator instance;
    private final PatternValidator validator;

    private SignUpFormValidator() {
        validator = PatternValidator.getInstance();
    }

    public static FormValidator getInstance() {
        if (instance == null) {
            instance = new SignUpFormValidator();
        }
        return instance;
    }

    @Override
    public List<String> validateForm(Map<String, String[]> data) {

        List<String> validationResult = new ArrayList<>();

        if (data.get(PARAMETER_USER_NAME) == null
                || data.get(PARAMETER_USER_NAME).length == 0
                || !validator.validUsername(data.get(PARAMETER_USER_NAME)[0])) {
            validationResult.add(PARAMETER_USER_NAME);
        }

        if(data.get(PARAMETER_USER_PASSWORD) != null && data.get(PARAMETER_USER_PASSWORD).length != 0){
            String password = data.get(PARAMETER_USER_PASSWORD)[0];

            if (!validator.validPassword(password)) {
                validationResult.add(PARAMETER_USER_PASSWORD);
            }

        } else {
            validationResult.add(PARAMETER_USER_PASSWORD);
        }

        if (data.get(PARAMETER_USER_EMAIL_ADDRESS) == null
                || data.get(PARAMETER_USER_EMAIL_ADDRESS).length == 0
                || !validator.validEmailAddress(data.get(PARAMETER_USER_EMAIL_ADDRESS)[0])) {
            validationResult.add(PARAMETER_USER_EMAIL_ADDRESS);
        }

        return validationResult;
    }
}