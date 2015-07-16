package ie.ucd.clarity.bmf.GUI.start;


import ie.ucd.clarity.bmf.communication.IConfigurationPacket;
import ie.ucd.clarity.bmf.network.manager.IBMFDataIn;
import ie.ucd.clarity.bmf.network.manager.IBMFNode;

import java.util.Calendar;

public class BMFManager implements BMFMarker{

	@Override
	public void newNodeInTheNetwork(IBMFNode newNode, Calendar time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newDataIn(IBMFDataIn data, Calendar time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ackReceived(int senderID, int pktTypeToAck, int param,
			Calendar time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestExpired(IConfigurationPacket iConfiguration) {
		// TODO Auto-generated method stub
		
	}

	

}
