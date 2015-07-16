package ie.ucd.clarity.bmf.communication;

import ie.ucd.clarity.bmf.common.InvalidPacketParametersException;


/**
 * @author   Matthew
 */
public interface IMembershipPacket extends IBMFPacket {
    
	public void addGroupID(int gID);

	/**
	 * @uml.property  name="destination"
	 * @uml.associationEnd  
	 */
	public IDestination getDestination();

	/**
	 * @return
	 * @uml.property  name="groupIDs"
	 */
	public int[] getGroupIDs();

	/**
	 * @return
	 * @uml.property  name="membershipGroups"
	 */
	public int getMembershipGroups();

	/**
	 * @return
	 * @uml.property  name="membershipType"
	 */
	public int getMembershipType();

	public int getPACKET_TYPE();

	/**
	 * @param  dest
	 * @uml.property  name="destination"
	 */
	public void setDestination(IDestination dest);

	public void setDestinationBroadcast();

	public void setDestinationGroups(String groups) throws InvalidPacketParametersException;

	public void setDestinationNode(int destNodeID);

	public void setGroupID(int groupID);

	/**
	 * @param  groupIDs
	 * @uml.property  name="groupIDs"
	 */
	public void setGroupIDs(int[] groupIDs);

	public void setHowManyGroups(int howManyGroups);

	public void setMembershipGroups(String g) throws InvalidPacketParametersException;

	/**
	 * @param  membershipGroups
	 * @uml.property  name="membershipGroups"
	 */
	public void setMembershipGroups(int membershipGroups);

	/**
	 * @param  membershipType
	 * @uml.property  name="membershipType"
	 */
	public void setMembershipType(int membershipType);

}
