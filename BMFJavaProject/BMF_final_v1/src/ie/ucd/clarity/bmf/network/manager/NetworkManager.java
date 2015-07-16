package ie.ucd.clarity.bmf.network.manager;

import java.io.IOException;

import java.util.Iterator;

import java.util.Vector;

import ie.ucd.clarity.bmf.common.GroupException;
import ie.ucd.clarity.bmf.common.InvalidPacketTypeException;
import ie.ucd.clarity.bmf.communication.IAckPacket;
import ie.ucd.clarity.bmf.communication.IBMFPacket;
import ie.ucd.clarity.bmf.communication.IConfigurationPacket;
import ie.ucd.clarity.bmf.communication.IDataPacket;
import ie.ucd.clarity.bmf.communication.IMembershipPacket;
import ie.ucd.clarity.bmf.communication.IMessageBuilder;
import ie.ucd.clarity.bmf.communication.IMessageParser;
import ie.ucd.clarity.bmf.communication.INewNodePacket;
import ie.ucd.clarity.bmf.communication.IResetPacket;
import ie.ucd.clarity.bmf.network.platform.IPlatform;

/**
 * @author  Matthew
 */
public class NetworkManager implements INetworkManager {

	private Vector<IPlatform> iPlatformList = new Vector<IPlatform>();
	private Vector<INetworkBMFListener> listeners = new Vector<INetworkBMFListener>();
	
	/**
	 * @uml.property  name="iMessageParser"
	 * @uml.associationEnd  
	 */
	private IMessageParser iMessageParser;
	/**
	 * @uml.property  name="iMessageBuilder"
	 * @uml.associationEnd  
	 */
	private IMessageBuilder iMessageBuilder;
	

	public void setIMessageParser (IMessageParser iMessageParser) {
		this.iMessageParser = iMessageParser;
		System.out.println("IMessageParser component is ready");
	}
	
	public void unsetIMessageParser (IMessageParser iMessageParser) {
		if (iMessageParser == this.iMessageParser)
			this.iMessageParser = null;
	}
	
	public void iBMFNodesAndGroupsManager (IMessageBuilder iMessageBuilder) {
		this.iMessageBuilder = iMessageBuilder;
		System.out.println("IMessageBuilder component is ready");
	}
	
	public void unsetIMessageBuilder (IMessageBuilder iMessageBuilder) {
		if (iMessageBuilder == this.iMessageBuilder)
			this.iMessageBuilder = null;
	}
	
	public void setIMessageBuilder (IMessageBuilder iMessageBuilder) {
		this.iMessageBuilder = iMessageBuilder;
		System.out.println("IMessageBuilder component is ready");
	}
	
	@Override
	public void addIPlatform(IPlatform iSensorBoard) {
		iSensorBoard.setIPlatformListener(this);
		this.iPlatformList.add(iSensorBoard);
		System.out.println(iSensorBoard.getPlatformName()+"   .................");
		iSensorBoard.openConnection();
		System.out.println("The Platform " + iSensorBoard.getPlatformName() + " is registered");

	}
	
	@Override
	public void removeIPlatform(IPlatform iSensorBoard) {
		this.iPlatformList.removeElement(iSensorBoard); 
	}

