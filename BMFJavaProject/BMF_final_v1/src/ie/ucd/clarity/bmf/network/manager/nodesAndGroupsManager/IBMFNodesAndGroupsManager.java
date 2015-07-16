package ie.ucd.clarity.bmf.network.manager.nodesAndGroupsManager;

import java.util.Vector;

import ie.ucd.clarity.bmf.common.InvalidPacketParametersException;
import ie.ucd.clarity.bmf.network.manager.IBMFGroup;
import ie.ucd.clarity.bmf.network.manager.IBMFNode;

public interface IBMFNodesAndGroupsManager {
	
	public boolean nodeIsUnknown(IBMFNode newNode);
	
	public IBMFNode getNodeByID(int id);
	
	public void addNode(IBMFNode newNode);
	
	public Vector<IBMFNode> getNodesFromDestinationFormula(boolean isDestinationNode, String dest) throws WrongDestinationFormulaException, InvalidPacketParametersException;

	public Vector<IBMFNode> groupsAND(Vector<IBMFNode> nodes, Vector<IBMFNode> nodes2);
	
	public Vector<IBMFNode> groupsOR(Vector<IBMFNode> nodes, Vector<IBMFNode> nodes2);

	public IBMFGroup addGroup(String name) throws TooManyGroupsCreatedException;
	
	public void removeGroup(IBMFGroup group);
	
	public void updateNodeMemberships(boolean isDestinationNode, String dest,
            int membershipType, IBMFGroup[] newGroups) throws InvalidPacketParametersException, WrongDestinationFormulaException;
	
	public Vector<IBMFGroup> getGroupsByIDs(int[] destGroupIDs);

	Vector<IBMFGroup> getGroups();

	Vector<IBMFNode> getNodes();
	
	
}
