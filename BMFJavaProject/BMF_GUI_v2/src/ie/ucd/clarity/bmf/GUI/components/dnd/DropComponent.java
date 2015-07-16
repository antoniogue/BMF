/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.ucd.clarity.bmf.GUI.components.dnd;

/**
 *
 * @author Andrea
 */
import ie.ucd.clarity.bmf.GUI.InitialFrame;
import ie.ucd.clarity.bmf.GUI.components.PopupRightClick;
import ie.ucd.clarity.bmf.GUI.common.ItemPopupObject;
import ie.ucd.clarity.bmf.GUI.common.BMFNodeGUI;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

/**
 * @author  Matthew
 */
public class DropComponent extends JLabel implements MouseListener, MouseMotionListener, Observer {

    Point mLastPoint;
    /**
	 * @uml.property  name="node"
	 * @uml.associationEnd  
	 */
    BMFNodeGUI node;
    /**
	 * @uml.property  name="popupMenu"
	 * @uml.associationEnd  
	 */
    PopupRightClick popupMenu = null;
    /**
	 * @uml.property  name="frame"
	 * @uml.associationEnd  
	 */
    InitialFrame frame;

    public DropComponent(BMFNodeGUI node, InitialFrame frame) {
        super(node.getIcon());
        super.setText(node.getID()+"");
        this.node = node;
        this.frame=frame;
        super.setBounds(30, 30, 45, 35);
        super.setOpaque(false);
        super.setForeground(Color.black);
        super.setFont(new java.awt.Font("Tahoma", 1, 12));
        super.addMouseListener(this);
        super.addMouseMotionListener(this);
    }

    /**
	 * @return
	 * @uml.property  name="node"
	 */
    public BMFNodeGUI getNode() {
        return node;
    }

    /**
	 * @param node
	 * @uml.property  name="node"
	 */
    public void setNode(BMFNodeGUI node) {
        this.node = node;
    }

    public void mouseDragged(MouseEvent event_) {
        int x, y;

        if (mLastPoint != null) {
            x = super.getX() + (event_.getX() - (int) mLastPoint.getX());
            y = super.getY() + (event_.getY() - (int) mLastPoint.getY());
            super.setLocation(x, y);
        }
    }

    public void mouseMoved(MouseEvent event_) {
        setCursorType(event_.getPoint());
    }

    public void mouseClicked(MouseEvent event_) {
    }

    public void mouseEntered(MouseEvent event_) {
    }

    public void mouseExited(MouseEvent event_) {
        if (mLastPoint == null) {
            super.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            super.setBorder(null);
        }
    }

    public void mousePressed(MouseEvent event_) {
        checkForTriggerEvent(event_);
        if (super.getCursor().equals(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR))) {
            mLastPoint = event_.getPoint();
            super.getParent().add(this, 0);
            super.getParent().repaint();
        } else {
            mLastPoint = null;
        }
    }

    private void checkForTriggerEvent(MouseEvent e) {
        if (e.isPopupTrigger()) {
            createPopupMenu();
            popupMenu.show(e.getComponent(),
                    e.getX(), e.getY());
        }
    }

    public void mouseReleased(MouseEvent e) {
        checkForTriggerEvent(e);
        mLastPoint = null;
    }

    private void setCursorType(Point p) {
        Point loc = super.getLocation();
        Dimension size = super.getSize();
        if ((p.y + 4 < loc.y + size.height) && (p.x + 4 < p.x + size.width)) {
            super.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            super.setBorder(BorderFactory.createLineBorder(Color.black));
        }
    }

    private void createPopupMenu() {
        ArrayList<ItemPopupObject> names = new ArrayList<ItemPopupObject>();
        names.add(new ItemPopupObject(node.toString()+" Properties", node,null));
        names.add(new ItemPopupObject("Remove from FloorPlan", node, null));
        names.add(new ItemPopupObject("Leave group", node, null));
        names.add(new ItemPopupObject("Join to group", node, null));
        popupMenu = new PopupRightClick(names, frame);
    }

    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}