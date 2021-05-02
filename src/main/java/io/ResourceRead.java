package io;

import string.StringUtil;


public class ResourceRead {
    public static String readString(String filePath) {
        ClassLoader cl = ResourceRead.class.getClassLoader();;
        if (cl==null) {
            // A system class.
            return StringUtil.inputStreamToString(ClassLoader.getSystemResourceAsStream(filePath));
        }
        return StringUtil.inputStreamToString(cl.getResourceAsStream(filePath));
    }

//    public static String staticReadString(String fileName) {
//        String result = "";
//        try (InputStream inputStream = ResourceRead.class.getClassLoader().getResourceAsStream(fileName)) {
//                if (inputStream == null) {
//                    throw new IllegalArgumentException("file not found! " + fileName);
//                } else {
//                    return StringUtil.inputStreamToString(inputStream);
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            throw new IllegalArgumentException("Could not read " + fileName);
//        }
//    }
}