import io.FileFinder;
import io.FileRead;
import io.FileWrite;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<File> all = new FileFinder()
                .isRecursive(true)
                .withPath("C:\\Users\\MM\\IdeaProjects")
                .withFileName("Main.java")
                .withFileNameContains(".java")
                .withoutFilePathContains("netexacademy")
                .findAll();

        for (File file : all) {
            System.out.println(file.getAbsolutePath());

            List<String> content = FileRead.readLines(file);
            System.out.println(content);
        }

        FileWrite.write("bojan.txt", "ʕ•͡ ᴥ•͡ ʔ");
    }
}
