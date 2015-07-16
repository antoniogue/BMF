package ie.ucd.clarity.bmf.GUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.DefaultMutableTreeNode;
import ie.ucd.clarity.bmf.GUI.common.BMFNodeGUI;
import ie.ucd.clarity.bmf.GUI.common.BMFObjectGUI;
import ie.ucd.clarity.bmf.GUI.common.BMFRequestObjectGUI;
import ie.ucd.clarity.bmf.GUI.common.Util;
import ie.ucd.clarity.bmf.GUI.notification.AckReceived;
import ie.ucd.clarity.bmf.GUI.notification.DataIn;
import ie.ucd.clarity.bmf.GUI.notification.NewNodeIn;
import ie.ucd.clarity.bmf.GUI.notification.NewRequest;
import ie.ucd.clarity.bmf.GUI.notification.Notification;
import ie.ucd.clarity.bmf.GUI.notification.RequestExpired;
import ie.ucd.clarity.bmf.GUI.notification.ToPrint;
import ie.ucd.clarity.bmf.GUI.start.BMFMarker;
import ie.ucd.clarity.bmf.aggregator.AggregatorManager;
import ie.ucd.clarity.bmf.aggregator.IAggregatorManager;
import ie.ucd.clarity.bmf.communication.IConfigurationPacket;
import ie.ucd.clarity.bmf.communication.IBMFPacket;
import ie.ucd.clarity.bmf.communication.IDataPacket;
import ie.ucd.clarity.bmf.communication.IPacketBuilder;
import ie.ucd.clarity.bmf.communication.IResetPacket;
import ie.ucd.clarity.bmf.communication.MessageBuilder;
import ie.ucd.clarity.bmf.communication.MessageParser;
import ie.ucd.clarity.bmf.communication.PacketBuilder;
import ie.ucd.clarity.bmf.network.manager.IBMFDataIn;
import ie.ucd.clarity.bmf.network.manager.IBMFNode;
import ie.ucd.clarity.bmf.network.manager.INetworkBMFListener;
import ie.ucd.clarity.bmf.network.manager.INetworkManager;
import ie.ucd.clarity.bmf.network.manager.NetworkManager;
import ie.ucd.clarity.bmf.network.manager.configurationPackets.ConfigurationPacketsManager;
import ie.ucd.clarity.bmf.network.manager.configurationPackets.IBMFRequestDurationListener;
import ie.ucd.clarity.bmf.network.manager.configurationPackets.IConfigurationPacketsManager;
import ie.ucd.clarity.bmf.network.manager.nodesAndGroups.BMFNodesAndGroupsManager;
import ie.ucd.clarity.bmf.network.manager.nodesAndGroupsManager.IBMFNodesAndGroupsManager;
import ie.ucd.clarity.bmf.network.platform.tinyOS.TinyOSBMFManager;
import ie.ucd.clarity.bmf.common.Constants;
import ie.ucd.clarity.bmf.configurationfile.ConfigurationFileReader;
import ie.ucd.clarity.bmf.configurationfile.IConfigurationFileReader;
import ie.ucd.clarity.bmf.data.IDataSaver;
import ie.ucd.clarity.bmf.data.file.FileDataSaverBuilder;
import ie.ucd.clarity.bmf.data.file.IFileDataSaver;
import ie.ucd.clarity.bmf.data.file.IFileDataSaverBuilder;

/**
 * @author  Andrea
 */

//public class Consumer extends Observable implements INetworkBMFListener,
//IBMFRequestDurationListener{   codice originale


public class Consumer extends Observable implements BMFMarker{

    /**
	 * @uml.property  name="packetBuilder"
	 * @uml.associationEnd  
	 */
    private IPacketBuilder packetBuilder;
    /**
	 * @uml.property  name="iNetworkManager"
	 * @uml.associationEnd  
	 */
    private INetworkManager iNetworkManager;
    /**
	 * @uml.property  name="iFileDataSaverBuilder"
	 * @uml.associationEnd  
	 */
    private IFileDataSaverBuilder iFileDataSaverBuilder;
    /**
	 * @uml.property  name="fileDataSaver"
	 * @uml.associationEnd  
	 */
    private IDataSaver fileDataSaver;    //
    /**
	 * @uml.property  name="iAggregatorManager"
	 * @uml.associationEnd  
	 */
    private IAggregatorManager iAggregatorManager;
    /**
	 * @uml.property  name="iBMFNodesAndGroupsManager"
	 * @uml.associationEnd  
	 */
    private IBMFNodesAndGroupsManager iBMFNodesAndGroupsManager;
    /**
	 * @uml.property  name="iConfigurationPacketsManager"
	 * @uml.associationEnd  
	 */
    private IConfigurationPacketsManager iConfigurationPacketsManager;
    /**
	 * @uml.property  name="iConfigurationFileReader"
	 * @uml.associationEnd  
	 */
    private IConfigurationFileReader iConfigurationFileReader;
    /**
	 * @uml.property  name="basestation"
	 */
    DefaultMutableTreeNode basestation;

