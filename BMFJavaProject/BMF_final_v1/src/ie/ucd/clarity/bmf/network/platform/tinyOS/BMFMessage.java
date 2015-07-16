package ie.ucd.clarity.bmf.network.platform.tinyOS;

import ie.ucd.clarity.bmf.common.Constants;

public class BMFMessage extends net.tinyos.message.Message {
    /** The default size of this message type in bytes. */
    public static final int DEFAULT_MESSAGE_SIZE = 3;

    /** The Active Message type associated with this message. */
    public static final int AM_TYPE = Constants.AM_BMF;

    /** Create a new BlackPlugMessage of size 4. */
    public BMFMessage() {
        super(DEFAULT_MESSAGE_SIZE);
        amTypeSet(AM_TYPE);
    }

    /** Create a new BlackPlugMessage of the given data_length. */
    public BMFMessage(int data_length) {
        super(data_length);
        amTypeSet(AM_TYPE);
    }

    /**
     * Create a new BlackPlugMessage using the given byte array
     * as backing store.
     */
    public BMFMessage(byte[] data) {
        super(data);
        amTypeSet(AM_TYPE);
    }

    /**
    /* Return a String representation of this message. Includes the
     * message type name and the non-indexed field values.
     */
    public String toString() {
      String s = "Message <BMFMessage> with length = "+super.dataGet().length +
      				"\n values BIN: ";
      byte[] dataTemp = super.dataGet();
      for(int i = 0; i < dataTemp.length; i++){
    	  for(int j = 7; j >= 0; j--) s += (dataTemp[i] >> j) & 1;
    	  s+=" ";
      }
      s += "\n values HEX: ";
      for(int i = 0; i < dataTemp.length; i++) {
    	  int temp = (dataTemp[i] & 0xFF);
    	  s +=Integer.toHexString(temp) + " ";
      }
      return s;
    }


}
