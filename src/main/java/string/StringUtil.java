package string;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {

    public static String capitalize (String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * Split string based on delimiters.
     *
     * @param string String to split
     * @param keepDelimiters Include delimiters in return result
     * @param delimiters Split based on these delimiters
     * @return List of split fragments
     */
    public static List<String> split (String string, Boolean keepDelimiters, String... delimiters) {
        List<String> fragments = new ArrayList<>();

        int index = 0;
        int lastFoundIndex = 0;
        String stringSoFar = "";
        for (; index < string.length();) {
            boolean found = false;

            for (String delimiter : delimiters) {
                int offset = index + delimiter.length();
                if (offset > string.length() || found) {
                    continue;
                }
                String substring = string.substring(index, offset);

                if (substring.equals(delimiter)) {
                    if (!stringSoFar.isEmpty()) {
                        fragments.add(stringSoFar);
                    }

                    if (keepDelimiters) {
                        fragments.add(substring);
                    }

                    stringSoFar = "";
                    index = offset;
                    lastFoundIndex = offset;
                    found = true;
                    break;
                }
            }

            if (!found) {
                stringSoFar += string.substring(index, index + 1);
                index++;
            }
        }

        fragments.add(string.substring(lastFoundIndex));

        return fragments;
    }
}
