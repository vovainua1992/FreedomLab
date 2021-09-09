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

import static com.freedom.services.utils.FilesUtil.duplicateFile;

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

    public Image createDuplicate(Image image,String nameDuplicatesPrefix) throws IOException {
        Path path = Paths.get(uploadPath.substring(1)+"/"+image.getUrl().substring(5));
        String duplicateName = duplicateFile(path,"small");
        Image duplicate = Image.newImage(nameDuplicatesPrefix+image.getName(),"/img/"+duplicateName,LocalDateTime.now());
        return imageRepository.save(duplicate);
    }


}
