package ie.ucd.clarity.bmf.communication;

import ie.ucd.clarity.bmf.communication.IDataPacket;

import ie.ucd.clarity.bmf.common.Constants;

/**
 * @author  Matthew
 */
public class DataPacket implements IDataPacket {

	/**
	 * @uml.property  name="pACKET_TYPE"
	 */
	private final int PACKET_TYPE = Constants.PKT_TYPE_DATA;
	// private int dataCount; // is the length of the following arrays
	/**
	 * @uml.property  name="senderID"
	 */
	private int senderID[];
	/**
	 * @uml.property  name="requestID"
	 */
	private int requestID[];
	/**
	 * @uml.property  name="result"
	 */
	private long result[];
	/**
	 * @uml.property  name="counter"
	 */
	private int counter[];

	public DataPacket() {
		
	}
	
	public void setParams(int senderID[], int requestID[], long result[],
			int counter[]) {
		this.senderID = new int[senderID.length];
		System.arraycopy(senderID, 0, this.senderID, 0, senderID.length);

		this.requestID = new int[requestID.length];
		System.arraycopy(requestID, 0, this.requestID, 0, requestID.length);

		this.result = new long[result.length];
		System.arraycopy(result, 0, this.result, 0, result.length);

		this.counter = new int[counter.length];
		System.arraycopy(counter, 0, this.counter, 0, counter.length);
	}

	/**
	 * @return
	 * @uml.property  name="pACKET_TYPE"
	 */
	public int getPACKET_TYPE() {
		return PACKET_TYPE;
	}

	/**
	 * @return
	 * @uml.property  name="senderID"
	 */
	public int[] getSenderID() {
		return senderID;
	}

	/**
	 * @return
	 * @uml.property  name="requestID"
	 */
	public int[] getRequestID() {
		return requestID;
	}

	/**
	 * @return
	 * @uml.property  name="result"
	 */
	public long[] getResult() {
		return result;
	}

	/**
	 * @return
	 * @uml.property  name="counter"
	 */
	public int[] getCounter() {
		return counter;
	}

}
