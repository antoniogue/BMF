package ie.ucd.clarity.bmf.communication;

import ie.ucd.clarity.bmf.communication.IConfigurationPacket;

import ie.ucd.clarity.bmf.common.Constants;
import ie.ucd.clarity.bmf.common.InvalidPacketParametersException;
import ie.ucd.clarity.bmf.communication.IDestination;

/**
 * @author  Matthew
 */
public class ConfigurationPacket implements IConfigurationPacket {
	/**
	 * @uml.property  name="configurationType"
	 */
	private int configurationType;
	// private int senderID;
	// private int addresseeType;
	// private int howManyGroups; //group case
	// private int[] isNotGroup; //group case
	// private int[] destGroupIDs; //group case
	// private int[] associativeRules; //group case
	// private int destNodeID; //node case

	/**
	 * @uml.property  name="dest"
	 * @uml.associationEnd  
	 */
	private IDestination dest;

	/**
	 * @uml.property  name="requestID"
	 */
	private int requestID;
	/**
	 * @uml.property  name="periodTimescale"
	 */
	private int periodTimescale;
	/**
	 * @uml.property  name="periodValue"
	 */
	private int periodValue;
	/**
	 * @uml.property  name="lifetimeTimescale"
	 */
	private int lifetimeTimescale;
	/**
	 * @uml.property  name="lifetimeValue"
	 */
	private int lifetimeValue;
	/**
	 * @uml.property  name="action"
	 */
	private int action;
	/**
	 * @uml.property  name="sensor_actuatorType"
	 */
	private int sensor_actuatorType;
	/**
	 * @uml.property  name="actuatorParams"
	 */
	private int actuatorParams; // actuator case
	/**
	 * @uml.property  name="dataType"
	 */
	private int dataType;
	/**
	 * @uml.property  name="syntheticData"
	 */
	private int syntheticData;
	// if dataType == sensedData from here should be all null
	/**
	 * @uml.property  name="thresholdNumber"
	 */
	private int thresholdNumber;
	/**
	 * @uml.property  name="sensorIfThreshold"
	 */
	private int sensorIfThreshold;
	/**
	 * @uml.property  name="thresholdType"
	 */
	private int[] thresholdType;
	/**
	 * @uml.property  name="thresholdValue"
	 */
	private int[] thresholdValue;
	/**
	 * @uml.property  name="sensorTypeMoreThreshold"
	 */
	private int[] sensorTypeMoreThreshold;

	public ConfigurationPacket() {
		dest = new Destination();
	}

	/**
	 * 
	 * @param requestID
	 * @param periodTimescale
	 * @param periodValue
	 * @param lifetimeTimescale
	 * @param lifetimeValue
	 * @param action
	 * @param sensor_actuatorType
	 * @param actuatorParams
	 */
	public ConfigurationPacket(int configurationType, int requestID,
			int periodTimescale, int periodValue, int lifetimeTimescale,
			int lifetimeValue, int action, int sensor_actuatorType,
			int actuatorParams) throws InvalidPacketParametersException {

		dest = new Destination();

		if (configurationType == Constants.PKT_TYPE_CONFIGURATION_SCHEDULE
				|| configurationType == Constants.PKT_TYPE_CONFIGURATION_UNSCHEDULE)
			this.configurationType = configurationType;
		else
			throw new InvalidPacketParametersException(
					"WRONG CONFIGURATION TYPE!");

		// this.addresseeType = addresseeType;
		// this.destNodeID = destNodeID;
		this.requestID = requestID;
		this.periodTimescale = periodTimescale;
		this.periodValue = periodValue;
		this.lifetimeTimescale = lifetimeTimescale;
		this.lifetimeValue = lifetimeValue;
		this.action = action;
		this.sensor_actuatorType = sensor_actuatorType;
		this.actuatorParams = actuatorParams;

	}

