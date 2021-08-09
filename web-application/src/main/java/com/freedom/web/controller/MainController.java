package com.freedom.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * Контролер головної сторінки (в розробці)
 */
@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "main_think/greeting";
    }

}
