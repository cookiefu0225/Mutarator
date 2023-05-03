package edu.illinois.mutarator;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.SourceRoot;
import edu.illinois.mutarator.binaryexpr.*;
import edu.illinois.mutarator.binaryexpr.Math;
import edu.illinois.mutarator.experimental.ArgumentPropagation;
import edu.illinois.mutarator.experimental.MemberVariable;
import edu.illinois.mutarator.experimental.NakedReceiver;
import edu.illinois.mutarator.experimental.Switch;
import edu.illinois.mutarator.returns.*;
import edu.illinois.mutarator.testrunner.TestRunner;
import edu.illinois.mutarator.unaryexpr.Increment;
import edu.illinois.mutarator.unaryexpr.InvertNegatives;
import edu.illinois.mutarator.unaryexpr.Negation;
import edu.illinois.mutarator.unaryexpr.UnaryOperatorInsertion;
import edu.illinois.mutarator.utils.ClassNameCollector;
import edu.illinois.mutarator.utils.CustomerTestLoader;
import edu.illinois.mutarator.utils.FileTraverser;
import edu.illinois.mutarator.variables.ConstantReplacementMutator;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.lang.ClassLoader;

import static edu.illinois.mutarator.utils.Executor.cleanup;
import static edu.illinois.mutarator.utils.Executor.runProcess;

