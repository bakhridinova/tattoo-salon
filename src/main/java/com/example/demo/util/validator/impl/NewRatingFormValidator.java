package com.example.demo.util.validator.impl;


import com.example.demo.util.validator.FormValidator;
import com.example.demo.util.validator.PatternValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.demo.controller.navigation.AttributeParameterHolder.PARAMETER_RATING_REVIEW;

public class NewRatingFormValidator implements FormValidator {
    private static FormValidator instance;
    private final PatternValidator validator;

    private NewRatingFormValidator() {
        validator = PatternValidator.getInstance();
    }

    public static FormValidator getInstance() {
        if (instance == null) {
            instance = new NewRatingFormValidator();
        }
        return instance;
    }

    @Override
    public List<String> validateForm(Map<String, String[]> data) {

        List<String> validationResult = new ArrayList<>();

        if (data.get(PARAMETER_RATING_REVIEW) == null
                || data.get(PARAMETER_RATING_REVIEW).length == 0
                || !validator.validUnlimitedText(data.get(PARAMETER_RATING_REVIEW)[0])) {
            validationResult.add(PARAMETER_RATING_REVIEW);
        }

        return validationResult;
    }
}
