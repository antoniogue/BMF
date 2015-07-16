package ie.ucd.clarity.bmf.network.manager;


public interface INetworkBMFListener {
	
	public void newNodeInTheNetwork(IBMFNode newNode, java.util.Calendar time);
	
	public void newDataIn(IBMFDataIn data, java.util.Calendar time);

    public void ackReceived(int senderID, int pktTypeToAck, int param, java.util.Calendar time);
}
