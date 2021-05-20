package com.example.springmvc.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Images service(in progress)
 */
@Service
public class ImageService {

    @Value("${upload.path}")
    private String uploadPath;

    public String saveImage(MultipartFile file) throws IOException {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists())
            uploadDir.mkdir();
        String uuid = UUID.randomUUID().toString();
        String resultFileName = uuid + '.' + file.getOriginalFilename();
        file.transferTo(new File(uploadPath + "/" + resultFileName));
        return "/img/" + resultFileName;
    }

}
