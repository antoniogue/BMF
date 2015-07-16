/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.ucd.clarity.bmf.GUI.components;

import ie.ucd.clarity.bmf.GUI.InitialFrame;
import ie.ucd.clarity.bmf.GUI.common.BMFNodeGUI;
import ie.ucd.clarity.bmf.GUI.components.dnd.DropPanel;
import ie.ucd.clarity.bmf.common.Constants;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author  Andrea
 */
public class AddTabButton extends JPanel implements ActionListener {

    private JTabbedPane pane;
    /**
	 * @uml.property  name="frame"
	 * @uml.associationEnd  
	 */
    private InitialFrame frame;
    private JFileChooser jFileChooser1 = new JFileChooser();
    private String name;

    public AddTabButton(InitialFrame frame, int index) {
        this.frame = frame;
        this.pane = frame.getMapsTabPane();
        setOpaque(false);
        add(new JLabel(
                pane.getTitleAt(index),
                pane.getIconAt(index),
                JLabel.LEFT));
        ImageIcon openIcon = new ImageIcon(Constants.piuGIF);
        JButton btOpen = new JButton(openIcon);
        btOpen.setContentAreaFilled(false);
        btOpen.setPreferredSize(new Dimension(
                openIcon.getIconWidth(), openIcon.getIconHeight()));
        add(btOpen);
        btOpen.addActionListener(this);
        setFocusable(false);
        btOpen.setBorder(BorderFactory.createEtchedBorder());
        btOpen.setBorderPainted(false);
        //Making nice rollover effect
        //we use the same listener for all buttons
        btOpen.addMouseListener(buttonMouseListener);
        btOpen.setRolloverEnabled(true);
        btOpen.setToolTipText("Add new FloorPlan");
        pane.setTabComponentAt(index, this);
    }

    private FileNameExtensionFilter createFileFilter(String description, boolean showExtensionInDescription, String... extensions) {
        if (showExtensionInDescription) {
            description = createFileNameFilterDescriptionFromExtensions(
                    description, extensions);
        }
        return new FileNameExtensionFilter(description, extensions);
    }

    private String createFileNameFilterDescriptionFromExtensions(String description, String[] extensions) {
        String fullDescription = (description == null) ? "(" : description + " (";
        // build the description from the extension list
        fullDescription += "." + extensions[0];
        for (int i = 1; i < extensions.length; i++) {
            fullDescription += ", .";
            fullDescription += extensions[i];
        }
        fullDescription += ")";
        return fullDescription;
    }

    private void checkExtention() throws Exception {
        String end = jFileChooser1.getSelectedFile().getName();
        if (end.endsWith(".JPG") || end.endsWith(".jpg") || end.endsWith(".GIF") || end.endsWith(".gif")) {
            name = end;
        } else {
            throw new Exception();
        }

    }

    private String loadImage() {
        String[] ext = {"jpg", "JPG", "gif", "GIF"};
        FileNameExtensionFilter filter = createFileFilter("Image File", true, ext);
        jFileChooser1.addChoosableFileFilter(filter);
        try {
            int returnVal = jFileChooser1.showOpenDialog(new JFrame());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                checkExtention();
                return jFileChooser1.getSelectedFile().getAbsolutePath();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Failled to load!!",
                    "Reading FloorPlan from File",
                    JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public void actionPerformed(ActionEvent e) {
        int i = pane.indexOfTabComponent(this);
        if ((i != -1)) {
            String path = loadImage();
            if (path != null) {
                JScrollPane jScrollPane2 = new JScrollPane();
                DropPanel floorPlan = new DropPanel(frame,path);
                jScrollPane2.add(floorPlan);
                jScrollPane2.setPreferredSize(floorPlan.getPreferredSize());
                jScrollPane2.setViewportView(floorPlan);
                jScrollPane2.setName(name);
                pane.add(jScrollPane2, pane.getTabCount() - 1);
                new CloseTabButton(frame, i);
                pane.setSelectedComponent(jScrollPane2);
                frame.addFloorPlan(floorPlan);
                ArrayList<BMFNodeGUI> nodes = frame.getNodesPanel().getTree().getNodes();
                for (int j = 0; j < nodes.size(); j++) {
                    ((NodesPanel) ((frame.getNodesPanel().getTree().getParent()).getParent()).getParent()).getFloorplan().addDropComponent(0, j * 40, nodes.get(j));
                }
            }

        }
    }
    private final static MouseListener buttonMouseListener = new MouseAdapter() {

        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }

        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }
    };
}
