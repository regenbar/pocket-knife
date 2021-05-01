package tester;

import io.ResourceRead;
import resource.FileFind;
import resource.ResourceIndexer;
import tester.test.R;

import java.io.File;
import java.io.IOException;
import java.util.List;

class Main {
    public static void main(String[] args) throws IOException {

        String s = ResourceRead.staticReadString(R.text.bojan);
        System.out.println(s);

//        List<File> all = new FileFind().withIncludeFolders().findAll();
//
//        for (File file : all) {
//            System.out.println(file);
//        }

        new ResourceIndexer()
                .withClassName("R")
                .withClassPackage("tester.test")
                .withSearchPath("src/main/resources/")
                .withIgnoreRootPath()
                .withIncludeFolders()
                .withGetFilesMethod()
                .withFilePathIsAbsolute()
                .build();
//
//        List<String> files = R.text.bojan;
//        System.out.println(files);

//        FileWrite.append("test.txt", "Some text");
//        FileWrite.append("test.txt", "Some Additional text");

    }
}
