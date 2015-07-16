/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.ucd.clarity.bmf.GUI.common;

import ie.ucd.clarity.bmf.network.manager.IBMFGroup;

import java.util.ArrayList;

import javax.swing.tree.TreePath;

/**
 * @author  Andrea
 */
public class BMFGroupGUI implements BMFObjectGUI {

	/**
	 * @uml.property  name="name"
	 */
	private String name;
	/**
	 * @uml.property  name="iD"
	 */
	private int ID;
	/**
	 * @uml.property  name="nodes"
	 */
	private ArrayList<BMFNodeGUI> nodes;
	private TreePath path;
	/**
	 * @uml.property  name="igroup"
	 * @uml.associationEnd  
	 */
	private IBMFGroup igroup;

	public BMFGroupGUI(IBMFGroup igroup) {
		if (igroup != null) {
			this.igroup = igroup;
			this.name = igroup.getName();
			this.nodes = new ArrayList<BMFNodeGUI>();
			ID = igroup.getGroupID();
			path = null;
		}else this.name = "Groups";
	}

	/**
	 * @return
	 * @uml.property  name="name"
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 * @uml.property  name="name"
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param path
	 * @return
	 * @uml.property  name="path"
	 */
	public BMFObjectGUI setPath(TreePath path) {
		this.path = path;
		return this;
	}

	/**
	 * @return
	 * @uml.property  name="nodes"
	 */
	public ArrayList<BMFNodeGUI> getNodes() {
		return nodes;
	}

	public boolean addNode(BMFNodeGUI n) {
		if (!nodes.contains(n)) {
			nodes.add(n);
			igroup.addNode(n.getInode());
			return true;
		} else {
			return false;
		}
	}

	public boolean removeNode(BMFNodeGUI n) {
		if (nodes.contains(n)) {
			nodes.remove(n);
			igroup.deleteNode(n.getInode());
			return true;
		} else {
			return false;
		}
	}
	
	void p(){
		for(int i=0; i<nodes.size();i++)
		System.out.println(nodes.get(i).getName());
	}

	/**
	 * @param nodes
	 * @uml.property  name="nodes"
	 */
	public void setNodes(ArrayList<BMFNodeGUI> nodes) {
		this.nodes = nodes;
	}

	/**
	 * @return
	 * @uml.property  name="iD"
	 */
	public int getID() {
		return ID;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof BMFGroupGUI))
			return false;
		if (((BMFGroupGUI) o).getID() == this.ID || ((BMFGroupGUI) o).getName().equals(this.getName()))
			return true;
		return false;
	}

	@Override
	public String toString() {
		return getName();
	}

	/**
	 * @return  the igroup
	 * @uml.property  name="igroup"
	 */
	public IBMFGroup getIgroup() {
		return igroup;
	}

	public void removeAll() {
		for (int i = 0; i < nodes.size(); i++){
			nodes.get(i).deleteGroup(this);
		}
		igroup.getNodes().clear();
		nodes.clear();
	}

	
}
