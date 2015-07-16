/*
 * This class is usefull to convert the codes in bmf.common.Constants
 * in Strings.
 */

package ie.ucd.clarity.bmf.common;

import ie.ucd.clarity.bmf.network.manager.BMFNode;

/**
 * 
 * @author Antonio
 */
public class CodeConversion {

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

	static final public String SENSORBOARD_UNKNOWN = "NO INFORMATION FOR THIS SENSORBOARD!";

	/*
	 * public static String getSensorboardInformation(int sensorBoardID){
	 * switch(sensorBoardID){
	 * 
	 * case Constants.SENSORBOARD_TYPE_NO_SENSORBOARD: return
	 * SENSORBOARD_TYPE_NO_SENSORBOARD_DESCRIPTION; case
	 * Constants.SENSORBOARD_TYPE_SBT80_TELOSB: return
	 * SENSORBOARD_TYPE_SBT80_TELOSB_DESCRIPTION; case
	 * Constants.SENSORBOARD_TYPE_STD_TELOSB: return
	 * SENSORBOARD_TYPE_STD_TELOSB_DESCRIPTION; case
	 * Constants.SENSORBOARD_TYPE_TYNSENSORBRD: return
	 * SENSORBOARD_TYPE_TYNSENSORBRD_DESCRIPTION; case
	 * Constants.SENSORBOARD_TYPE_BLACKPLUG: return
	 * SENSORBOARD_TYPE_BLACKPLUG_DESCRIPTION; case
	 * Constants.SENSORBOARD_TYPE_ACMEX2: return
	 * SENSORBOARD_TYPE_ACMEX2_DESCRIPTION; default: return SENSORBOARD_UNKNOWN;
	 * } }
	 */

	public static long getMillisecMultiplierFromTimescale(int timescale) {
		switch (timescale) {
		case Constants.TIMESCALE_DAY:
			return 1000 * 60 * 60 * 24;
		case Constants.TIMESCALE_HOUR:
			return 1000 * 60 * 60;
		case Constants.TIMESCALE_MIN:
			return 1000 * 60;
		case Constants.TIMESCALE_SEC:
			return 1000;
		default:
			return 1;
		}
	}

	public static String getSensorName(int sensorCode) {
		switch (sensorCode) {
		case Constants.SENSOR_TYPE_ACC_X:
			return "ACC_X";
		case Constants.SENSOR_TYPE_ACC_Y:
			return "ACC_Y";
		case Constants.SENSOR_TYPE_ACC_Z:
			return "ACC_Z";
		case Constants.SENSOR_TYPE_ELECTRICITY:
			return "ELECTRICITY";
		case Constants.SENSOR_TYPE_ENERGY:
			return "ENERGY";
		case Constants.SENSOR_TYPE_HUMIDITY:
			return "HUMIDITY";
		case Constants.SENSOR_TYPE_IR:
			return "IR";
		case Constants.SENSOR_TYPE_LIGHT:
			return "LIGHT";
		case Constants.SENSOR_TYPE_MAGNETIC_X:
			return "MAGNETIC_X";
		case Constants.SENSOR_TYPE_MAGNETIC_Y:
			return "MAGNETIC_Y";
		case Constants.SENSOR_TYPE_SOUND:
			return "SOUND";
		case Constants.SENSOR_TYPE_TEMPERATURE:
			return "TEMPERATURE";
		
		case Constants.SENSOR_TYPE_INTERNAL_VOLTAGE:
			return "INTERNAL_VOLTAGE";
		case Constants.SENSOR_TYPE_RX_TIME:
			return "RX_TIME";
		case Constants.SENSOR_TYPE_SEND_TIME:
			return "SEND_TIME";
		case Constants.SENSOR_TYPE_SLEEP_TIME:
			return "SLEEP_TIME";
		case Constants.SENSOR_TYPE_RX_SEND_SLEEP_TIME:
			return "RX_SEND_SLEEP_TIME";
		case Constants.SENSOR_LINK_QUALITY_PARENT_ID:
			return "LINK_QUALITY_PARENT_ID";
			
		default:
			return "UNKNOWN SENSOR";
		}
	}
	
	public static String getSensorScale(int sensorCode) {
		switch (sensorCode) {
		case Constants.SENSOR_TYPE_ACC_X:
			return "g";
		case Constants.SENSOR_TYPE_ACC_Y:
			return "g";
		case Constants.SENSOR_TYPE_ACC_Z:
			return "g";
		case Constants.SENSOR_TYPE_ELECTRICITY:
			return "WATT";
		case Constants.SENSOR_TYPE_ENERGY:
			return "Joule";
		case Constants.SENSOR_TYPE_HUMIDITY:
			return "HUMIDITY";
		case Constants.SENSOR_TYPE_IR:
			return "Presence(YES/NO)";
		case Constants.SENSOR_TYPE_LIGHT:
			return "Lux";
		case Constants.SENSOR_TYPE_MAGNETIC_X:
			return "MAGNETIC_X";
		case Constants.SENSOR_TYPE_MAGNETIC_Y:
			return "MAGNETIC_Y";
		case Constants.SENSOR_TYPE_SOUND:
			return "SOUND";
		case Constants.SENSOR_TYPE_TEMPERATURE:
			return "°C";
		default:
			return "UNKNOWN SENSOR";
		}
	}

