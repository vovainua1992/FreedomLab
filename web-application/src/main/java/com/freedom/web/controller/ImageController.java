package com.freedom.web.controller;

import com.freedom.services.dommain.dto.ImageDto;
import com.freedom.services.dommain.dto.ImageInsert;
import com.freedom.services.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Controller
@RequestMapping("/image")
@AllArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/add")
    @ResponseBody
    public ImageDto addImage(@RequestParam("test") String test,
                           @RequestParam("image") MultipartFile file) throws IOException {
        ImageDto image = new ImageDto();
        image.setUrl(imageService.saveImage(file).getUrl());
        image.setSize(480);
        return image;
    }

    @PostMapping("/insert")
    @ResponseBody
    public ImageInsert insertImage(@RequestParam("url") String url) throws IOException {
        ImageInsert insert = new ImageInsert();
        insert.setUrl(url);
        insert.setAlt("default image");
        insert.setSize(new int[]{600, 400, 200});
        return insert;
    }

}
