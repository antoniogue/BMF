package ie.ucd.clarity.bmf.network.manager.configurationPackets;

import ie.ucd.clarity.bmf.communication.IConfigurationPacket;

public interface IConfigurationPacketsManager {

	public IConfigurationPacket getBMFRequestByID(int configurationID);
	
	public int addIConfigurationPacket(String name, IConfigurationPacket iConfigurationPacket );
	
	public int addIConfigurationPacket(IConfigurationPacket iConfigurationPacket);
	
	public void activateIConfigurationTimerByID(int configurationID, IBMFRequestDurationListener listener);
	
	public void deactivateIConfigurationTimerByID(int configurationID);
	
	public String getIConfigurationNameByID(int configurationID);
}
