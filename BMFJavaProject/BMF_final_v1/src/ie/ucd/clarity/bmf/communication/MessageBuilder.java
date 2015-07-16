package ie.ucd.clarity.bmf.communication;

import ie.ucd.clarity.bmf.common.Constants;
import ie.ucd.clarity.bmf.common.GroupException;

public class MessageBuilder implements IMessageBuilder {

	public byte[] buildResetPacket(IResetPacket rpkt) {

		// packet type is known : Constants.PKT_TYPE_RESET_NODE

		byte[] pkt = new byte[Constants.PKT_MAX_LENGTH];
		byte index = 0;

		pkt[index++] = Constants.PKT_TYPE_RESET_NODE;
		pkt[index++] = Constants.SENDER_ID;

		if (rpkt.getDestination().getAddresseeType() == Constants.ADDRESSEE_TYPE_NODE) {
			pkt[index++] = (byte) (rpkt.getDestination().getAddresseeType() << 6);
			pkt[index++] = (byte) rpkt.getDestination().getDestNodeID();
		} else { // addresseeType == Constants.ADDRESSEE_TYPE_GROUP
			pkt[index++] = (byte) ((rpkt.getDestination().getAddresseeType() << 6) | (rpkt
					.getDestination().getHowManyGroups() - 1));
			pkt[index++] = (byte) ((rpkt.getDestination().getIsNotGroup()[0] << 5) | (rpkt
					.getDestination().getDestGroupIDs()[0]));
			for (int i = 1; i < rpkt.getDestination().getHowManyGroups(); i++) {
				pkt[index++] = (byte) ((rpkt.getDestination()
						.getAssociativeRules()[i - 1] << 6)
						| (rpkt.getDestination().getIsNotGroup()[i] << 5) | (rpkt
						.getDestination().getDestGroupIDs()[i]));
			}
		}

		byte[] returnArray = new byte[index];
		System.arraycopy(pkt, 0, returnArray, 0, index);
		System.out.println(index + " bytes packet to send");

		return returnArray;
	}

	/**
	 * 
	 * @param pkt
	 * @return
	 * @throws GroupException
	 */
	public byte[] buildMembershipPacket(IMembershipPacket pkt)
			throws GroupException {
		return buildMembershipPacket(Constants.SENDER_ID, pkt.getDestination()
				.getAddresseeType(), pkt.getDestination().getHowManyGroups(),
				pkt.getDestination().getIsNotGroup(), pkt.getDestination()
						.getDestGroupIDs(), pkt.getDestination()
						.getAssociativeRules(), pkt.getDestination()
						.getDestNodeID(), pkt.getMembershipType(), pkt
						.getMembershipGroups(), pkt.getGroupIDs());
	}

	/**
	 * 
	 * @param senderID
	 * @param addresseeType
	 * @param howManyGroups
	 * @param isNotGroup
	 * @param destGroupIDs
	 * @param associativeRules
	 * @param destNodeID
	 * @param membershipType
	 * @param membershipGroups
	 * @param groupIDs
	 * @return
	 * @throws GroupException
	 */
	public byte[] buildMembershipPacket(int senderID, int addresseeType,
			int howManyGroups, int[] isNotGroup, int[] destGroupIDs,
			int[] associativeRules, // group case
			int destNodeID, // node case
			int membershipType, int membershipGroups, int[] groupIDs)
			throws GroupException {

		// packet type is known : Constants.PKT_MEMBERSHIP_TYPE

		byte[] pkt = new byte[Constants.PKT_MEMBERSHIP_MAX_LENGTH];
		byte index = 0;

		pkt[index++] = Constants.PKT_TYPE_MEMBERSHIP;
		pkt[index++] = (byte) senderID;

		if (addresseeType == Constants.ADDRESSEE_TYPE_NODE) {
			pkt[index++] = (byte) (addresseeType << 6);
			pkt[index++] = (byte) destNodeID;
		} else { // addresseeType == Constants.ADDRESSEE_TYPE_GROUP
			pkt[index++] = (byte) ((addresseeType << 6) | (howManyGroups - 1));
			pkt[index++] = (byte) ((isNotGroup[0] << 5) | (destGroupIDs[0]));
			for (int i = 1; i < howManyGroups; i++) {
				pkt[index++] = (byte) ((associativeRules[i - 1] << 6)
						| (isNotGroup[i] << 5) | (destGroupIDs[i]));
			}
		}

		if (membershipType == Constants.MEMBERSHIP_TYPE_RESET) {

			pkt[index++] = (byte) ((membershipType << 6));
		} else {
			pkt[index++] = (byte) ((membershipType << 6) | (membershipGroups - 1));
			for (int i = 0; i < membershipGroups; i++) {
				if (groupIDs[i] > Constants.GROUP_ID_MAX_ID_AVAILABLE) // Max
																		// group
																		// ID is
																		// 31
					throw new GroupException("wrong groupID");
				pkt[index++] = (byte) groupIDs[i];
			}

		}// else

		byte[] returnArray = new byte[index];
		System.arraycopy(pkt, 0, returnArray, 0, index);
		System.out.println(index + " bytes packet to send");

		return returnArray;
	}

