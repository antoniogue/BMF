/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * InitialFrame.java
 *
 * Created on 5-dic-2010, 16.27.52
 */
package ie.ucd.clarity.bmf.GUI;

import ie.ucd.clarity.bmf.GUI.common.BMFRequestObjectGUI;
import ie.ucd.clarity.bmf.GUI.common.ChartsObject;
import ie.ucd.clarity.bmf.GUI.common.Util;
import ie.ucd.clarity.bmf.GUI.components.AddTabButton;
import ie.ucd.clarity.bmf.GUI.components.CloseTabButton;
import ie.ucd.clarity.bmf.GUI.components.ComboBoxRequestsModel;
import ie.ucd.clarity.bmf.GUI.components.GroupsPanel;
import ie.ucd.clarity.bmf.GUI.components.NodesPanel;
import ie.ucd.clarity.bmf.GUI.components.dnd.DropPanel;
import ie.ucd.clarity.bmf.GUI.notification.DataIn;
import ie.ucd.clarity.bmf.GUI.notification.NewNodeIn;
import ie.ucd.clarity.bmf.GUI.notification.NewRequest;
import ie.ucd.clarity.bmf.GUI.notification.RequestExpired;
import ie.ucd.clarity.bmf.GUI.notification.ToPrint;
import ie.ucd.clarity.bmf.common.AggregationItem;
import ie.ucd.clarity.bmf.common.CodeConversion;
import ie.ucd.clarity.bmf.common.Constants;
import ie.ucd.clarity.bmf.common.InvalidPacketParametersException;
import ie.ucd.clarity.bmf.communication.IConfigurationPacket;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import org.jfree.ui.RefineryUtilities;

/**
 * @author  Andrea
 */
public class InitialFrame extends javax.swing.JFrame implements Observer {

    ArrayList<DropPanel> floorPlans;
    ArrayList<BMFRequestObjectGUI> requests;
    ArrayList<ChartsObject> chartInternal;
    /**
	 * @uml.property  name="chartsFram"
	 * @uml.associationEnd  
	 */
    ChartsFrame chartsFram;
    /**
	 * @uml.property  name="consumer"
	 * @uml.associationEnd  
	 */
    Consumer consumer;
    private Dimension screensize;
    /**
	 * @uml.property  name="scheduledRequestFrame"
	 * @uml.associationEnd  
	 */
    private ScheduledRequestFrame scheduledRequestFrame;
    /**
	 * @uml.property  name="_this"
	 * @uml.associationEnd  
	 */
    InitialFrame _this = this;
    /**
	 * @uml.property  name="basestation"
	 */
    private DefaultMutableTreeNode basestation;
    private JFileChooser csvFileChooser;
    /**
	 * @uml.property  name="requestsFrame"
	 * @uml.associationEnd  
	 */
    RequestsFrame requestsFrame;
    ArrayList<Long> results = new ArrayList<Long>();

    /** Creates new form InitialFrame */
    public InitialFrame() {
        initComponents();
    }

    public InitialFrame(Consumer consumer) {
        this.consumer = consumer;
        this.basestation = consumer.getBasestation();
        requests = new ArrayList<BMFRequestObjectGUI>();
        chartInternal = new ArrayList<ChartsObject>();
        initComponents();
        floorPlans = new ArrayList<DropPanel>();
        csvFileChooser = new JFileChooser();
        RefineryUtilities.centerFrameOnScreen(this);
        screensize = Toolkit.getDefaultToolkit().getScreenSize();
        scheduledRequestFrame = new ScheduledRequestFrame(requests, this);
        showAllGraphCheckBox.setEnabled(false);
        this.setBounds(0, 0, screensize.width, screensize.height - 30);
        requestsFrame = new RequestsFrame();
        setTabbedPane();
        addObservers();
        print("APPLICATION STARTED!");
    }

