/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.ucd.clarity.bmf.GUI.DnDTrees;

import ie.ucd.clarity.bmf.GUI.InitialFrame;
import ie.ucd.clarity.bmf.GUI.common.BMFGroupGUI;
import ie.ucd.clarity.bmf.GUI.common.BMFNodeGUI;
import ie.ucd.clarity.bmf.common.Constants;
import ie.ucd.clarity.bmf.network.manager.nodesAndGroupsManager.IBMFNodesAndGroupsManager;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.dnd.Autoscroll;
import java.awt.dnd.DnDConstants;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

/**
 * @author  Andrea
 */
public class NodesTree extends JTree implements Autoscroll {

	private int margin = 12;
	/**
	 * @uml.property  name="ds"
	 * @uml.associationEnd  
	 */
	TreeDragSource ds;
	/**
	 * @uml.property  name="dt"
	 * @uml.associationEnd  
	 */
	TreeDropTarget dt;
	DefaultMutableTreeNode root;
	/**
	 * @uml.property  name="ml"
	 * @uml.associationEnd  
	 */
	TreeMouseListener ml;
	/**
	 * @uml.property  name="nodes"
	 */
	ArrayList<BMFNodeGUI> nodes;
	/**
	 * @uml.property  name="frame"
	 * @uml.associationEnd  
	 */
	InitialFrame frame;
	/**
	 * @uml.property  name="manager"
	 * @uml.associationEnd  
	 */
	private IBMFNodesAndGroupsManager manager;

	public NodesTree(DefaultMutableTreeNode tree) {
		super(tree);
		root = tree;
		nodes = new ArrayList<BMFNodeGUI>();
		setIcons();
	}

	public void createDragSource() {
		ds = new TreeDragSource(this, DnDConstants.ACTION_COPY);
	}

	public void createDropTarget() {
		// dt = new TreeDropTarget(this);
	}

	public void autoscroll(Point p) {
		int realrow = getRowForLocation(p.x, p.y);
		Rectangle outer = getBounds();
		realrow = (p.y + outer.y <= margin ? realrow < 1 ? 0 : realrow - 1
				: realrow < getRowCount() - 1 ? realrow + 1 : realrow);
		scrollRowToVisible(realrow);
	}

	public Insets getAutoscrollInsets() {
		Rectangle outer = getBounds();
		Rectangle inner = getParent().getBounds();
		return new Insets(inner.y - outer.y + margin, inner.x - outer.x
				+ margin, outer.height - inner.height - inner.y + outer.y
				+ margin, outer.width - inner.width - inner.x + outer.x
				+ margin);
	}

	// Use this method if you want to see the boundaries of the
	// autoscroll active region
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	private void setIcons() {
		ImageIcon leafIcon = new ImageIcon(Constants.sensorsGIF);
		ImageIcon closedIcon = new ImageIcon(Constants.closedNodeGIF);
		ImageIcon openedIcon = new ImageIcon(Constants.openedNodeGIF);
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		if (leafIcon != null) {
			renderer.setLeafIcon(leafIcon);
		} else {
			System.err.println("Leaf icon missing; using default.");
		}
		if (closedIcon != null || openedIcon != null) {
			renderer.setClosedIcon(closedIcon);
			renderer.setOpenIcon(openedIcon);
		} else {
			System.err.println("Icon missing; using default.");
		}
		this.setCellRenderer(renderer);
	}

	public DefaultMutableTreeNode addNewNode(BMFNodeGUI n) {
		try {
			if (!nodes.contains(n)) {
				DefaultMutableTreeNode sensors = new DefaultMutableTreeNode(n
						.getSensors().get(0));
				DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(n);
				for (int i = 0; i < n.getSensors().size(); i++) {
					sensors = new DefaultMutableTreeNode(n.getSensors().get(i));
					newNode.add(sensors);
				}
				nodes.add(n);
				root.add(newNode);
				manager.addNode(n.getInode());
				

				
				((DefaultTreeModel) this.getModel()).reload();
				return newNode;
			} else {
				DefaultMutableTreeNode dmtnnode = null;
				for (int j = 0; j < root.getChildCount(); j++) {
					dmtnnode = (DefaultMutableTreeNode) ((DefaultTreeModel) this
							.getModel()).getChild(root, j);
					if (dmtnnode.getUserObject().equals(n))
						return dmtnnode;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @return
	 * @uml.property  name="nodes"
	 */
	public ArrayList<BMFNodeGUI> getNodes() {
		return nodes;
	}

	/**
	 * @param nodes
	 * @uml.property  name="nodes"
	 */
	public void setNodes(ArrayList<BMFNodeGUI> nodes) {
		this.nodes = nodes;
	}

	/**
	 * @param frame
	 * @uml.property  name="frame"
	 */
	public void setFrame(InitialFrame frame) {
		this.frame = frame;
		ml = new TreeMouseListener(this, frame);
		manager = this.frame.getConsumer().getiBMFNodesAndGroupsManager();
	}

	public void removeFloorPlan(int j) {
		for (int i = 0; i < nodes.size(); i++) {
			nodes.get(i).removeFloorplan(j);
		}
	}
}
