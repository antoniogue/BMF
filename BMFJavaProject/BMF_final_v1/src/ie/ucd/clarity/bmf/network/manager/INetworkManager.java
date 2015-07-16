package ie.ucd.clarity.bmf.network.manager;

import java.io.IOException;

import ie.ucd.clarity.bmf.communication.IBMFPacket;
import ie.ucd.clarity.bmf.network.platform.IPlatform;
import ie.ucd.clarity.bmf.network.platform.IPlatformListener;

public interface INetworkManager extends IPlatformListener {

	public void sendPacket(IBMFPacket pkt) throws IOException;
	
	//public void setSensorboardParams(int sensorboardType, String bsPort, String bsSpeed);
	
	public void addIPlatform(IPlatform iPlatform);
	
	public void removeIPlatform(IPlatform iPlatform);
	
	public void registerListener(INetworkBMFListener iLowLevelBMFListener);
	
	public void unRegisterListener(INetworkBMFListener iLowLevelBMFListener);
	
	public IBMFNode getIBMFNode(int ID, int sensorBoard,String sensorBoardName,
			int[] sensors, int[]functions);
	
	public IBMFGroup getIBMFGroup(int ID, String name);
	
	public IBMFDataIn getIBMFDataIn(int senderID, int requestID, long result, int counter);

}