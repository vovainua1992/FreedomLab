package com.freedom.web.controller;

import com.freedom.services.dommain.User;
import com.freedom.services.dommain.enums.Role;
import com.freedom.services.repos.UserRepos;
import com.freedom.services.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserRepos userRepos;
    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public String userList(@PageableDefault(sort = {"id"},direction = Sort.Direction.ASC) Pageable pageable,
                           Model model) {
        model.addAttribute("users", userRepos.findAll(pageable));
        return "user/userList";
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/avatar/update")
    @ResponseBody
    public String updateAvatar( @AuthenticationPrincipal User user,
                                @RequestParam( required=false,name="file" ) MultipartFile file,
                                @RequestParam( name="scale") Double  scalar,
                                @RequestParam( required=false,name="posX",defaultValue = "50") Integer  posX,
                                @RequestParam( required=false,name="posY",defaultValue = "50") Integer  posY,
                                @RequestParam( required=false,name="size",defaultValue = "100") Integer  size) throws IOException {
        userService.updateAvatar(user,file,scalar,posX,posY,size);
        return "ok";
    }

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