    private boolean startFromConsumer;
    
    
    public static void main(String[] args) {
    	new Starter(new Consumer(true)); 
    }

    public Consumer(){
    	this.startFromConsumer = false;
    }
    
    
    public Consumer(boolean inizializeEverything){
    	this.startFromConsumer = inizializeEverything;
    	if (inizializeEverything) {
			NetworkManager nm = new NetworkManager();
			BMFNodesAndGroupsManager bmf = new BMFNodesAndGroupsManager();
			PacketBuilder pb = new PacketBuilder();
			FileDataSaverBuilder fdsb = new FileDataSaverBuilder();
			AggregatorManager ag = new AggregatorManager();
			ConfigurationPacketsManager cpm = new ConfigurationPacketsManager();
			ConfigurationFileReader cfr = new ConfigurationFileReader();
			setINetworkManager(nm);
			nm.setIMessageParser(new MessageParser());
			nm.setIMessageBuilder(new MessageBuilder());
			setIFileDataSaverBuilder(fdsb);
			setIAggregatorManager(ag);
			setIPacketBuilder(pb);
			setIBMFNodesAndGroupsManager(bmf);
			bmf.setINetworkManager(nm);
			bmf.setIPacketBuilder(pb);
			setIConfigurationPacketsManager(cpm);
			setIConfigurationFileReader(cfr);
			TinyOSBMFManager t = new TinyOSBMFManager();
			nm.addIPlatform(t);
		}



    	//in setIplatform is used networkmanager addPlattform

    }
    
    
 
	public synchronized void setIConfigurationPacketsManager(
            IConfigurationPacketsManager iConfigurationPacketsManager) {
        System.out.println("IConfigurationPacketsManager was set. Thank you DS!");
        this.iConfigurationPacketsManager = iConfigurationPacketsManager;
        if (this.checkPlugIn()) {
            new Starter(this).start();
        }
    }


	public synchronized void unsetIConfigurationPacketsManager(
            IConfigurationPacketsManager iConfigurationPacketsManager) {
        System.out.println("IConfigurationPacketsManager was unset. Why did you do this to me?");
        if (this.iConfigurationPacketsManager == iConfigurationPacketsManager) {
            this.iConfigurationPacketsManager = null;
        }
    }


	public synchronized void setIConfigurationFileReader(
            IConfigurationFileReader iConfigurationFileReader) {
        System.out.println("IConfigurationFileReader was set. Thank you DS!");
        this.iConfigurationFileReader = iConfigurationFileReader;
        readConfigurationFile();
        basestation = new DefaultMutableTreeNode();
        basestation.setUserObject(new BMFNodeGUI(null));  
    }


	public synchronized void unsetIConfigurationFileReader(
            IConfigurationFileReader iConfigurationFileReader) {
        System.out.println("IConfigurationFileReader was unset. Why did you do this to me?");
        if (this.iConfigurationFileReader == iConfigurationFileReader) {
            this.iConfigurationFileReader = null;
        }
    }


	public boolean readConfigurationFile() {
        return this.iConfigurationFileReader.readConfigurationFile();

    }

	public synchronized void setIPacketBuilder(IPacketBuilder packetBuilder) {
        System.out.println("IPacketBuilder was set. Thank you DS!");
        this.packetBuilder = packetBuilder;
        if (this.checkPlugIn()) {
            new Starter(this).start();
        }
    }

 
	public synchronized void unsetIPacketBuilder(IPacketBuilder packetBuilder) {
        System.out.println("IPacketBuilder was unset. Why did you do this to me?");
        if (this.packetBuilder == packetBuilder) {
            this.packetBuilder = null;
        }
    }

   
	public synchronized void setINetworkManager(INetworkManager networkManager) {
        System.out.println("INetworkManager was set. Thank you DS!");
        this.iNetworkManager = networkManager;
        this.iNetworkManager.registerListener(this);
        if (this.checkPlugIn()) {
            new Starter(this).start();
        }
    }

	public synchronized void unsetINetworkManager(INetworkManager requestManager) {
        System.out.println("INetworkManager was unset. Why did you do this to me?");
        if (this.iNetworkManager == requestManager) {
            this.iNetworkManager = null;
        }
    }

