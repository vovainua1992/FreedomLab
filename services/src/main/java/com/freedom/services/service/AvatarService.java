package com.freedom.services.service;

import com.freedom.services.dommain.Avatar;
import com.freedom.services.dommain.Image;
import com.freedom.services.utils.ImageEditor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
@RequiredArgsConstructor
public class AvatarService {
    private final ImageService imageService;
    private final ImageEditor imageEditor;
    @Value("${upload.path}")
    private String uploadPath;


    public Avatar createAvatar(MultipartFile file,double scalar, int posX, int posY, int size) throws IOException {
        Avatar avatar = new Avatar();
        Image origin = imageService.saveImage(file);
        Image small = imageService.createDuplicate(origin,"small-");
        Path path = Paths.get(uploadPath.substring(1)+"/"+small.getUrl().substring(5));
        imageEditor.cropImage(path, scalar, posX, posY, size);
        avatar.setOrigin(origin);
        avatar.setSmall(small);
        avatar.setSmallSize(size);
        avatar.setSmallPosX(posX);
        avatar.setSmallPosY(posY);
        return avatar;
    }

    public Avatar updateAvatar(Avatar avatar,double scalar, int posX, int posY, int size) throws IOException {
        Image small = imageService.createDuplicate(avatar.getOrigin(),"small-");
        Path path = Paths.get(uploadPath.substring(1)+"/"+small.getUrl().substring(5));
        imageEditor.cropImage(path, scalar, posX, posY, size);
        avatar.setSmall(small);
        avatar.setSmallSize(size);
        avatar.setSmallPosX(posX);
        avatar.setSmallPosY(posY);
        return avatar;
    }
}
