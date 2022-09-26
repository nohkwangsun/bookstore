package com.onlinejava.project.bookstore.core.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

    @Test
    void getFileLines() throws IOException {
        Path path = Path.of("unittest.tmp");
        Files.writeString(path, "hi");
        String body = FileUtils.getFileLines("unittest.tmp").collect(Collectors.joining("/"));
        Files.delete(path);
        assertEquals("hi", body);
    }
}