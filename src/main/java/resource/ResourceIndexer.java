package resource;

import com.squareup.javapoet.*;
import model.Folder;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import javax.lang.model.element.Modifier;

public class ResourceIndexer extends FileFindBuilder<ResourceIndexer> {

    private boolean filePathIsRelative = true;
    private boolean filePathIsAbsolute = false;
    private String className;
    private String classPackage;
    private String sourceFolderPath = "src/main/java";
    private boolean withIncludeGetFilesMethod;
    public boolean withIgnoreRootPath = false;
    public ResourceIndexer withIncludeFolders() {
        this.includeFoldersIntoList = true;
        return this;
    }
    public ResourceIndexer withIgnoreRootPath() {
        this.withIgnoreRootPath = true;
        return this;
    }

    /**
     * Specify package path for a class
     *
     * @param classPackage Example: com.resource.service
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

    public ResourceIndexer withGetFilesMethod() {
        this.withIncludeGetFilesMethod = true;
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

        List<FieldSpec> classFields = createClassFields(folder.getFiles());
        addClassField(root, classFields);

        JavaFile javaFile = JavaFile.builder(classPackage, root.build())
                                .skipJavaLangImports(false)
                                .addStaticImport(ClassName.get(Arrays.class), "asList")
                                .build();

        javaFile.writeTo(System.out);
        javaFile.writeToFile(new File(sourceFolderPath));
    }

    private void addClassField(TypeSpec.Builder root, List<FieldSpec> classFields) {
        for (FieldSpec classField : classFields) {
            root.addField(classField);
        }
    }

    private List<FieldSpec> createClassFields(List<File> files) {
        List<String> fieldNames = new ArrayList<>();
        List<FieldSpec> fields = new ArrayList<>();
        for (File file : files) {
            String filePathValue = "";
            if (filePathIsAbsolute) {
                String filePath = file.getAbsolutePath();
                if (withIgnoreRootPath) {
                    filePath = filePath.substring(new File(searchPath).getAbsolutePath().length() + 1);
                }
                filePathValue = filePath;
            } else {
                String filePath = file.toString();
                if (withIgnoreRootPath) {
                    String removeSearchPath = searchPath;
                    removeSearchPath = removeSearchPath.replaceAll("//", "\\");
                    removeSearchPath = removeSearchPath.replaceAll("/", "\\\\");
                    filePath = filePath.replace(removeSearchPath, "");
                }

                filePathValue = filePath;
            }

            // Append iteration number if file exists under that name
            String fieldName = formatVariableName(getFileNameRemoveEntension(file));
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

            fields.add(field);
        }
        return fields;
    }

    private String formatVariableName(String string) {
        if (Character.isDigit(string.charAt(0))) {
            string = "_" + string;
        }
        String regexValidVariableCharacters = "[^0-9a-zA-Z_$\\.]";
        String cleanVariableName = string.replaceAll(regexValidVariableCharacters, "_");
        return cleanVariableName;
    }

    private TypeSpec addInnerClass(TypeSpec.Builder parent , Folder folder) {
        TypeSpec.Builder innerClass = TypeSpec.classBuilder(formatFolderName(folder))
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC);

        List<FieldSpec> classFields = createClassFields(folder.getFiles());
        addClassField(innerClass, classFields);
        addMethodGetFiles(innerClass, classFields);

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

    private void addMethodGetFiles(TypeSpec.Builder innerClass, List<FieldSpec> classFields) {
        if (!withIncludeGetFilesMethod) {
            return;
        }
        if (classFields.isEmpty()) {
            return;
        }

        List<String> fieldsNames = new ArrayList<>();
        for (FieldSpec field : classFields) {
            fieldsNames.add(field.name);
        }
        String classFieldsAsString = fieldsNames.stream().collect(Collectors.joining(","));
        String statemnt = String.format("asList(%s)", classFieldsAsString);

        MethodSpec spec = MethodSpec.methodBuilder("getFiles")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ParameterizedTypeName.get( ClassName.get(List.class),
                        ClassName.get(String.class)))
                .addStatement(String.format("return %s", statemnt))
                .build();
        innerClass.addMethod(spec);
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
}
