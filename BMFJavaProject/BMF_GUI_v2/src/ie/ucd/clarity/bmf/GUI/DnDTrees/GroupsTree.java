/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.ucd.clarity.bmf.GUI.DnDTrees;

import ie.ucd.clarity.bmf.GUI.InitialFrame;
import ie.ucd.clarity.bmf.GUI.common.BMFGroupGUI;
import ie.ucd.clarity.bmf.GUI.common.BMFNodeGUI;
import ie.ucd.clarity.bmf.common.Constants;
import ie.ucd.clarity.bmf.common.InvalidPacketParametersException;
import ie.ucd.clarity.bmf.communication.IMembershipPacket;
import ie.ucd.clarity.bmf.network.manager.IBMFGroup;
import ie.ucd.clarity.bmf.network.manager.IBMFNode;
import ie.ucd.clarity.bmf.network.manager.nodesAndGroupsManager.IBMFNodesAndGroupsManager;
import ie.ucd.clarity.bmf.network.manager.nodesAndGroupsManager.TooManyGroupsCreatedException;
import ie.ucd.clarity.bmf.network.manager.nodesAndGroupsManager.WrongDestinationFormulaException;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.dnd.Autoscroll;
import java.awt.dnd.DnDConstants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 * @author  Andrea
 */
public class GroupsTree extends JTree implements Autoscroll {

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
	 * @uml.property  name="groups"
	 */
	ArrayList<BMFGroupGUI> groups;
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

	public GroupsTree(DefaultMutableTreeNode tree) {
		super(tree);
		root = tree;
		groups = new ArrayList<BMFGroupGUI>();
		dt = new TreeDropTarget(this);
		setIcons();
	}

	public void createDragSource() {
		ds = new TreeDragSource(this, DnDConstants.ACTION_COPY);
	}

