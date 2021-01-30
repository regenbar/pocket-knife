package io;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FileFinder {
    private String searchPath = "";
    private boolean searchRecursive = true;

    private List<String> withFileNames = new ArrayList<>();
    private List<String> withFileNameContains = new ArrayList<>();
    private List<String> withoutFileNames = new ArrayList<>();
    private List<String> withoutFileNameContains = new ArrayList<>();

    private List<String> withFilePath = new ArrayList<>();
    private List<String> withFilePathContains = new ArrayList<>();
    private List<String> withoutFilePath = new ArrayList<>();
    private List<String> withoutFilePathContains = new ArrayList<>();

    /**
     * Find all files based on the provided selection of conditions
     * @return All selected files
     */
    public List<File> findAll () {
        List<File> allFiles = new ArrayList<>();

        File file = new File(searchPath);
        if (file.isDirectory()) {
            if (ifAddFolder(file)) {
                allFiles.addAll(findAll(file.listFiles()));
            }
        } else {
            if (ifAddFile(file)) {
                allFiles.add(file);
            }
        }

        return allFiles;
    }

    private boolean ifAddFolder(File startFile) {
        return true;
    }

    /**
     * Check if function should add the file to filtered selection of files or ignore it
     * @param file File to check
     * @return boolean that approves or ignores a file based on FileFinder selection
     */
    private boolean ifAddFile(File file) {
//        TODO: Remove debugger code
//        if (startFile.getAbsolutePath().contains("xml") && startFile.getAbsolutePath().contains("pocket")) {
//            System.out.println("sss");
//        }

        // Check name of file
        boolean fileNameIsCondition = false;
        boolean fileNameContainsCondition = false;
        String fileName = file.getName();

        if (withFileNames.isEmpty() && withoutFileNames.isEmpty()) {
            fileNameIsCondition = true;
        } else if (withFileNames.contains(fileName) && !withoutFileNames.contains(fileName)) {
            fileNameIsCondition = true;
        }
        if (withFileNameContains.isEmpty() && withoutFileNameContains.isEmpty()) {
            fileNameContainsCondition = true;
        } else if (checkContainsConditions(fileName, withFileNameContains, withoutFileNameContains)) {
            fileNameContainsCondition = true;
        }

        // Check path of file
        boolean filePathCondition = false;
        boolean filePathContainsCondition = false;
        String filePath = file.getAbsolutePath();

        if (withFilePath.isEmpty() && withoutFilePath.isEmpty()) {
            filePathCondition = true;
        } else if (withFilePath.contains(filePath) && !withoutFilePath.contains(filePath)) {
            filePathCondition = true;
        }
        if (withFilePathContains.isEmpty() && withoutFilePathContains.isEmpty()) {
            filePathContainsCondition = true;
        } else if ( checkContainsConditions(filePath, withFilePathContains, withoutFilePathContains) ) {
            filePathContainsCondition = true;
        }

        return fileNameIsCondition && fileNameContainsCondition && filePathCondition && filePathContainsCondition;
    }

    private boolean checkContainsConditions(String fragment, List<String> withConditions, List<String> withoutConditions) {
        return  (withConditions.isEmpty() || ifCollectionElementContains(fragment, withConditions))
                &&
                (withoutConditions.isEmpty() || !ifCollectionElementContains(fragment, withoutConditions));
    }

    private <T> boolean ifCollectionElementEquals(T filePath, Collection<T> filePathConditions) {
        for (T filePathFragment : filePathConditions) {
            if (filePath.equals(filePathFragment)) {
                return true;
            }
        }

        return false;
    }


    private <T> boolean ifCollectionElementContains(T filePath, Collection<T> filePathConditions) {
        for (T filePathFragment : filePathConditions) {
            if (filePath.toString().contains(filePathFragment.toString())) {
                return true;
            }
        }

        return false;
    }

    private Collection<File> findAll(File[] listFiles) {
        List<File> allFiles = new ArrayList<>();

        for (File file : listFiles) {
            if (file.isDirectory()) {
                if (searchRecursive) {
                    allFiles.addAll(findAll(file.listFiles()));
                }
            } else {
                if (ifAddFile(file)) {
                    allFiles.add(file);
                }
            }
        }

        return allFiles;
    }

    public FileFinder withFileName (String fileName) {
        withFileNames.add(fileName);
        return this;
    }

    public FileFinder withPath(String startPath) {
        this.searchPath = startPath;
        return this;
    }

    public FileFinder withFileNameContains(String fileNameFragment) {
        withFileNameContains.add(fileNameFragment);
        return this;
    }

    public FileFinder withoutFileNameContains(String fileNameFragment) {
        withoutFileNameContains.add(fileNameFragment);
        return this;
    }

    public FileFinder withFilePathContains(String filePathFragment) {
        withFilePathContains.add(filePathFragment);
        return this;
    }

    public FileFinder withoutFilePathContains(String filePathFragment) {
        withoutFilePathContains.add(filePathFragment);
        return this;
    }

    public FileFinder isRecursive (boolean searchRecursive) {
        this.searchRecursive = searchRecursive;
        return this;
    }
}
