package ie.ucd.clarity.bmf.communication;


import ie.ucd.clarity.bmf.communication.IDestination;

import java.util.StringTokenizer;
import ie.ucd.clarity.bmf.common.Constants;

/**
 * @author  Matthew
 */
public class Destination implements IDestination {

	/**
	 * @uml.property  name="addresseeType"
	 */
	private int addresseeType;
	/**
	 * @uml.property  name="howManyGroups"
	 */
	private int howManyGroups; // group case
	/**
	 * @uml.property  name="isNotGroup"
	 */
	private int[] isNotGroup; // group case
	/**
	 * @uml.property  name="destGroupIDs"
	 */
	private int[] destGroupIDs; // group case
	/**
	 * @uml.property  name="associativeRules"
	 */
	private int[] associativeRules; // group case
	/**
	 * @uml.property  name="destNodeID"
	 */
	private int destNodeID; // node case

	private boolean initialized = false;
	/**
	 * @uml.property  name="groups"
	 */
	private String groups = null;

	public boolean isDestinationProperlySet() {
		if (!initialized)
			return false;
		return true;
	}

	/**
	 * 
	 * @param groups
	 * @throws InvalidPacketParametersException
	 */
	// the same method is in the ConfigurationPacket Class.... it should be in a
	// bmf.common Header Class
	public void setDestinationGroups(String groups){
		// WE WAIT FOR A STRING LIKE: "10|11&12"
		this.groups = groups;
		
		StringTokenizer st = new StringTokenizer(groups, "&|", false);
		this.howManyGroups = st.countTokens();
		this.isNotGroup = new int[howManyGroups];
		this.destGroupIDs = new int[howManyGroups];
		this.associativeRules = new int[howManyGroups - 1];

		st = new StringTokenizer(groups, "!&|", true);
		try {
			int countPos = 0;
			while (st.hasMoreTokens()) {
				String temp = st.nextToken();
				if (temp.equalsIgnoreCase("!")) {
					this.isNotGroup[countPos] = Constants.IS_NOT_FIELD_TRUE;
					temp = st.nextToken();
				} else
					this.isNotGroup[countPos] = Constants.IS_NOT_FIELD_FALSE;

				this.destGroupIDs[countPos] = Integer.parseInt(temp);

				if (st.hasMoreTokens()) {
					temp = st.nextToken();
					if (temp.equalsIgnoreCase("&"))
						this.associativeRules[countPos] = Constants.ASSOCIATIVE_RULE_AND;
					else
						this.associativeRules[countPos] = Constants.ASSOCIATIVE_RULE_OR;
				}
				countPos++;
			}
		} catch (Exception e) {
		}

		// if we are here, then the group string were OK!
		addresseeType = Constants.ADDRESSEE_TYPE_GROUP;
		initialized = true;
	}

	/**
	 * 
	 * @param destNodeID
	 */
	public void setDestinationNode(int destNodeID) {
		addresseeType = Constants.ADDRESSEE_TYPE_NODE;
		this.destNodeID = destNodeID;
		initialized = true;
	}

	/**
     *
     */
	public void setDestinationBroadcast() {
		addresseeType = Constants.ADDRESSEE_TYPE_NODE;
		this.destNodeID = Constants.ADDRESSEE_BROADCAST;
		initialized = true;
	}

	
	/**
	 * 
	 * @param howManyGroups2
	 * @param isNotGroup2
	 * @param destGroupIDs2
	 * @param associativeRules2
	 * @return
	 */
	/*
	private boolean isGroupsNumberCompatibleWithPacket() {
		if (howManyGroups == 0 && isNotGroup == null && destGroupIDs == null
				&& associativeRules == null)
			return true;
		return howManyGroups == isNotGroup.length
				&& howManyGroups == destGroupIDs.length
				&& howManyGroups == associativeRules.length + 1;
	}
	*/
	
	public String toString() {
		switch(this.addresseeType) {
		case Constants.ADDRESSEE_TYPE_NODE:
		case Constants.ADDRESSEE_BROADCAST:
			return this.destNodeID+"";
		case Constants.ADDRESSEE_TYPE_GROUP:
			return this.groups;
		default:
			return "";
		}
	}

	/**
	 * @return
	 * @uml.property  name="addresseeType"
	 */
	public int getAddresseeType() {
		return this.addresseeType;
	}

	/**
	 * @return
	 * @uml.property  name="howManyGroups"
	 */
	public int getHowManyGroups() {
		return this.howManyGroups;
	}

	/**
	 * @return
	 * @uml.property  name="isNotGroup"
	 */
	public int[] getIsNotGroup() {
		return this.isNotGroup;
	}

	/**
	 * @return
	 * @uml.property  name="destGroupIDs"
	 */
	public int[] getDestGroupIDs() {
		return this.destGroupIDs;
	}

	/**
	 * @return
	 * @uml.property  name="associativeRules"
	 */
	public int[] getAssociativeRules() {
		return this.associativeRules;
	}

	/**
	 * @return
	 * @uml.property  name="destNodeID"
	 */
	public int getDestNodeID() {
		return this.destNodeID;
	}
	
	/**
	 * @return
	 * @uml.property  name="groups"
	 */
	public String getGroups() {
		return this.groups;
	}

}
