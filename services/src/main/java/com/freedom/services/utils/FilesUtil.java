package com.freedom.services.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FilesUtil {
    /**
     *The method of creating a duplicate file.
     * @param file file to copy
     * @param prefixDuplicatesName  prefix for duplications file name
     * @return  the name of the duplicate file
     * @throws IOException
     */
    public static String duplicateFile(Path file, String prefixDuplicatesName) throws IOException {
        String nameDuplicate = prefixDuplicatesName+file.getFileName();
        Path duplicate = Paths.get(file.getParent()+"/"+nameDuplicate);
        Files.copy(file,duplicate, StandardCopyOption.REPLACE_EXISTING);
        return nameDuplicate;
    }

}
