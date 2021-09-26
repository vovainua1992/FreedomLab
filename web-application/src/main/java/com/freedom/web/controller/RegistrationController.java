package com.freedom.web.controller;

import com.freedom.services.dommain.User;
import com.freedom.services.dommain.dto.CaptchaResponseDto;
import com.freedom.services.service.UserService;
import com.freedom.services.utils.EmailValidator;
import com.freedom.services.utils.RegistrationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;
    private final RestTemplate restTemplate;
    private static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    @Value("${recaptcha.secret}")
    private String captchaSecret;
    private EmailValidator emailValidator= new EmailValidator();

    @GetMapping("/registration")
    public String registration() {
        return "login/registration";
    }

    @PostMapping("/registration")
    public RedirectView addUser(@RequestParam("password2") String confirmPassword,
                                @RequestParam("g-recaptcha-response") String captcha,
                                @Valid User user,
                                BindingResult bindingResult,
                                RedirectAttributes attributes) {
        String url = String.format(CAPTCHA_URL, captchaSecret, captcha);
        CaptchaResponseDto captchaResponse = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);
        Map<String, String> errors = RegistrationValidator.collectionOfErrors(captchaResponse, confirmPassword, user, bindingResult);
        if(emailValidator.validate(user.getEmail())){
             attributes.addFlashAttribute("emailError","Введено некоректний email!");
         }
        if (!errors.isEmpty()) {
            errors.forEach(attributes::addFlashAttribute);
            return new RedirectView("/registration");
        }
        if (!userService.addUser(user)) {
            attributes.addFlashAttribute("usernameError", "Користувач із таким іменем уже є");
            return new RedirectView("/registration");
        }
        attributes.addFlashAttribute("succ", "Будь ласка тепер підтвердіть свою почту");
        return new RedirectView("/login");
    }

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
