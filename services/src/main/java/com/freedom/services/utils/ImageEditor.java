package com.freedom.services.utils;

import java.io.IOException;
import java.nio.file.Path;

public interface ImageEditor {

    void cropImage(Path path,double scalar, int posX, int posY, int size);

    void mirrorHorizontal(Path path);

    void mirrorVertical(Path path);

    void rotateRight(Path path);

    void rotateLeft(Path path);

}
