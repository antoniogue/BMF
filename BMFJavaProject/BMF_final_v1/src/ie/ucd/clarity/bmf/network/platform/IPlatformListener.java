package ie.ucd.clarity.bmf.network.platform;

public interface IPlatformListener {

	public void messageReceived(byte[] messageData, IPlatform iPlatform);
	
}
