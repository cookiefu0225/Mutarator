package edu.illinois.mutarator.experimental;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.stmt.SwitchStmt;
import edu.illinois.mutarator.Mutarator;

import java.util.List;

public class Switch extends Mutarator {
//    NodeList<Expression> labels;
//    public NodeList<Expression> getLabels(){
//        return this.labels;
//    }
    public void visit(SwitchStmt ss, Object obj){
        super.visit(ss, obj);
        if (mutateMode) {
            NodeList<SwitchEntry> ses = ss.getEntries();
            if(ses.size()>0){
//                SwitchEntry se1 = ses.get(0);
                for(int i = 0; i < ses.size(); i++){
                    SwitchEntry se = ses.get(i);
                    if(se.getLabels().size() == 0){//default
                        if(mutantsId == callingCount){
                            ses.set(0, se.clone());
                            ses.remove(i);


                        }
                        callingCount++;
                        break;

                    }
                }
            }

        } else {
            NodeList<SwitchEntry> ses = ss.getEntries();
            if(ses.size()>0){
                for(SwitchEntry se : ses){
//                    this.labels = se.getLabels();
                    if(se.getLabels().size() == 0){//default
                        pointCount++;
                        break;
                    }
                }
            }
        }
    }
}
