package ie.ucd.clarity.bmf.communication;

import ie.ucd.clarity.bmf.communication.IDestination;
import ie.ucd.clarity.bmf.common.Constants;
import ie.ucd.clarity.bmf.common.InvalidPacketParametersException;
import ie.ucd.clarity.bmf.communication.IMembershipPacket;

import java.util.StringTokenizer;


/**
 * @author  Matthew
 */
public class MembershipPacket implements IMembershipPacket {

	/**
	 * @uml.property  name="pACKET_TYPE"
	 */
	private final int PACKET_TYPE = Constants.PKT_TYPE_MEMBERSHIP;
	private final int DEFAUL_MEMBERSHIP = Constants.MEMBERSHIP_TYPE_ADD;

	/**
	 * @uml.property  name="dest"
	 * @uml.associationEnd  
	 */
	private IDestination dest;

	/**
	 * @uml.property  name="membershipType"
	 */
	private int membershipType;
	/**
	 * @uml.property  name="membershipGroups"
	 */
	private int membershipGroups;
	/**
	 * @uml.property  name="groupIDs"
	 */
	private int[] groupIDs;

	public MembershipPacket(
		// int destNodeID,
		int membershipType, int membershipGroups, int[] groupIDs) {

		// this.addresseeType = Constants.ADDRESSEE_TYPE_NODE;
		// this.destNodeID = destNodeID;

		dest = new Destination();

		this.membershipType = membershipType;
		this.membershipGroups = membershipGroups;
		this.groupIDs = new int[groupIDs.length];
		System.arraycopy(groupIDs, 0, this.groupIDs, 0, groupIDs.length);
	}

	// public MembershipPacket(
	// //int addresseeType, int destNodeID,
	// int membershipType,
	// int membershipGroups, int[] groupIDs){
	//
	// //this.senderID = senderID;
	// //this.destinationID = destinationID;
	// // this.addresseeType = addresseeType;
	// // this.destNodeID = destNodeID;
	//
	// dest = new Destination();
	//
	// this.membershipType = membershipType;
	// this.membershipGroups = membershipGroups;
	// this.groupIDs = new int[groupIDs.length];
	// System.arraycopy(groupIDs, 0, this.groupIDs, 0, groupIDs.length);
	// }
	//
	// public MembershipPacket(
	// //int addresseeType,
	// // groups destination case
	// //int howManyGroups, int[] isNotGroup, int[] destGroupIDs, int[]
	// associativeRules,
	// int membershipType, int membershipGroups, int[] groupIDs){
	//
	// //this.senderID = senderID;
	// //this.destinationID = destinationID;
	//
	// // this.addresseeType = addresseeType;
	// // this.howManyGroups = howManyGroups;
	// // this.isNotGroup = new int[isNotGroup.length];
	// // System.arraycopy(isNotGroup, 0, this.isNotGroup, 0,
	// isNotGroup.length);
	// // this.destGroupIDs = new int[destGroupIDs.length];
	// // System.arraycopy(destGroupIDs, 0, this.destGroupIDs, 0,
	// destGroupIDs.length);
	// // this.associativeRules = new int[associativeRules.length];
	// // System.arraycopy(associativeRules, 0, this.associativeRules, 0,
	// associativeRules.length);
	//
	// dest = new Destination();
	//
	// this.membershipType = membershipType;
	// this.membershipGroups = membershipGroups;
	// this.groupIDs = new int[groupIDs.length];
	// System.arraycopy(groupIDs, 0, this.groupIDs, 0, groupIDs.length);
	// }

	public MembershipPacket() {
		dest = new Destination();
		groupIDs = new int[0];
		this.membershipType = DEFAUL_MEMBERSHIP;
		this.membershipGroups = 0;
	}

