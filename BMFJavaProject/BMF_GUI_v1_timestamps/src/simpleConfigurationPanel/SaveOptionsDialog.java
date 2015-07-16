/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NewNodeDialog.java
 *
 * Created on 24-feb-2010, 14.49.56
 */

package simpleConfigurationPanel;

import ie.ucd.clarity.bmf.network.manager.BMFGroup;
import ie.ucd.clarity.bmf.network.manager.BMFNode;

import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Antonio
 */
public class SaveOptionsDialog extends javax.swing.JDialog {

    private JCheckBox saveTimestampsJCheckBox;

	/** Creates new form NewNodeDialog */
    public SaveOptionsDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        savingChoice = new javax.swing.ButtonGroup();
        nodeJLabel = new javax.swing.JLabel();
        NodeInformationJLabel = new javax.swing.JLabel();
        allNodesJRadioButton = new javax.swing.JRadioButton();
        saveTimestampsJCheckBox = new javax.swing.JCheckBox();
        onlyGroupsJRadioButton = new javax.swing.JRadioButton();
        onlyNodesJRadioButton = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        nodesJList = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        groupsJList = new javax.swing.JList();
        cancelJButton = new javax.swing.JButton();
        OKJButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Options");
        setAlwaysOnTop(true);

        nodeJLabel.setFont(new java.awt.Font("Tahoma", 1, 14));
        nodeJLabel.setText("Saving Options");

        NodeInformationJLabel.setText("SAVE DATA FROM");

