package resource;

import model.Folder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

abstract class FileFindBuilder<T extends FileFindBuilder<T>>  {
    protected String searchPath = ".";
    protected boolean isSearchRecursive = true;
    protected boolean includeFoldersIntoList = false;
//    protected boolean withIgnoreRootPath;

    protected List<String> withFileNames = new ArrayList<>();
    protected List<String> withFileNameContains = new ArrayList<>();
    protected List<String> withoutFileNames = new ArrayList<>();
    protected List<String> withoutFileNameContains = new ArrayList<>();

    protected List<String> withFilePath = new ArrayList<>();
    protected List<String> withFilePathContains = new ArrayList<>();
    protected List<String> withoutFilePath = new ArrayList<>();
    protected List<String> withoutFilePathContains = new ArrayList<>();



//    public T withIgnoreRootPath() {
//        this.withIgnoreRootPath = true;
//        return (T)this;
//    }

    public T withSearchPath(String startPath) {
        this.searchPath = startPath;
        return (T)this;
    }

    public T withFileName (String... fileNames) {
        for (String fileName : fileNames) {
            withFileNames.add(fileName);
        }
        return (T)this;
    }

    public T withFileNameContains(String... fileNameFragments) {
        for (String fileNameFragment : fileNameFragments) {
            withFileNameContains.add(fileNameFragment);
        }
        return (T)this;
    }

    public T withoutFileNameContains(String... fileNameFragments) {
        for (String fileNameFragment : fileNameFragments) {
            withoutFileNameContains.add(fileNameFragment);
        }
        return (T)this;
    }

    public T withFilePathContains(String... fileNameFragments) {
        for (String fileNameFragment : fileNameFragments) {
            withFilePathContains.add(fileNameFragment);
        }
        return (T)this;
    }

    public T withoutFilePathContains(String... fileNameFragments) {
        for (String fileNameFragment : fileNameFragments) {
            withoutFilePathContains.add(fileNameFragment);
        }
        return (T)this;
    }

    public T isRecursive (boolean searchRecursive) {
        this.isSearchRecursive = searchRecursive;
        return (T)this;
    }

    /**
     * Check if function should add the file to filtered selection of files or ignore it
     * @param file File to check
     * @return boolean that approves or ignores a file based on FileFinder selection
     */
    protected boolean ifAddFile(File file) {
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

    protected boolean checkContainsConditions(String fragment, List<String> withConditions, List<String> withoutConditions) {
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

    /**
     * Find all files based on the provided selection of conditions
     * @return All selected files
     */
    protected List<File> findAll () throws FileNotFoundException {
        List<File> allFiles = new ArrayList<>();

        File file = new File(searchPath);
        if (!file.exists()) {
            throw new FileNotFoundException(file.getAbsolutePath());
        }
        if (file.isDirectory()) {
            if (ifAddFolder(file)) {
                if (includeFoldersIntoList) {
                    allFiles.add(file);
                }
                allFiles.addAll(findAll(file.listFiles()));
            }
        } else {
            if (ifAddFile(file)) {
                allFiles.add(file);
            }
        }

        return allFiles;
    }

    /**
     * Find all files based on the provided selection of conditions
     * @return All selected files
     */
    protected Folder findAndGroup() throws FileNotFoundException {
        File file = new File(searchPath);
        if (!file.exists()) {
            throw new FileNotFoundException(file.getAbsolutePath());
        } else if (file.isFile()) {
            throw new RuntimeException("You have to specify folder and not a file");
        }

        Folder folder = new Folder(file);
        if (ifAddFolder(file)) {
            findAndGroup(folder, file.listFiles());
        }

        return folder;
    }

    protected boolean ifAddFolder(File startFile) {
        return true;
    }


    protected Collection<File> findAll(File[] listFiles) {
        List<File> allFiles = new ArrayList<>();

        for (File file : listFiles) {
            if (file.isDirectory()) {
                if (includeFoldersIntoList) {
                    allFiles.add(file);
                }
                if (isSearchRecursive) {
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

    protected Folder findAndGroup(Folder folder, File[] listFiles) {
        for (File file : listFiles) {
            if (file.isDirectory()) {
                if (isSearchRecursive) {
                    Folder innerFolder = new Folder(file);
                    innerFolder = findAndGroup(innerFolder, file.listFiles());
                    folder.addFolder(innerFolder);
                }
            } else if (file.isFile()){
                if (ifAddFile(file)) {
                    folder.addFile(file);
                }
            }
        }

        return folder;
    }


}
