package ie.ucd.clarity.bmf.GUI.common;

/**
 * @author  Matthew
 */
public class BMFDataIn {
	/**
	 * @uml.property  name="senderID"
	 */
	private int senderID;
	/**
	 * @uml.property  name="requestID"
	 */
	private int requestID;
	/**
	 * @uml.property  name="result"
	 */
	private long result;
	/**
	 * @uml.property  name="counter"
	 */
	private int counter;
	
	public BMFDataIn(int senderID, int requestID, long result, int counter){
		this.senderID = senderID;
		this.requestID = requestID;
		this.result = result;
		this.counter = counter;
	}

	/**
	 * @return
	 * @uml.property  name="senderID"
	 */
	public int getSenderID() {
		return senderID;
	}

	/**
	 * @return
	 * @uml.property  name="requestID"
	 */
	public int getRequestID() {
		return requestID;
	}

	/**
	 * @return
	 * @uml.property  name="result"
	 */
	public long getResult() {
		return result;
	}
	
	/**
	 * @return
	 * @uml.property  name="counter"
	 */
	public int getCounter() {
		return counter;
	}
}
