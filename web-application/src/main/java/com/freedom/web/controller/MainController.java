package com.freedom.web.controller;

import com.freedom.services.dommain.Image;
import com.freedom.services.dommain.Message;
import com.freedom.services.dommain.User;
import com.freedom.services.repos.MessageRepos;
import com.freedom.services.repos.UserRepos;
import com.freedom.services.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

/**
 * Контролер головної сторінки (в розробці)
 */
@Controller
@RequiredArgsConstructor
public class MainController {
    @Value("${upload.path}")
    private String uploadPath;
    private final ImageService imageService;
    private final MessageRepos messageRepos;
    private final UserRepos userRepos;



    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "main_think/greeting";
    }



    @PostMapping("/main")
    public String add(@Valid Message message,
                      BindingResult bindingResult,
                      @AuthenticationPrincipal User user,
                      @RequestParam("file") MultipartFile file,
                      Model model) throws IOException {
        message.setAuthor(user);
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllersUtil.getErrors(bindingResult);
            model.mergeAttributes(errors);
            model.addAttribute("message", message);
        } else {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                Image image = imageService.saveImage(file);
                message.setImage(image);
            }
            model.addAttribute("message", null);
            messageRepos.save(message);
        }
        Iterable<Message> messages = messageRepos.findAll();
        model.addAttribute("messages", messages);
        return "main_think/main";
    }
}
