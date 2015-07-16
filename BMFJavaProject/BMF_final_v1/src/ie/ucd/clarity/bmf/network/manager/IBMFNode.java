package ie.ucd.clarity.bmf.network.manager;

import java.util.Vector;

public interface IBMFNode {
	
	public Vector<IBMFGroup> getGroups();

    public boolean isBelongingToGroup(IBMFGroup group);

    /**
     * This method is to add a group in the node membership.
     * The modification is propagated to the corresponding BMFGroup Object
     * @param group
     * @return
     */
	public boolean addGroup(IBMFGroup group);
    
    /**
     * This method is to delete a group in the node membership.
     * The modification is propagated to the corresponding BMFGroup Object
     * @param group
     * @return
     */
    public boolean deleteGroup(IBMFGroup group);
    
    public void resetGroups();

	public int getNodeID();
	
	public int getSensorBoard();
	
	public String getSensorBoardName();
	
	public int [] getSensors();
	
	public int [] getFunctions();
	
	
}
