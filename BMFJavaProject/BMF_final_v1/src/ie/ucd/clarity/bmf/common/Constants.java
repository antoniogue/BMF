package ie.ucd.clarity.bmf.common;

public class Constants {

	static final public int AM_BMF = 1;
	static final public int BASE_STATION_ID = 0;
	static final public int SENDER_ID = BASE_STATION_ID;
	static final public int ADDRESSEE_TYPE_NODE = 0;
	static final public int ADDRESSEE_TYPE_GROUP = 1;
	static final public int ADDRESSEE_BROADCAST = 0xFF;
	static final public int IS_NOT_FIELD_TRUE = 1;
	static final public int IS_NOT_FIELD_FALSE = 0;
	static final public int ASSOCIATIVE_RULE_AND = 0;
	static final public int ASSOCIATIVE_RULE_OR = 1;
	static final public int TIMESCALE_SEC = 0;
	static final public int TIMESCALE_MIN = 1;
	static final public int TIMESCALE_HOUR = 2;
	static final public int TIMESCALE_DAY = 3;
	static final public int ACTION_SENSING = 0;
	static final public int ACTION_ACTUATING = 1;
	static final public int SENSOR_TYPE_NO_SENSOR = 0;
	static final public int SENSOR_TYPE_ACC_X = 1;
	static final public int SENSOR_TYPE_ACC_Y = 2;
	static final public int SENSOR_TYPE_ACC_Z = 3;
	static final public int SENSOR_TYPE_HUMIDITY = 4;
	static final public int SENSOR_TYPE_IR = 5;
	static final public int SENSOR_TYPE_LIGHT = 6;
	static final public int SENSOR_TYPE_MAGNETIC_X = 7;
	static final public int SENSOR_TYPE_MAGNETIC_Y = 8;
	static final public int SENSOR_TYPE_SOUND = 9;
	static final public int SENSOR_TYPE_TEMPERATURE = 10;
	static final public int SENSOR_TYPE_ELECTRICITY = 11;
	static final public int SENSOR_TYPE_INTERNAL_VOLTAGE         	= 17;
	
	
	// THESE ARE NOT SENSOR TYPES - TO REVIEW
	static final public int SENSOR_TYPE_ENERGY = 12;
	static final public int SENSOR_TYPE_RX_TIME = 13;
	static final public int SENSOR_TYPE_SEND_TIME = 14;
	static final public int SENSOR_TYPE_SLEEP_TIME = 15;
	static final public int SENSOR_TYPE_RX_SEND_SLEEP_TIME = 16;
	static final public int SENSOR_LINK_QUALITY_PARENT_ID = 18;
	
		
	// IS THIS AN ACTUATION?
	static final public int ACTUATION_TYPE_LOW_POWER = 1;
	static final public int ACTUATION_TYPE_LED = 2;
	static final public int ACTUATION_TYPE_DYNAMIC_LENGHT_PACKET = 3;
	static final public int ACTUATION_TYPE_DYNAMIC_LENGHT_PARAM_YES = 1;
	static final public int ACTUATION_TYPE_DYNAMIC_LENGHT_PARAM_NO = 0;
	static final public int ACTUATION_PARAM_LED_0_TOGGLE = 0;
	static final public int ACTUATION_PARAM_LED_1_TOGGLE = 1;
	static final public int ACTUATION_PARAM_LED_2_TOGGLE = 2;
	
