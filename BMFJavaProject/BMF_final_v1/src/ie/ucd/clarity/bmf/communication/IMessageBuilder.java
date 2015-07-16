package ie.ucd.clarity.bmf.communication;

import ie.ucd.clarity.bmf.common.GroupException;

public interface IMessageBuilder {

	
    public byte[] buildResetPacket(IResetPacket rpkt);
    
    public byte[] buildMembershipPacket(IMembershipPacket pkt) throws GroupException;
    
    public byte[] buildMembershipPacket(int senderID, int addresseeType, 
                                int howManyGroups, int[] isNotGroup, int[] destGroupIDs, int[] associativeRules, //group case
                                int destNodeID, //node case
                                int membershipType, int membershipGroups,
                                int[] groupIDs) throws GroupException;
    
    public byte[] buildConfigurationPacket(IConfigurationPacket pkt);
    
    public byte[] buildConfigurationPacket(int senderID, int addresseeType, 
							int howManyGroups, int[] isNotGroup, int[] destGroupIDs, int[] associativeRules, //group case 
							int destNodeID, //node case
                                                            int configurationType,
							int requestID,
							int periodTimescale, int periodValue, 
							int lifetimeTimescale, int lifetimeValue,
							int action, int sensor_actuatorType,
							int actuatorParams, // actuator case
							int dataType, int syntheticData,
							// if dataType == sensedData from here should be all null
							int thresholdNumber, int sensorIfThreshold,
							int[] thresholdType, int[] thresholdValue, int[] sensorTypeMoreThreshold);
}
