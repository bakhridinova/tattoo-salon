package com.example.demo.util.validator;

import java.util.List;
import java.util.Map;

public interface FormValidator {
    List<String> validateForm(Map <String, String[]> data);
}