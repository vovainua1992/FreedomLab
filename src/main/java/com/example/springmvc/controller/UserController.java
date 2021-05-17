package com.example.springmvc.controller;

import com.example.springmvc.dommain.Role;
import com.example.springmvc.dommain.User;
import com.example.springmvc.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

/**
 * Контроллер редагування користувачів
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * Отримання списку користувачів
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public String userList(Model model){
        model.addAttribute("users", userService.findAll());
        return  "user/userList";
    }

    /**
     * Редагування профілю для адміністрації
     * @param user
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("user",user);
        model.addAttribute("roles", Role.values());
        return  "user/userEdit";
    }

    /**
     * Збереження налаштувань користувача
     * @param username
     * @param form
     * @param user
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave( @RequestParam String username,
                            @RequestParam Map<String,String> form,
                            @RequestParam("userId")User user){
        userService.saveUser(user,username,form);
        return "redirect:/user";
    }

    /**
     * Видалення користувачів
     * @param attributes
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{id}")
    public RedirectView delete(RedirectAttributes attributes,
                         @PathVariable String id){
        userService.removeUser(id);
        return new RedirectView("/user");
    }

    /**
     * Налаштування профілю для користувача
     * @param model
     * @param user
     * @return
     */
    @GetMapping("/profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user){
        model.addAttribute("username",user.getUsername());
        model.addAttribute("email", user.getEmail());

        return "user/profile";
    }

    /**
     * Оновленя налаштувань профілю
      * @param user
     * @param username
     * @param password
     * @param password2
     * @param email
     * @return
     */
    @PostMapping("/profile")
    public String updateProfile(
            @AuthenticationPrincipal User user, @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "") String password, @RequestParam(defaultValue = "") String password2,
            @RequestParam(defaultValue = "") String email
    ){
        userService.updateProfile(user,username,password, password2,email);

        return "redirect:/user/profile";
    }

}