	/**
	 * 
	 * @param requestID
	 * @param periodTimescale
	 * @param periodValue
	 * @param lifetimeTimescale
	 * @param lifetimeValue
	 * @param action
	 * @param sensor_actuatorType
	 * @param dataType
	 * @param syntheticData
	 */
	public ConfigurationPacket(
			// int addresseeType,
			// single node destination case
			// int destNodeID,
			// end of node destination case
			int configurationType,

			int requestID, int periodTimescale, int periodValue,
			int lifetimeTimescale, int lifetimeValue, int action,
			int sensor_actuatorType,
			// if action == sensing
			int dataType, int syntheticData
	// if data type == sensed data --> no more fields to fill
	) throws InvalidPacketParametersException {

		dest = new Destination();

		if (configurationType == Constants.PKT_TYPE_CONFIGURATION_SCHEDULE
				|| configurationType == Constants.PKT_TYPE_CONFIGURATION_UNSCHEDULE)
			this.configurationType = configurationType;
		else
			throw new InvalidPacketParametersException(
					"WRONG CONFIGURATION TYPE!");

		// this.addresseeType = addresseeType;
		// this.destNodeID = destNodeID;
		this.requestID = requestID;
		this.periodTimescale = periodTimescale;
		this.periodValue = periodValue;
		this.lifetimeTimescale = lifetimeTimescale;
		this.lifetimeValue = lifetimeValue;
		this.action = action;
		this.sensor_actuatorType = sensor_actuatorType;
		this.dataType = dataType;
		this.syntheticData = syntheticData;
	}

	

	/**
	 * 
	 * @param requestID
	 * @param periodTimescale
	 * @param periodValue
	 * @param lifetimeTimescale
	 * @param lifetimeValue
	 * @param action
	 * @param sensor_actuatorType
	 * @param dataType
	 * @param syntheticData
	 * @param thresholdNumber
	 * @param sensorIfThreshold
	 * @param thresholdType
	 * @param thresholdValue
	 * @param sensorTypeMoreThreshold
	 * @throws InvalidPacketParametersException
	 */
	public ConfigurationPacket(
			int configurationType, int requestID, int periodTimescale,
			int periodValue, int lifetimeTimescale, int lifetimeValue,
			int action, int sensor_actuatorType,
			int dataType, int syntheticData,
			int thresholdNumber, int sensorIfThreshold, int[] thresholdType,
			int[] thresholdValue, int[] sensorTypeMoreThreshold)
			throws InvalidPacketParametersException {

		if (configurationType == Constants.PKT_TYPE_CONFIGURATION_SCHEDULE
				|| configurationType == Constants.PKT_TYPE_CONFIGURATION_UNSCHEDULE)
			this.configurationType = configurationType;
		else
			throw new InvalidPacketParametersException(
					"WRONG CONFIGURATION TYPE!");

		if (!isThresholdNumberCompatibleWithPacket(thresholdNumber,
				thresholdType, thresholdValue, sensorTypeMoreThreshold)) {
			// ERROR: NO COMPATIBILITY among threshold notification parameters
			throw new InvalidPacketParametersException(
					"ConfigurationPacket: NO COMPATIBILITY AMONG THRESHOLD NOTIFICATION PARAMETERS!");
		}

		dest = new Destination();

		// this.addresseeType = addresseeType;
		// this.destNodeID = destNodeID;
		this.requestID = requestID;
		this.periodTimescale = periodTimescale;
		this.periodValue = periodValue;
		this.lifetimeTimescale = lifetimeTimescale;
		this.lifetimeValue = lifetimeValue;
		this.action = action;
		this.sensor_actuatorType = sensor_actuatorType;
		this.dataType = dataType;
		this.syntheticData = syntheticData;
		this.thresholdNumber = thresholdNumber;
		this.sensorIfThreshold = sensorIfThreshold;
		this.thresholdType = new int[thresholdType.length];
		System.arraycopy(thresholdType, 0, this.thresholdType, 0,
				thresholdType.length);
		this.thresholdValue = new int[thresholdValue.length];
		System.arraycopy(thresholdValue, 0, this.thresholdValue, 0,
				thresholdValue.length);
		this.sensorTypeMoreThreshold = new int[sensorTypeMoreThreshold.length];
		System
				.arraycopy(sensorTypeMoreThreshold, 0,
						this.sensorTypeMoreThreshold, 0,
						sensorTypeMoreThreshold.length);
	}

