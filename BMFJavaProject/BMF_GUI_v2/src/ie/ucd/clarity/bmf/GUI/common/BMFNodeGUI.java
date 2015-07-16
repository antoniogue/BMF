/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.ucd.clarity.bmf.GUI.common;

import ie.ucd.clarity.bmf.common.Constants;
import ie.ucd.clarity.bmf.common.CodeConversion;
import ie.ucd.clarity.bmf.network.manager.IBMFNode;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.tree.TreePath;

/**
 * @author  Andrea
 */
public class BMFNodeGUI implements BMFObjectGUI {

	/**
	 * @uml.property  name="iD"
	 */
	private int ID;
	/**
	 * @uml.property  name="name"
	 */
	private String name;
	/**
	 * @uml.property  name="sensors"
	 */
	private ArrayList<BMFSensorGUI> sensors;
	/**
	 * @uml.property  name="groups"
	 */
	private ArrayList<BMFGroupGUI> groups;
	/**
	 * @uml.property  name="functions"
	 */
	private ArrayList<BMFFunctionsGUI> functions;
	/**
	 * @uml.property  name="icon"
	 */
	private ImageIcon icon;
	/**
	 * @uml.property  name="path"
	 */
	private TreePath path;
	/**
	 * @uml.property  name="floorplans"
	 */
	private ArrayList<Integer> floorplans;
	/**
	 * @uml.property  name="inode"
	 * @uml.associationEnd  
	 */
	private IBMFNode inode;

	
		
	public BMFNodeGUI(IBMFNode in) {
		if (in != null) {
			this.inode = in;
			this.ID = in.getNodeID();
			this.name = toString();
			createSensors();
			createFunctions();
			createGroups();
			this.icon = new ImageIcon(Constants.telosbNodeGIF);
			path = null;
			floorplans = new ArrayList<Integer>();
		}else{
			this.ID = 0;
			this.name = Constants.SUNSPOT_BS_SPEED+" PORT="+Constants.SUNSPOT_BS_PORT+", "+Constants.TELOSB_BS_SPEED+" PORT="+Constants.TELOSB_BS_PORT;
			this.sensors = new ArrayList<BMFSensorGUI>();
			this.icon = new ImageIcon(Constants.telosbNodeGIF);
			path = null;
			floorplans = new ArrayList<Integer>();			
		}
	}

	private void createSensors() {
		this.sensors = new ArrayList<BMFSensorGUI>();
		for (int i = 0; i < inode.getSensors().length; i++) {
			sensors.add(new BMFSensorGUI(inode.getSensors()[i]));			
		}
	}
	private void createFunctions() {
		this.functions = new ArrayList<BMFFunctionsGUI>();
		for (int i = 0; i < inode.getFunctions().length; i++) {
			functions.add(new BMFFunctionsGUI(CodeConversion.getFunctionName(inode.getFunctions()[i])));			
		}
	}
	private void createGroups() {
		groups = new ArrayList<BMFGroupGUI>();
		for (int i = 0; i < inode.getGroups().size(); i++) {
			groups.add(new BMFGroupGUI(inode.getGroups().elementAt(i)));
		}
	}

	public Integer getFloorplan(int i) {
		return floorplans.get(i);
	}

	public boolean isOnFloorplan(int nFloorplan) {
		return floorplans.contains(nFloorplan);
	}

	/**
	 * @return
	 * @uml.property  name="iD"
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @return
	 * @uml.property  name="floorplans"
	 */
	public ArrayList<Integer> getFloorplans() {
		return floorplans;
	}

	public void setFloorplan(Integer floorplan) {
		this.floorplans.add(floorplan);
	}

	public void removeFloorplan(int floorplanToRemove) {
		this.floorplans.remove(new Integer(floorplanToRemove));
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
	 * @return
	 * @uml.property  name="sensors"
	 */
	public ArrayList<BMFSensorGUI> getSensors() {
		return sensors;
	}

	/**
	 * @param sensors
	 * @uml.property  name="sensors"
	 */
	public void setSensors(ArrayList<BMFSensorGUI> sensors) {
		this.sensors = sensors;
	}
	
	

	/**
	 * @return  the functions
	 * @uml.property  name="functions"
	 */
	public ArrayList<BMFFunctionsGUI> getFunctions() {
		return functions;
	}

	/**
	 * @param functions  the functions to set
	 * @uml.property  name="functions"
	 */
	public void setFunctions(ArrayList<BMFFunctionsGUI> functions) {
		this.functions = functions;
	}

	/**
	 * @return
	 * @uml.property  name="icon"
	 */
	public ImageIcon getIcon() {
		return icon;
	}
	
	/**
	 * @return
	 * @uml.property  name="inode"
	 */
	public IBMFNode getInode() {
		return inode;
	}

	/**
	 * @param icon
	 * @uml.property  name="icon"
	 */
	public void setIcon(ImageIcon icon) {
		this.icon = icon;
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

	/**
	 * @return
	 * @uml.property  name="path"
	 */
	public TreePath getPath() {
		return path;
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

	public void printGroups() {
		for (int i = 0; i < groups.size(); i++) {
			System.out.println(groups.get(i));
		}
	}

	public boolean isBelongingToGroup(BMFGroupGUI g) {		
		//return inode.isBelongingToGroup(g.getIgroup());
		return groups.contains(g);		
	}

	public boolean addGroup(BMFGroupGUI g) {
		if (!groups.contains(g)) {
			groups.add(g);
			inode.addGroup(g.getIgroup());
			return true;
		} else {
			return false;
		}
	}

	public boolean deleteGroup(BMFGroupGUI g) {
		if (groups.contains(g)) {
			groups.remove(g);
			inode.deleteGroup(g.getIgroup());
			return true;
		} else {
			return false;
		}
	}

	public void resetGroups() {
		groups.clear();
		
	}

	@Override
	public String toString() {
		if(ID!=0)
		return "Node " + this.ID;
		else return name;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof BMFNodeGUI))
			return false;
		if (((BMFNodeGUI) o).getID() == this.ID)
			return true;
		return false;
	}
}