	// TINYOS SENSORBOARDS
	static final public int SENSORBOARD_TYPE_NO_SENSORBOARD = 0;
	static final public int SENSORBOARD_TYPE_SBT80_TELOSB = 1;
	static final public int SENSORBOARD_TYPE_STD_TELOSB = 2;
	static final public int SENSORBOARD_TYPE_TYNSENSORBRD = 3;
	static final public int SENSORBOARD_TYPE_BLACKPLUG = 4;
	static final public int SENSORBOARD_TYPE_ACMEX2 = 5;
	static final public int SENSORBOARD_TYPE_SHIMMER2R_KINEMATICS	=6;
	
	
	static final public int DATA_TYPE_SENSED_DATA = 0;
	static final public int DATA_TYPE_THRESHOLD_NOTIFICATION = 1;
	
	
	static final public int SYNTHETIC_DATA_NO_SYNTHETIC = 0;
	static final public int SYNTHETIC_DATA_AVERAGE = 1;
	static final public int SYNTHETIC_DATA_MIN = 2;
	static final public int SYNTHETIC_DATA_MAX = 3;
	
	static final public int THRESHOLD_TYPE_LOWER = 0;
	static final public int THRESHOLD_TYPE_BIGGER = 1;
	static final public int THRESHOLD_TYPE_TRANSITION = 2;
	static final public int THRESHOLD_TRANSITION_DOWN_UP = 1;
	static final public int THRESHOLD_TRANSITION_UP_DOWN = 0;
	
	static final public int MEMBERSHIP_TYPE_UPDATE = 0;
	static final public int MEMBERSHIP_TYPE_ADD = 1;
	static final public int MEMBERSHIP_TYPE_DELETE = 2;
	static final public int MEMBERSHIP_TYPE_RESET = 3;
	

	
	// WSN TO BS PACKETS
	static final public int PKT_TYPE_NEW_NODE = 10;
	static final public int PKT_TYPE_DATA = 11;
	static final public int PKT_TYPE_ACK = 12;
	static final public int PKT_TYPE_INFO = 13;
	// BS TO WSN PACKETS
	static final public int PKT_TYPE_MEMBERSHIP = 1;
	static final public int PKT_TYPE_CONFIGURATION_SCHEDULE = 2;
	static final public int PKT_TYPE_CONFIGURATION_UNSCHEDULE = 3;
	static final public int PKT_TYPE_RESET_NODE = 4;
	
	
	static final public byte PKT_CONFIGURATION_MAX_LENGTH = 19;
	static final public byte PKT_MEMBERSHIP_MAX_LENGTH = 16;
	static final public byte PKT_NEW_NODE_MAX_LENGTH = 3;
	static final public byte PKT_DATA_MAX_LENGTH = 26;
	static final public byte PKT_MAX_LENGTH = 26;
	
	
	static final public byte RESULT_BITS_8 = 2;
	static final public byte RESULT_BITS_16 = 0;
	static final public byte RESULT_BITS_32 = 1;
	
	// IN THE FOLLOWING THERE ARE THE CODES FOR THE FUNCTIONS ACTIVATED ON THE NODE

    static final public int FUNCTION_ELABORATION_AND_THRESHOLD_STANDARD_ENABLED = 1; //  includes elaboration and threshold elaborations
    static final public int FUNCTION_ELABORATION_STANDARD_ENABLED = 2; // Includes average, max and min
    static final public int FUNCTION_THRESHOLD_STANDARD_ENABLED = 3; // includes lower, bigger and transition threshold
    static final public int FUNCTION_AVERAGE_ENABLED = 50;
    static final public int FUNCTION_MIN_ENABLED = 51;
    static final public int FUNCTION_MAX_ENABLED = 52;
    static final public int FUNCTION_THRESHOLD_TYPE_LOWER_ENABLED = 100;
    static final public int FUNCTION_THRESHOLD_TYPE_BIGGER = 101;
    static final public int FUNCTION_THRESHOLD_TYPE_TRANSITION = 102;
	
