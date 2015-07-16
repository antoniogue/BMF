package ie.ucd.clarity.bmf.communication;

public interface IPacketBuilder {
	
	public IAckPacket getIAckPacket();
	
	public IConfigurationPacket getIConfigurationPacket();
	
	public IDataPacket getIDataPacket();
	
	public IMembershipPacket getIMembershipPacket();
	
	public INewNodePacket getINewNodePacket();
	
	public IResetPacket getIResetPacket();
	
	public IDestination getIDestination();

	
	

	
}