	/**
	 * 
	 * @param thresholdNumber
	 * @param thresholdType
	 * @param thresholdValue
	 * @param sensorTypeMoreThreshold
	 * @return
	 */
	public boolean isThresholdNumberCompatibleWithPacket(int thresholdNumber,
			int[] thresholdType, int[] thresholdValue,
			int[] sensorTypeMoreThreshold) {
		return thresholdNumber == thresholdType.length
				&& thresholdNumber == thresholdValue.length
				&& thresholdNumber == (sensorTypeMoreThreshold.length + 1);
	}

	// DESTINATION STUFF
	// //////////////////////////////////////////////////////////////////////////////////7
	/**
	 * single node destination case
	 * 
	 * @param destNodeID
	 */
	public void setDestinationNode(int destNodeID) {
		dest.setDestinationNode(destNodeID);
	}

	/**
         * 
         */
	public void setDestinationBroadcast() {
		dest.setDestinationBroadcast();
	}

	/**
         * 
         */
	public IDestination getDestination() {
		return this.dest;
	}

	/**
         * 
         */
	public void setDestination(IDestination dest) {
		this.dest = dest;
	}

	/**
	 * 
	 * @param groups
	 * @throws InvalidPacketParametersException
	 */
	public void setDestinationGroups(String groups)
			throws InvalidPacketParametersException {
		dest.setDestinationGroups(groups);
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////

	public int getPACKET_TYPE() {
		return configurationType;
	}

	/**
	 * @return
	 * @uml.property  name="configurationType"
	 */
	public int getConfigurationType() {
		return configurationType;
	}

	/**
	 * @param configurationType
	 * @uml.property  name="configurationType"
	 */
	public void setConfigurationType(int configurationType) {
		if (configurationType == Constants.PKT_TYPE_CONFIGURATION_SCHEDULE
				|| configurationType == Constants.PKT_TYPE_CONFIGURATION_UNSCHEDULE)
			this.configurationType = configurationType;
	}

	/**
	 * @return
	 * @uml.property  name="periodTimescale"
	 */
	public int getPeriodTimescale() {
		return periodTimescale;
	}

	/**
	 * @param periodTimescale
	 * @uml.property  name="periodTimescale"
	 */
	public void setPeriodTimescale(int periodTimescale) {
		this.periodTimescale = periodTimescale;
	}

	/**
	 * @return
	 * @uml.property  name="periodValue"
	 */
	public int getPeriodValue() {
		return periodValue;
	}

	/**
	 * @param periodValue
	 * @uml.property  name="periodValue"
	 */
	public void setPeriodValue(int periodValue) {

		this.periodValue = periodValue;
	}

	/**
	 * @return
	 * @uml.property  name="lifetimeTimescale"
	 */
	public int getLifetimeTimescale() {
		return lifetimeTimescale;
	}

	/**
	 * @param lifetimeTimescale
	 * @uml.property  name="lifetimeTimescale"
	 */
	public void setLifetimeTimescale(int lifetimeTimescale) {
		this.lifetimeTimescale = lifetimeTimescale;
	}

	/**
	 * @return
	 * @uml.property  name="lifetimeValue"
	 */
	public int getLifetimeValue() {
		return lifetimeValue;
	}

	/**
	 * @param lifetimeValue
	 * @uml.property  name="lifetimeValue"
	 */
	public void setLifetimeValue(int lifetimeValue) {
		this.lifetimeValue = lifetimeValue;
	}

	/**
	 * @return
	 * @uml.property  name="action"
	 */
	public int getAction() {
		return action;
	}

	/**
	 * @param action
	 * @uml.property  name="action"
	 */
	public void setAction(int action) {
		this.action = action;
	}

	/**
	 * @return
	 * @uml.property  name="sensor_actuatorType"
	 */
	public int getSensor_actuatorType() {
		return sensor_actuatorType;
	}

	/**
	 * @param sensor_actuatorType
	 * @uml.property  name="sensor_actuatorType"
	 */
	public void setSensor_actuatorType(int sensor_actuatorType) {
		this.sensor_actuatorType = sensor_actuatorType;
	}

	/**
	 * @return
	 * @uml.property  name="actuatorParams"
	 */
	public int getActuatorParams() {
		return actuatorParams;
	}

	/**
	 * @param actuatorParams
	 * @uml.property  name="actuatorParams"
	 */
	public void setActuatorParams(int actuatorParams) {
		this.actuatorParams = actuatorParams;
	}

	/**
	 * @return
	 * @uml.property  name="dataType"
	 */
	public int getDataType() {
		return dataType;
	}

	/**
	 * @param dataType
	 * @uml.property  name="dataType"
	 */
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return
	 * @uml.property  name="syntheticData"
	 */
	public int getSyntheticData() {
		return syntheticData;
	}

	/**
	 * @param syntheticData
	 * @uml.property  name="syntheticData"
	 */
	public void setSyntheticData(int syntheticData) {
		this.syntheticData = syntheticData;
	}

	/**
	 * @return
	 * @uml.property  name="thresholdNumber"
	 */
	public int getThresholdNumber() {
		return thresholdNumber;
	}

	/**
	 * @param thresholdNumber
	 * @uml.property  name="thresholdNumber"
	 */
	public void setThresholdNumber(int thresholdNumber) {
		this.thresholdNumber = thresholdNumber;
	}

	/**
	 * @return
	 * @uml.property  name="sensorIfThreshold"
	 */
	public int getSensorIfThreshold() {
		return sensorIfThreshold;
	}

	/**
	 * @param sensorIfThreshold
	 * @uml.property  name="sensorIfThreshold"
	 */
	public void setSensorIfThreshold(int sensorIfThreshold) {
		this.sensorIfThreshold = sensorIfThreshold;
	}

	/**
	 * @return
	 * @uml.property  name="thresholdType"
	 */
	public int[] getThresholdType() {
		return thresholdType;
	}

	/**
	 * @param thresholdType
	 * @uml.property  name="thresholdType"
	 */
	public void setThresholdType(int[] thresholdType) {
		this.thresholdType = new int[thresholdType.length];
		System.arraycopy(thresholdType, 0, this.thresholdType, 0,
				thresholdType.length);
	}

	/**
	 * @return
	 * @uml.property  name="thresholdValue"
	 */
	public int[] getThresholdValue() {
		return thresholdValue;
	}

	/**
	 * @param thresholdValue
	 * @uml.property  name="thresholdValue"
	 */
	public void setThresholdValue(int[] thresholdValue) {
		this.thresholdValue = new int[thresholdValue.length];
		System.arraycopy(thresholdValue, 0, this.thresholdValue, 0,
				thresholdValue.length);
	}

	/**
	 * @return
	 * @uml.property  name="sensorTypeMoreThreshold"
	 */
	public int[] getSensorTypeMoreThreshold() {
		return sensorTypeMoreThreshold;
	}

	/**
	 * @param sensorTypeMoreThreshold
	 * @uml.property  name="sensorTypeMoreThreshold"
	 */
	public void setSensorTypeMoreThreshold(int[] sensorTypeMoreThreshold) {
		this.sensorTypeMoreThreshold = new int[sensorTypeMoreThreshold.length];
		System
				.arraycopy(sensorTypeMoreThreshold, 0,
						this.sensorTypeMoreThreshold, 0,
						sensorTypeMoreThreshold.length);
	}

	/**
	 * @return
	 * @uml.property  name="requestID"
	 */
	public int getRequestID() {
		return requestID;
	}

	/**
	 * @param requestID
	 * @uml.property  name="requestID"
	 */
	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}

}
