package ie.ucd.clarity.bmf.communication;

/**
 * @author   Matthew
 */
public interface IAckPacket extends IBMFPacket {
	
   /**
 * @return
 * @uml.property  name="param"
 */
public int getParam();

   /**
 * @return
 * @uml.property  name="pktTypeToAck"
 */
public int getPktTypeToAck();

   /**
 * @return
 * @uml.property  name="senderID"
 */
public int getSenderID();

   /**
 * @param  param
 * @uml.property  name="param"
 */
public void setParam(int param);

   /**
 * @param  pktTypeToAck
 * @uml.property  name="pktTypeToAck"
 */
public void setPktTypeToAck(int pktTypeToAck);

   /**
 * @param  senderID
 * @uml.property  name="senderID"
 */
public void setSenderID(int senderID);
   
}