	public synchronized void setIFileDataSaverBuilder(
            IFileDataSaverBuilder iFileDataSaverBuilder) {
        System.out.println("IFileDataSaverBuilder was set. Thank you DS!");
        this.iFileDataSaverBuilder = iFileDataSaverBuilder;
        if (this.checkPlugIn()) {
            new Starter(this).start();
        }
    }

  
	public synchronized void unsetIFileDataSaverBuilder(
            IFileDataSaverBuilder iFileDataSaverBuilder) {
        System.out.println("IFileDataSaverBuilder was unset. Why did you do this to me?");
        if (this.iFileDataSaverBuilder == iFileDataSaverBuilder) {
            this.iFileDataSaverBuilder = null;
        }
    }

   
	public synchronized void setIAggregatorManager(
            IAggregatorManager iAggregatorManager) {
        System.out.println("IAggregatorManager was set. Thank you DS!");
        this.iAggregatorManager = iAggregatorManager;
        if (this.checkPlugIn()) {
            new Starter(this).start();
        }
    }


	public synchronized void unsetIAggregatorManager(
            IAggregatorManager iAggregatorManager) {
        System.out.println("IAggregatorManager was unset. Why did you do this to me?");
        if (this.iAggregatorManager == iAggregatorManager) {
            this.iAggregatorManager = null;
        }
    }

  
	public synchronized void setIBMFNodesAndGroupsManager(
            IBMFNodesAndGroupsManager iBMFNodesAndGroupsManager) {
        System.out.println("IBMFNodesAndGroupsManager was set. Thank you DS!");
        this.iBMFNodesAndGroupsManager = iBMFNodesAndGroupsManager;
        if (this.checkPlugIn()) {
            new Starter(this).start();
        }
    }

    private boolean checkPlugIn() {
        return  this.startFromConsumer
        		&& this.packetBuilder != null && this.iNetworkManager != null
                && this.iAggregatorManager != null
                && this.iFileDataSaverBuilder != null
                && this.iBMFNodesAndGroupsManager != null
                && this.iConfigurationPacketsManager != null;
    }

    
	public synchronized void unsetIBMFNodesAndGroupsManager(
            IBMFNodesAndGroupsManager iBMFNodesAndGroupsManager) {
        System.out.println("IBMFNodesAndGroupsManager was unset. Why did you do this to me?");
        if (this.iBMFNodesAndGroupsManager == iBMFNodesAndGroupsManager) {
            this.iBMFNodesAndGroupsManager = null;
        }
    }