	/**
	 * 
	 * @param pkt
	 * @return
	 */
	public byte[] buildConfigurationPacket(IConfigurationPacket pkt) {
		return this.buildConfigurationPacket(Constants.SENDER_ID, pkt
				.getDestination().getAddresseeType(), pkt.getDestination()
				.getHowManyGroups(), pkt.getDestination().getIsNotGroup(), pkt
				.getDestination().getDestGroupIDs(), pkt.getDestination()
				.getAssociativeRules(), pkt.getDestination().getDestNodeID(),
				pkt.getConfigurationType(), pkt.getRequestID(), pkt
						.getPeriodTimescale(), pkt.getPeriodValue(), pkt
						.getLifetimeTimescale(), pkt.getLifetimeValue(), pkt
						.getAction(), pkt.getSensor_actuatorType(), pkt
						.getActuatorParams(), pkt.getDataType(), pkt
						.getSyntheticData(), pkt.getThresholdNumber(), pkt
						.getSensorIfThreshold(), pkt.getThresholdType(), pkt
						.getThresholdValue(), pkt.getSensorTypeMoreThreshold());
	}

	/**
	 * 
	 * @param senderID
	 * @param addresseeType
	 * @param howManyGroups
	 * @param isNotGroup
	 * @param destGroupIDs
	 * @param associativeRules
	 * @param destNodeID
	 * @param requestID
	 * @param periodTimescale
	 * @param periodValue
	 * @param lifetimeTimescale
	 * @param lifetimeValue
	 * @param action
	 * @param sensor_actuatorType
	 * @param actuatorParams
	 * @param dataType
	 * @param syntheticData
	 * @param thresholdNumber
	 * @param sensorIfThreshold
	 * @param thresholdType
	 * @param thresholdValue
	 * @param sensorTypeMoreThreshold
	 * @return
	 */
	public byte[] buildConfigurationPacket(
			int senderID,
			int addresseeType,
			int howManyGroups,
			int[] isNotGroup,
			int[] destGroupIDs,
			int[] associativeRules, // group case
			int destNodeID, // node case
			int configurationType, int requestID, int periodTimescale,
			int periodValue, int lifetimeTimescale, int lifetimeValue,
			int action, int sensor_actuatorType,
			int actuatorParams, // actuator case
			int dataType, int syntheticData,
			// if dataType == sensedData from here should be all null
			int thresholdNumber, int sensorIfThreshold, int[] thresholdType,
			int[] thresholdValue, int[] sensorTypeMoreThreshold) {

		// packet type is known : Constants.PKT_CONFIGURATION_ID

		byte[] pkt = new byte[Constants.PKT_CONFIGURATION_MAX_LENGTH];
		byte index = 0;

		pkt[index++] = (byte) configurationType;

		pkt[index++] = (byte) senderID;

		if (addresseeType == Constants.ADDRESSEE_TYPE_NODE) {
			pkt[index++] = (byte) (addresseeType << 6);
			pkt[index++] = (byte) destNodeID;
		} else { // addresseeType == Constants.ADDRESSEE_TYPE_GROUP
			pkt[index++] = (byte) ((addresseeType << 6) | (howManyGroups - 1));
			pkt[index++] = (byte) ((isNotGroup[0] << 5) | (destGroupIDs[0]));
			for (int i = 1; i < howManyGroups; i++) {
				pkt[index++] = (byte) ((associativeRules[i - 1] << 6)
						| (isNotGroup[i] << 5) | (destGroupIDs[i]));
			}
		}

		pkt[index++] = (byte) requestID;

		if (configurationType == Constants.PKT_TYPE_CONFIGURATION_SCHEDULE) {

			pkt[index++] = (byte) ((periodTimescale << 6) | periodValue);
			pkt[index++] = (byte) ((lifetimeTimescale << 6) | lifetimeValue);

			pkt[index++] = (byte) ((action << 6) | sensor_actuatorType);
			if (action == Constants.ACTION_ACTUATING) {
				pkt[index++] = (byte) ((actuatorParams >> 8) & 0xFF);
				pkt[index++] = (byte) (actuatorParams & 0xFF);
			} else { // action == Constants.ACTION_SENSING
				pkt[index++] = (byte) ((dataType << 6) | syntheticData);

				if (dataType == Constants.DATA_TYPE_THRESHOLD_NOTIFICATION) {
					pkt[index++] = (byte) (((thresholdNumber - 1) << 6) | sensorIfThreshold);
					pkt[index++] = (byte) thresholdType[0];
					pkt[index++] = (byte) ((thresholdValue[0] >> 8) & 0xFF);
					pkt[index++] = (byte) (thresholdValue[0] & 0xFF);

					for (int i = 1; i < thresholdNumber; i++) {
						pkt[index++] = (byte) ((sensorTypeMoreThreshold[i - 1] << 2) | thresholdType[i]);
						pkt[index++] = (byte) ((thresholdValue[i] >> 8) & 0xFF);
						pkt[index++] = (byte) (thresholdValue[i] & 0xFF);
					}
				}
				// else if(dataType == Constants.DATA_TYPE_SENSED_DATA) -->
				// packet end
			}

		} // else configurationType ==
			// Constants.PKT_TYPE_CONFIGURATION_UNSCHEDULE

		byte[] returnArray = new byte[index];
		System.arraycopy(pkt, 0, returnArray, 0, index);

		//System.out.println(index + " bytes packet to send");

		return returnArray;
	}

}
