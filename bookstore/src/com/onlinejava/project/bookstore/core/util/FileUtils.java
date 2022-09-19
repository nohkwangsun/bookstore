package com.onlinejava.project.bookstore.core.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileUtils {

    public static Stream<String> getFileLines(String first) {
        try {
            return Files.lines(Path.of(first));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
