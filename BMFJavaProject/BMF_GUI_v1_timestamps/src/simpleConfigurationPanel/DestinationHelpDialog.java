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

/**
 *
 * @author Antonio
 */
public class DestinationHelpDialog extends javax.swing.JDialog {

    /** Creates new form NewNodeDialog */
    public DestinationHelpDialog(java.awt.Frame parent, boolean modal) {
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

        nodeJLabel = new javax.swing.JLabel();
        imageJLabel = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        explanationJTextArea2 = new javax.swing.JTextArea();
        OKJButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("info");
        setAlwaysOnTop(true);

        nodeJLabel.setFont(new java.awt.Font("Tahoma", 1, 14));
        nodeJLabel.setText("DESTINATION HELP");

        imageJLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bmf/images/logomini.gif"))); // NOI18N

        jScrollPane5.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane5.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        explanationJTextArea2.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        explanationJTextArea2.setColumns(20);
        explanationJTextArea2.setEditable(false);
        explanationJTextArea2.setFont(new java.awt.Font("Tahoma", 0, 11));
        explanationJTextArea2.setLineWrap(true);
        explanationJTextArea2.setRows(3);
        explanationJTextArea2.setTabSize(3);
        explanationJTextArea2.setText("To send a packet, select an individual node or select more groups and combine them with the operators. To add Nodes or Groups press the buttons below.");
        explanationJTextArea2.setWrapStyleWord(true);
        explanationJTextArea2.setBorder(null);
        jScrollPane5.setViewportView(explanationJTextArea2);

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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(nodeJLabel)
                                .addGap(73, 73, 73))
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(imageJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(OKJButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(nodeJLabel)
                        .addGap(11, 11, 11)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(imageJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(OKJButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void OKJButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKJButton1ActionPerformed
        this.dispose();
}//GEN-LAST:event_OKJButton1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DestinationHelpDialog dialog = new DestinationHelpDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton OKJButton1;
    private javax.swing.JTextArea explanationJTextArea2;
    private javax.swing.JLabel imageJLabel;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel nodeJLabel;
    // End of variables declaration//GEN-END:variables

  

}
