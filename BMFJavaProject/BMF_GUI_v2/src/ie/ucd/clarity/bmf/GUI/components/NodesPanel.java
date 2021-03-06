/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NodesPanel.java
 *
 * Created on 5-dic-2010, 19.18.26
 */
package ie.ucd.clarity.bmf.GUI.components;

import ie.ucd.clarity.bmf.GUI.DnDTrees.NodesTree;
import ie.ucd.clarity.bmf.GUI.InitialFrame;
import ie.ucd.clarity.bmf.GUI.common.BMFNodeGUI;
import ie.ucd.clarity.bmf.GUI.common.BMFSensorGUI;
import ie.ucd.clarity.bmf.GUI.components.dnd.DropPanel;
import ie.ucd.clarity.bmf.GUI.notification.NewNodeIn;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * @author  Andrea
 */
public class NodesPanel extends javax.swing.JPanel implements Observer {

    DefaultMutableTreeNode top;
    ArrayList<BMFSensorGUI> namesensors;
    /**
	 * @uml.property  name="frame"
	 * @uml.associationEnd  
	 */
    InitialFrame frame;

    /** Creates new form NodesPanel */
    public NodesPanel() {
        initComponents();
    }

    public NodesPanel(InitialFrame frame) {
        this.frame = frame;
        top = this.frame.getBasestation();
        initComponents();
        getTree().setFrame(this.frame);
        getTree().createDragSource();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tree = new ie.ucd.clarity.bmf.GUI.DnDTrees.NodesTree(top);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tree.setToolTipText("Drag and Drop Nodes in the Groups Tree");
        tree.setName("tree"); // NOI18N
        jScrollPane1.setViewportView(tree);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    /**
	 * @uml.property  name="tree"
	 */
    private javax.swing.JTree tree;
    // End of variables declaration//GEN-END:variables

    public DefaultTreeCellRenderer getTreeRenderer() {
        return (DefaultTreeCellRenderer) tree.getCellRenderer();
    }

    public void addNewNode(BMFNodeGUI n) {
        DefaultMutableTreeNode newNode = getTree().addNewNode(n);
        if (newNode != null) {
            getGroupsPanel().createGroups(newNode);
            frame.print("Node" + n.getInode().getNodeID() + " joined to network");
        }
    }

    public DropPanel getFloorplan() {
        return frame.getSelectedFloorPlan();
    }

    public GroupsPanel getGroupsPanel() {
        return frame.getGroupsPanel();
    }

    /**
	 * @return
	 * @uml.property  name="tree"
	 */
    public NodesTree getTree() {
        return (NodesTree) tree;
    }

    public void update(Observable o, Object arg) {
        if (arg instanceof NewNodeIn) {
            NewNodeIn newn = (NewNodeIn) arg;
            BMFNodeGUI n = new BMFNodeGUI(newn.getNewNode());
            addNewNode(n);
            addToFloorPlan(n);
        }
    }

    private void addToFloorPlan(BMFNodeGUI n) {
       if (getFloorplan() == null) {
                return;
            }
            getFloorplan().addDropComponent(0, 0, n);
    }
}

