package com.freedom.web.controller;

import com.freedom.services.dommain.User;
import com.freedom.services.dommain.enums.Role;
import com.freedom.services.repos.GalleryRepos;
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
import java.util.Set;

/**
 * Контроллер редагування користувачів
 */
@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepos userRepos;
    private final GalleryRepos galleryRepos;

    /**
     * Отримання списку користувачів
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public String userList(Model model) {
        model.addAttribute("users", userRepos.findAll());
        return "user/userList";
    }

    /**
     * Сторінка користувача
     *
     * @param currentUser
     * @param filter
     * @param id
     * @param model
     * @return
     */
    @GetMapping("{id}")
    public String user(@AuthenticationPrincipal User currentUser,
                       @RequestParam(required = false, defaultValue = "") String filter,
                       @PathVariable long id,
                       Model model) {
        User account = userRepos.findById(id);
        boolean isCurrentUser = account.equals(currentUser);
        boolean isSubscriber = account.isMySubscriber(currentUser);
        model.addAttribute("subscribers_count", account.getSubscribers().size());
        model.addAttribute("subscriptions_count", account.getSubscriptions().size());
        model.addAttribute("account", account);
        model.addAttribute("isCurrentUser", isCurrentUser);
        model.addAttribute("isSubscriber", isSubscriber);
        model.addAttribute("subscribers_dif", "+1");
        return "user/main";
    }

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

    /**
     * Редагування профілю для адміністрації
     *
     * @param id
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/edit/{id}")
    public String userEditForm(@PathVariable String id,
                               Model model) {
        User selectUser = userRepos.findById(Long.parseLong(id));
        model.addAttribute("user", selectUser);
        model.addAttribute("roles", Role.values());
        return "user/userEdit";
    }

    /**
     * Збереження налаштувань користувача
     *
     * @param form
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(@RequestParam Map<String, String> form,
                           @RequestParam("userId") String id) {
        User editsUser = userRepos.findById(Long.parseLong(id));
        userService.updateUserAuthority(editsUser, form);
        return "redirect:/user";
    }

    /**
     * Видалення користувачів
     *
     * @param attributes
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{id}")
    public RedirectView delete(RedirectAttributes attributes,
                               @PathVariable String id) {
        userService.removeUser(id);
        return new RedirectView("/user");
    }

    /**
     * Налаштування профілю для користувача
     *
     * @param model
     * @param user
     * @return
     */
    @GetMapping("/profile")
    public String getProfile(Model model,
                             @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "user/profile";
    }

    /**
     * Оновленя налаштувань профілю
     *
     * @param user
     * @param username
     * @param password
     * @param password2
     * @param email
     * @return
     */
    @PostMapping("/profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "") String password,
            @RequestParam(defaultValue = "") String password2,
            @RequestParam(defaultValue = "") String email
    ) {
        userService.updateProfile(user, username, password, password2, email);
        return "redirect:/user/profile";
    }

}
