package edu.illinois.mutarator.utils;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.SourceRoot;

import java.util.Map;

/**
 * SourceRoot srt: root of the traverser (should be at the root main/java of the custom project)
 * Map originalFile: the map of relative file path to original compilation unit
 */
public class FileTraverser {
    SourceRoot srt = null;
    Map<String, CompilationUnit> originalFile = null;

//    private
}
