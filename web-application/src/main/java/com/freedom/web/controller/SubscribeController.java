package com.freedom.web.controller;

import com.freedom.services.dommain.User;
import com.freedom.services.repos.UserRepos;
import com.freedom.services.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

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

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/subscribers/{id}")
    public String subscribers(@AuthenticationPrincipal User user,
                              @PathVariable long id,
                              Model model){
        User account = userRepos.findById(id);
        boolean isCurrentUser = user.equals(account);
        Set<User> subscribers = account.getSubscribers();
        model.addAttribute("isSubscribers",true);
        model.addAttribute("isCurrentUser",isCurrentUser);
        model.addAttribute("subscribers",subscribers);
        model.addAttribute("account",account);
        return "user/subscribe";
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/subscriptions/{id}")
    public String subscriptions(@AuthenticationPrincipal User user,
                                @PathVariable long id,
                                Model model){
        User account = userRepos.findById(id);
        boolean isCurrentUser = user.equals(account);
        Set<User> subscriptions = account.getSubscriptions();
        model.addAttribute("isSubscribers",false);
        model.addAttribute("isCurrentUser",isCurrentUser);
        model.addAttribute("subscribers",subscriptions);
        model.addAttribute("account",account);
        return "user/subscribe";
    }
}
