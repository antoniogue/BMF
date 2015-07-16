/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.ucd.clarity.bmf.GUI.components;

import ie.ucd.clarity.bmf.GUI.InitialFrame;
import ie.ucd.clarity.bmf.common.Constants;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * @author  Andrea
 */
public class CloseTabButton extends JPanel implements ActionListener {

    /**
	 * @uml.property  name="frame"
	 * @uml.associationEnd  
	 */
    private InitialFrame frame;
    private JTabbedPane pane;

    public CloseTabButton(InitialFrame frame, int index) {
        this.frame=frame;
        this.pane = this.frame.getMapsTabPane();
        setOpaque(false);
        add(new JLabel(
                pane.getTitleAt(index),
                pane.getIconAt(index),
                JLabel.LEFT));
        //Icon closeIcon = new CloseIcon();
        ImageIcon closeIcon = new ImageIcon(Constants.closeGIF);
        JButton btClose = new JButton(closeIcon);
        btClose.setContentAreaFilled(false);
        btClose.setPreferredSize(new Dimension(
                closeIcon.getIconWidth(), closeIcon.getIconHeight()));
        add(btClose);
        btClose.addActionListener(this);
        setFocusable(false);
        btClose.setBorder(BorderFactory.createEtchedBorder());
        btClose.setBorderPainted(false);
        //Making nice rollover effect
        //we use the same listener for all buttons
        btClose.addMouseListener(buttonMouseListener);
        btClose.setRolloverEnabled(true);
        btClose.setToolTipText("Close Tab");
        pane.setTabComponentAt(index, this);
    }

    public void actionPerformed(ActionEvent e) {
        int i = pane.indexOfTabComponent(this);
        if ((i != -1)&&(JOptionPane.showConfirmDialog(this, "Do you want to remove the FloorPlan?", "Remove FloorPlan", JOptionPane.YES_NO_CANCEL_OPTION) == 0)) {
            frame.removeFloorPlan(i);
            pane.remove(i);

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