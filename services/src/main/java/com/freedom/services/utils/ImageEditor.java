package com.freedom.services.utils;

import java.io.IOException;
import java.nio.file.Path;

public interface ImageEditor {
    String duplicateAndCrop(Path path, int posX, int posY, int width);

    String duplicate(Path path, String prefixName) throws IOException;

    void cropImage(Path path, int posX, int posY, int size);

    void mirrorHorizontal(Path path);

    void mirrorVertical(Path path);

    void rotateRight(Path path);

    void rotateLeft(Path path);

}
