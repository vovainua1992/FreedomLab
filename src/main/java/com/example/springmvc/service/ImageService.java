package com.example.springmvc.service;


import com.example.springmvc.dommain.Image;
import com.example.springmvc.repos.ImageRepository;
import com.example.springmvc.utils.ImageEditor;
import com.example.springmvc.utils.ImageEditorWithImageJ;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Images service(in progress)
 */
@Service
@RequiredArgsConstructor
public class ImageService {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");
    private Logger logger = LogManager.getLogger(ImageService.class);
    private final ImageRepository imageRepository;
    private ImageEditor editor = new ImageEditorWithImageJ();

    @Value("${upload.path}")
    private String uploadPath;

    @Transactional
    @Scheduled(cron = "0 * * * * ?")
    public void scheduleFixedRateTask() {
        logger.info("test delete");

        imageRepository.removeAllByDeleteDateTimeBefore(LocalDateTime.now());
    }


    public Image saveImage(MultipartFile file) throws IOException {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists())
            uploadDir.mkdir();
        String uuid = UUID.randomUUID().toString();
        String name= file.getOriginalFilename();
        String resultFileName = uuid + '.' + name;
        file.transferTo(new File(uploadPath + "/" + resultFileName));
        String url = "/img/" + resultFileName;
        Image image = Image.newImage(name,url,LocalDateTime.now());
        imageRepository.save(image);
        return image;
    }

}
