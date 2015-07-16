package ie.ucd.clarity.bmf.communication;

import ie.ucd.clarity.bmf.common.InvalidPacketParametersException;


public interface IDestination {

    public int getAddresseeType();

    public int[] getAssociativeRules();

    public int[] getDestGroupIDs();

    public int getDestNodeID();

    public int getHowManyGroups();

    public int[] getIsNotGroup();

    public boolean isDestinationProperlySet();

    public void setDestinationBroadcast();

    public void setDestinationGroups(String groups) throws InvalidPacketParametersException;

    public void setDestinationNode(int destNodeID);

	public String toString();
}