        savingChoice.add(allNodesJRadioButton);
        allNodesJRadioButton.setSelected(true);
        allNodesJRadioButton.setText("All the Nodes");
        allNodesJRadioButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        allNodesJRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allNodesJRadioButtonActionPerformed(evt);
            }
        });

        savingChoice.add(onlyGroupsJRadioButton);
        onlyGroupsJRadioButton.setText("Only Groups:");
        onlyGroupsJRadioButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        onlyGroupsJRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onlyGroupsJRadioButtonActionPerformed(evt);
            }
        });

        savingChoice.add(onlyNodesJRadioButton);
        onlyNodesJRadioButton.setText("Only Nodes:");
        onlyNodesJRadioButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        onlyNodesJRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onlyNodesJRadioButtonActionPerformed(evt);
            }
        });

        nodesJList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Node 1", "Node 2", "Node 3", "Node 4", "Node 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        nodesJList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        nodesJList.setEnabled(false);
        jScrollPane1.setViewportView(nodesJList);

        groupsJList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Ambiental Light", "Light State", "Presence Detector" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        groupsJList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        groupsJList.setEnabled(false);
        jScrollPane2.setViewportView(groupsJList);

        
        
        
        
        
        
        
        
        
        
        saveTimestampsJCheckBox.setText("Save Timestamps");
        saveTimestampsJCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        saveTimestampsJCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	saveTimestampsJCheckBoxActionPerformed(evt);
            }
        });
        
        
        
        
        
        
        
        
        
        
        
        
        cancelJButton.setFont(new java.awt.Font("Tahoma", 1, 11));
        cancelJButton.setIcon((new GetIcon()).getCancelIcon());
        cancelJButton.setText("");
        cancelJButton.setToolTipText("CANCEL");
        cancelJButton.setBorder(null);
        cancelJButton.setBorderPainted(false);
        cancelJButton.setContentAreaFilled(false);
        cancelJButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cancelJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelJButtonActionPerformed(evt);
            }
        });

        OKJButton1.setFont(new java.awt.Font("Tahoma", 1, 11));
        OKJButton1.setIcon((new GetIcon()).getOKIcon());
        OKJButton1.setText("");
        OKJButton1.setToolTipText("OK");
        OKJButton1.setBorder(null);
        OKJButton1.setBorderPainted(false);
        OKJButton1.setContentAreaFilled(false);
        OKJButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        OKJButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKJButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(NodeInformationJLabel)
                            .addComponent(nodeJLabel))
                        .addGap(385, 385, 385))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(allNodesJRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(onlyGroupsJRadioButton)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(onlyNodesJRadioButton)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(53, 53, 53))
                    .addComponent(saveTimestampsJCheckBox)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cancelJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(OKJButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        
        
        //ORIZZONTAL OLD
//        layout.setHorizontalGroup(
//                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                .addGroup(layout.createSequentialGroup()
//                    .addContainerGap()
//                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(layout.createSequentialGroup()
//                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                                .addComponent(NodeInformationJLabel)
//                                .addComponent(nodeJLabel))
//                            .addGap(385, 385, 385))
//                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
//                            .addComponent(allNodesJRadioButton)
//                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
//                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                                .addComponent(onlyGroupsJRadioButton)
//                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
//                            .addGap(42, 42, 42)
//                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                                .addComponent(onlyNodesJRadioButton)
//                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
//                            .addGap(53, 53, 53))
//                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
//                            .addComponent(cancelJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
//                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                            .addComponent(OKJButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
//                    .addContainerGap())
//            );
//        
        
        
         
        
        
        
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nodeJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NodeInformationJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(allNodesJRadioButton)
                    .addComponent(onlyGroupsJRadioButton)
                    .addComponent(onlyNodesJRadioButton))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(saveTimestampsJCheckBox)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(OKJButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        
        
///		VERTICAL GROUP ORIGINAL        
//        layout.setVerticalGroup(
//                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                .addGroup(layout.createSequentialGroup()
//                    .addContainerGap()
//                    .addComponent(nodeJLabel)
//                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                    .addComponent(NodeInformationJLabel)
//                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                        .addComponent(allNodesJRadioButton)
//                        .addComponent(onlyGroupsJRadioButton)
//                        .addComponent(onlyNodesJRadioButton))
//                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGroup(layout.createSequentialGroup()
//                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
//                        .addGroup(layout.createSequentialGroup()
//                            .addGap(3, 3, 3)
//                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)))
//                    .addGap(18, 18, 18)
//                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                        .addComponent(OKJButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
//                        .addComponent(cancelJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
//                    .addContainerGap())
//            );
        
        

        pack();
    }// </editor-fold>//GEN-END:initComponents

    protected void saveTimestampsJCheckBoxActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		
	}

	private void allNodesJRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allNodesJRadioButtonActionPerformed
        groupsJList.setEnabled(false);
        nodesJList.setEnabled(false);
    }//GEN-LAST:event_allNodesJRadioButtonActionPerformed

    private void onlyGroupsJRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onlyGroupsJRadioButtonActionPerformed
        groupsJList.setEnabled(true);
        nodesJList.setEnabled(false);
    }//GEN-LAST:event_onlyGroupsJRadioButtonActionPerformed

    private void onlyNodesJRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onlyNodesJRadioButtonActionPerformed
        groupsJList.setEnabled(false);
        nodesJList.setEnabled(true);
    }//GEN-LAST:event_onlyNodesJRadioButtonActionPerformed

    private void cancelJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelJButtonActionPerformed
