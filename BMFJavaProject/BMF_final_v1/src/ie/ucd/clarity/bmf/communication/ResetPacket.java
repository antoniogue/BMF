package ie.ucd.clarity.bmf.communication;

import ie.ucd.clarity.bmf.communication.IDestination;

import ie.ucd.clarity.bmf.common.Constants;
import ie.ucd.clarity.bmf.common.InvalidPacketParametersException;
import ie.ucd.clarity.bmf.communication.IResetPacket;

/**
 * @author  Matthew
 */
public class ResetPacket implements IResetPacket {

	/**
	 * @uml.property  name="pACKET_TYPE"
	 */
	private final int PACKET_TYPE = Constants.PKT_TYPE_RESET_NODE;

	/**
	 * @uml.property  name="dest"
	 * @uml.associationEnd  
	 */
	private IDestination dest;

	public ResetPacket() {
		dest = new Destination();
	}

	/**
	 * @return
	 * @uml.property  name="pACKET_TYPE"
	 */
	public int getPACKET_TYPE() {
		return PACKET_TYPE;
	}

	public void setDestinationNode(int destNodeID) {
		dest.setDestinationNode(destNodeID);
	}

	public void setDestinationBroadcast() {
		dest.setDestinationBroadcast();
	}

	@Override
	public IDestination getDestination() {
		return this.dest;
	}

	public void setDestination(IDestination dest) {
		this.dest = dest;
	}

	public void setDestinationGroups(String groups) throws InvalidPacketParametersException {
		dest.setDestinationGroups(groups);
	}


}
