/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.ucd.clarity.bmf.GUI.DnDTrees;

import java.util.Enumeration;
import java.util.Vector;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Andrea
 */
public class DefaultMutableTreeNodeClonable extends DefaultMutableTreeNode {

    public DefaultMutableTreeNodeClonable(Object userObject, boolean allowsChildren) {
        super(userObject, allowsChildren);
    }

    public DefaultMutableTreeNodeClonable(DefaultMutableTreeNode n) {
        this.userObject = n.getUserObject();
        this.children=new Vector();
        Enumeration e=n.children();
        while (e.hasMoreElements()) {
            this.children.add(e.nextElement());
        }
    }

    public DefaultMutableTreeNodeClonable(Object userObject) {
        super(userObject);
    }

    public DefaultMutableTreeNodeClonable() {
        super();
    }

}
