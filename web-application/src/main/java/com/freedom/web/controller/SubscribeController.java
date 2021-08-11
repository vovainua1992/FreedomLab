package com.freedom.web.controller;

import com.freedom.services.dommain.User;
import com.freedom.services.repos.UserRepos;
import com.freedom.services.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/subscribe")
@AllArgsConstructor
public class SubscribeController {
    private final UserRepos userRepos;
    private final UserService userService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/subscribe/{id}")
    public String subscribe(@AuthenticationPrincipal User currentUser,
                            @PathVariable long id) {
        userService.subscribe(currentUser, id);
        return "redirect:/user/" + id;
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/unsubscribe/{id}")
    public String unsubscribe(@AuthenticationPrincipal User currentUser,
                              @PathVariable long id) {
        userService.unsubscribe(currentUser, id);
        return "redirect:/user/" + id;
    }

}
