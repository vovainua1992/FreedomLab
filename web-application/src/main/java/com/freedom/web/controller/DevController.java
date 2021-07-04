package com.freedom.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Контролеер для тестування нових функцій
 */
@Controller
@RequestMapping("/develop")
@PreAuthorize("hasAuthority('ADMIN')")
public class DevController {

    @GetMapping("")
    public String develop() {
        return "develop/develop";
    }

    @GetMapping("/test")
    public String testWorkSpace() {
        return "develop/test";
    }

    @GetMapping("/demo")
    public String testDemo() {
        return "develop/demo";
    }
}
