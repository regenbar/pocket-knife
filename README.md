# pocket-knife
sss

<groupId>groupId</groupId>
<artifactId>pocket-knife</artifactId>
<version>1.0-SNAPSHOT</version>
    
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.lyxbassy</groupId>
        <artifactId>Paspartu</artifactId>
        <version>master</version>
    </dependency>
</dependencies>

## Using ResourceIndexer
Creates a hierarchy of static classes and static field which represent folder structure.
Traverse through class structure as you would through folder structure to get the path of a file.
```
new ResourceIndexer()
        .withClassName("R")
        .withClassPackage("tester.test")
        .withSearchPath("src/main/resources/")
        .withFileNameContains(".txt")
        .withIncludeFolders()
        .withGetFilesMethod()
        .build();
```
      