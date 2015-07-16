/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.ucd.clarity.bmf.GUI.DnDTrees;

import ie.ucd.clarity.bmf.GUI.common.BMFObjectGUI;
import java.awt.Toolkit;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * @author  Andrea
 */
public class TreeDragSource implements DragSourceListener, DragGestureListener {

    DragSource source;
    DragGestureRecognizer recognizer;
    /**
	 * @uml.property  name="transferable"
	 * @uml.associationEnd  
	 */
    TransferableTreeNode transferable;
    DefaultMutableTreeNode oldNode;
    JTree sourceTree;

    public TreeDragSource(JTree tree, int actions) {
        sourceTree = tree;
        source = new DragSource();
        recognizer = source.createDefaultDragGestureRecognizer(sourceTree,
                actions, this);
    }

    /*
     * Drag Gesture Handler
     */
    public void dragGestureRecognized(DragGestureEvent dge) {

        // devo modificare questo codice per consentire la selezione multipla!
        TreePath path = sourceTree.getSelectionPath();

        BMFObjectGUI obj = getNodeFromTreePath(path);
        if (obj == null) {
            return;
        }
        transferable = new TransferableTreeNode(obj.setPath(path));
        source.startDrag(dge, DragSource.DefaultCopyDrop, transferable, this);

        // If you support dropping the node anywhere, you should probably
        // start with a valid move cursor:
        //source.startDrag(dge, DragSource.DefaultMoveDrop, transferable,
        // this);
    }

    /*
     * Drag Event Handlers
     */
    public void dragEnter(DragSourceDragEvent dsde) {
    }

    public void dragExit(DragSourceEvent dse) {
    }

    public void dragOver(DragSourceDragEvent dsde) {
    }

    public void dropActionChanged(DragSourceDragEvent dsde) {
        System.out.println("Action: " + dsde.getDropAction());
        System.out.println("Target Action: " + dsde.getTargetActions());
        System.out.println("User Action: " + dsde.getUserAction());
    }

    public void dragDropEnd(DragSourceDropEvent dsde) {
        /*
         * to support move or copy, we have to check which occurred:
         */
        if (dsde.getDropSuccess() && sourceTree instanceof GroupsTree) {
            ((GroupsTree) sourceTree).removeNodeFromGroup(oldNode);
        }
        if (!dsde.getDropSuccess()) {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    private BMFObjectGUI getNodeFromTreePath(TreePath path) {
        if (!(((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject() instanceof BMFObjectGUI)) {
            Toolkit.getDefaultToolkit().beep();
            return null;
        } else {
            oldNode = (DefaultMutableTreeNode) path.getLastPathComponent();
            BMFObjectGUI ret = (BMFObjectGUI) oldNode.getUserObject();
            return ret;
        }

    }
}

