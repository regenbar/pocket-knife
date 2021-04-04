package resource;

import model.Folder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;

public class FileFind extends FileFindBuilder<FileFind> {
    public List<File> findAll () throws FileNotFoundException {
        return findAll();
    }

    public Folder findAndGroup() throws FileNotFoundException {
        return findAndGroup();
    }


    public Collection<File> findAll(File[] listFiles) {
        return findAll(listFiles);
    }

    public Folder findAndGroup(Folder folder, File[] listFiles) {
        return findAndGroup(folder, listFiles);
    }
}
