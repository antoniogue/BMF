/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LeaveGroupFrame.java
 *
 * Created on 24-ago-2010, 17.13.30
 */
package ie.ucd.clarity.bmf.GUI;

import ie.ucd.clarity.bmf.GUI.common.BMFGroupGUI;
import ie.ucd.clarity.bmf.GUI.common.BMFNodeGUI;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author  Andrea
 */
public class LeaveGroupFrame extends javax.swing.JFrame {

    /**
	 * Creates new form LeaveGroupFrame
	 * @uml.property  name="node"
	 * @uml.associationEnd  
	 */
    BMFNodeGUI node;
    /**
	 * @uml.property  name="frame"
	 * @uml.associationEnd  
	 */
    InitialFrame frame;
    ArrayList<GroupCheckBox> groups;

    public LeaveGroupFrame(InitialFrame frame, BMFNodeGUI node) {
        this.node = node;
        this.frame = frame;
        groups = new ArrayList<GroupCheckBox>();
        initComponents();
        createButtons();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        leaveButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        panel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        leaveButton.setText("Leave Group/s");
        leaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leaveButtonActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(node.getName()));

        panel.setLayout(new java.awt.GridLayout(1, 1));
        jScrollPane1.setViewportView(panel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(leaveButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(leaveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void leaveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leaveButtonActionPerformed

        this.dispose();
        ArrayList<DefaultMutableTreeNode> nodefromgroupTree = frame.getGroupsPanel().getTree().getGroupsContains(node);
        for (int i = 0, j=0; i < groups.size() && j<nodefromgroupTree.size(); i++) {
            GroupCheckBox cb = groups.get(i);
            if (cb.isSelected()) {
                frame.getGroupsPanel().getTree().removeNodeFromGroup(nodefromgroupTree.get(index(cb.getGroup(), nodefromgroupTree)));
            }
        }        
    }//GEN-LAST:event_leaveButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton leaveButton;
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables

    private void createButtons() {
        
        panel.setLayout(new GridLayout(node.getGroups().size(), 1));
        for (int i = 0; i < node.getGroups().size(); i++) {
            GroupCheckBox c = new GroupCheckBox(node.getGroups().get(i));
            c.setText(node.getGroups().get(i).getName());
            groups.add(c);
            panel.add(c);
        }
    }

    private int index(BMFGroupGUI group, ArrayList<DefaultMutableTreeNode> nodefromgroupTree) {
        int ret=-1;
        DefaultMutableTreeNode parent=null;
        for(int i=0; i<nodefromgroupTree.size();i++){
            parent=(DefaultMutableTreeNode) nodefromgroupTree.get(i).getParent();
            if(((BMFGroupGUI)parent.getUserObject()).equals(group)){
                return i;
            }
        }
        return ret;
    }
}

/**
 * @author  Matthew
 */
class GroupCheckBox extends JCheckBox {

    /**
	 * @uml.property  name="group"
	 * @uml.associationEnd  
	 */
    BMFGroupGUI group;

    public GroupCheckBox(BMFGroupGUI group) {
        super();
        this.group = group;
    }

    /**
	 * @return
	 * @uml.property  name="group"
	 */
    public BMFGroupGUI getGroup() {
        return group;
    }
}