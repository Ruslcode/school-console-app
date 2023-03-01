package com.foxmined.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class FileReader {
    public static List<String> readLines(String fileName) throws IOException {

        File fileExistValidation = new File(fileName);

        List<String> lines = Files.lines(fileExistValidation.toPath()).collect(Collectors.toList());

        if (!fileExistValidation.exists()) {
            throw new IllegalArgumentException(ExceptionConstants.NO_FILE);
        }
        return lines;
    }
}
