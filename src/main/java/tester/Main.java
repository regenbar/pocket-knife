package tester;

import resource.ResourceIndexer;

import java.io.IOException;

class Main {
    public static void main(String[] args) throws IOException {

//        List<File> all = new FileFind().withIncludeFolders().findAll();
//
//        for (File file : all) {
//            System.out.println(file);
//        }
//
//        System.out.println(all.size());

        new ResourceIndexer()
                .withClassName("R")
                .withClassPackage("tester")
                .withSearchPath("src/main/resources/")
                .withIncludeFolders()
                .build();



//        FileWrite.append("test.txt", "Some text");
//        FileWrite.append("test.txt", "Some Additional text");

    }
}
