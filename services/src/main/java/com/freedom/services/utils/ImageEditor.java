package com.freedom.services.utils;

import java.nio.file.Path;

public interface ImageEditor {

    String duplicateAndCrop(Path path, int posX, int posY, int width);

    void mirrorHorizontal(Path path);

    void mirrorVertical(Path path);

    void rotateRight(Path path);

    void rotateLeft(Path path);

}