this.dispose();
}//GEN-LAST:event_cancelJButtonActionPerformed

    private void OKJButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKJButton1ActionPerformed

        if(allNodesJRadioButton.isSelected()){
            dad.isStoringAllData = true;
        }
        else if(onlyGroupsJRadioButton.isSelected()){
            dad.isStoringAllData = false;
            dad.isStoringNodes = false;

            Vector<BMFGroup> groups = new Vector();
            Object[] groupsObj = groupsJList.getSelectedValues();

            if(groupsObj.length == 0){
                JOptionPane.showMessageDialog(this, "No Group Selected!");
                return;
            }

            for(int i=0; i<groupsObj.length; i++){
                groups.addElement((BMFGroup)groupsObj[i]);
            }

            dad.storingInBMFGroupVector = groups;
        }
        else if(onlyNodesJRadioButton.isSelected()){
            dad.isStoringAllData = false;
            dad.isStoringNodes = true;

            Vector<BMFNode> nodes = new Vector();
            Object[] nodesObj = nodesJList.getSelectedValues();

            if(nodesObj.length == 0){
                JOptionPane.showMessageDialog(this, "No Node Selected!");
                return;
            }

             for(int i=0; i<nodesObj.length; i++){
                nodes.addElement((BMFNode)nodesObj[i]);
            }

            dad.storingInBMFNodeVector = nodes;
        }
        
        
        
        /// ADDED on the 18/02/2014
        
        if(saveTimestampsJCheckBox.isSelected()) {
        	dad.isStoringTimestamps = true;
        	System.out.println("*** Timestamps will be stored in the csv file ;)  ***");
        }
        else {
        	dad.isStoringTimestamps = false;
        	System.out.println("*** Timestamps will NOT be stored in the csv file :(  ***");
        }
        

        this.dispose();
    }//GEN-LAST:event_OKJButton1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SaveOptionsDialog dialog = new SaveOptionsDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel NodeInformationJLabel;
    private javax.swing.JButton OKJButton1;
    private javax.swing.JRadioButton allNodesJRadioButton;
    private javax.swing.JButton cancelJButton;
    private javax.swing.JList groupsJList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel nodeJLabel;
    private javax.swing.JList nodesJList;
    private javax.swing.JRadioButton onlyGroupsJRadioButton;
    private javax.swing.JRadioButton onlyNodesJRadioButton;
    private javax.swing.ButtonGroup savingChoice;
    // End of variables declaration//GEN-END:variables

    GUIFrame dad;

    void setDadAndPopulate(GUIFrame dad) {
        this.dad = dad;

        groupsJList.setListData(dad.BMFGroupVector);
        nodesJList.setListData(dad.BMFNodeVector);

        if(dad.isStoringAllData){
            allNodesJRadioButton.setSelected(true);
            onlyGroupsJRadioButton.setSelected(false);
            onlyNodesJRadioButton.setSelected(false);
            groupsJList.setEnabled(false);
            nodesJList.setEnabled(false);
        }
        else if(dad.isStoringNodes){
            allNodesJRadioButton.setSelected(false);
            onlyGroupsJRadioButton.setSelected(false);
            onlyNodesJRadioButton.setSelected(true);
            groupsJList.setEnabled(false);
            nodesJList.setEnabled(true);

            int[] selectedIndices = new int[dad.storingInBMFNodeVector.size()];

            for(int j=0; j<selectedIndices.length; j++){
                for(int i=0;i<dad.BMFNodeVector.size();i++){
                    if(dad.storingInBMFNodeVector.elementAt(j).equals(dad.BMFNodeVector.elementAt(i))){
                        selectedIndices[j] = i;
                        break;
                    }

                }
            }
            if(selectedIndices.length>0) nodesJList.setSelectedIndices(selectedIndices);
        }
        else{ // is storing groups
            allNodesJRadioButton.setSelected(false);
            onlyGroupsJRadioButton.setSelected(true);
            onlyNodesJRadioButton.setSelected(false);
            groupsJList.setEnabled(true);
            nodesJList.setEnabled(false);

            int[] selectedIndices = new int[dad.storingInBMFGroupVector.size()];

            for(int j=0; j<selectedIndices.length; j++){
                for(int i=0;i<dad.BMFGroupVector.size();i++){
                    if(dad.storingInBMFGroupVector.elementAt(j).equals(dad.BMFGroupVector.elementAt(i))){
                        selectedIndices[j] = i;
                        break;
                    }

                }
            }
            if(selectedIndices.length>0) groupsJList.setSelectedIndices(selectedIndices);
            
        }
        
        //added on the 18/02/2014
        if(dad.isStoringTimestamps) {
        	saveTimestampsJCheckBox.setSelected(true);
        	System.out.println("*** Timestamps will be stored in the csv file ***");
        }
    }

    
}
