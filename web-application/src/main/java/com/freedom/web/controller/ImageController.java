package com.freedom.web.controller;

import com.freedom.services.dommain.dto.ImageDto;
import com.freedom.services.dommain.dto.ImageInsert;
import com.freedom.services.service.ImageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

/**
 * Контроллер зображень
 */
@Controller
@RequestMapping("/image")
@AllArgsConstructor
public class ImageController {
    private final ImageService imageService;

    //TODO refactor remove test from freemarker post
    //TODO create method in image service
    @PostMapping("/add")
    @ResponseBody
    public String addImage(@RequestParam("test") String test,
                           @RequestParam("image") MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ImageDto image = new ImageDto();
        image.setUrl(imageService.saveImage(file).getUrl());
        image.setSize(480);
        return objectMapper.writeValueAsString(image);
    }

    //TODO create method in imageSerice
    @PostMapping("/insert")
    @ResponseBody
    public String insertImage(@RequestParam("url") String url) throws IOException {
        ImageInsert insert = new ImageInsert();
        ObjectMapper objectMapper = new ObjectMapper();
        insert.setUrl(url);
        insert.setAlt("default image");
        insert.setSize(new int[]{600, 400, 200});
        return objectMapper.writeValueAsString(insert);
    }

}
