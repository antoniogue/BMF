package ie.ucd.clarity.bmf.communication;

import ie.ucd.clarity.bmf.common.Constants;
import ie.ucd.clarity.bmf.communication.IAckPacket;

/**
 * @author  Matthew
 */
public class AckPacket implements IAckPacket {

	/**
	 * @uml.property  name="pACKET_TYPE"
	 */
	private final int PACKET_TYPE = Constants.PKT_TYPE_ACK;

    /**
	 * @uml.property  name="pktTypeToAck"
	 */
    private int pktTypeToAck;
    /**
	 * @uml.property  name="param"
	 */
    private int param;
    /**
	 * @uml.property  name="senderID"
	 */
    private int senderID;
    
	public AckPacket(int senderID, int pktTypeToAck, int param){
        this.senderID = senderID;
        this.pktTypeToAck = pktTypeToAck;
        this.param = param;
	}

    public AckPacket() {}


	/**
	 * @return
	 * @uml.property  name="pktTypeToAck"
	 */
	public int getPktTypeToAck() {
		return pktTypeToAck;
	}

	/**
	 * @param pktTypeToAck
	 * @uml.property  name="pktTypeToAck"
	 */
	public void setPktTypeToAck(int pktTypeToAck) {
		this.pktTypeToAck = pktTypeToAck;
	}

	/**
	 * @return
	 * @uml.property  name="param"
	 */
	public int getParam() {
		return param;
	}

	/**
	 * @param param
	 * @uml.property  name="param"
	 */
	public void setParam(int param) {
		this.param = param;
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
	 * @uml.property  name="pACKET_TYPE"
	 */
	public int getPACKET_TYPE() {
		return PACKET_TYPE;
	}

    
}
