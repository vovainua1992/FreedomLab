package com.freedom.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "about_project";
    }

}
