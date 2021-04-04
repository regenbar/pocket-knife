package resource;

import com.squareup.javapoet.*;
import model.Folder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.lang.model.element.Modifier;

public class ResourceIndexer extends FileFindBuilder<ResourceIndexer> {

    private boolean filePathIsRelative = true;
    private boolean filePathIsAbsolute = false;
    private String className;
    private String classPackage;
    private String sourceFolderPath = "src/main/java";

    /**
     * Specify package path for a class
     *
     * @param classPackage Example: tester.test.service
     * @return
     */
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
        Folder folder = findAndGroup();
        build(folder);
    }

    public void build(Folder folder) throws IOException {
        TypeSpec.Builder root = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        root.addJavadoc("This class is autogenerated using ResourceIndexer.\nDont modify it manually cause it will we overwritten next time its generated.");
        for (Folder folderFolder : folder.getFolders()) {
            addInnerClass(root, folderFolder);
        }

        addInnerField(root, folder.getFiles());

        JavaFile javaFile = JavaFile.builder(classPackage, root.build()).build();
        javaFile.writeTo(System.out);
        javaFile.writeToFile(new File(sourceFolderPath));
    }

    private void addInnerField(TypeSpec.Builder root, List<File> files) {
        List<String> fieldNames = new ArrayList<>();
        for (File file : files) {
            String filePathValue = "";
            if (filePathIsAbsolute) {
                filePathValue = file.getAbsolutePath();
            } else {
                filePathValue = file.toString();
            }

            // Append iteration number if file exists under that name
            String fieldName = buildVariableName(getFileNameRemoveEntension(file));
            if (fieldNames.contains(fieldName)) {
                int iterator = 1;
                String original = fieldName;
                do {
                    fieldName = original + "_" + iterator++;
                } while (fieldNames.contains(fieldName));
            }
            fieldNames.add(fieldName);;

            FieldSpec field = FieldSpec.builder(String.class, fieldName)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .initializer("$S", filePathValue)
                    .build();

            root.addField(field);

        }
    }

    private String buildVariableName(String string) {
        if (string.contains(".")) {
            System.out.println("ss");
        }
        String regex = "[^0-9a-zA-Z_$\\.]";
        if (Character.isDigit(string.charAt(0))) {
            string = "_" + string;
        }
        String cleanVariableName = string.replaceAll(regex, "_");
        return cleanVariableName;
    }

    private TypeSpec addInnerClass(TypeSpec.Builder parent , Folder folder) {
        TypeSpec.Builder innerClass = TypeSpec.classBuilder(formatFolderName(folder))
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC);

        addInnerField(innerClass, folder.getFiles());

        for (Folder innerFolder : folder.getFolders()) {
            addInnerClass(innerClass, innerFolder);
        }

        String path =  folder.asFile().toString().replaceAll("\\\\", "/");
        MethodSpec method = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .setName("getPath")
                .returns(String.class)
                .addStatement(String.format("return " + "\"" + "%s" + "\"", path))
                .build();


        innerClass.addMethod(method);

        TypeSpec build = innerClass.build();
        parent.addType(build);

        return innerClass.build();
    }

    private String formatFolderName(Folder folder) {
        String folderName = folder.asFile().getName();
        folderName = folderName.replaceAll("\\.", "_");
        folderName = folderName.replaceAll("[^0-9a-zA-Z_]", "_");
        if (Character.isDigit(folderName.charAt(0))) {
            folderName = "_" + folderName;
        }
        return folderName;
    }


    private String getFileNameRemoveEntension(File file) {
        if (!file.isFile()) {
            throw new RuntimeException("Have to supply a file");
        }
        String fileName = file.getName();
        if (!fileName.contains(".")) {
            return fileName;
        }
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

//        System.out.println(allStructure);
//
//        JavaFile javaFile = JavaFile.builder("com.example.helloworld", root.build()).build();
//        javaFile.writeTo(System.out);
//
//    }
}
