/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.ucd.clarity.bmf.GUI.common.formulas;

import ie.ucd.clarity.bmf.GUI.common.BMFNodeGUI;
import ie.ucd.clarity.bmf.GUI.common.BMFSensorGUI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Andrea
 */
public class UtilList {

    public static ArrayList<BMFNodeGUI> intersect(ArrayList<BMFNodeGUI> a, ArrayList<BMFNodeGUI> b) {
        ArrayList<BMFNodeGUI> lstIntersectAB = new ArrayList<BMFNodeGUI>(a);
        lstIntersectAB.retainAll(b);
        return lstIntersectAB;
    }
    public static ArrayList<BMFSensorGUI> intersectSensors(ArrayList<BMFSensorGUI> a, ArrayList<BMFSensorGUI> b) {
        ArrayList<BMFSensorGUI> lstIntersectAB = new ArrayList<BMFSensorGUI>(a);
        lstIntersectAB.retainAll(b);
        return lstIntersectAB;
    }

    public static ArrayList<BMFNodeGUI> union(ArrayList<BMFNodeGUI> a, ArrayList<BMFNodeGUI> b) {
        Set<BMFNodeGUI> union = new HashSet<BMFNodeGUI>(a);
        union.addAll(new HashSet<BMFNodeGUI>(b));
        return new ArrayList<BMFNodeGUI>(union);
    }

    public static ArrayList<BMFNodeGUI> difference(ArrayList<BMFNodeGUI> from, ArrayList<BMFNodeGUI> to) {
        ArrayList<BMFNodeGUI> from1=new ArrayList<BMFNodeGUI>(from);
        from1.removeAll(to);
        return from1;
    }
}
