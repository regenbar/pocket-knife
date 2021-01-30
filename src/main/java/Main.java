import io.FileFinder;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<File> all = new FileFinder()
                .isRecursive(true)
                .withPath("C:\\Users\\MM\\IdeaProjects")
                .withFileName("Main.java")
                .withFileNameContains(".java")
                .withoutFilePathContains("netexacademy")
                .findAll();

        for (File file : all) {
            System.out.println(file.getAbsolutePath());
        }

    }
}
