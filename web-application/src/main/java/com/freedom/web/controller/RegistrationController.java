package com.freedom.web.controller;

import com.freedom.services.dommain.User;
import com.freedom.services.dommain.dto.CaptchaResponseDto;
import com.freedom.services.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

import static com.freedom.services.utils.ControllersUtil.getErrors;

/**
 * Контролер регістрації
 */
@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;
    private final RestTemplate restTemplate;
    private static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    @Value("${recaptcha.secret}")
    private String captchaSecret;


    //TODO union registration.ftl and login.ftl

    /**
     * Сторінка регістрації
     *
     * @return
     */
    @GetMapping("/registration")
    public String registration() {
        return "login/registration";
    }

    //TODO refactor method post create registrationService or update userService
    /**
     * Post - запит регістрації нового користувача
     *
     * @param confirmPassword
     * @param captcha
     * @param user
     * @param bindingResult
     * @param attributes
     * @return
     */
    @PostMapping("/registration")
    public RedirectView addUser(@RequestParam("password2") String confirmPassword,
                                @RequestParam("g-recaptcha-response") String captcha,
                                @Valid User user,
                                BindingResult bindingResult,
                                RedirectAttributes attributes) {
        String url = String.format(CAPTCHA_URL, captchaSecret, captcha);
        CaptchaResponseDto captchaResponse = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);
        if (!captchaResponse.isSuccess()) {
            attributes.addFlashAttribute("captchaError", "Підтвердіть що ви не робот");
        }
        boolean isConfirmEmpty = StringUtils.isEmpty(confirmPassword);
        if (isConfirmEmpty) {
            attributes.addFlashAttribute("password2Error", "Поле підтвердження паролю маєбути заповненим!");
            return new RedirectView("/registration");
        }
        if (user.getPassword() != null
                && !user.getPassword().equals(confirmPassword)) {
            attributes.addFlashAttribute("passwordError", "Паролі не співпадають");
            attributes.addFlashAttribute("password2Error", "Паролі не співпадають");
            return new RedirectView("/registration");
        }
        if (bindingResult.hasErrors() || !captchaResponse.isSuccess()) {
            Map<String, String> errors = getErrors(bindingResult);
            errors.forEach(attributes::addFlashAttribute);
            return new RedirectView("/registration");
        }
        if (!userService.addUser(user)) {
            attributes.addFlashAttribute("usernameError", "Користувач із таким іменем уже є");
            return new RedirectView("/registration");
        }
        attributes.addFlashAttribute("warn", "Будь ласка тепер підтвердіть свою почту");
        return new RedirectView("/login");
    }

    //TODO refactor activateUser
    /**
     * Підтвердження пошти
     *
     * @param model
     * @param code
     * @return
     */
    @GetMapping("/activate/{code}")
    public String activate(Model model,
                           @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            model.addAttribute("succ", "Почту підтвердженно");
            return "login/login";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor Not Found");
        }
    }

}