    /**
	 * @return
	 * @uml.property  name="requestsFrame"
	 */
    public RequestsFrame getRequestsFrame() {
        return requestsFrame;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        nodesTreePanel = new ie.ucd.clarity.bmf.GUI.components.NodesPanel(this);
        jPanel2 = new javax.swing.JPanel();
        groupsTreePanel1 = new ie.ucd.clarity.bmf.GUI.components.GroupsPanel(this);
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        requestComboBox = new javax.swing.JComboBox();
        showRequestChartButton = new javax.swing.JButton();
        showDetailRequestButton = new javax.swing.JButton();
        AllRequestsDetailsButton = new javax.swing.JButton();
        showAllGraphCheckBox = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        console = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        mapsTabPane = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        graphsTabPane = new javax.swing.JTabbedPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuItemExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        openCVSMenuItem = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("BMF");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "NODES MANAGEMENT", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel1.setName("jPanel1"); // NOI18N

        nodesTreePanel.setName("nodesTreePanel"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(nodesTreePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(nodesTreePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "GROUPS MANAGEMENT", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel2.setName("jPanel2"); // NOI18N

        groupsTreePanel1.setName("groupsTreePanel1"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(groupsTreePanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(groupsTreePanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("REQUESTS"));
        jPanel3.setName("jPanel3"); // NOI18N

        jLabel1.setText("Request:");
        jLabel1.setName("jLabel1"); // NOI18N

        requestComboBox.setModel(new ie.ucd.clarity.bmf.GUI.components.ComboBoxRequestsModel(requests));
        requestComboBox.setName("requestComboBox"); // NOI18N
        requestComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                requestComboBoxActionPerformed(evt);
            }
        });

        showRequestChartButton.setText("Chart");
        showRequestChartButton.setName("showRequestChartButton"); // NOI18N
        showRequestChartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showRequestChartButtonActionPerformed(evt);
            }
        });

