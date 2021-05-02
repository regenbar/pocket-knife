package tester;

import io.ResourceRead;
import resource.FileFind;
import resource.ResourceIndexer;
import tester.test.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

class Main {

    public static InputStream getResourceAsStream(String name) {
        ClassLoader cl = Main.class.getClassLoader();;
        if (cl==null) {
            // A system class.
            return ClassLoader.getSystemResourceAsStream(name);
        }
        return cl.getResourceAsStream(name);
    }

    public static void main(String[] args) throws IOException {


//        String texts = ResourceRead.readString(R.text.bojan);
//        System.out.println(texts);

//        new ResourceIndexer()
//                .withClassName("R")
//                .withClassPackage("tester.test")
//                .withSearchPath("src/main/resources/")
//                .withIgnoreRootPath()
//                .withIncludeFolders()
//                .withGetFilesMethod()
//                .build();
////
//        List<String> files = R.text.bojan;
//        System.out.println(files);

//        FileWrite.append("test.txt", "Some text");
//        FileWrite.append("test.txt", "Some Additional text");

    }
}
