package com.example.demo.util.validator.impl;

import com.example.demo.util.validator.FormValidator;
import com.example.demo.util.validator.PatternValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.demo.controller.navigation.AttributeParameterHolder.PARAMETER_USER_EMAIL_ADDRESS;

/**
 *ign up form validator. Checks username and email validity.
 */
public class EditAccountFormValidator implements FormValidator {
    private static FormValidator instance;
    private final PatternValidator validator;

    private EditAccountFormValidator() {
        validator = PatternValidator.getInstance();
    }

    public static FormValidator getInstance() {
        if (instance == null) {
            instance = new EditAccountFormValidator();
        }
        return instance;
    }

    @Override
    public List<String> validateForm(Map<String, String[]> data) {

        List<String> validationResult = new ArrayList<>();

        if (data.get(PARAMETER_USER_EMAIL_ADDRESS) == null
                || data.get(PARAMETER_USER_EMAIL_ADDRESS).length == 0
                || !validator.validEmailAddress(data.get(PARAMETER_USER_EMAIL_ADDRESS)[0])) {
            validationResult.add(PARAMETER_USER_EMAIL_ADDRESS);
        }

        return validationResult;
    }
}
