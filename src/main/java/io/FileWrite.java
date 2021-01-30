package io;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FileWrite {

    // Write content to file
    //////////////////////////////////////////////////////////////////////

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
        }
    }
}
