package resource;

import model.Folder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;

public class FileFind extends FileFindBuilder<FileFind> {
    public List<File> findAll () throws FileNotFoundException {
        return super.findAll();
    }

    public Folder findAndGroup() throws FileNotFoundException {
        return super.findAndGroup();
    }


    public Collection<File> findAll(File[] listFiles) {
        return super.findAll(listFiles);
    }

    public Folder findAndGroup(Folder folder, File[] listFiles) {
        return super.findAndGroup(folder, listFiles);
    }
}