	public void createDropTarget() {
		dt = new TreeDropTarget(this);
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
		// Rectangle outer = getBounds();
		// Rectangle inner = getParent().getBounds();
		// g.setColor(Color.red);
		// g.drawRect(-outer.x + 12, -outer.y + 12, inner.width - 24,
		// inner.height - 24);
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

	public DefaultMutableTreeNode addNewGroup(BMFGroupGUI n, boolean isAutomatic) {
		try {
			if (!groups.contains(n)) {
				if (groups.size() < Constants.MAX_GROUPS) {// mettere il max
					// numero di gruppi
					// creati
					DefaultMutableTreeNode newGroup = new DefaultMutableTreeNode(
							n);
					groups.add(n);
					root.add(newGroup);
					manager.addGroup(n.getName());
					((DefaultTreeModel) this.getModel()).reload();
					return newGroup;
				} else {
					JOptionPane.showMessageDialog(this, "Too Many Groups!!");
					return null;
				}
			} else {
				if (!isAutomatic)
					JOptionPane.showMessageDialog(this, n.getName()
							+ " already exists");
				for (int i = 0; i < groups.size(); i++)
					if (groups.get(i).equals(n))
						return (DefaultMutableTreeNode) ((DefaultTreeModel) this
								.getModel()).getChild(root, i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @return
	 * @uml.property  name="groups"
	 */
	public ArrayList<BMFGroupGUI> getGroups() {
		return groups;
	}

	/**
	 * @param groups
	 * @uml.property  name="groups"
	 */
	public void setGroups(ArrayList<BMFGroupGUI> groups) {
		this.groups = groups;
	}

	public boolean removeNodeFromGroup(DefaultMutableTreeNode oldNode) {

		BMFNodeGUI old = (BMFNodeGUI) oldNode.getUserObject();
		DefaultMutableTreeNode par = (DefaultMutableTreeNode) oldNode
				.getParent();
		BMFGroupGUI torem = (BMFGroupGUI) par.getUserObject();
		if (JOptionPane.showConfirmDialog(this, "Do you want leave the group "
				+ torem.getName() + "?", "Move or Copy",
				JOptionPane.YES_NO_CANCEL_OPTION) == 0) {
			torem.removeNode(old);
			old.deleteGroup(torem);
			((DefaultTreeModel) getModel()).removeNodeFromParent(oldNode);
			((DefaultTreeModel) getModel()).reload();
			// setMembership(torem, old.getID(),
			// Constants.MEMBERSHIP_TYPE_DELETE);
			return setMembership(torem, old.getID(),
					Constants.MEMBERSHIP_TYPE_DELETE);
		} else {
			return false;
		}
	}

	public boolean addNodeToGroup(BMFNodeGUI nodeObject,
			DefaultMutableTreeNode parent) {
		TreePath p = nodeObject.getPath();
		DefaultMutableTreeNodeClonable node = new DefaultMutableTreeNodeClonable(
				(DefaultMutableTreeNode) p.getLastPathComponent());
		BMFGroupGUI g = ((BMFGroupGUI) parent.getUserObject());
		DefaultTreeModel model = (DefaultTreeModel) this.getModel();
		if (!nodeObject
				.isBelongingToGroup((BMFGroupGUI) parent.getUserObject())) {
			if (JOptionPane.showConfirmDialog(this,
					"Are you sure you want to add the node '"
							+ nodeObject.getName() + "' to '" + g.getName()
							+ "'?") == 0) {
				g.addNode(nodeObject);
				nodeObject.addGroup((BMFGroupGUI) parent.getUserObject());
				model.insertNodeInto(node, parent, 0);
				model.reload();
				return setMembership(g, nodeObject.getID(),
						Constants.MEMBERSHIP_TYPE_ADD);
			} else {
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "The node "
					+ nodeObject.getName() + " already belongs to "
					+ ((BMFGroupGUI) parent.getUserObject()).getName());
			return false;
		}

	}

	/*
	 * public boolean addNodeToGroupAutomatically(DefaultMutableTreeNode node,
	 * DefaultMutableTreeNode parent) {
	 * 
	 * DefaultMutableTreeNodeClonable nodeC = new
	 * DefaultMutableTreeNodeClonable(node); BMFNodeGUI nodeObject=(BMFNodeGUI)
	 * nodeC.getUserObject(); BMFGroupGUI g = ((BMFGroupGUI)
	 * parent.getUserObject()); DefaultTreeModel model = (DefaultTreeModel)
	 * this.getModel(); if (!nodeObject.isBelongingToGroup(g)) {
	 * g.addNode(nodeObject); nodeObject.addGroup((BMFGroupGUI)
	 * parent.getUserObject()); model.insertNodeInto(nodeC, parent, 0);
	 * model.reload(); return setMembership(g, nodeObject.getID(),
	 * Constants.MEMBERSHIP_TYPE_ADD); } else { System.out.println("The node " +
	 * nodeObject.getName() + " already belongs to " + ((BMFGroupGUI)
	 * parent.getUserObject()).getName()); return false; }
	 * 
	 * }
	 */

	public boolean addNodeToGroupsAutomatically(DefaultMutableTreeNode node,
			ArrayList<DefaultMutableTreeNode> parents) {

		BMFNodeGUI nodeObject = null;
		DefaultTreeModel model = (DefaultTreeModel) this.getModel();
		ArrayList<BMFGroupGUI> list = new ArrayList<BMFGroupGUI>();
		for (int i = 0; i < parents.size(); i++) {
			DefaultMutableTreeNodeClonable nodeC = new DefaultMutableTreeNodeClonable(
					node);
			nodeObject = (BMFNodeGUI) nodeC.getUserObject();
			BMFGroupGUI g = ((BMFGroupGUI) parents.get(i).getUserObject());
			if (!nodeObject.isBelongingToGroup(g)) {
				g.addNode(nodeObject);
				nodeObject.addGroup(g);
				model.insertNodeInto(nodeC, parents.get(i), 0);
				model.reload();
				list.add(g);
			} else {
				System.out.println("The node "
						+ nodeObject.getName()
						+ " already belongs to "
						+ ((BMFGroupGUI) parents.get(i).getUserObject())
								.getName());
				// return false;
			}
		}
		return setMembership(list, nodeObject.getID(),
				Constants.MEMBERSHIP_TYPE_ADD);
	}

	public DefaultMutableTreeNode getGroupNode(BMFGroupGUI g) {
		return findAndExpandInTree(g.getName());
	}

	public DefaultMutableTreeNode findAndExpandInTree(String nodeName) {
		TreePath path = this.getNextMatch(nodeName, 0,
				javax.swing.text.Position.Bias.Forward);
		return (DefaultMutableTreeNode) path.getLastPathComponent();
	}

	public boolean clearGroup(BMFGroupGUI groupObject,
			DefaultMutableTreeNode node) {
		if (JOptionPane.showConfirmDialog(this, "Do you want clear the group "
				+ groupObject.getName() + "?", "Clear Group",
				JOptionPane.YES_NO_CANCEL_OPTION) == 0) {
			DefaultMutableTreeNode dmtnnode = null;
			ArrayList<BMFNodeGUI> n = new ArrayList<BMFNodeGUI>();
			for (int j = 0; j < node.getChildCount(); j++) {
				dmtnnode = (DefaultMutableTreeNode) ((DefaultTreeModel) this
						.getModel()).getChild(node, j);
				n.add((BMFNodeGUI) dmtnnode.getUserObject());
			}

			groupObject.removeAll();
			node.removeAllChildren();
			((DefaultTreeModel) getModel()).reload();
			return setMembership(groupObject, n,
					Constants.MEMBERSHIP_TYPE_DELETE);
		}
		return false;
	}

	public void removeGroup(BMFGroupGUI groupObject, DefaultMutableTreeNode node) {
		clearGroup(groupObject, node);
		((DefaultTreeModel) getModel()).removeNodeFromParent(node);
		((DefaultTreeModel) getModel()).reload();
		groups.remove(groupObject);
		manager.removeGroup(groupObject.getIgroup());
	}

	public void renameGroup(BMFGroupGUI groupObject, DefaultMutableTreeNode node) {
		String s = JOptionPane.showInputDialog(this, "Insert a new Name",
				new String("Group"));
		if (s != null) {
			groupObject.setName(s);
			BMFGroupGUI toren = ((BMFGroupGUI) node.getUserObject());
			toren.setName(s);
			toren.getIgroup().setName(s);
			((DefaultTreeModel) getModel()).reload();
		}
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

	public ArrayList<DefaultMutableTreeNode> getGroupsContains(BMFNodeGUI nodeO) {
		ArrayList<DefaultMutableTreeNode> toRet = new ArrayList<DefaultMutableTreeNode>();
		DefaultMutableTreeNode dmtngroup = null;
		DefaultMutableTreeNode dmtnnode = null;
		for (int i = 0; i < groups.size(); i++) {
			dmtngroup = (DefaultMutableTreeNode) ((DefaultTreeModel) this
					.getModel()).getChild(root, i);
			for (int j = 0; j < dmtngroup.getChildCount(); j++) {
				dmtnnode = (DefaultMutableTreeNode) ((DefaultTreeModel) this
						.getModel()).getChild(dmtngroup, j);
				if (((BMFNodeGUI) dmtnnode.getUserObject()).equals(nodeO)) {
					toRet.add(dmtnnode);
				}
			}
		}
		return toRet;
	}

	private boolean setMembership(BMFGroupGUI n, int destNodeID,
			int membershipType) {

		IMembershipPacket memb = frame.getConsumer().getPacketBuilder()
				.getIMembershipPacket();
		IBMFGroup group = n.getIgroup();

		int[] groupIDs = { group.getGroupID() };
		memb.setGroupIDs(groupIDs);
		memb.setMembershipType(membershipType);
		memb.setDestinationNode(destNodeID);
		new MembershipSender(memb).start();
		return true;
	}

	private boolean setMembership(BMFGroupGUI n, ArrayList<BMFNodeGUI> nodes,
			int membershipType) {

		IMembershipPacket memb = frame.getConsumer().getPacketBuilder()
				.getIMembershipPacket();
		IBMFGroup group = n.getIgroup();
		int[] groupIDs = { group.getGroupID() };
		memb.setGroupIDs(groupIDs);
		memb.setMembershipType(membershipType);
		for (int i = 0; i < nodes.size(); i++) {
			memb.setDestinationNode(nodes.get(i).getInode().getNodeID());
			new MembershipSender(memb).start();
		}
		return true;
	}

	private boolean setMembership(ArrayList<BMFGroupGUI> n, int destNodeID,
			int membershipType) {

		IMembershipPacket memb = frame.getConsumer().getPacketBuilder()
				.getIMembershipPacket();

		int[] groupIDs = new int[n.size()];
		for (int i = 0; i < groupIDs.length; i++) {
			groupIDs[i] = n.get(i).getIgroup().getGroupID();
		}
		memb.setGroupIDs(groupIDs);
		memb.setMembershipType(membershipType);
		memb.setDestinationNode(destNodeID);
		new MembershipSender(memb).start();	
		return true;
	}

	/**
	 * @author  Matthew
	 */
	class MembershipSender extends Thread {

		/**
		 * @uml.property  name="memb"
		 * @uml.associationEnd  
		 */
		IMembershipPacket memb;

		public MembershipSender(IMembershipPacket memb) {
			this.memb = memb;
		}

		@Override
		public void run() {
			try {
				frame.getConsumer().getiNetworkManager().sendPacket(memb);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
