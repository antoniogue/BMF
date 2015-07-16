package ie.ucd.clarity.bmf.data.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import ie.ucd.clarity.bmf.common.CodeConversion;
import ie.ucd.clarity.bmf.communication.IConfigurationPacket;

public class FileDataSaver implements IFileDataSaver {

	private FileWriter csvWriter;
	private HashMap<String, String> configurationData;
	
	@Override
	public boolean openConnection(String fileNamePath) throws IOException{
	    
		File file = new File(fileNamePath);
        if(!file.exists()){
            file.mkdir();
        }
    
        this.csvWriter = new FileWriter(fileNamePath + System.currentTimeMillis() + ".csv");

        String columnNames = 
    				//DATA
    				"TIME;SENDER_ID;COUNTER;REQUEST_ID;RESULT;" +
    				//CONFIGURATION
    				"REQUEST_UUID;DESTINATION;PERIOD_TIMESCALE;PERIOD_VALUE;LIFETIME_TIME_SCALE;LIFETIME_VALUE;ACTION;SENSOR_ACTUATOR_TYPE;" +
    				"DATA_TYPE;SYNTHETIC_DATA;THRESHOLD_NUMBER;SENSOR_IF_THRESHOLD;THRESHOLD_TYPE;THRESHOLD_VALUE;SENSOR_TYPE_MORE_THRESHOLD;";

        csvWriter.write(columnNames+"\n");

        csvWriter.flush();

        this.configurationData = new HashMap<String, String>();
        
        return true;


	}

	@Override
	public boolean closeConnection() {
		try {
			this.csvWriter.flush();
			this.csvWriter.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
       
	}

	@Override
	public void saveIConfigurationPacket(IConfigurationPacket packet) {
		
		String configurationDataString = "";
		configurationDataString += UUID.randomUUID().toString() + ";";
		configurationDataString += packet.getDestination().toString()+";";
		configurationDataString += packet.getPeriodTimescale() + ";";
		configurationDataString += packet.getPeriodValue() + ";";
		configurationDataString += packet.getLifetimeTimescale() + ";";
		configurationDataString += packet.getLifetimeValue() + ";";
		configurationDataString += packet.getAction() + ";";
		configurationDataString += CodeConversion.getSensorName(packet.getSensor_actuatorType()) + ";";
		configurationDataString += packet.getDataType() + ";";
		configurationDataString += packet.getSyntheticData() + ";";
		configurationDataString += packet.getThresholdNumber() + ";";
		configurationDataString += packet.getSensorIfThreshold() + ";";
		
		int [] thresholdType = packet.getThresholdType();
		if (thresholdType != null) {
			for (int i = 0; i < thresholdType.length; i++) {
				configurationDataString += thresholdType[i];
				if(i < thresholdType.length) configurationDataString += ",";
				else configurationDataString += ";";
			}
		}
		
		int [] thresholdValue = packet.getThresholdValue();
		if (thresholdValue != null) {
			for (int i = 0; i < thresholdValue.length; i++) {
				configurationDataString += thresholdValue[i];
				if(i < thresholdValue.length) configurationDataString += ",";
				else configurationDataString += ";";
			}
		}
		
		int [] sensorTypeMoreThreshold = packet.getSensorTypeMoreThreshold();
		if (sensorTypeMoreThreshold != null) {
			for (int i = 0; i < sensorTypeMoreThreshold.length; i++) {
				configurationDataString += sensorTypeMoreThreshold[i];
				if(i < sensorTypeMoreThreshold.length) configurationDataString += ",";
				else configurationDataString += ";";
			}
		}
		
		String key = packet.getRequestID()+"";
		
		this.configurationData.put(key, configurationDataString);
		
		
		
	}

	@Override
	public void saveData(int senderID, int requestID, long result, int counter) throws Exception {
		
		java.util.Calendar currTime = java.util.Calendar.getInstance();
		
		//PACKET
        String dataToStore = ""+currTime.getTime() + ";"
        	+ senderID + ";"
        	+ counter + ";"
        	+ requestID + ";"
        	+ result + ";";
        //CONFIGURATION
        String configurationDataString = this.configurationData.get(requestID+"");
        dataToStore += configurationDataString;
        
        csvWriter.write(dataToStore+"\n");
        csvWriter.flush();
        

	}

	@Override
	public boolean iConfigurationPacketExpired(IConfigurationPacket packet) {
		String returnValue = this.configurationData.remove(packet.getRequestID()+"");
		if (returnValue != null) return true;
		else return false;
	}
}
