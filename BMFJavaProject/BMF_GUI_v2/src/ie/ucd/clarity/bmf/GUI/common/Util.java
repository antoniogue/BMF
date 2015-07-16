package ie.ucd.clarity.bmf.GUI.common;

import ie.ucd.clarity.bmf.common.Constants;

public class Util {
	
	public static long getDataConverted(long data, int sensorType){
		
		switch(sensorType){
		case Constants.SENSOR_TYPE_IR: return getIRDataConverted(data);
		case Constants.SENSOR_TYPE_TEMPERATURE: return getTemperatureDataConverted(data);
		case Constants.SENSOR_TYPE_ELECTRICITY: return getElectricityDataConverted(data);
		default: return data;	
		}	
	}
	
	public static String getActivityDataConverted(String data,int sensorType)
	{
	switch(sensorType)
	{
	case Constants.SENSOR_TYPE_BODY_ACTIVITY :return getActivityDataConverted(data);
	default: return data;
	}
	}
	private static long getElectricityDataConverted(long data) {
		return (long)(data/23.5);
	}

	private static long getTemperatureDataConverted(long data) {
		// TODO Auto-generated method stub
		return (long)(-39.60+(0.01*data)-6);
	}

	private static long getIRDataConverted(long data){
		long convertedData;
		if(data>2000)
			convertedData=1;
		else convertedData=0;
		return convertedData;
	}
	private static String getActivityDataConverted(String data){
		
		return data;
	}
}
