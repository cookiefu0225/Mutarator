package edu.illinois.mutarator.utils;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.SourceRoot;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * todo: find out methods to traverse over packages and all .java files
 */
public class FileTraverser {
    SourceRoot sourceRoot = null;
    SourceRoot backupRoot = null;

    public FileTraverser() {
        sourceRoot = new SourceRoot(Paths.get("./src/main/java"));
        File f = new File("./src/main/resources/temp");
        if (!f.exists()) {
            f.mkdir();
        }
        backupRoot = new SourceRoot(Paths.get(f.getPath()));
    }

    /**
     *
     * @return List of CompilationUnit for all classes in package
     */
    public List<CompilationUnit> getAllSrcCodes() {
        // currently explicitly defined
        // should find way to iterate over packages and files
        sourceRoot.parse("example", "BinarySearcher.java");


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
        int idx = 0;

        // currently explicitly defined
        // should find way to iterate over packages and files
        cuPointers.set(idx, backupRoot.parse("example", "BinarySearcher.java"));


        // refresh sourceRoot
        backupRoot.saveAll(sourceRoot.getRoot());

        return cuPointers;
    }
}
