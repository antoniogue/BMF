package ie.ucd.clarity.bmf.communication;

/**
 * @author   Matthew
 */
public interface INewNodePacket extends IBMFPacket {

    /**
	 * @return
	 * @uml.property  name="senderID"
	 */
    public int getSenderID();

    /**
	 * @return
	 * @uml.property  name="sensorBoardType"
	 */
    public int getSensorBoardType();

    /**
	 * @param  senderID
	 * @uml.property  name="senderID"
	 */
    public void setSenderID(int senderID);

    /**
	 * @param  sensorBoardType
	 * @uml.property  name="sensorBoardType"
	 */
    public void setSensorBoardType(int sensorBoardType);
    
    public int[] getFunctions();
    
}