	public void messageReceived(byte[] messageData, IPlatform iPlatform) {
		
		java.util.Calendar currTime = java.util.Calendar.getInstance();
		IBMFPacket packet = null;
		
		try {
			packet = this.iMessageParser.parse(messageData);
		} catch (InvalidPacketTypeException e) {
			System.out.println("MESSAGE INCOME ERROR");
			for (int i = 0; i < messageData.length; i++) {
				System.out.print(messageData[i]);
			}
			e.printStackTrace();
		}
		
		if(packet instanceof INewNodePacket){
			INewNodePacket newNodePacket = (INewNodePacket)packet;
			IBMFNode newNode = this.getIBMFNode(newNodePacket.getSenderID(), 
					newNodePacket.getSensorBoardType(), 
					iPlatform.getSensorBoardName(newNodePacket.getSensorBoardType()), 
					iPlatform.getSensorBoardSensors(newNodePacket.getSensorBoardType()),
					newNodePacket.getFunctions());
			
			for(int i=0; i<this.listeners.size(); i++)
				((this.listeners.elementAt(i))).newNodeInTheNetwork(newNode, currTime);
		}
		
		else if(packet instanceof IDataPacket){
			IDataPacket dataPacket = (IDataPacket)packet;
			
			//System.out.println("NEW DATA PACKET");
			for (int j = 0; j < dataPacket.getSenderID().length; j++) {
				IBMFDataIn dataTemp = this.getIBMFDataIn(dataPacket.getSenderID()[j], 
						dataPacket.getRequestID()[j], dataPacket.getResult()[j], 
						dataPacket.getCounter()[j]);
				for (int i = 0; i < this.listeners.size(); i++)
					((this.listeners.elementAt(i))).newDataIn(dataTemp, currTime);
			} 
		}
		
        else if(packet instanceof IAckPacket){
            IAckPacket ack = (IAckPacket)packet;
            for(int i=0; i<this.listeners.size(); i++)
            	((this.listeners.elementAt(i))).ackReceived(ack.getSenderID(), 
                                ack.getPktTypeToAck(), ack.getParam(), currTime);
        }
		
	}

	
	/*
	@Override
	public void setSensorboardParams(int sensorBoardType, String bsPort,
			String bsSpeed) {
		
		SensorBoardParams sbp = new SensorBoardParams(sensorBoardType, bsPort, bsSpeed);
		if (iPlatformParamsList.indexOf(sbp)==-1) {
			this.iPlatformParamsList.add(sbp);
			for (Iterator<IPlatform> iterator = this.iPlatformList.iterator(); iterator.hasNext();) {
				IPlatform platform = (IPlatform) iterator.next();
				if (platform.getPlatformName()==sensorBoardType) 
					platform.openConnection(bsPort, bsSpeed);
			}
		}
	}
	*/
	
	@Override
	public void sendPacket(IBMFPacket pkt) throws IOException {
		
		System.out.println("NetworkManager.sendPacket");
		
		byte [] messageData = null;
		try {
			if(pkt instanceof IMembershipPacket){
				messageData = this.iMessageBuilder.buildMembershipPacket((IMembershipPacket)pkt);
			}
			else if(pkt instanceof IConfigurationPacket){ 
				messageData = this.iMessageBuilder.buildConfigurationPacket((IConfigurationPacket)pkt);  
			}
            else if(pkt instanceof IResetPacket){
				messageData = this.iMessageBuilder.buildResetPacket((IResetPacket)pkt);
			}
			else{
				byte bmfNull[] = {(byte)0,(byte)0,(byte)0};
				messageData = bmfNull;
			}
			
		} catch (GroupException e) {
			System.out.println(e);
			e.printStackTrace();
		} 
		
		for (Iterator<IPlatform> iterator = iPlatformList.iterator(); iterator.hasNext();) {
			IPlatform platform = (IPlatform) iterator.next();
			platform.sendMessage(messageData);
		}
		
	}

	
	//LISTENER
	public void registerListener(INetworkBMFListener iLowLevelBMFListener){
		this.listeners.addElement(iLowLevelBMFListener);
	}
	
	public void unRegisterListener(INetworkBMFListener iLowLevelBMFListener){
		this.listeners.removeElement(iLowLevelBMFListener);
	}

	@Override
	public IBMFGroup getIBMFGroup(int ID, String name) {
		return new BMFGroup(ID, name);
		
	}

	@Override
	public IBMFNode getIBMFNode(int ID, int sensorBoard, String sensorBoardName, int[] sensors, int[]functions) {
		return new BMFNode(ID, sensorBoard, sensorBoardName, sensors, functions);
	}

	@Override
	public IBMFDataIn getIBMFDataIn(int senderID, int requestID, long result,
			int counter) {
		return new BMFDataIn(senderID, requestID, result, counter);
	}
}