	public static String getFunctionName(int functionCode) {
		switch (functionCode) {
		case Constants.FUNCTION_ELABORATION_AND_THRESHOLD_STANDARD_ENABLED:
			return "ELABORATION AND THRESHOLD STANDARD";
		case Constants.FUNCTION_ELABORATION_STANDARD_ENABLED:
			return "ELABORATION STANDARD";
		case Constants.FUNCTION_THRESHOLD_STANDARD_ENABLED:
			return "THRESHOLD STANDARD";
		case Constants.FUNCTION_AVERAGE_ENABLED:
			return "AVERAGE";
		case Constants.FUNCTION_MIN_ENABLED:
			return "MIN";
		case Constants.FUNCTION_MAX_ENABLED:
			return "MAX";
		case Constants.FUNCTION_THRESHOLD_TYPE_LOWER_ENABLED:
			return "THRESHOLD TYPE LOWER";
		case Constants.FUNCTION_THRESHOLD_TYPE_BIGGER:
			return "THRESHOLD TYPE BIGGER";
		case Constants.FUNCTION_THRESHOLD_TYPE_TRANSITION:
			return "THRESHOLD TYPE TRANSITION";
		default:
			return "";
		}
	}

	public static String getConfigurationTypeString(int configurationType) {
		switch (configurationType) {
		case 1:
			return "MEMBERSHIP";
		case 2:
			return "SCHEDULE";
		case 3:
			return "UNSCHEDULE";
		case 4:
			return "RESET";
		default:
			return "";
		}
	}

	public static String getActionString(int action) {
		switch (action) {
		case 0:
			return "SENSING";
		case 1:
			return "ACTUATING";
		default:
			return "";
		}
	}

	public static String getDataTypeString(int dataType) {
		switch (dataType) {
		case 0:
			return "SENSED DATA";
		case 1:
			return "THRESHOLD";
		case 2:
		default:
			return "";
		}
	}

	public static String getThresholdName(int thresholdNumber) {
		switch (thresholdNumber) {
		case 0:
			return "LOWER";
		case 1:
			return "BIGGER";
		case 2:
			return "TRANSITION";
		default:
			return "";
		}
	}

	public static String getTimeScaleString(int periodTimescale) {
		switch (periodTimescale) {
		case 0:
			return "SEC";
		case 1:
			return "MIN";
		case 2:
			return "HOUR";
		case 3:
			return "DAY";
		default:
			return null;
		}
	}

	public static String getActuatorParamsString(int actuatorParams) {
		switch (actuatorParams) {
		case 0:
			return "NO PARAMS";
		case 1:
			return "LED_1_TOGGLE";
		case 2:
			return "LED_2_TOGGLE";
		default:
			return "";
		}
	}

	public static String getSyntheticDataString(int syntheticData) {
		switch (syntheticData) {
		case 0:
			return "RAW";
		case 1:
			return "AVERAGE";
		case 2:
			return "MIN";
		case 3:
			return "MAX";
		default:
			return "";
		}
	}
	
    public static String getTxtSensorboardInformation(int sensorBoardID){
        switch(sensorBoardID){

            case Constants.SENSORBOARD_TYPE_NO_SENSORBOARD: return Constants.SENSORBOARD_TYPE_NO_SENSORBOARD_DESCRIPTION;
            case Constants.SENSORBOARD_TYPE_SBT80_TELOSB: return Constants.SENSORBOARD_TYPE_SBT80_TELOSB_DESCRIPTION;
            case Constants.SENSORBOARD_TYPE_STD_TELOSB: return Constants.SENSORBOARD_TYPE_STD_TELOSB_DESCRIPTION;
            case Constants.SENSORBOARD_TYPE_TYNSENSORBRD: return Constants.SENSORBOARD_TYPE_TYNSENSORBRD_DESCRIPTION;
            case Constants.SENSORBOARD_TYPE_BLACKPLUG: return Constants.SENSORBOARD_TYPE_BLACKPLUG_DESCRIPTION;
            case Constants.SENSORBOARD_TYPE_ACMEX2: return Constants.SENSORBOARD_TYPE_ACMEX2_DESCRIPTION;
            default: return Constants.SENSORBOARD_UNKNOWN;
        }
    }
    
    public static String getTxtSensorboardInformation(BMFNode node){
        return getTxtSensorboardInformation(node.getSensorBoard());
    }

}
