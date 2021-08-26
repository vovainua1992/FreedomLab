package com.freedom.services.service;

import com.freedom.services.dommain.Image;
import com.freedom.services.repos.ImageRepository;
import com.freedom.services.utils.ImageEditor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final ImageEditor imageEditor;
    @Value("${upload.path}")
    private String uploadPath;

    @Transactional
    @Scheduled(cron = "0 * * * * ?")
    public void scheduleFixedRateTask() {
        imageRepository.removeAllByDeleteDateTimeBefore(LocalDateTime.now());
    }

    /**
     * Save image in upload path and create record in DB
      * @param file
     * @return
     * @throws IOException
     */
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

    public Image createAvatar(MultipartFile file, int posX, int posY, int size) throws IOException {
        Image avatar = saveImage(file);
        Path path = Paths.get(uploadPath.substring(1)+"/"+avatar.getUrl().substring(5));
        imageEditor.cropImage(path, posX, posY, size);
        return avatar;
    }
}
