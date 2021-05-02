package string;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StringUtil {

    public static String capitalize (String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String inputStreamToString (InputStream inputStream) {
        String text = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining());
        return text;
    }

    public static List<String> inputStreamToLines (InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
    }

    public static String bytesToString (byte[] bytes) {
        InputStream inputStream = new ByteArrayInputStream(bytes);
        return inputStreamToString(inputStream);
    }

    public static List<String> bytesToList (byte[] bytes) {
        InputStream inputStream = new ByteArrayInputStream(bytes);
        return inputStreamToLines(inputStream);
    }

    public static boolean isNumeric(String strNum) {
        return strNum.matches("-?\\d+(\\.\\d+)?");
    }

    public static String padRight(String str, int padCount, String c) {
        String paddedStr = "";
        for (int i = 0; i < padCount; i++) {
            paddedStr += c;
        }
        paddedStr += str;
        return paddedStr;
    }

    public static String padLeft(String str, int padCount, String c) {
        String paddedStr = "";
        String padded = "";
        for (int i = 0; i < padCount; i++) {
            padded += c;
        }
        paddedStr = str + padded;
        return paddedStr;
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
