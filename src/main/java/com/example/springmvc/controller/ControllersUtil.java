package com.example.springmvc.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Клас статичних методів для контролерів
 */
public class ControllersUtil {

    /**
     * Метод отримання переліку помилок із bindingResult
     *
     * @param bindingResult
     * @return Map<String, String>
     */
    static Map<String, String> getErrors(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, String>> collectors = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        );
        return bindingResult.getFieldErrors().stream().collect(collectors);
    }


}
