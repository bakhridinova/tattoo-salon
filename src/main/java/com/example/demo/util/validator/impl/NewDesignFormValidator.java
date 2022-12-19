package com.example.demo.util.validator.impl;


import com.example.demo.util.validator.FormValidator;
import com.example.demo.util.validator.PatternValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.demo.controller.navigation.AttributeParameterHolder.*;

/**
 * New design form validator. Checks image url and description validity.
 */
public class NewDesignFormValidator implements FormValidator {
    private static FormValidator instance;
    private final PatternValidator validator;

    private NewDesignFormValidator() {
        validator = PatternValidator.getInstance();
    }

    public static FormValidator getInstance() {
        if (instance == null) {
            instance = new NewDesignFormValidator();
        }
        return instance;
    }

    @Override
    public List<String> validateForm(Map<String, String[]> data) {

        List<String> validationResult = new ArrayList<>();

        if (data.get(PARAMETER_IMAGE_URL) == null
                || data.get(PARAMETER_IMAGE_URL).length == 0
                || !validator.validImageUrl(data.get(PARAMETER_IMAGE_URL)[0])) {
            validationResult.add(PARAMETER_IMAGE_URL);
        }

        if (data.get(PARAMETER_IMAGE_SHORT_DESC) == null
                || data.get(PARAMETER_IMAGE_SHORT_DESC).length == 0
                || !validator.validUnlimitedText(data.get(PARAMETER_IMAGE_SHORT_DESC)[0])) {
            validationResult.add(PARAMETER_IMAGE_SHORT_DESC);
        }

        if (data.get(PARAMETER_IMAGE_LONG_DESC) == null
                || data.get(PARAMETER_IMAGE_LONG_DESC).length == 0
                || !validator.validUnlimitedText(data.get(PARAMETER_IMAGE_LONG_DESC)[0])) {
            validationResult.add(PARAMETER_IMAGE_LONG_DESC);
        }

        return validationResult;
    }
}
