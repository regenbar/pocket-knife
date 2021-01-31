import string.StringUtil;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        String s = "Test1-2-3-4-56-6-788-sad";
        List<String> split = StringUtil.split(s, true, "56-", "788");
        System.out.println(split);

//        List<File> all = new FileFinder()
//                .isRecursive(true)
//                .withPath("C:\\Users\\MM\\IdeaProjects")
//                .withFileName("Main.java")
//                .withFileNameContains(".java")
//                .withoutFilePathContains("netexacademy")
//                .findAll();
//
//        for (File file : all) {
//            System.out.println(file.getAbsolutePath());
//
//            List<String> content = FileRead.readLines(file);
//            System.out.println(content);
//        }
//
//        FileWrite.write("bojan.txt", "ʕ•͡ ᴥ•͡ ʔ");
    }
}
