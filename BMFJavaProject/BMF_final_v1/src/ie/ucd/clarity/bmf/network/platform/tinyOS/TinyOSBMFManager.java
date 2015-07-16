package ie.ucd.clarity.bmf.network.platform.tinyOS;

import ie.ucd.clarity.bmf.common.Constants;
import ie.ucd.clarity.bmf.network.platform.IPlatform;
import ie.ucd.clarity.bmf.network.platform.IPlatformListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import net.tinyos.message.Message;
import net.tinyos.message.MessageListener;
import net.tinyos.message.MoteIF;
import net.tinyos.packet.BuildSource;
import net.tinyos.packet.PacketSource;
import net.tinyos.packet.PhoenixSource;
import net.tinyos.util.PrintStreamMessenger;

/**
 *
 */
public class TinyOSBMFManager implements IPlatform, MessageListener {

	//private INetworkManager iNetworkManager;
	
	private ArrayList<String> sensorBoardsNames;
	private int[][] sensors;
	
	/**
	 * @uml.property  name="iPlatformListener"
	 * @uml.associationEnd  
	 */
	private IPlatformListener iPlatformListener;
	private String bsPort;
	private String bsSpeed;
	
	private String bsPrefix = "serial@";
	private String baseStation;
	
	private PacketSource pks;
	private PhoenixSource phoenix;
	private static MoteIF moteIF;
	
	@Override
	public String getPlatformName() {
		return "TinyOS";
	}

	@Override
	public void openConnection(){
			setup();
			this.bsPort=Constants.TELOSB_BS_PORT;
			this.bsSpeed=Constants.TELOSB_BS_SPEED;
			System.out.println(this.bsPort + ", " + this.bsSpeed);			
			this.prepareConnection();		
	}
	
	private void setup() {
		this.sensorBoardsNames = new ArrayList<String>(7);
		this.sensorBoardsNames.add("NO SENSORBOARD INSTALLED");
		this.sensorBoardsNames.add("Sensorboard SBT80 on TelosB node");
		this.sensorBoardsNames.add("Sensorboard standard on TelosB node");
		this.sensorBoardsNames.add("Sensorboard TYNSENSORBRD on Tyndall25 node");
		this.sensorBoardsNames.add("Sensorboard ACmeX2 on Epic node sending data to a TelosB node");
		this.sensorBoardsNames.add("Sensorboard ACmeX2 on Epic node");
		this.sensorBoardsNames.add("Sensorboard Kinematics on Shimmer node");
		
		this.sensors = new int [7][];
		
		//NO SENSORBOARD ISNTALLED
		this.sensors[0] = new int[1];
		this.sensors[0][0] = 0;
		
		//Sensorboard SBT80 on TelosB node
		this.sensors[1] = new int [1];
		this.sensors[1][0] = Constants.SENSOR_TYPE_IR;
		
		//Sensorboard standard on TelosB node
		this.sensors[2] = new int [3];
		this.sensors[2][0] = Constants.SENSOR_TYPE_LIGHT;
		this.sensors[2][1] = Constants.SENSOR_TYPE_HUMIDITY;
		this.sensors[2][2] = Constants.SENSOR_TYPE_TEMPERATURE;
		
		//Sensorboard TYNSENSORBRD on Tyndall25 node
		this.sensors[3] = new int [7];
		this.sensors[3][0] = Constants.SENSOR_TYPE_LIGHT;
		this.sensors[3][1] = Constants.SENSOR_TYPE_HUMIDITY;
		this.sensors[3][2] = Constants.SENSOR_TYPE_TEMPERATURE;
		this.sensors[3][3] = Constants.SENSOR_TYPE_ACC_X;
		this.sensors[3][4] = Constants.SENSOR_TYPE_ACC_Y;
		this.sensors[3][5] = Constants.SENSOR_TYPE_ACC_Z;
		this.sensors[3][6] = Constants.SENSOR_TYPE_SOUND;
		
		//Sensorboard ACmeX2 on Epic node sending data to a TelosB node
		this.sensors[4] = new int [1];
		this.sensors[4][0] = Constants.SENSOR_TYPE_ELECTRICITY;
		//Sensorboard ACmeX2 on Epic node
		this.sensors[5] = new int [1];
		this.sensors[5][0] = Constants.SENSOR_TYPE_ELECTRICITY;
		
		//Sensorboard Kinematics on Shimmer node
		this.sensors[6] = new int [3];
		this.sensors[6][0] = Constants.SENSOR_TYPE_ACC_X;
		this.sensors[6][1] = Constants.SENSOR_TYPE_ACC_Y;
		this.sensors[6][2] = Constants.SENSOR_TYPE_ACC_Z;
	}

	@Override
	public void sendMessage(byte [] messageData) throws IOException {
		System.out.println("Tiny OS sends message");
		BMFMessage bmf = new BMFMessage(messageData);
		moteIF.send(MoteIF.TOS_BCAST_ADDR, bmf);
		
	}
	
	private void prepareConnection() {

		this.baseStation = this.bsPrefix + bsPort + ":" + bsSpeed;
		
		System.out.println(baseStation);

        // the following two lines let useless the use of the SerialForwarder
		pks = BuildSource.makePacketSource(baseStation);
		phoenix = BuildSource.makePhoenix(pks, PrintStreamMessenger.err);
        
		moteIF = new MoteIF(phoenix);
        moteIF.registerListener(new BMFMessage(), this);
        
	}

	public void messageReceived(int to, Message message) {
		System.out.println("MESSAGE INCOME");
		if(message instanceof BMFMessage){
			this.iPlatformListener.messageReceived(message.dataGet(), this);
		}
		
	}

	@Override
	public void setIPlatformListener(IPlatformListener iPlatformListener) {
		this.iPlatformListener = iPlatformListener;
	}

	@Override
	public void unsetIPlatformListener(
			IPlatformListener iPlatformListener) {
		if(this.iPlatformListener == iPlatformListener)
			this.iPlatformListener = null;
	}

	@Override
	public ArrayList<String> getSensorBoards() {
		return this.sensorBoardsNames;
	}

	@Override
	public int[] getSensorBoardSensors(int index) {
		if (index >= this.sensorBoardsNames.size() || index < 0)
			return null;
		else
			return this.sensors[index];
	}

	@Override
	public String getSensorBoardName(int sensorBoardType) {
		return this.sensorBoardsNames.get(sensorBoardType);
	}
	
	
	
}