/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.ucd.clarity.bmf.GUI.DnDTrees;

import ie.ucd.clarity.bmf.GUI.InitialFrame;
import ie.ucd.clarity.bmf.GUI.components.PopupRightClick;
import ie.ucd.clarity.bmf.GUI.common.BMFGroupGUI;
import ie.ucd.clarity.bmf.GUI.common.ItemPopupObject;
import ie.ucd.clarity.bmf.GUI.common.BMFNodeGUI;
import ie.ucd.clarity.bmf.GUI.common.BMFSensorGUI;
import ie.ucd.clarity.bmf.network.manager.nodesAndGroupsManager.TooManyGroupsCreatedException;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

/**
 * @author  Andrea
 */
public class TreeMouseListener implements MouseListener, TreeSelectionListener {

	JTree tree;
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

	public TreeMouseListener(JTree tree, InitialFrame frame) {
		this.tree = tree;
		this.frame = frame;
		this.tree.addMouseListener(this);
		this.tree.addTreeSelectionListener(this);
	}

	public DefaultTreeCellRenderer getTreeRenderer() {
		return (DefaultTreeCellRenderer) tree.getCellRenderer();
	}

	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		TreePath[] paths = tree.getSelectionModel().getSelectionPaths();
		if (paths != null) {
			for (TreePath tp : paths) {
				System.out.println(tp); // stampa la selezione multipla
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		checkForTriggerEvent(e);
	}

	public void mousePressed(MouseEvent e) {
		checkForTriggerEvent(e);
	}

	public void mouseReleased(MouseEvent e) {
		checkForTriggerEvent(e);
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	private void checkForTriggerEvent(MouseEvent e) {
		if (e.isPopupTrigger()) {
			if (createPopupMenu()) {
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}

		}
	}

	private boolean createPopupMenu() {
		try {
			ArrayList<ItemPopupObject> names = new ArrayList<ItemPopupObject>();
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
					.getLastSelectedPathComponent();
			createItems(names, node);
			popupMenu = new PopupRightClick(names, frame);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private void createItems(ArrayList<ItemPopupObject> names,
			DefaultMutableTreeNode node) {
		if (node.getUserObject() instanceof BMFNodeGUI) {
			BMFNodeGUI new1 = (BMFNodeGUI) node.getUserObject();
			if (new1.getID() != 0) {
				names.add(new ItemPopupObject(new1.getName() + " Properties",
						new1, node));
				names.add(new ItemPopupObject("Leave Group", new1, node));
				names.add(new ItemPopupObject("New Request", new1, node));
				names.add(new ItemPopupObject("Reset Node", new1, node));
			} else
				names.add(new ItemPopupObject("Broadcast Reset", new1, node));
		} else if (node.getUserObject() instanceof BMFSensorGUI) {
			BMFSensorGUI new1 = (BMFSensorGUI) node.getUserObject();
			names.add(new ItemPopupObject(new1.getName() + " Properties", new1,
					node));
		} else if (node.getUserObject() instanceof BMFGroupGUI) {
			BMFGroupGUI new1 = (BMFGroupGUI) node.getUserObject();
			if (new1.getID() != 0) {
				names.add(new ItemPopupObject(new1.getName() + " Properties",
						new1, node));
				names.add(new ItemPopupObject("Delete " + new1.getName(), new1,
						node));
				names.add(new ItemPopupObject("Rename ", new1, node));
				names.add(new ItemPopupObject("Remove all nodes", new1, node));
				names.add(new ItemPopupObject("Add all nodes on the FloorPlan",
						new1, node));
				names.add(new ItemPopupObject("New Request", new1, node));
			} else
				names.add(new ItemPopupObject("Broadcast Reset", new1, node));
		} else if ((node.getUserObject() instanceof String)
				&& (tree instanceof GroupsTree)) {
			BMFGroupGUI new1 = new BMFGroupGUI(null);
			names.add(new ItemPopupObject("Create new Group", new1, node));
		}
	}
}
