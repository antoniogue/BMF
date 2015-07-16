package ie.ucd.clarity.bmf.communication;

public class PacketBuilder implements IPacketBuilder {

	@Override
	public IAckPacket getIAckPacket() {
		return new AckPacket();
	}

	@Override
	public IConfigurationPacket getIConfigurationPacket() {
		return new ConfigurationPacket();
	}

	@Override
	public IDataPacket getIDataPacket() {
		return new DataPacket();
	}

	@Override
	public IMembershipPacket getIMembershipPacket() {
		return new MembershipPacket();
	}

	@Override
	public INewNodePacket getINewNodePacket() {
		return new NewNodePacket();
	}

	@Override
	public IResetPacket getIResetPacket() {
		return new ResetPacket();
	}

	@Override
	public IDestination getIDestination() {
		return new Destination();
	}

}
