/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.ucd.clarity.bmf.GUI.components;

import ie.ucd.clarity.bmf.GUI.DnDTrees.GroupsTree;
import ie.ucd.clarity.bmf.GUI.DnDTrees.NodesTree;
import ie.ucd.clarity.bmf.GUI.InitialFrame;
import ie.ucd.clarity.bmf.GUI.GroupDetailDialog;
import ie.ucd.clarity.bmf.GUI.LeaveGroupFrame;
import ie.ucd.clarity.bmf.GUI.NodeDetailDialog;
import ie.ucd.clarity.bmf.GUI.RequestsFrame;
import ie.ucd.clarity.bmf.GUI.components.dnd.DropComponent;
import ie.ucd.clarity.bmf.GUI.components.dnd.DropPanel;
import ie.ucd.clarity.bmf.GUI.common.BMFGroupGUI;
import ie.ucd.clarity.bmf.GUI.common.ItemPopupObject;
import ie.ucd.clarity.bmf.GUI.common.BMFNodeGUI;
import ie.ucd.clarity.bmf.GUI.common.BMFSensorGUI;
import ie.ucd.clarity.bmf.network.manager.nodesAndGroupsManager.TooManyGroupsCreatedException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author  Andrea
 */
public class PopupRightClick extends JPopupMenu implements ActionListener {

	ArrayList<JMenuItem> items;
	ArrayList<ItemPopupObject> infoItems;
	/**
	 * @uml.property  name="this_"
	 * @uml.associationEnd  
	 */
	PopupRightClick this_ = this;
	DefaultMutableTreeNode node;
	/**
	 * @uml.property  name="frame"
	 * @uml.associationEnd  
	 */
	InitialFrame frame;

	public PopupRightClick(ArrayList<ItemPopupObject> nameitem,
			InitialFrame frame) {
		super();
		this.frame = frame;
		items = new ArrayList<JMenuItem>();
		infoItems = new ArrayList<ItemPopupObject>();
		for (int i = 0; i < nameitem.size(); i++) {
			items.add(new JMenuItem(nameitem.get(i).getName()));
			items.get(i).addActionListener(this);
			infoItems.add(nameitem.get(i));
			add(items.get(i));
		}
	}

	public void reset(ArrayList<String> nameitem) {
		items = new ArrayList<JMenuItem>();
		for (int i = 0; i < items.size(); i++) {
			items.add(new JMenuItem(nameitem.get(i)));
			add(items.get(i));
		}
	}

	public JMenuItem getItemAt(int index) {
		return items.get(index);
	}

