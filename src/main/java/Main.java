import io.FileRead;
import resource.ResourceIndexer;
import string.StringUtil;
import tester.R;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        new ResourceIndexer()
                .withClassName("R")
                .withClassPackage("tester")
                .withSearchPath("src/main/resources/")
                .build();

        List<String> strings = FileRead.readLines(R.text.TextA);
        for (String string : strings) {
            System.out.println(string);
        }

    }
}