        showDetailRequestButton.setText("Detail");
        showDetailRequestButton.setName("showDetailRequestButton"); // NOI18N
        showDetailRequestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showDetailRequestButtonActionPerformed(evt);
            }
        });

        AllRequestsDetailsButton.setText("All Requests Details");
        AllRequestsDetailsButton.setName("AllRequestsDetailsButton"); // NOI18N
        AllRequestsDetailsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AllRequestsDetailsButtonActionPerformed(evt);
            }
        });

        showAllGraphCheckBox.setText("Show All Graphs");
        showAllGraphCheckBox.setName("showAllGraphCheckBox"); // NOI18N
        showAllGraphCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showAllGraphCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(requestComboBox, 0, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(showRequestChartButton, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(showDetailRequestButton))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(showAllGraphCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addComponent(AllRequestsDetailsButton))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(requestComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showRequestChartButton)
                    .addComponent(showDetailRequestButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AllRequestsDetailsButton)
                    .addComponent(showAllGraphCheckBox)))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CONSOLE", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel4.setName("jPanel4"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        console.setColumns(20);
        console.setEditable(false);
        console.setRows(5);
        console.setName("console"); // NOI18N
        jScrollPane1.setViewportView(console);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "MAPS AND GRAPHS", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel5.setName("jPanel5"); // NOI18N

        jSplitPane1.setDividerLocation(5);
        jSplitPane1.setName("jSplitPane1"); // NOI18N

        mapsTabPane.setName("mapsTabPane"); // NOI18N

        jPanel6.setName("jPanel6"); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 839, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 467, Short.MAX_VALUE)
        );

        mapsTabPane.addTab("", jPanel6);

        jSplitPane1.setRightComponent(mapsTabPane);

        graphsTabPane.setName("graphsTabPane"); // NOI18N
        jSplitPane1.setLeftComponent(graphsTabPane);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 855, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
        );

        jMenuBar1.setName("jMenuBar1"); // NOI18N

        jMenu1.setText("File");
        jMenu1.setName("jMenu1"); // NOI18N

        menuItemExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuItemExit.setIcon(new javax.swing.ImageIcon("images/close.gif"));
        menuItemExit.setText("Exit");
        menuItemExit.setName("menuItemExit"); // NOI18N
        menuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemExitActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemExit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Saving");
        jMenu2.setName("jMenu2"); // NOI18N

        jMenu3.setIcon(new javax.swing.ImageIcon("images/filesave.gif"));
        jMenu3.setText("File");
        jMenu3.setName("jMenu3"); // NOI18N

        openCVSMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_SPACE, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        openCVSMenuItem.setIcon(new javax.swing.ImageIcon("images/excel.gif"));
        openCVSMenuItem.setText("Open CSV File");
        openCVSMenuItem.setName("openCVSMenuItem"); // NOI18N
        openCVSMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openCVSMenuItemActionPerformed(evt);
            }
        });
        jMenu3.add(openCVSMenuItem);

        jMenu2.add(jMenu3);

        jMenu4.setIcon(new javax.swing.ImageIcon("images/databasesave.gif"));
        jMenu4.setText("DataBase");
        jMenu4.setName("jMenu4"); // NOI18N
        jMenu2.add(jMenu4);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void showRequestChartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showRequestChartButtonActionPerformed
        final BMFRequestObjectGUI req = (BMFRequestObjectGUI) requestComboBox.getModel().getSelectedItem();
        new Thread() {

            public void run() {
                if (requests.size() != 0) {
                    JFrame f = new JFrame(req.getName());
                    f.setSize(new Dimension(700, 500));
                    f.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - _this.getWidth()) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - _this.getHeight()) / 2);
                    f.add(req.getCharts().getComponent());
                    f.setVisible(true);
                } else {
                    new JOptionPane().showMessageDialog(_this, "No (Active/Not Active) Requests", "Requests Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        }.start();
    }//GEN-LAST:event_showRequestChartButtonActionPerformed

    private void showDetailRequestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showDetailRequestButtonActionPerformed
        final BMFRequestObjectGUI req = (BMFRequestObjectGUI) requestComboBox.getModel().getSelectedItem();
        new Thread() {

            public void run() {
                if (requests.size() != 0) {
                    new SingleRequestDetailsFrame(req).setVisible(true);
                } else {
                    new JOptionPane().showMessageDialog(_this, "No (Active/Not Active) Requests", "Requests Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        }.start();
    }//GEN-LAST:event_showDetailRequestButtonActionPerformed

    private void openCVSMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openCVSMenuItemActionPerformed
        final String s = loadCSVFile();
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new CSVReaderFrame(s).setVisible(true);
            }
        });
    }//GEN-LAST:event_openCVSMenuItemActionPerformed

    private void menuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_menuItemExitActionPerformed

    private void AllRequestsDetailsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AllRequestsDetailsButtonActionPerformed
        new Thread() {

            public void run() {
                scheduledRequestFrame.showGUI(requests);
            }
        }.start();
    }//GEN-LAST:event_AllRequestsDetailsButtonActionPerformed

    private void showAllGraphCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showAllGraphCheckBoxActionPerformed
        graphsTabPane.setVisible(showAllGraphCheckBox.isSelected());
        if (showAllGraphCheckBox.isSelected()) {
            for (int i = 0; i < chartInternal.size(); i++) {
                graphsTabPane.add(chartInternal.get(i).getComponent());
            }
        } else {
            graphsTabPane.removeAll();
        }
        jSplitPane1.setDividerLocation(jSplitPane1.getWidth() - 20);
}//GEN-LAST:event_showAllGraphCheckBoxActionPerformed

    private void requestComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_requestComboBoxActionPerformed
        BMFRequestObjectGUI req = (BMFRequestObjectGUI) requestComboBox.getModel().getSelectedItem();
        showRequestChartButton.setEnabled(!req.isActive());
    }//GEN-LAST:event_requestComboBoxActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AllRequestsDetailsButton;
    private javax.swing.JTextArea console;
    private javax.swing.JTabbedPane graphsTabPane;
    /**
	 * @uml.property  name="groupsTreePanel1"
	 * @uml.associationEnd  
	 */
    private ie.ucd.clarity.bmf.GUI.components.GroupsPanel groupsTreePanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    /**
	 * @uml.property  name="mapsTabPane"
	 */
    private javax.swing.JTabbedPane mapsTabPane;
    private javax.swing.JMenuItem menuItemExit;
    /**
	 * @uml.property  name="nodesTreePanel"
	 * @uml.associationEnd  
	 */
    private ie.ucd.clarity.bmf.GUI.components.NodesPanel nodesTreePanel;
    private javax.swing.JMenuItem openCVSMenuItem;
    private javax.swing.JComboBox requestComboBox;
    private javax.swing.JCheckBox showAllGraphCheckBox;
    private javax.swing.JButton showDetailRequestButton;
    private javax.swing.JButton showRequestChartButton;
    // End of variables declaration//GEN-END:variables

    public GroupsPanel getGroupsPanel() {
        return this.groupsTreePanel1;
    }

    public NodesPanel getNodesPanel() {
        return this.nodesTreePanel;
    }

    public DropPanel getFloorPlan(int i) {
        return floorPlans.get(i);
    }

    public DropPanel getSelectedFloorPlan() {
        if (!floorPlans.isEmpty()) {
            return floorPlans.get(mapsTabPane.indexOfComponent(mapsTabPane.getSelectedComponent()));
        } else {
            return null;
        }
    }

    public void addFloorPlan(DropPanel dp) {
        if (!floorPlans.contains(dp)) {
            floorPlans.add(dp);
        }
    }

    public void removeFloorPlan(int j) {
        floorPlans.remove(j);
        getNodesPanel().getTree().removeFloorPlan(j);
    }

    private void setTabbedPane() {
        for (int i = 0; i < mapsTabPane.getTabCount(); i++) {
            if (i != mapsTabPane.getTabCount() - 1) {
                new CloseTabButton(this, i);
            } else {
                new AddTabButton(this, i);
                mapsTabPane.getComponentAt(i).setEnabled(false);
            }
        }
    }

    /**
	 * @return
	 * @uml.property  name="mapsTabPane"
	 */
    public JTabbedPane getMapsTabPane() {
        return mapsTabPane;
    }

    public void update(Observable o, Object arg) {
        if (arg instanceof ToPrint) {
            ToPrint tp = (ToPrint) arg;
            print(tp.getString());
        }
        if (arg instanceof DataIn) {
            DataIn di = (DataIn) arg;
            newDataIn(di);
        }
        if (arg instanceof NewRequest) {
            NewRequest di = (NewRequest) arg;
            addRequest(di.getRequest());
        }
        if (arg instanceof NewNodeIn) {
            NewNodeIn ni = (NewNodeIn) arg;
        }
        if (arg instanceof RequestExpired) {
            RequestExpired di = (RequestExpired) arg;
            requestExpired(di.getID());
        }
    }

    private void requestExpired(int id) {
        for (int i = 0; i < requests.size(); i++) {
            if (requests.get(i).getRequest().getRequestID() == id) {
                requests.get(i).setActive(false);
                requests.get(i).setCharts(getChartInternalFrame(requests.get(i).getRequest().getRequestID()));
                graphsTabPane.remove(requests.get(i).getCharts().getComponent());
            }
        }
        requestComboBox.setModel(new ComboBoxRequestsModel(requests));
        requestComboBox.setSelectedIndex(requestComboBox.getModel().getSize() - 1);
        if (oneRequestActiveAtLeast()) {
            showAllGraphCheckBox.setEnabled(true);
        } else {
            showAllGraphCheckBox.setSelected(false);
            graphsTabPane.removeAll();
            jSplitPane1.setDividerLocation(5);
        }
    }

    private void addRequest(BMFRequestObjectGUI request) {
        if (request.getRequest().getConfigurationType() != Constants.PKT_TYPE_CONFIGURATION_UNSCHEDULE) {
            showAllGraphCheckBox.setEnabled(true);
            requests.add(request);
            if (request.isAggregation()) { // è un aggregazione devo memorizzare due grafici!!!!
                JInternalFrame aggregationInternalFrame = new JInternalFrame(request.getName() + " ID:" + request.getRequest().getRequestID());
                aggregationInternalFrame.setLayout(new GridLayout(2, 1));
                ChartInternalFrame ac = new ChartInternalFrame("AGGREGATE DATA", request, 1);
                ChartInternalFrame rc = new ChartInternalFrame(consumer.getiConfigurationPacketsManager().getIConfigurationNameByID(
                        request.getRequest().getRequestID()), request, 0);
                aggregationInternalFrame.setName(request.getName() + " ID:" + request.getRequest().getRequestID());
                aggregationInternalFrame.add(rc);
                aggregationInternalFrame.add(ac);
                chartInternal.add(new ChartsObject(rc, ac, aggregationInternalFrame));
                if (showAllGraphCheckBox.isSelected()) {
                    graphsTabPane.add(aggregationInternalFrame);
                }
                requestComboBox.setModel(new ComboBoxRequestsModel(requests));
                requestComboBox.setSelectedIndex(requestComboBox.getModel().getSize() - 1);
                rc.displayGUI();
                ac.displayGUI();
            } else {
                ChartInternalFrame fc = new ChartInternalFrame(consumer.getiConfigurationPacketsManager().getIConfigurationNameByID(
                        request.getRequest().getRequestID()), request, 0);
                fc.setName(request.getName() + " ID:" + request.getRequest().getRequestID());
                chartInternal.add(new ChartsObject(fc, null, fc));
                if (showAllGraphCheckBox.isSelected()) {
                    graphsTabPane.add(fc);
                }
                requestComboBox.setModel(new ComboBoxRequestsModel(requests));
                requestComboBox.setSelectedIndex(requestComboBox.getModel().getSize() - 1);
                fc.displayGUI();
            }
        }
    }

    private void addObservers() {
        consumer.addObserver(this);
        consumer.addObserver(this.getGroupsPanel());
        consumer.addObserver(this.getNodesPanel());
        consumer.addObserver(this.scheduledRequestFrame);
        System.out.println("Observer registered!");
    }

    /**
	 * @return
	 * @uml.property  name="consumer"
	 */
    public Consumer getConsumer() {
        return this.consumer;
    }

    /**
     * This method prints the String in the console and, evetually, in the text
     * file
     *
     * @param s
     */
    public void print(String s) {
        try {
            if (this.console.getDocument().getLength() > 200) {            	
                this.console.setText(s + "\n");
            } else {
                this.console.append(s + "\n");
                this.console.setCaretPosition(this.console.getDocument().getLength());
            }
        } catch (Exception e) {
        }
    }

    /**
     * @return all requests
     */
    public ArrayList<BMFRequestObjectGUI> getAllRequests() {
        return requests;


    }

    public ChartsObject getChartInternalFrame(int reqID) {
    	for (int i = 0; i < chartInternal.size(); i++) {
            if (chartInternal.get(i).getRequestChart().getRequest().getRequest().getRequestID() == reqID) {
                return chartInternal.get(i);
            }
        }
        return null;


    }

    private void checkExtention() throws Exception {
        String end = csvFileChooser.getSelectedFile().getName();


        if (!end.endsWith(".csv")) {
            throw new Exception();


        }
    }

    private String loadCSVFile() {
        String[] ext = {"csv"};
        FileNameExtensionFilter filter = createFileFilter("CSV File", true, ext);
        csvFileChooser.addChoosableFileFilter(filter);
        try {
            int returnVal = csvFileChooser.showOpenDialog(new JFrame());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                checkExtention();
                return csvFileChooser.getSelectedFile().getAbsolutePath();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failled to load!!", "Reading CSV File", JOptionPane.ERROR_MESSAGE);
        }
        return null;


    }

    private String createFileNameFilterDescriptionFromExtensions(String description, String[] extensions) {
        String fullDescription = (description == null) ? "(" : description + " (";
        // build the description from the extension list
        fullDescription += "." + extensions[0];
        for (int i = 1; i
                < extensions.length; i++) {
            fullDescription += ", .";
            fullDescription += extensions[i];
        }
        fullDescription += ")";
        return fullDescription;
    }

    private FileNameExtensionFilter createFileFilter(String description, boolean showExtensionInDescription, String... extensions) {
        if (showExtensionInDescription) {
            description = createFileNameFilterDescriptionFromExtensions(
                    description, extensions);
        }
        return new FileNameExtensionFilter(description, extensions);
    }

    /**
	 * @return
	 * @uml.property  name="basestation"
	 */
    public DefaultMutableTreeNode getBasestation() {
        return basestation;
    }

    public void rescheduleRequest(BMFRequestObjectGUI req) {

        BMFRequestObjectGUI r = null;
        if (requests.contains(req)) {
        	r =new BMFRequestObjectGUI(requests.get(requests.indexOf(req)), getConsumer().getPacketBuilder().getIConfigurationPacket());
            r.setActive(true);
        }
        getConsumer().sendICofigurationPacket(r.getRequest(), r.getName(), r.getResult(), false);
        scheduledRequestFrame.updateRequests(requests);
    }

    public void unscheduleRequest(BMFRequestObjectGUI request) {

        BMFRequestObjectGUI req = null;
        if (requests.contains(request)) {
            try {
                req =new BMFRequestObjectGUI(requests.get(requests.indexOf(request)), getConsumer().getPacketBuilder().getIConfigurationPacket());
                req.getRequest().setConfigurationType(Constants.PKT_TYPE_CONFIGURATION_UNSCHEDULE);
                req.setActive(false);
                requests.get(requests.indexOf(request)).setActive(false);                
                req.getRequest().setRequestID(request.getRequest().getRequestID());
            
            getConsumer().sendICofigurationPacket(req.getRequest(), req.getName(), req.getResult(), false);            
            } catch (InvalidPacketParametersException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            scheduledRequestFrame.updateRequests(requests);
        }
    }

    private boolean oneRequestActiveAtLeast() {
        for (int i = 0; i
                < requests.size(); i++) {
            if (requests.get(i).isActive()) {
                return true;
            }
        }
        return false;
    }

    private void newDataIn(DataIn di) {
    	long now=System.currentTimeMillis();
    	long convertedData=Util.getDataConverted(di.getData().getResult(), consumer.getiConfigurationPacketsManager().getBMFRequestByID(di.getRequestID()).getSensor_actuatorType());
    	if (consumer.getiConfigurationPacketsManager().getIConfigurationNameByID(di.getRequestID()) != null) {
            if (isAggregation(di.getRequestID())) {
                long result = consumer.getiAggregatorManager().getIAggregator(di.getRequestID()).addAggregateValue(new AggregationItem(di.getData().getSenderID(), convertedData));
                if (result != -1) {
                    getChartInternalFrame(di.getRequestID()).getAggregationChart().AddDataToDataset(di.getData().getRequestID(), (double) result, now);
                    getChartInternalFrame(di.getRequestID()).getAggregationChart().repaint();
                }
            }
            getChartInternalFrame(di.getRequestID()).getRequestChart().AddDataToDataset(di.getData().getSenderID(), (double) convertedData, now);
            getChartInternalFrame(di.getRequestID()).getRequestChart().repaint();
        }

    }

    private boolean isAggregation(int requestID) {
        for (int i = 0; i < requests.size(); i++) {
            if (requests.get(i).getRequest().getRequestID() == requestID) {
                return requests.get(i).isAggregation();
            }
        }
        return false;
    }
}