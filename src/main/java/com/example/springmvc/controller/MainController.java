package com.example.springmvc.controller;

import com.example.springmvc.dommain.Message;
import com.example.springmvc.dommain.User;
import com.example.springmvc.repos.MessageRepos;
import com.example.springmvc.service.ImageService;
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

import static com.example.springmvc.controller.ControllersUtil.getErrors;

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



    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "main_think/greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter,
                       Model model) {
        Iterable<Message> messages;
        if (filter != null && !filter.isEmpty())
            messages = messageRepos.findByTag(filter);
        else
            messages = messageRepos.findAll();
        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "main_think/main";
    }

    @PostMapping("/main")
    public String add(@Valid Message message,
                      BindingResult bindingResult,
                      @AuthenticationPrincipal User user,
                      @RequestParam("file") MultipartFile file,
                      Model model) throws IOException {
        message.setAuthor(user);
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = getErrors(bindingResult);
            model.mergeAttributes(errors);
            model.addAttribute("message", message);
        } else {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                String fileUrl = imageService.saveImage(file);
                message.setImage(fileUrl);
            }
            model.addAttribute("message", null);
            messageRepos.save(message);
        }
        Iterable<Message> messages = messageRepos.findAll();
        model.addAttribute("messages", messages);
        return "main_think/main";
    }
}
