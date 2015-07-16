package ie.ucd.clarity.bmf.communication;

import ie.ucd.clarity.bmf.common.InvalidPacketParametersException;


/**
 * @author   Matthew
 */
public interface IConfigurationPacket extends IBMFPacket {

	/**
	 * @return
	 * @uml.property  name="action"
	 */
	public int getAction();

	/**
	 * @return
	 * @uml.property  name="actuatorParams"
	 */
	public int getActuatorParams();

	/**
	 * @return
	 * @uml.property  name="configurationType"
	 */
	public int getConfigurationType();

	/**
	 * @return
	 * @uml.property  name="dataType"
	 */
	public int getDataType();

	/**
	 * @uml.property  name="destination"
	 * @uml.associationEnd  
	 */
	public IDestination getDestination();

	/**
	 * @return
	 * @uml.property  name="lifetimeTimescale"
	 */
	public int getLifetimeTimescale();

	/**
	 * @return
	 * @uml.property  name="lifetimeValue"
	 */
	public int getLifetimeValue();

	/**
	 * @return
	 * @uml.property  name="periodTimescale"
	 */
	public int getPeriodTimescale();

	/**
	 * @return
	 * @uml.property  name="periodValue"
	 */
	public int getPeriodValue();

	/**
	 * @return
	 * @uml.property  name="requestID"
	 */
	public int getRequestID();

	/**
	 * @return
	 * @uml.property  name="sensorIfThreshold"
	 */
	public int getSensorIfThreshold();

	/**
	 * @return
	 * @uml.property  name="sensorTypeMoreThreshold"
	 */
	public int[] getSensorTypeMoreThreshold();

	/**
	 * @return
	 * @uml.property  name="sensor_actuatorType"
	 */
	public int getSensor_actuatorType();

	/**
	 * @return
	 * @uml.property  name="syntheticData"
	 */
	public int getSyntheticData();

	/**
	 * @return
	 * @uml.property  name="thresholdNumber"
	 */
	public int getThresholdNumber();

	/**
	 * @return
	 * @uml.property  name="thresholdType"
	 */
	public int[] getThresholdType();

	/**
	 * @return
	 * @uml.property  name="thresholdValue"
	 */
	public int[] getThresholdValue();

	public boolean isThresholdNumberCompatibleWithPacket(int thresholdNumber, int[] thresholdType, int[] thresholdValue, int[] sensorTypeMoreThreshold);

	/**
	 * @param  action
	 * @uml.property  name="action"
	 */
	public void setAction(int action);

	/**
	 * @param  actuatorParams
	 * @uml.property  name="actuatorParams"
	 */
	public void setActuatorParams(int actuatorParams);

	/**
	 * @param configurationType
	 * @throws InvalidPacketParametersException
	 * @uml.property  name="configurationType"
	 */
	public void setConfigurationType(int configurationType) throws InvalidPacketParametersException;

	/**
	 * @param  dataType
	 * @uml.property  name="dataType"
	 */
	public void setDataType(int dataType);

	/**
	 * @param  dest
	 * @uml.property  name="destination"
	 */
	public void setDestination(IDestination dest);

	public void setDestinationBroadcast();

	public void setDestinationGroups(String groups) throws InvalidPacketParametersException;

	public void setDestinationNode(int destNodeID);

	/**
	 * @param  lifetimeTimescale
	 * @uml.property  name="lifetimeTimescale"
	 */
	public void setLifetimeTimescale(int lifetimeTimescale);

	/**
	 * @param lifetimeValue
	 * @throws InvalidPacketParametersException
	 * @uml.property  name="lifetimeValue"
	 */
	public void setLifetimeValue(int lifetimeValue) throws InvalidPacketParametersException;

	/**
	 * @param  periodTimescale
	 * @uml.property  name="periodTimescale"
	 */
	public void setPeriodTimescale(int periodTimescale);

	/**
	 * @param periodValue
	 * @throws InvalidPacketParametersException
	 * @uml.property  name="periodValue"
	 */
	public void setPeriodValue(int periodValue) throws InvalidPacketParametersException;

	/**
	 * @param  requestID
	 * @uml.property  name="requestID"
	 */
	public void setRequestID(int requestID);

	/**
	 * @param  sensorIfThreshold
	 * @uml.property  name="sensorIfThreshold"
	 */
	public void setSensorIfThreshold(int sensorIfThreshold);

	/**
	 * @param  sensorTypeMoreThreshold
	 * @uml.property  name="sensorTypeMoreThreshold"
	 */
	public void setSensorTypeMoreThreshold(int[] sensorTypeMoreThreshold);

	/**
	 * @param  sensor_actuatorType
	 * @uml.property  name="sensor_actuatorType"
	 */
	public void setSensor_actuatorType(int sensor_actuatorType);

	/**
	 * @param  syntheticData
	 * @uml.property  name="syntheticData"
	 */
	public void setSyntheticData(int syntheticData);

	/**
	 * @param  thresholdNumber
	 * @uml.property  name="thresholdNumber"
	 */
	public void setThresholdNumber(int thresholdNumber);

	/**
	 * @param  thresholdType
	 * @uml.property  name="thresholdType"
	 */
	public void setThresholdType(int[] thresholdType);
	
	/**
	 * @param  thresholdValue
	 * @uml.property  name="thresholdValue"
	 */
	public void setThresholdValue(int[] thresholdValue);

}
