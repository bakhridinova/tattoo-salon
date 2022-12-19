package com.example.demo.util.validator.impl;

import com.example.demo.util.validator.FormValidator;
import com.example.demo.util.validator.PatternValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.demo.controller.navigation.AttributeParameterHolder.PARAMETER_USER_NAME;
import static com.example.demo.controller.navigation.AttributeParameterHolder.PARAMETER_USER_PASSWORD;

/**
 * Sign in form validator. Checks email and password validity.
 */
public class SignInFormValidator implements FormValidator {

    private static FormValidator instance;
    private final PatternValidator validator;

    private SignInFormValidator() {
        validator = PatternValidator.getInstance();
    }

    public static FormValidator getInstance() {
        if (instance == null) {
            instance = new SignInFormValidator();
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

        if (data.get(PARAMETER_USER_PASSWORD) == null
                || data.get(PARAMETER_USER_PASSWORD).length == 0
                || !validator.validPassword(data.get(PARAMETER_USER_PASSWORD)[0])) {
            validationResult.add(PARAMETER_USER_PASSWORD);
        }

        return validationResult;
    }
}