public class Main {
    public static void main(String[] args) {
        System.out.println("System started");

        // constants declaration
        FileTraverser ft = new FileTraverser();
        List<CompilationUnit> codes = ft.getAllSrcCodes();
        VoidVisitorAdapter<List<String>> classNameVisitor = new ClassNameCollector();
        Mutarator mutarator = null;
        final int numberOfFiles = codes.size();
        int totalMutants = 0;
        int totalKilled = 0;
        int mutatorLevelMutants = 0;
        int mutatorLevelKilled = 0;
        int currentMutantsNumber = 0;
        int currentKilled = 0;

        // Mutation
        for (String mutator: args) {
            // Initialize mutator
            switch (mutator) {
                case "AOD" -> {
                    mutarator = new ArithmeticOperatorDeletion();
                    System.out.println();
                    System.out.println("Arithmetic Operator Deletion");
                }
                case "AOR" -> {
                    mutarator = new ArithmeticOperatorReplacement();
                    System.out.println();
                    System.out.println("Arithmetic Operator Replacement");
                }
                case "BO" -> {
                    mutarator = new BitwiseOperator();
                    System.out.println();
                    System.out.println("Bitwise Operator Replacement");
                }
                case "CB" -> {
                    mutarator = new ConditionalBoundary();
                    System.out.println();
                    System.out.println("Conditional Boundary Replacement");
                }
                case "Math" -> {
                    mutarator = new Math();
                    System.out.println();
                    System.out.println("Math");
                }
                case "NC" -> {
                    mutarator = new NegateConditional();
                    System.out.println();
                    System.out.println("Negate Conditionals");
                }
                case "ROR" -> {
                    mutarator = new RelationalOperatorReplacement();
                    System.out.println();
                    System.out.println("Relational Operator Replacement");
                }
                case "AP" -> {
                    mutarator = new ArgumentPropagation();
                    System.out.println();
                    System.out.println("Argument Propagation");
                }
                case "MV" -> {
                    mutarator = new MemberVariable();
                    System.out.println();
                    System.out.println("Member Variable");
                }
                case "NR" -> {
                    mutarator = new NakedReceiver();
                    System.out.println();
                    System.out.println("Naked Receiver");
                }
                case "CC" -> {
                    mutarator = new ConstructorCall();
                    System.out.println();
                    System.out.println("Constructor Call");
                }
                case "ER" -> {
                    mutarator = new EmptyReturn();
                    System.out.println();
                    System.out.println("Empty Return");
                }
                case "FR" -> {
                    mutarator = new FalseReturn();
                    System.out.println();
                    System.out.println("False Return");
                }
                case "Null" -> {
                    mutarator = new NullReturn();
                    System.out.println();
                    System.out.println("Null Return");
                }
                case "PR" -> {
                    mutarator = new PrimitiveReturn();
                    System.out.println();
                    System.out.println("Primitive Return");
                }
                case "TR" -> {
                    mutarator = new TrueReturn();
                    System.out.println();
                    System.out.println("True Return");
                }
                case "VMC" -> {
                    mutarator = new VoidMethodCall();
                    System.out.println();
                    System.out.println("Void Method Call");
                }
                case "Increment" -> {
                    mutarator = new Increment();
                    System.out.println();
                    System.out.println("Increment");
                }
                case "IN" -> {
                    mutarator = new InvertNegatives();
                    System.out.println();
                    System.out.println("Invert Negatives");
                }
                case "Negation" -> {
                    mutarator = new Negation();
                    System.out.println();
                    System.out.println("Negation");
                }
                case "UOI" -> {
                    mutarator = new UnaryOperatorInsertion();
                    System.out.println();
                    System.out.println("Unary Operation Insertion");
                }
                case "CR" -> {
                    mutarator = new ConstantReplacementMutator();
                    System.out.println();
                    System.out.println("Constant Replacement");
                }
                case "Switch" -> {
                    mutarator = new Switch();
                    System.out.println();
                    System.out.println("Switch Block Mutation");
                }
            }
            System.out.println();

            if (mutarator == null) {
                continue;
            }

            for (int i = 0; i < numberOfFiles; i++) {
                CompilationUnit cu = codes.get(i);
                List<String> container = new ArrayList<>();
                classNameVisitor.visit(cu, container);

                mutarator.switchToCount();
                mutarator.resetPointCount();
                mutarator.visit(cu, null);
                currentMutantsNumber = mutarator.getMutantsNumber();

                if (currentMutantsNumber == 0) {
                    continue;
                }

                // print out current mutating file
                System.out.println("Mutating Class:");
                container.forEach(System.out::println);
                System.out.println();

                mutarator.switchToMutation();
                for (int j = 0; j < currentMutantsNumber; j++) {
                    mutarator.setMutantId(j);
                    mutarator.visit(cu, null);
                    mutarator.resetCallingCount();
                    ft.saveChanges();

                    try {
                        boolean isKilled = runProcess("mvn test");

                        if (isKilled) {
                            currentKilled ++;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    codes = ft.resumeFiles(codes);
                    cu = codes.get(i);
                }

                mutatorLevelKilled += currentKilled;
                mutatorLevelMutants += currentMutantsNumber;

                System.out.printf("Mutants Generated: %d, Mutants Killed: %d, Killing Rate(%%): %.2f%n",
                        currentMutantsNumber,
                        currentKilled,
                        (double)(currentKilled)/(double)currentMutantsNumber * 100
                );
                System.out.println();
                System.out.println("=============================================");
                currentKilled = 0;
            }

            if (mutatorLevelMutants == 0) {
                continue;
            }
            System.out.printf("Total Mutants Generated: %d, Total Mutants Killed: %d, Killing Rate (%%): %.2f%n",
                    mutatorLevelMutants,
                    mutatorLevelKilled,
                    (double)mutatorLevelKilled/(double)mutatorLevelMutants* 100
            );
            System.out.println();

            totalKilled += mutatorLevelKilled;
            totalMutants += mutatorLevelMutants;

            mutatorLevelKilled = 0;
            mutatorLevelMutants = 0;
        }

        System.out.println();
        System.out.println("Final Report:");
        System.out.printf("Total Mutants Generated: %d, Total Mutants Killed: %d, Killing Rate (%%): %.2f%n",
                totalMutants,
                totalKilled,
                (double)totalKilled/(double)totalMutants* 100
        );
        System.out.println();

        try {
            System.out.println("Clean up generated data");
            cleanup("mvn clean");
            cleanup("rm -r src/main/temp");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("End");

    }

}
