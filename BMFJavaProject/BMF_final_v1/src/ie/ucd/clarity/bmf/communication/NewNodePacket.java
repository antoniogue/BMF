package ie.ucd.clarity.bmf.communication;

import ie.ucd.clarity.bmf.communication.INewNodePacket;
import ie.ucd.clarity.bmf.common.Constants;

/**
 * @author  Matthew
 */
public class NewNodePacket implements INewNodePacket {
	
	/**
	 * @uml.property  name="pACKET_TYPE"
	 */
	private final int PACKET_TYPE = Constants.PKT_TYPE_NEW_NODE;
	/**
	 * @uml.property  name="senderID"
	 */
	private int senderID;
	/**
	 * @uml.property  name="sensorBoardType"
	 */
	private int sensorBoardType;
    /**
	 * @uml.property  name="functions"
	 */
    private int functions[];
	
	public NewNodePacket(int senderID, int sensorBoardType){
		this.senderID = senderID;
		this.sensorBoardType = sensorBoardType;
	}
	
	public NewNodePacket() {
	}

	/**
	 * @return
	 * @uml.property  name="senderID"
	 */
	public int getSenderID() {
		return senderID;
	}
	/**
	 * @param senderID
	 * @uml.property  name="senderID"
	 */
	public void setSenderID(int senderID) {
		this.senderID = senderID;
	}
	/**
	 * @return
	 * @uml.property  name="sensorBoardType"
	 */
	public int getSensorBoardType() {
		return sensorBoardType;
	}
	/**
	 * @param sensorBoardType
	 * @uml.property  name="sensorBoardType"
	 */
	public void setSensorBoardType(int sensorBoardType) {
		this.sensorBoardType = sensorBoardType;
	}
	/**
	 * @return
	 * @uml.property  name="pACKET_TYPE"
	 */
	public int getPACKET_TYPE() {
		return PACKET_TYPE;
	}

	/**
	 * @return  the functions
	 * @uml.property  name="functions"
	 */
	public int[] getFunctions() {
		return functions;
	}

	/**
	 * @param functions  the functions to set
	 * @uml.property  name="functions"
	 */
	public void setFunctions(int[] functions) {
		this.functions = new int[functions.length];
        System.arraycopy(functions, 0, this.functions, 0, functions.length);
	}
	
}
