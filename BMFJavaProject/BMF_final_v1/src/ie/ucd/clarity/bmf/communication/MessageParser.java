package ie.ucd.clarity.bmf.communication;

import ie.ucd.clarity.bmf.common.Constants;
import ie.ucd.clarity.bmf.common.InvalidPacketTypeException;

public class MessageParser implements IMessageParser {

	@Override
	public IBMFPacket parse(byte[] pkt) throws InvalidPacketTypeException {
		IBMFPacket newPacket;
		int index = 0;
        try{
            switch(pkt[index++]){

		        case Constants.PKT_TYPE_NEW_NODE:
		            // here is logic to fill a NewNodePacket object
		            newPacket = new NewNodePacket();
		            ((NewNodePacket)newPacket).setSenderID((pkt[index++] & 0xFF));
		            ((NewNodePacket)newPacket).setSensorBoardType((pkt[index++] & 0xFF));
		            int howManyFunctionsAvailable = (pkt[index++] & 0xFF);
		            int functions[] = new int[howManyFunctionsAvailable];
                    for(int i=0; i<howManyFunctionsAvailable; i++){
                        functions[i] = (pkt[index++] & 0xFF);
                    }
                    ((NewNodePacket)newPacket).setFunctions(functions);
		            break;
		        case Constants.PKT_TYPE_DATA:
                    // here is logic to fill a DataPacket object

                    int dataCount;

                    dataCount = pkt[index++];
                    int senderID[] = new int[dataCount];
                    int counter[] = new int[dataCount];
                    int requestID[] = new int[dataCount];
                    long result[] = new long[dataCount];
                    byte bitsOfResult = 0; // Constants.RESULT_BITS_8 or Constants.RESULT_BITS_16 or Constants.RESULT_BITS_32

                    for(int i=0; i<dataCount; i++){
                            senderID[i] = (pkt[index++] & 0xFF);

                            int tempV = pkt[index++] & 0xFF;
                            bitsOfResult = (byte)((tempV >> 7) & 0x1);
                            counter[i] = tempV & 0x7F;

                            //counter[i] = pkt[index++] & 0xFF;
                            counter[i] = (counter[i] << 8) | (pkt[index++] & 0xFF);

                            requestID[i] = ((pkt[index] >> 1) & 0x7F);

                            bitsOfResult = (byte)((bitsOfResult << 1) | ((byte)(pkt[index++] & 0x1)));


                            if(bitsOfResult == Constants.RESULT_BITS_8){
                                    result[i] = pkt[index++] & 0xFF;
                            }
                            else if(bitsOfResult == Constants.RESULT_BITS_16){
                                    result[i] = pkt[index++] & 0xFF;
                                    result[i] = (result[i] << 8) | (pkt[index++] & 0xFF);
                            }
                            else{ // bitsOfResult == Constants.RESULT_BITS_32
                                    result[i] = pkt[index++] & 0xFF;
                                    result[i] = (result[i] << 8) | (pkt[index++] & 0xFF);
                                    result[i] = (result[i] << 8) | (pkt[index++] & 0xFF);
                                    result[i] = (result[i] << 8) | (pkt[index++] & 0xFF);
                            }
                    }

                    newPacket = new DataPacket();
                    ((DataPacket)newPacket).setParams(senderID, requestID, result, counter);
                    
                    break;

		        case Constants.PKT_TYPE_ACK:
		
		        	
		            newPacket = new AckPacket();
		            ((AckPacket)newPacket).setSenderID((pkt[index++] & 0xFF));
		            ((AckPacket)newPacket).setPktTypeToAck((pkt[index++] & 0xFF));
		            ((AckPacket)newPacket).setParam((pkt[index++] & 0xFF));
		
		            break;
		
		        default: throw new InvalidPacketTypeException("No valid Packet Type in the Packet!");
            }
        } 
        catch(Exception e){
            throw new InvalidPacketTypeException("No valid Packet Type in the Packet!");
        }
		return newPacket;
	}

}
