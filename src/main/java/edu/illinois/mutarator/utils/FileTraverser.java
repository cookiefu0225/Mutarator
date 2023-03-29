package edu.illinois.mutarator.utils;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.SourceRoot;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * todo: find out methods to traverse over packages and all .java files
 */
public class FileTraverser {
    SourceRoot sourceRoot = null;
    SourceRoot backupRoot = null;
    List<List<String>> compileTargets = null;
    private final String root = "./src/main/java/";
    private final String storage = "./src/main/temp/";

    public FileTraverser() {
        sourceRoot = new SourceRoot(Paths.get(root));
        File f = new File(storage);
        if (!f.exists()) {
            f.mkdir();
        }
        backupRoot = new SourceRoot(Paths.get(f.getPath()));
        compileTargets = new ArrayList<>();
        getMembers();
    }

    /**
     *
     * @return List of CompilationUnit for all classes in package
     */
    public List<CompilationUnit> getAllSrcCodes() {
        // refresh source root object
        sourceRoot = new SourceRoot(Paths.get(root));

        // currently explicitly defined
        // should find way to iterate over packages and files
//        sourceRoot.parse("example", "BinarySearcher.java");
        for (List<String> pairs : compileTargets) {
            sourceRoot.parse(pairs.get(0), pairs.get(1));
        }

        // after iterating, the backup file record the original files
        sourceRoot.saveAll(backupRoot.getRoot());

        return sourceRoot.getCompilationUnits();
    }

    /**
     * called after mutations are done, store the mutated files to project directory
     */
    public void saveChanges() {
        sourceRoot.saveAll();
    }

    /**
     * resume original files
     * @param cuPointers a list of mutated files
     * @return a list of original files
     */
    public List<CompilationUnit> resumeFiles(List<CompilationUnit> cuPointers) {
        // currently explicitly defined
        // should find way to iterate over packages and files
        for (List<String> pairs : compileTargets) {
            backupRoot.parse(pairs.get(0), pairs.get(1));
        }

        // refresh sourceRoot
        backupRoot.saveAll(sourceRoot.getRoot());
        cuPointers = getAllSrcCodes();

        return cuPointers;
    }

    /**
     * get all files we want to visit
     */
    private void getMembers() {
        try {
            Files.walkFileTree(Paths.get(root), new FileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    if (Files.isHidden(dir)) {
                        return FileVisitResult.SKIP_SUBTREE;
                    }

//                    System.out.println("Enter: " + dir);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    // find out files with <name>.java
                    String filename = file.toString();

                    if (!filename.substring(file.toString().length() - 5).equals(".java")) {
                        // Unrelated files
                        return FileVisitResult.CONTINUE;
                    }

                    // create entry for the file and store package-file pair in list
                    List<String> element = new ArrayList<>();
                    String[] split = filename.substring(root.length()).split("/");
                    filename = split[split.length - 1];
                    String[] packagePrefix = Arrays.stream(split, 0, split.length - 1)
                            .toArray(String[]::new);


                    element.add(String.join(".", packagePrefix));
                    element.add(filename);

                    compileTargets.add(element);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
//                    System.out.println("Unable to visit: " + file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
//                    System.out.println("Exit: " + dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
