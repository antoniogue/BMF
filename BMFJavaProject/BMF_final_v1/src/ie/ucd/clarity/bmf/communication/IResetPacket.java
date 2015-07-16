package ie.ucd.clarity.bmf.communication;

import ie.ucd.clarity.bmf.common.InvalidPacketParametersException;


/**
 * @author   Matthew
 */
public interface IResetPacket extends IBMFPacket {
    
	/**
	 * @uml.property  name="destination"
	 * @uml.associationEnd  
	 */
	public IDestination getDestination();

	/**
	 * @param  dest
	 * @uml.property  name="destination"
	 */
	public void setDestination(IDestination dest);

	public void setDestinationBroadcast();

	public void setDestinationGroups(String groups) throws InvalidPacketParametersException;

	public void setDestinationNode(int destNodeID);
}
