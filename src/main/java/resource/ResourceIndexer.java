package resource;

import com.squareup.javapoet.*;
import io.FileFind;
import model.Folder;
import string.StringUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Struct;
import java.util.List;
import javax.lang.model.element.Modifier;

public class ResourceIndexer {

    private boolean filePathIsRelative = true;
    private boolean filePathIsAbsolute = false;
    private String searchPath;
    private String className;
    private String classPackage;
    private String sourceFolderPath = "src/main/java";

    public ResourceIndexer withClassPackage (String classPackage) {
        this.classPackage = classPackage;
        return this;
    }

    public ResourceIndexer withSourceFolderPath (String sourceFolderPath) {
        this.sourceFolderPath = sourceFolderPath;
        return this;
    }

    public ResourceIndexer withClassName (String className) {
        this.className = className;
        return this;
    }

    public ResourceIndexer withSearchPath (String searchPath) {
        this.searchPath = searchPath;
        return this;
    }

    public ResourceIndexer withFilePathIsRelative () {
        this.filePathIsRelative = true;
        this.filePathIsAbsolute = false;
        return this;
    }

    public ResourceIndexer withFilePathIsAbsolute () {
        this.filePathIsRelative = false;
        this.filePathIsAbsolute = true;
        return this;
    }

    public void build () throws IOException {
        Folder folder = new FileFind().withPath(searchPath).findAndGroupAll();
        build(folder);
    }

    public void build(Folder folder) throws IOException {
        TypeSpec.Builder root = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

        for (Folder folderFolder : folder.getFolders()) {
            addInnerClass(root, folderFolder);
        }

        addInnerField(root, folder.getFiles());

        JavaFile javaFile = JavaFile.builder(classPackage, root.build()).build();
        javaFile.writeTo(System.out);
        javaFile.writeToFile(new File(sourceFolderPath));
    }

    private void addInnerField(TypeSpec.Builder root, List<File> files) {
        for (File file : files) {
            String filePathValue = "";
            if (filePathIsAbsolute) {
                filePathValue = file.getAbsolutePath();
            } else {
                filePathValue = file.toString();
            }

            FieldSpec field = FieldSpec.builder(String.class, StringUtil.capitalize(getNameNoExtension(file).replaceAll("-", "_")))
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .initializer("$S", filePathValue)
                    .build();

            root.addField(field);
        }
    }

    private TypeSpec addInnerClass(TypeSpec.Builder parent , Folder folder) {
        TypeSpec.Builder innerClass = TypeSpec.classBuilder(folder.asFile().getName())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC);

        addInnerField(innerClass, folder.getFiles());

        for (Folder innerFolder : folder.getFolders()) {
            addInnerClass(innerClass, innerFolder);
        }

        TypeSpec build = innerClass.build();
        parent.addType(build);

        return innerClass.build();
    }


    private String getNameNoExtension(File file) {
        if (!file.isFile()) {
            throw new RuntimeException("Have to supply a file");
        }
        String name = file.getName();
        return name.substring(0, name.lastIndexOf("."));
    }

//        System.out.println(allStructure);
//
//        JavaFile javaFile = JavaFile.builder("com.example.helloworld", root.build()).build();
//        javaFile.writeTo(System.out);
//
//    }
}
