package com.example.springmvc.controller;

import com.example.springmvc.dommain.User;
import com.example.springmvc.dommain.dto.CaptchaResponseDto;
import com.example.springmvc.repos.UserRepos;
import com.example.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

import static com.example.springmvc.controller.ControllerUtil.getErrors;

/**
 * Контролер регістрації
 */
@Controller
public class RegistrationController {
    private final UserRepos userRepos;
    private final UserService userService;
    private static final String CAPTCHA_URL ="https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    @Value("${recaptcha.secret}")
    private String captchaSecret;
    @Autowired
    private RestTemplate restTemplate;


    public RegistrationController(UserRepos userRepos, UserService userService) {
        this.userRepos = userRepos;
        this.userService = userService;
    }

    /**
     * Сторінка регістрації
     * @return
     */
    @GetMapping("/registration")
    public String registration(){
        return "login/registration";
    }

    /**
     * Post - запит регістрації нового користувача
     * @param confirmPassword
     * @param captcha
     * @param user
     * @param bindingResult
     * @param attributes
     * @return
     */
    @PostMapping("/registration")
    public RedirectView addUser(@RequestParam("password2")String confirmPassword,
                                @RequestParam("g-recaptcha-response") String captcha,
                                @Valid User user,
                                BindingResult bindingResult,
                                RedirectAttributes attributes){

        String url = String.format(CAPTCHA_URL,captchaSecret,captcha);
        CaptchaResponseDto captchaResponse = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);

        if(!captchaResponse.isSuccess()){
            attributes.addFlashAttribute("captchaError","Підтвердіть що ви не робот");
        }

        boolean isConfirmEmpty = StringUtils.isEmpty(confirmPassword);
        if(isConfirmEmpty){
            attributes.addFlashAttribute("password2Error","Полепідтвердження паролю маєбути заповненим!");
            return new RedirectView("/registration");
        }
        if (user.getPassword() != null
                && !user.getPassword().equals(confirmPassword)){
            attributes.addFlashAttribute("passwordError", "Паролі не співпадають");
            return new RedirectView("/registration");
        }

        if (isConfirmEmpty||bindingResult.hasErrors()||!captchaResponse.isSuccess()){
            Map<String, String> errors = getErrors(bindingResult);
            errors.forEach(attributes::addFlashAttribute);
           // return "registration";
            return new RedirectView("/registration");

        }

        if(!userService.addUser(user)){

            return new RedirectView("/registration");
        }
        attributes.addFlashAttribute("warn","Будь ласка тепер підтвердіть свою почту");
        return  new RedirectView("/login");
    }

    /**
     * Підтвердження пошти
     * @param model
     * @param code
     * @return
     */
    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code){
        boolean isActivated = userService.activateUser(code);
        model.addAttribute("succ","Почту підтвердженно");
        return "login/login";
    }

}