	/**
	 * @param st
	 * @throws InvalidPacketParametersException
	 * @uml.property  name="membershipType"
	 */
	// the same method is in the ConfigurationPacket Class.... it should be in a
	// bmf.common Header Class
	// public void setDestinationGroups(String groups) throws
	// InvalidPacketParametersException{
	// //WE WAIT FOR A STRING LIKE: "10|11&12"
	//
	// StringTokenizer st = new StringTokenizer(groups, "&|", false);
	// this.howManyGroups = st.countTokens();
	// if(this.howManyGroups == 0) throw new
	// InvalidPacketParametersException("No Groups in the String!");
	// if(this.howManyGroups > 4) throw new
	// InvalidPacketParametersException("Too many Groups in the String!");
	// this.isNotGroup = new int[howManyGroups];
	// this.destGroupIDs = new int[howManyGroups];
	// this.associativeRules = new int[howManyGroups-1];
	//
	// st = new StringTokenizer(groups, "!&|", true);
	// try {
	// int countPos = 0;
	// while (st.hasMoreTokens()) {
	// String temp = st.nextToken();
	// if(temp.equalsIgnoreCase("!")){
	// this.isNotGroup[countPos] = Constants.IS_NOT_FIELD_TRUE;
	// temp = st.nextToken();
	// }
	// else this.isNotGroup[countPos] = Constants.IS_NOT_FIELD_FALSE;
	//
	// this.destGroupIDs[countPos] = Integer.parseInt(temp);
	//
	// if(st.hasMoreTokens()){
	// temp = st.nextToken();
	// if(temp.equalsIgnoreCase("&"))
	// this.associativeRules[countPos] = Constants.ASSOCIATIVE_RULE_AND;
	// else this.associativeRules[countPos] = Constants.ASSOCIATIVE_RULE_OR;
	// }
	// countPos++;
	// }
	// } catch (Exception e) {
	// throw new
	// InvalidPacketParametersException("Invalid Destination Groups String");
	// }
	// }

	// public int getDestinationID() {
	// return destinationID;
	// }
	//
	// public void setDestinationID(int destinationID) {
	// this.destinationID = destinationID;
	// }

	public int getMembershipType() {
		return membershipType;
	}

	/**
	 * @param membershipType
	 * @uml.property  name="membershipType"
	 */
	public void setMembershipType(int membershipType) {
		this.membershipType = membershipType;
	}

	/**
	 * @return
	 * @uml.property  name="membershipGroups"
	 */
	public int getMembershipGroups() {
		return membershipGroups;
	}

	public void setHowManyGroups(int howManyGroups) {
		this.membershipGroups = howManyGroups;
	}

	/**
	 * @return
	 * @uml.property  name="groupIDs"
	 */
	public int[] getGroupIDs() {
		return groupIDs;
	}

	public void setMembershipGroups(String g) {
		StringTokenizer st = new StringTokenizer(g, "&| -;,.:", false);
		this.membershipGroups = st.countTokens();
		this.groupIDs = new int[membershipGroups];

		int counterGroup = 0;
		while (st.hasMoreTokens()) {
			this.groupIDs[counterGroup++] = Integer.parseInt(st.nextToken());
		}

	}

	/**
	 * @param groupIDs
	 * @uml.property  name="groupIDs"
	 */
	public void setGroupIDs(int[] groupIDs) {
		this.groupIDs = groupIDs;
		this.membershipGroups = groupIDs.length;
	}

	public void setGroupID(int groupID) {
		this.groupIDs = new int[1];
		this.groupIDs[0] = groupID;
		this.membershipGroups = this.groupIDs.length;
	}

	public void addGroupID(int gID) {
		int temp[] = new int[this.groupIDs.length];
		System.arraycopy(this.groupIDs, 0, temp, 0, this.groupIDs.length);
		this.groupIDs = new int[this.groupIDs.length + 1];
		System.arraycopy(temp, 0, this.groupIDs, 0, temp.length);
		this.groupIDs[this.groupIDs.length - 1] = gID;
		membershipGroups = this.groupIDs.length;
	}

	/**
	 * @return
	 * @uml.property  name="pACKET_TYPE"
	 */
	public int getPACKET_TYPE() {
		return PACKET_TYPE;
	}

	/**
	 * @param membershipGroups
	 * @uml.property  name="membershipGroups"
	 */
	public void setMembershipGroups(int membershipGroups) {
		this.membershipGroups = membershipGroups;
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
	public void setDestinationGroups(String groups) throws InvalidPacketParametersException {
		dest.setDestinationGroups(groups);
	}

}
