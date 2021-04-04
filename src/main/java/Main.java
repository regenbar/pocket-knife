import resource.ResourceIndexer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        new ResourceIndexer()
                .withClassName("R")
                .withClassPackage("tester")
                .withSearchPath("src/main/resources/")
                .withFileNameContains(".jpg", ".txt")
                .build();


//        FileWrite.append("test.txt", "Some text");
//        FileWrite.append("test.txt", "Some Additional text");

    }
}
