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
import ie.ucd.clarity.bmf.GUI.common.BMFNodeGUI;
import ie.ucd.clarity.bmf.GUI.components.GroupsPanel;
import ie.ucd.clarity.bmf.GUI.components.NodesPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import javax.swing.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.util.Observer;

/**
 * @author  Matthew
 */
public class DropPanel extends JPanel implements DropTargetListener, Observer {

    private DropTarget dropTarget;
    public static DataFlavor NODE_OBJECT_FLAVOR = new DataFlavor(BMFNodeGUI.class,
            "Node Object");
    private ImageIcon imageicon;
    /**
	 * @uml.property  name="frame"
	 * @uml.associationEnd  
	 */
    InitialFrame frame;
    String path;
    

    public DropPanel(InitialFrame frame, String pathPlan) {
        super();
        this.frame = frame;
        this.path=pathPlan;
        imageicon=new ImageIcon(pathPlan);
        super.setLayout(null);
        this.dropTarget = new DropTarget(this, DnDConstants.ACTION_COPY, this, true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        imageicon.paintIcon(this, g, 0, 0);
        this.setBackground(Color.white);
    }

    public void removeFromFloorPlan(BMFNodeGUI n, int i) {
        n.removeFloorplan(i);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(imageicon.getIconWidth(),
                imageicon.getIconHeight());
    }

    private boolean isDragFlavorSupported(DropTargetDragEvent e) {
        boolean ok = false;
        if (e.isDataFlavorSupported(NODE_OBJECT_FLAVOR)) {
            ok = true;
        }
        return (ok);
    }

    private DataFlavor[] chooseDropFlavor(DropTargetDropEvent e) {
        return e.getCurrentDataFlavors();
    }

    private boolean isDragOk(DropTargetDragEvent e) {
        boolean isDragOK = true;
        if (isDragFlavorSupported(e) == false) {
            isDragOK = false;
        } else {
            if ((e.getDropAction() & DnDConstants.ACTION_COPY) == 0) {
                isDragOK = false;
            }
        }

        return (isDragOK);
    }

    public void dragEnter(DropTargetDragEvent e) {
        if (isDragOk(e) == false) {
            e.rejectDrag();
        } else {
            e.acceptDrag(e.getDropAction());
        }
    }

    public void dragOver(DropTargetDragEvent e) {
        if (isDragOk(e) == false) {
            e.rejectDrag();
        } else {
            e.acceptDrag(e.getDropAction());
        }
    }

    public void dropActionChanged(DropTargetDragEvent e) {
        if (isDragOk(e) == false) {
            e.rejectDrag();
        } else {
            e.acceptDrag(e.getDropAction());
        }
    }

    public void dragExit(DropTargetEvent e) {
    }

    public void drop(DropTargetDropEvent e) {
        DataFlavor[] chosen = chooseDropFlavor(e);
        if (chosen == null) {
            e.rejectDrop();
        } else {
            if ((e.getSourceActions() & DnDConstants.ACTION_COPY) == 0) {
                e.rejectDrop();
            } else {
                for (int i = 0; i < chosen.length; i++) {
                    if (chosen[i].equals(NODE_OBJECT_FLAVOR)) {
                        try {
                            e.acceptDrop(DnDConstants.ACTION_COPY);
                            int x = e.getLocation().x;
                            int y = e.getLocation().y;                            
                            addDropComponent(x, y, (BMFNodeGUI) e.getTransferable().getTransferData(NODE_OBJECT_FLAVOR));
                        } catch (Throwable t) {
                            System.err.println("Couldn't get transfer data: " + t.getMessage());
                            t.printStackTrace();
                            e.dropComplete(false);
                            return;
                        }
                        e.dropComplete(true);
                        break;
                    }
                }
            }
        }
    }

    public void addDropComponent(int x, int y, BMFNodeGUI o) {

           if (!o.isOnFloorplan(getIndexSelectedFloorPlan())) {
                o.setFloorplan(getIndexSelectedFloorPlan());
                DropComponent dc = new DropComponent(o, frame);
                dc.setLocation(x, y);
                super.add(dc);
                repaint();
            } else {
                JOptionPane.showMessageDialog(this.getParent(), "The Node '" + o.getName() + "' is already in the Floor Plan");
            }
    }

    public GroupsPanel getGroupsPanel() {
        return frame.getGroupsPanel();
    }

    public NodesPanel getNodesPanel() {
        return frame.getNodesPanel();
    }

    public int getIndexSelectedFloorPlan(){
        int i=frame.getMapsTabPane().indexOfComponent((frame.getMapsTabPane().getSelectedComponent()));
        return i;        
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

   

    
}
