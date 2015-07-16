package ie.ucd.clarity.bmf.network.manager;

import java.util.Vector;

/**
 * @author  Matthew
 */
public class BMFNode implements IBMFNode {
	private int ID;
	/**
	 * @uml.property  name="sensorBoard"
	 */
	private int sensorBoard;
	/**
	 * @uml.property  name="groups"
	 */
	private Vector<IBMFGroup> groups;
	/**
	 * @uml.property  name="sensorBoardName"
	 */
	private String sensorBoardName;
	/**
	 * @uml.property  name="sensors"
	 */
	private int[] sensors;
	/**
	 * @uml.property  name="functions"
	 */
	private int[] functions;
	/**
	 * 
	 * @param ID
	 * @param sensorBoard
	 * @param sensors 
	 * @param sensorBoardName 
	 */
	public BMFNode(int ID, int sensorBoard, String sensorBoardName, int[] sensors, int[] functions){
		this.ID = ID;
		this.sensorBoard = sensorBoard;
		groups = new Vector<IBMFGroup>();
		this.sensorBoardName =sensorBoardName;
		this.sensors = sensors;
		this.functions=functions;
	}

	/**
	 * @return
	 * @uml.property  name="groups"
	 */
	public Vector<IBMFGroup> getGroups() {
		return groups;
	}

    public boolean isBelongingToGroup(IBMFGroup group){
        return groups.contains(group);
    }

	public boolean addGroup(IBMFGroup group){
        if(groups.contains(group)) 
        	return false;
        this.groups.addElement(group);
        group.addNode(this);
        
        return true;
	}


    public boolean deleteGroup(IBMFGroup group){
        if(!groups.contains(group)) return false;
        groups.removeElement(group);
        group.deleteNode(this);
        return true;
    }

    public void resetGroups(){
        while(groups.size()!=0){
            deleteGroup(groups.elementAt(0));
        }
    }

	public int getNodeID() {
		return ID;
	}

	/**
	 * @return
	 * @uml.property  name="sensorBoard"
	 */
	public int getSensorBoard() {
		return sensorBoard;
	}
	
	/**
	 * @return
	 * @uml.property  name="sensorBoardName"
	 */
	public String getSensorBoardName() {
		return sensorBoardName;
	}

	/**
	 * @return
	 * @uml.property  name="sensors"
	 */
	public int[] getSensors() {
		return sensors;
	}

    /**
	 * @return  the functions
	 * @uml.property  name="functions"
	 */
	public int[] getFunctions() {
		return functions;
	}

	@Override
    public String toString(){
        return "Node "+ this.ID;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof IBMFNode)) return false;
        if(((IBMFNode)o).getNodeID() == this.ID) return true;
        return false;
    }

	
}