	// ONLY JAVA CONSTANTS:
	static final public byte GROUP_ID_MAX_ID_AVAILABLE = 31;
	static final public byte PERIOD_VALUE_MAX_VALUE = 63;
	static final public byte GROUP_MAX_DESTINATIONS_IN_THE_ADDRESSEE = 4;
	static final public int REQUEST_ID_MAX_VALUE = 255;
//	static final public String IMG_TELOSB_PATH = "/bmf/images/telosb.gif";
//static final public String IMG_EPIC_PATH = "/bmf/images/ACme.jpg";
//	static final public String IMG_TYNDALL25_PATH = "/bmf/images/tyndall.jpg";
	static final public String SENSORBOARD_TYPE_NO_SENSORBOARD_DESCRIPTION = "NO SENSORBOARD INSTALLED!";
	static final public String SENSORBOARD_TYPE_SBT80_TELOSB_DESCRIPTION = "Sensorboard SBT80 on TelosB node.\n"
			+ "   SENSORS ONBOARD: \n"
			+ "      - Motion Detection;\n"
			+ "      - etc...";
	static final public String SENSORBOARD_TYPE_STD_TELOSB_DESCRIPTION = "Sensorboard standard on TelosB node.\n"
			+ "   SENSORS ONBOARD: \n"
			+ "      - Light;\n"
			+ "      - Humidity;\n" + "      - Temperature.";
	static final public String SENSORBOARD_TYPE_TYNSENSORBRD_DESCRIPTION = "Sensorboard TYNSENSORBRD on Tyndall25 node.\n"
			+ "   SENSORS ONBOARD: \n"
			+ "      - Light;\n"
			+ "      - Humidity;\n"
			+ "      - Temperature;\n"
			+ "      - 3 Axis Accelerometer;\n" + "      - Microphone.";
	static final public String SENSORBOARD_TYPE_BLACKPLUG_DESCRIPTION = "Sensorboard ACmeX2 on Epic node sending data to a TelosB node.\n"
			+ "   SENSORS ONBOARD: \n" + "      - Electricity.";
	static final public String SENSORBOARD_TYPE_ACMEX2_DESCRIPTION = "Sensorboard ACmeX2 on Epic node.\n"
			+ "   SENSORS ONBOARD: \n" + "      - Electricity.";
	static final public String SENSORBOARD_TYPE_SHIMMER2R_KINEMATICS_DESCRIPTION	= "Sensorboard Kinematics on Shimmer node.\n"
			+ "   SENSORS ONBOARD: \n"
			+ "      - Accelerometer;\n" + "      - Gyroscope.";	
	static final public String SENSORBOARD_UNKNOWN = "NO INFORMATION FOR THIS SENSORBOARD!";
	
	public static String sensorsGIF = "images/telosbSensors.gif";
    public static String openedNodeGIF = "images/telosb.gif";
    public static String closedNodeGIF = "images/telosb.gif";
    public static String telosbNodeGIF = "images/telosbBig.gif";
    public static String floorplanGIF = "images/floorplan2.jpg";
    public static String piuGIF = "images/piu.gif";
    public static String closeGIF = "images/close.gif";
    //public static String CSV_FOLDER = System.getProperty("WORK_DIRECTORY")+"CSV_Folder\\";
    public static String CSV_FOLDER = "CSV_Folder/";
    

    public static String SUNSPOT_BS_PORT=System.getProperty("SUNSPOT_BS_PORT");
    public static String SUNSPOT_BS_SPEED=System.getProperty("SUNSPOT_BS_SPEED");
    public static String TELOSB_BS_PORT=System.getProperty("TELOSB_BS_PORT");
    public static String TELOSB_BS_SPEED=System.getProperty("TELOSB_BS_SPEED");
    public static int MAX_GROUPS=Integer.parseInt(System.getProperty("MAX_GROUPS")); //Originale
    //public static int MAX_GROUPS=100;
    // sunSPOT
    public static int PORT=67;
    static final public int SENSORBOARD_TYPE_SUNSPOT = 1;
    
    
    // CONSTANTS FOR BSN GATEWAY
    static final public int GATEWAY_ID=2;
    static final public int SENSOR_TYPE_BODY_ACTIVITY=12;
    //static final public int SENSOR_TYPE_ACTIVITY=12;
    static final public int THRESHOLD_TYPE_EQUALS = 3;
    

}
