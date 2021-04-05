package tester;

import resource.ResourceIndexer;

import java.io.IOException;
import java.util.List;

class Main {
    public static void main(String[] args) throws IOException {

//        List<File> all = new FileFind().withIncludeFolders().findAll();
//
//        for (File file : all) {
//            System.out.println(file);
//        }

        new ResourceIndexer()
                .withClassName("R")
                .withClassPackage("tester.test")
                .withSearchPath("src/main/resources/")
                .withFileNameContains(".txt")
                .withIncludeFolders()
                .withGetFilesMethod()
                .build();

        List<String> files = R.images.getFiles();
        System.out.println(files);

//        FileWrite.append("test.txt", "Some text");
//        FileWrite.append("test.txt", "Some Additional text");

    }
}
