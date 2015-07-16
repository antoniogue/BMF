/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.ucd.clarity.bmf.GUI.DnDTrees;

import ie.ucd.clarity.bmf.GUI.common.BMFGroupGUI;
import ie.ucd.clarity.bmf.GUI.common.BMFNodeGUI;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetContext;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * @author  Andrea
 */
public class TreeDropTarget implements DropTargetListener {

    DropTarget target;
    /**
	 * @uml.property  name="targetTree"
	 * @uml.associationEnd  
	 */
    GroupsTree targetTree;

    public TreeDropTarget(GroupsTree tree) {
        targetTree = tree;
        target = new DropTarget(targetTree, this);
    }

    /*
     * Drop Event Handlers
     */
    private DefaultMutableTreeNode getNodeForEvent(DropTargetDragEvent dtde) {
        Point p = dtde.getLocation();
        DropTargetContext dtc = dtde.getDropTargetContext();
        JTree tree = (JTree) dtc.getComponent();
        TreePath path = tree.getClosestPathForLocation(p.x, p.y);
        return (DefaultMutableTreeNode) path.getLastPathComponent();

    }

    public void dragEnter(DropTargetDragEvent dtde) {
        DefaultMutableTreeNode node = getNodeForEvent(dtde);
        if (!(node.getUserObject() instanceof BMFGroupGUI)) {
            dtde.rejectDrag();
        } else {
            dtde.acceptDrag(dtde.getDropAction());
        }
    }

    public void dragOver(DropTargetDragEvent dtde) {
        DefaultMutableTreeNode node = getNodeForEvent(dtde);
        if (!(node.getUserObject() instanceof BMFGroupGUI)) {
            dtde.rejectDrag();
        } else {
            dtde.acceptDrag(dtde.getDropAction());
        }
    }

    public void dragExit(DropTargetEvent dte) {
    }

    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    public void drop(DropTargetDropEvent dtde) {
        Point pt = dtde.getLocation();
        DropTargetContext dtc = dtde.getDropTargetContext();
        GroupsTree tree = (GroupsTree) dtc.getComponent();
        TreePath parentpath = tree.getClosestPathForLocation(pt.x, pt.y);
        DefaultMutableTreeNode parent = (DefaultMutableTreeNode) parentpath.getLastPathComponent();

        try {
            Transferable tr = dtde.getTransferable();
            DataFlavor[] flavors = tr.getTransferDataFlavors();
            for (int i = 0; i < flavors.length; i++) {
                if (tr.isDataFlavorSupported(flavors[i])) {
                    dtde.acceptDrop(dtde.getDropAction());
                    dtde.dropComplete(tree.addNodeToGroup((BMFNodeGUI) tr.getTransferData(flavors[i]), parent));
                    return;
                }
            }
            dtde.rejectDrop();
        } catch (Exception e) {
            e.printStackTrace();
            dtde.rejectDrop();
        }
    }

    private void p(String string) {
        System.out.println(string);
    }
}

