package resource;

import com.squareup.javapoet.*;
import io.FileFind;
import model.Folder;
import tester.R;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.lang.model.element.Modifier;

public class ResourceIndexer {


    public static void main(String[] args) throws IOException {


        String searchPath = "src/main/resources/";
        build(R.class, searchPath);


//        TypeSpec.Builder root = TypeSpec.classBuilder("R")
//                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
//
//        Map<String, Object> allStructure = new LinkedHashMap<>();

//        for (File file : all) {
//            String fileName = file.getName();
//            String fileNameNoExt = fileName.substring(0, fileName.lastIndexOf("."));
//            String filePath = file.toString().substring(searchPath.length());
//
//
//            FieldSpec name = FieldSpec.builder(String.class, fileNameNoExt)
//                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
//                    .initializer("$S", filePath)
//                    .build();
//
//            root.addField(name);
//
//            List<String> split = StringUtil.split(filePath, false, "/", "\\");
//
//            Map<String, Object> structure = new LinkedHashMap<>();
//            for (int i = 0; i < split.size()-1; i++) {
//                Map<String, Object> s = new LinkedHashMap<>();
//
//                if (structure.containsKey(fileNameNoExt)) {
//                    Map<String, Object> map = (Map) structure.get(fileName);
//                    map.put(fileNameNoExt, s);
//                    structure = map;
//                } else {
//                    structure.put(fileNameNoExt, s);
//                    structure = s;
//                }
//
//
//            }
//            structure.put(fileNameNoExt, fileName);
//
//            allStructure.putAll(structure);;
//            System.out.println(split);
            // System.out.println(file);
        }

    private static void build(Class<R> klass, String searchPath) throws IOException {
        Folder folder = new FileFind().withPath(searchPath).findAndGroupAll();
        TypeSpec.Builder root = TypeSpec.classBuilder(klass.getSimpleName())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

        for (Folder folderFolder : folder.getFolders()) {
            addInnerClass(root, folderFolder);
        }

        addInnerField(root, folder.getFiles());

        String packagePath = klass.getPackage().toString().replace("package", "").trim();
        JavaFile javaFile = JavaFile.builder(packagePath, root.build()).build();
        javaFile.writeTo(System.out);

        javaFile.writeToFile(new File("R.java"));
        //javaFile.writeTo(System.out);
    }

    private static void addInnerField(TypeSpec.Builder root, List<File> files) {
        for (File file : files) {
            FieldSpec field = FieldSpec.builder(String.class, getNameNoExtension(file))
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .initializer("$S", file.getAbsoluteFile())
                    .build();

            root.addField(field);
        }
    }

    private static TypeSpec addInnerClass(TypeSpec.Builder parent , Folder folder) {
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


    private static String getNameNoExtension(File file) {
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
