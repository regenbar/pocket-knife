package io;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class FileWrite {

    // Append content to file
    //////////////////////////////////////////////////////////////////////

    public static void append (String filePath, Collection<String> content) throws IOException {
        append(new File(filePath), content,  StandardCharsets.UTF_8);;
    }
    public static void append (File file, Collection<String> content) throws IOException {
        append(file, content,  StandardCharsets.UTF_8);;
    }
    public static void append (File file, Collection<String> content, Charset encoding) throws IOException {
        validateFileAndCreateIfNotExist(file);

        try ( PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true))); ) {
            for (String row : content) {
                writer.write(row);
                writer.write(System.lineSeparator());
            }
        }
    }

    public static void append (String filePath, String content) throws IOException {
        append(new File(filePath), content, StandardCharsets.UTF_8);
    }
    public static void append (File file, String content) throws IOException {
        append(file, content, StandardCharsets.UTF_8);
    }
    public static void append (String filePath, String content, Charset encoding) throws IOException {
        append(new File(filePath), content, encoding);
    }
    public static void append (File file, String content, Charset encoding) throws IOException {
        validateFileAndCreateIfNotExist(file);

        try ( PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true))); ) {
            writer.write(content);
            writer.write(System.lineSeparator());
        }
    }

    // Write content to file
    //////////////////////////////////////////////////////////////////////

    public static void write (String filePath, Collection<String> content) throws IOException {
        write(new File(filePath), content,  StandardCharsets.UTF_8);;
    }
    public static void write (File file, Collection<String> content) throws IOException {
        write(file, content,  StandardCharsets.UTF_8);;
    }
    public static void write (File file, Collection<String> content, Charset encoding) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoding))) {
            for (String row : content) {
                writer.write(row);
                writer.newLine();
            }
        }
    }

    public static void write (String filePath, String content) throws IOException {
        write(new File(filePath), content, StandardCharsets.UTF_8);
    }
    public static void write (File file, String content) throws IOException {
        write(file, content, StandardCharsets.UTF_8);
    }
    public static void write (String filePath, String content, Charset encoding) throws IOException {
        write(new File(filePath), content, encoding);
    }
    public static void write (File file, String content, Charset encoding) throws IOException {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoding))) {
            writer.write(content);
            writer.write(System.lineSeparator());
        }
    }

    // Utility methods
    //////////////////////////////////////////////////////////////////////

    private static void validateFileAndCreateIfNotExist(File file) {
        if (file.isDirectory()) {
            throw new IllegalArgumentException("You need to specify a file path, not folder path.");
        }

        if (file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
    }
}
