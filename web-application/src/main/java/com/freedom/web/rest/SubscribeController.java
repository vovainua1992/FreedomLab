package com.freedom.web.rest;

import com.freedom.services.dommain.User;
import com.freedom.services.repos.UserRepos;
import com.freedom.services.service.ImageService;
import com.freedom.services.service.UserService;
import com.freedom.services.utils.RedirectUrlBuilder;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping("/subscribe")
@AllArgsConstructor
public class SubscribeController {
    private final UserRepos userRepos;
    private final UserService userService;

    //TODO change to rest
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/add/{id}")
    public String subscribe(@AuthenticationPrincipal User currentUser,
                            @RequestHeader(required = false) String referer,
                            RedirectAttributes redirectAttributes,
                            @PathVariable long id) {
        userService.subscribe(currentUser, id);
        return RedirectUrlBuilder.buildRedirect(referer,redirectAttributes);
    }

    //TODO change to rest
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/remove/{id}")
    public String unsubscribe(@AuthenticationPrincipal User currentUser,
                              @RequestHeader(required = false) String referer,
                              RedirectAttributes redirectAttributes,
                              @PathVariable long id) {
        userService.unsubscribe(currentUser, id);
        return RedirectUrlBuilder.buildRedirect(referer,redirectAttributes);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/all-subscribers")
    public String subscribers(@AuthenticationPrincipal User currentUser,
                              @PageableDefault(sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                              Model model) {
        model.addAttribute("isSubscribers",true);
        model.addAttribute("subscribers",userService.getMySubscribers(pageable,currentUser));
        model.addAttribute("url","/subscribe/all-subscribers");
        model.addAttribute("user",currentUser);
        return "subscriptions/subscriptions_list";
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/all-subscriptions")
    public String subscriptions(@AuthenticationPrincipal User currentUser,
                                @PageableDefault(sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                                Model model) {
        model.addAttribute("isSubscribers",false);
        model.addAttribute("subscribers",userService.getMySubscriptions(pageable,currentUser));
        model.addAttribute("url","/subscribe/all-subscriptions");
        model.addAttribute("user",currentUser);
        return "subscriptions/subscriptions_list";
    }
}
