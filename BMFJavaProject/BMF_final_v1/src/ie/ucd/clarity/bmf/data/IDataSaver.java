package ie.ucd.clarity.bmf.data;

import ie.ucd.clarity.bmf.communication.IConfigurationPacket;

public interface IDataSaver {
	
	public void saveIConfigurationPacket(IConfigurationPacket packet) throws Exception;
	
	public boolean iConfigurationPacketExpired(IConfigurationPacket packet);
	
	public void saveData(int senderID, int requestID, long result, int counter) throws Exception;
	
	public boolean closeConnection();
	
}