	public void openDataSaverConnections() {
        this.fileDataSaver = this.iFileDataSaverBuilder.getIDataSaverInstance();
        try {
            ((IFileDataSaver) this.fileDataSaver).openConnection(Constants.CSV_FOLDER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public void sendICofigurationPacket(IConfigurationPacket newReq,
            String name, ArrayList<BMFNodeGUI> nodes, boolean isAggregation) {

    	int reqID=-1;
    	if(newReq.getConfigurationType()==Constants.PKT_TYPE_CONFIGURATION_UNSCHEDULE)
    		reqID=newReq.getRequestID();
    	else reqID = this.iConfigurationPacketsManager.addIConfigurationPacket(
                name, newReq);
        BMFRequestObjectGUI request=new BMFRequestObjectGUI(this.iConfigurationPacketsManager.getIConfigurationNameByID(reqID), newReq, nodes, isAggregation);
        if (request.isAggregation()) {// se è un aggregation credo l'aggregator
            iAggregatorManager.createIAggregator(reqID, newReq.getSyntheticData(), nodes.size());
        }
        this.notifyEventToObservers(new NewRequest(request));
        notifyEventToObservers(new ToPrint("REQUEST " + reqID + " SENT!"));
        new Sender(newReq).start();
        if (newReq.getConfigurationType() == Constants.PKT_TYPE_CONFIGURATION_SCHEDULE) {
            this.iConfigurationPacketsManager.activateIConfigurationTimerByID(
                    reqID, this);
        } else {
            this.iConfigurationPacketsManager.deactivateIConfigurationTimerByID(reqID);
        }
        System.out.println("CONFIG NAME: "
                + this.iConfigurationPacketsManager.getIConfigurationNameByID(reqID) + "  DESTINATION:"
                + newReq.getDestination());

        try {
            this.fileDataSaver.saveIConfigurationPacket(newReq);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


	public void sendIResetPacket(int id) {
        IResetPacket rp;
        rp = this.packetBuilder.getIResetPacket();
        rp.setDestinationNode(id);
        notifyEventToObservers(new ToPrint("RESET PACKET SENT TO NODE " + id));
        new Sender(rp).start();

    }

 
	public void sendIDataPacket() {
        IDataPacket data = this.packetBuilder.getIDataPacket();
    }


    public void ackReceived(int senderID, int pktTypeToAck, int param,
            Calendar time) {
        notifyEventToObservers(new ToPrint("ACK RECEIVED FROM " + senderID));
        notifyEventToObservers(new AckReceived(senderID, pktTypeToAck, param,
                time));
    }

    public void newDataIn(IBMFDataIn data, Calendar time) {

        if (iConfigurationPacketsManager.getBMFRequestByID(data.getRequestID()) != null) {
            /*if (iConfigurationPacketsManager.getBMFRequestByID(
                    data.getRequestID()).getThresholdValue() != null) {
                if (data.getResult() == 0) {// down
                    JOptionPane.showMessageDialog(new JFrame(),
                            "exceeded the threshold from ABOVE!");
                } else {// up
                    JOptionPane.showMessageDialog(new JFrame(),
                            "exceeded the threshold from BELOW!");
                }
            }*/
        	long convertedData=Util.getDataConverted(data.getResult(), iConfigurationPacketsManager.getBMFRequestByID(data.getRequestID()).getSensor_actuatorType());
            notifyEventToObservers(new ToPrint("DATA RECEIVED FROM "
                    + data.getSenderID()
                    + "  Request:"
                    + iConfigurationPacketsManager.getIConfigurationNameByID(data.getRequestID())
                    + "  Result:" + convertedData));            
            try {
                this.fileDataSaver.saveData(data.getSenderID(), data.getRequestID(), convertedData, data.getCounter());
            } catch (Exception e) {
                e.printStackTrace();
            }
            notifyEventToObservers(new DataIn(data, data.getRequestID()));
        }

    }

 
    public void newNodeInTheNetwork(IBMFNode newNode, Calendar time) {
        this.iBMFNodesAndGroupsManager.addNode(newNode);
        notifyEventToObservers(new NewNodeIn(newNode, time));
    }


    public void requestExpired(IConfigurationPacket iConfiguration) {
        notifyEventToObservers(new ToPrint("CONFIG EXPIRED: "
                + iConfigurationPacketsManager.getIConfigurationNameByID(iConfiguration.getRequestID()) + "  Destination node: "
                + iConfiguration.getDestination().getDestNodeID()));
        notifyEventToObservers(new RequestExpired(iConfiguration.getRequestID()));
    }

 
	public void notifyEventToObservers(Notification n) {
        if (n instanceof NewNodeIn) {
            NewNodeIn o = (NewNodeIn) n;
            this.setChanged();
            this.notifyObservers(o);
        }
        if (n instanceof ToPrint) {
            ToPrint o = (ToPrint) n;
            this.setChanged();
            this.notifyObservers(o);
        }
        if (n instanceof NewRequest) {
            NewRequest o = (NewRequest) n;
            this.setChanged();
            this.notifyObservers(o);
        }
        if (n instanceof RequestExpired) {
            RequestExpired o = (RequestExpired) n;
            this.setChanged();
            this.notifyObservers(o);
        }
        if (n instanceof DataIn) {
            DataIn o = (DataIn) n;
            this.setChanged();
            this.notifyObservers(o);
        }
    }

  
	public IPacketBuilder getPacketBuilder() {
        return packetBuilder;
    }

  
	public INetworkManager getiNetworkManager() {
        return iNetworkManager;
    }

 
	public IFileDataSaverBuilder getiFileDataSaverBuilder() {
        return iFileDataSaverBuilder;
    }

  
	public IAggregatorManager getiAggregatorManager() {
        return iAggregatorManager;
    }

   
	public IDataSaver getFileDataSaver() {
        return fileDataSaver;
    }

  
	public IBMFNodesAndGroupsManager getiBMFNodesAndGroupsManager() {
        return iBMFNodesAndGroupsManager;
    }


	public IConfigurationPacketsManager getiConfigurationPacketsManager() {
        return iConfigurationPacketsManager;
    }


    class Sender extends Thread {

        /**
		 * @uml.property  name="packet"
		 * @uml.associationEnd  
		 */
        IBMFPacket packet;

        public Sender(IBMFPacket p) {
            packet = p;
        }

        public void run() {
            try {
                iNetworkManager.sendPacket(packet);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

 
	public DefaultMutableTreeNode getBasestation() {
        return basestation;
    }


	public void sendBroadCastIResetPacket() {
        IResetPacket rp;
        rp = this.packetBuilder.getIResetPacket();
        rp.setDestinationNode(Constants.ADDRESSEE_BROADCAST);
        notifyEventToObservers(new ToPrint("BROADCAST RESET SENT!"));
        new Sender(rp).start();
    }
}


class Starter extends Thread {

    /**
	 * @uml.property  name="tc"
	 * @uml.associationEnd  
	 */
    private Consumer tc;

    public Starter(Consumer tc) {
        this.tc = tc;
    }

    public void run() {
    	try {
       	
        	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            new InitialFrame(tc).setVisible(true);
            tc.openDataSaverConnections();
         } catch (ClassNotFoundException ex) {
            Logger.getLogger(InitialFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(InitialFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(InitialFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
           Logger.getLogger(InitialFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