	public JMenuItem getItemByName(String name) {

		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getText().equalsIgnoreCase(name))
				;
			return items.get(i);
		}
		return null;
	}

	public void setTextItemAt(int index, String text) {
		items.get(index).setText(text);
	}

	public void setItems(ArrayList<String> names) {
		reset(names);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JMenuItem) {
			JMenuItem m = (JMenuItem) e.getSource();
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).equals(m)) {
					node = infoItems.get(i).getNode();
					createEvent(infoItems.get(i), i);
				}
			}
		}
	}

	private void removeFromFloorplan() {

		DropPanel c = (DropPanel) this_.getInvoker().getParent();
		DropComponent d = (DropComponent) this_.getInvoker();
		c.removeFromFloorPlan(d.getNode(), c.getIndexSelectedFloorPlan());
		c.remove(this_.getInvoker());
		c.repaint();
	}

	private void createEvent(ItemPopupObject get, int i) {

		if (get.getObject() instanceof BMFNodeGUI) {
			if (((BMFNodeGUI) get.getObject()).getID() == 0) { // radice DA
																// CONTROLLARE
																// L'ID
				broadcastReset();
			} else if (this_.getInvoker() instanceof DropComponent) { // invocato
																		// dalla
																		// floorplan
				switch (i) {
				case 0:
					NodeDetailDialog nodedetail = new NodeDetailDialog(frame,
							(BMFNodeGUI) infoItems.get(0).getObject());
					nodedetail.setLocationRelativeTo(this);
					nodedetail.setVisible(true);
					break;
				case 1:
					removeFromFloorplan();
					break;
				case 2:
					leaveGroup((BMFNodeGUI) infoItems.get(i).getObject());
					break;
				case 3: // unirsi al gruppo

				}
			} else { // invocato dall'albero
				switch (i) {
				case 0:
					NodeDetailDialog nodedetail = new NodeDetailDialog(frame,
							(BMFNodeGUI) infoItems.get(0).getObject());
					nodedetail.setLocationRelativeTo(this);
					nodedetail.setVisible(true);
					break;
				case 1:
					leaveGroup((BMFNodeGUI) infoItems.get(i).getObject());
					break;
				case 2:
					java.awt.EventQueue.invokeLater(new Runnable() {
						public void run() {
							frame.getRequestsFrame().showGUI(
									(BMFNodeGUI) infoItems.get(2).getObject(),
									frame);
						}
					});
					break;
				case 3:
					resetNode(((BMFNodeGUI) infoItems.get(i).getObject())
							.getID());
				}
			}
		} else if (get.getObject() instanceof BMFGroupGUI) {
			if (((BMFGroupGUI) get.getObject()).getName().equals("Groups")) { // radice
				createNewGroup();
			} else {
				switch (i) {
				case 0:
					GroupDetailDialog groupdetail = new GroupDetailDialog(
							frame, (BMFGroupGUI) infoItems.get(0).getObject());
					groupdetail.setLocationRelativeTo(this);
					groupdetail.setVisible(true);
					break;
				case 1:
					deleteGroup((BMFGroupGUI) infoItems.get(i).getObject());
					break;
				case 2:
					renameGroup((BMFGroupGUI) infoItems.get(i).getObject());
					break;
				case 3:
					clearGroup((BMFGroupGUI) infoItems.get(i).getObject());
					break;
				case 4:
					allOnFloorPlanFromGroup((BMFGroupGUI) infoItems.get(i)
							.getObject());
					break;
				case 5:
					java.awt.EventQueue.invokeLater(new Runnable() {
						public void run() {
							frame.getRequestsFrame().showGUI(
									(BMFGroupGUI) infoItems.get(5).getObject(),
									frame);
						}
					});
					break;
				}
			}
		} else if (get.getObject() instanceof BMFSensorGUI) {
			System.out.println(((BMFSensorGUI) get.getObject()).getName());
		}
	}

	private void broadcastReset() {
		frame.getConsumer().sendBroadCastIResetPacket();
	}

	private void resetNode(int id) {
		int i = JOptionPane.showConfirmDialog(this,
				"Do you want to reset the Node " + id + "?", "Reset",
				JOptionPane.YES_NO_OPTION);
		if (i == 0) {
			frame.getConsumer().sendIResetPacket(id);
		}
	}

	private void clearGroup(BMFGroupGUI groupObject) {
		GroupsTree tree = (GroupsTree) this_.getInvoker();
		tree.clearGroup(groupObject, node);
	}

	private void leaveGroup(final BMFNodeGUI n) {
		if (this_.getInvoker() instanceof GroupsTree) {
			GroupsTree tree = (GroupsTree) this_.getInvoker();
			tree.removeNodeFromGroup(node);
		} else if ((this_.getInvoker() instanceof NodesTree)
				|| this_.getInvoker() instanceof DropComponent) {
			java.awt.EventQueue.invokeLater(new Runnable() {

				public void run() {
					new LeaveGroupFrame(frame, n).setVisible(true);
				}
			});
		}
	}

	private void createNewGroup() {
		GroupsTree tree = (GroupsTree) this_.getInvoker();
		String s = JOptionPane.showInputDialog(tree, "Insert a Group Name",
				new String("Group"));
		if (s != null) {
			try {
				boolean exists = false;
				for (int i = 0; i < frame.getConsumer()
						.getiBMFNodesAndGroupsManager().getGroups().size(); i++)
					if (frame.getConsumer().getiBMFNodesAndGroupsManager()
							.getGroups().get(i).getName().equals(s)) {
						exists = true;
					}
				if (!exists)
					tree
							.addNewGroup(
									new BMFGroupGUI(frame.getConsumer()
											.getiBMFNodesAndGroupsManager()
											.addGroup(s)), false);
				else JOptionPane.showMessageDialog(this, s+ " already exists");		
			} catch (TooManyGroupsCreatedException e) {
				e.printStackTrace();
			}//
		}
	}

	private void deleteGroup(BMFGroupGUI groupObject) {
		GroupsTree tree = (GroupsTree) this_.getInvoker();
		tree.removeGroup(groupObject, node);
	}

	private void renameGroup(BMFGroupGUI groupObject) {
		GroupsTree tree = (GroupsTree) this_.getInvoker();
		tree.renameGroup(groupObject, node);
	}

	private void allOnFloorPlan() {

		NodesTree tree = (NodesTree) this_.getInvoker();
		if (((NodesPanel) ((tree.getParent()).getParent()).getParent())
				.getFloorplan() == null) {
			JOptionPane.showMessageDialog(frame, "No FloorPlan!");
			return;
		}
		ArrayList<BMFNodeGUI> nodes = tree.getNodes();
		for (int i = 0; i < nodes.size(); i++) {
			((NodesPanel) ((tree.getParent()).getParent()).getParent())
					.getFloorplan().addDropComponent(0, i * 40, nodes.get(i));
		}

	}

	private void allOnFloorPlanFromGroup(BMFGroupGUI groupObject) {
		GroupsTree tree = (GroupsTree) this_.getInvoker();
		ArrayList<BMFGroupGUI> g = tree.getGroups();
		for (int i = 0; i < g.size(); i++) {
			if (g.get(i).equals(groupObject)) {
				ArrayList<BMFNodeGUI> nodes = g.get(i).getNodes();
				for (int j = 0; j < nodes.size(); j++) {
					((GroupsPanel) ((tree.getParent()).getParent()).getParent())
							.getFloorplan().addDropComponent(0, j * 40,
									nodes.get(j));
				}
				break;
			}
		}
	}
}
