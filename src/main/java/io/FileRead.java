package io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileRead {

    // Read file as string
    //////////////////////////////////////////////////////////////////////

    public static String readString(File file) throws IOException {
        return readString(file, StandardCharsets.UTF_8);
    }
    public static String readString(String filePath) throws IOException {
        return readString(new File(filePath), StandardCharsets.UTF_8);
    }
    public static String readString(String filePath, Charset encoding) throws IOException {
        return readString(new File(filePath), encoding);
    }
    public static String readString(File file, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
        return new String(encoded, encoding);
    }

    // Read file as string lines
    //////////////////////////////////////////////////////////////////////

    public static List<String> readLines(String filePath) throws IOException {
        return readLines(Paths.get(filePath), StandardCharsets.UTF_8);
    }
    public static List<String> readLines(File file) throws IOException {
        return readLines(file.toPath(), StandardCharsets.UTF_8);
    }
    public static List<String> readLines(File file, Charset encoding) throws IOException {
        return readLines(file.toPath(), encoding);
    }
    public static List<String> readLines(Path filePath, Charset encoding) throws IOException {
        List<String> lines = new ArrayList<>();

        try (Stream<String> content = Files.lines(filePath, encoding)) {
            content.forEach(s -> lines.add(s));
        }

        return lines;
    }
}
