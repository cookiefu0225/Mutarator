package edu.illinois.mutarator.experimental;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import edu.illinois.mutarator.Mutarator;


public class MemberVariable extends Mutarator {
    @Override
    public void visit(FieldDeclaration fd, Object obj) {
        super.visit(fd, obj);
        if(mutateMode){
            fd.walk(VariableDeclarator.class, vd-> {
                vd.walk(PrimitiveType.class, pt -> {
                    switch (pt.getType()) {
                        case BOOLEAN -> {
                            if (mutantsId == callingCount) {
                                vd.setInitializer(new BooleanLiteralExpr());
                            }
                            callingCount++;
                        }
                        case INT, BYTE, SHORT, LONG -> {
                            if (mutantsId == callingCount) {
                                vd.setInitializer(new IntegerLiteralExpr());
                            }
                            callingCount++;
                        }
                        case FLOAT, DOUBLE -> {
                            if (mutantsId == callingCount) {
                                vd.setInitializer(new DoubleLiteralExpr(0.0));
                            }
                            callingCount++;
                        }
                        case CHAR -> {
                            if (mutantsId == callingCount) {
                                vd.setInitializer(new CharLiteralExpr('\u0000'));
                            }
                            callingCount++;
                        }
                    }
                });
                vd.walk(ClassOrInterfaceType.class, cit -> {
                    if (mutantsId == callingCount) {
                        vd.setInitializer(new NullLiteralExpr());
                    }
                    callingCount++;


                });
            });
        } else{
            fd.walk(VariableDeclarator.class, vd->{
                vd.walk(PrimitiveType.class, pt -> {
                    pointCount++;
                });
                vd.walk(ClassOrInterfaceType.class, cit -> {
                    pointCount++;
                });
            });
        }

    }
}
