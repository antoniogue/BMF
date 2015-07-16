package ie.ucd.clarity.bmf.GUI.common.formulas;

import ie.ucd.clarity.bmf.GUI.common.BMFGroupGUI;
import ie.ucd.clarity.bmf.GUI.common.BMFNodeGUI;

import java.util.ArrayList;

public class EvaluationVisitor
        implements Visitor {

    ArrayList<BMFNodeGUI> allnodes;
    ArrayList<BMFNodeGUI> value;

    public EvaluationVisitor(ArrayList<BMFNodeGUI> allnodes) {
        this.allnodes = allnodes;
        value = new ArrayList<BMFNodeGUI>();
    }

    public void visit(Operator o) {
        o.left().accept(this);
        ArrayList<BMFNodeGUI> v1 = new ArrayList<BMFNodeGUI>(value);
        if (o instanceof Not) {
            value = UtilList.difference(allnodes, v1);
            return;
        }
        o.right().accept(this);
        ArrayList<BMFNodeGUI> v2 = new ArrayList<BMFNodeGUI>(value);
        if (o instanceof Intersection) {
        	value = UtilList.intersect(v1, v2);
        } else {            
            value = UtilList.union(v1, v2);
        }
    } //visit

    public void visit(Group group) {
        value = group.getNodes();
    }

    public ArrayList<BMFNodeGUI> valueOf(Expression e) {
        e.accept(this);
        return value;
    } //valueOf
}
