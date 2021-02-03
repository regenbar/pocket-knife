package model;



import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Folder {
    private File folder;

    private List<Folder> folders = new ArrayList<>();
    private List<File> files = new ArrayList<>();

    public Folder(File folder) {
        this.folder = folder;
    }

    public File asFile() {
        return folder;
    }

    public List<Folder> getFolders() {
        return folders;
    }

    public List<File> getFiles() {
        return files;
    }

    public void addFolder(Folder folder) {
        this.folders.add(folder);
    }

    public void addFile(File file) {
        this.files.add(file);
    }

    private String subFolderNames () {
        List<String> subfolderNames = new ArrayList<>();
        for (Folder subfolder : this.folders) {
            subfolderNames.add(subfolder.asFile().getName());
        }
        return String.join(",", subfolderNames);
    }

    private String fileNames () {
        List<String> fileNames = new ArrayList<>();
        for (File file : this.files) {
            fileNames.add(file.getName());
        }
        return String.join(",", fileNames);
    }

    @Override
    public String toString() {
        return "Folder\t{" +
                "path=" + folder.getAbsolutePath() +
                "\nfolders\t[" + subFolderNames() + "]" +
                "\nfiles\t[" + fileNames() + "]";
    }
}
