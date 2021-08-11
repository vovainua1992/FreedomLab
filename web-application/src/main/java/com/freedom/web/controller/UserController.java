package com.freedom.web.controller;

import com.freedom.services.dommain.User;
import com.freedom.services.dommain.enums.Role;
import com.freedom.services.repos.UserRepos;
import com.freedom.services.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserRepos userRepos;
    private final UserService userService;

    //refactor user_list.ftl
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public String userList(Model model) {
        model.addAttribute("users", userRepos.findAll());
        return "user/userList";
    }

    //refactor refactor user_edit.ftl
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/edit/{id}")
    public String userEditForm(@PathVariable String id,
                               Model model) {
        User selectUser = userRepos.findById(Long.parseLong(id));
        model.addAttribute("user", selectUser);
        model.addAttribute("roles", Role.values());
        return "user/userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(@RequestParam Map<String, String> form,
                           @RequestParam("userId") long id) {
        userService.updateUserAuthority(userRepos.findById(id), form);
        return "redirect:/user";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{id}")
    public RedirectView delete(RedirectAttributes attributes,
                               @PathVariable long id) {
        userService.removeUser(id);
        return new RedirectView("/user");
    }

    @GetMapping("/profile")
    public String getProfile(Model model,
                             @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "user/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@AuthenticationPrincipal User user,
                                @RequestParam(defaultValue = "") String username,
                                @RequestParam(defaultValue = "") String password,
                                @RequestParam(defaultValue = "") String password2,
                                @RequestParam(defaultValue = "") String email
    ) {
        userService.updateProfile(user, username, password, password2, email);
        return "redirect:/user/profile";
    }

}
