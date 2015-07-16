package ie.ucd.clarity.bmf.network.platform;

import java.io.IOException;
import java.util.ArrayList;

public interface IPlatform {

	public void sendMessage(byte [] messageData) throws IOException;
	
	public void openConnection(/*String bsPort, String bsSpeed*/);
	
	public String getPlatformName();
	
	public ArrayList<String> getSensorBoards();
	
	public int[] getSensorBoardSensors(int sensorBoardType);
	
	public String getSensorBoardName(int sensorBoardType);
	
	public void setIPlatformListener(IPlatformListener iPlatformListener);
	
	public void unsetIPlatformListener(IPlatformListener iPlatformListener);
	
}