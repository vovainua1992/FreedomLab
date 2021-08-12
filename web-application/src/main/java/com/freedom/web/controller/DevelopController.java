package com.freedom.web.controller;

import com.freedom.services.dommain.User;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dev")
@AllArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class DevelopController {

    @GetMapping("/mail")
    public String testMail(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("link","#");
        model.addAttribute("name",user.getUsername());
        return "mail/test_mail";
    }

}
