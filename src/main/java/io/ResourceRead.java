package io;

import string.StringUtil;

import java.io.IOException;
import java.io.InputStream;

public class ResourceRead {
    /**
     * Keeping this as a backup. To be removed.
     * @param fileName
     * @return
     */
//    public String readString(String fileName) {
//        ClassLoader classLoader = getClass().getClassLoader();
//        InputStream inputStream = classLoader.getResourceAsStream(fileName);
//
//        if (inputStream == null) {
//            throw new IllegalArgumentException("File not found! " + fileName);
//        } else {
//            return StringUtil.inputStreamToString(inputStream);
//        }
//    }

    public static String staticReadString(String fileName) {
        String result = "";
        try (InputStream inputStream = ResourceRead.class.getClassLoader().getResourceAsStream(fileName)) {
                if (inputStream == null) {
                    throw new IllegalArgumentException("file not found! " + fileName);
                } else {
                    return StringUtil.inputStreamToString(inputStream);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            throw new IllegalArgumentException("Could not read " + fileName);
        }
    }
