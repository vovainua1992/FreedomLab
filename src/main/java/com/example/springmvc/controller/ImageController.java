package com.example.springmvc.controller;


import com.example.springmvc.dommain.dto.ImageDto;
import com.example.springmvc.dommain.dto.ImageInsert;
import com.example.springmvc.service.ImageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 *Контроллер зображень
 */
@Controller
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/add")
    @ResponseBody
    public String addImage(@RequestParam("test")String test,
                            @RequestParam("image") MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ImageDto image = new ImageDto();
        image.setUrl(imageService.saveImage(file));
        image.setSize(480);
        return objectMapper.writeValueAsString(image);
    }

    @PostMapping("/insert")
    @ResponseBody
    public String insertImage(@RequestParam("url") String url) throws IOException {
        ImageInsert insert = new ImageInsert();
        ObjectMapper objectMapper = new ObjectMapper();
        insert.setUrl(url);
        insert.setAlt("default image");
        insert.setSize(new int[]{600, 400,200});
        return objectMapper.writeValueAsString(insert);
    }

}
