package ie.ucd.clarity.bmf.network.manager.nodesAndGroups;

import ie.ucd.clarity.bmf.common.Constants;
import ie.ucd.clarity.bmf.common.InvalidPacketParametersException;

import ie.ucd.clarity.bmf.communication.IDestination;
import ie.ucd.clarity.bmf.communication.IMembershipPacket;
import ie.ucd.clarity.bmf.communication.IPacketBuilder;
import ie.ucd.clarity.bmf.network.manager.IBMFGroup;
import ie.ucd.clarity.bmf.network.manager.IBMFNode;
import ie.ucd.clarity.bmf.network.manager.INetworkManager;
import ie.ucd.clarity.bmf.network.manager.nodesAndGroupsManager.IBMFNodesAndGroupsManager;
import ie.ucd.clarity.bmf.network.manager.nodesAndGroupsManager.TooManyGroupsCreatedException;
import ie.ucd.clarity.bmf.network.manager.nodesAndGroupsManager.WrongDestinationFormulaException;

import java.util.Vector;

/**
 * @author  Matthew
 */
public class BMFNodesAndGroupsManager implements IBMFNodesAndGroupsManager {

	private Vector<IBMFNode> iBMFNodesVector = new Vector<IBMFNode>();

	private Vector<IBMFGroup> iBMFGroupsVector = new Vector<IBMFGroup>();

	/**
	 * @uml.property  name="iPacketBuilder"
	 * @uml.associationEnd  
	 */
	private IPacketBuilder iPacketBuilder;
	/**
	 * @uml.property  name="iNetworkManager"
	 * @uml.associationEnd  
	 */
	private INetworkManager iNetworkManager;

	public synchronized void setIPacketBuilder(IPacketBuilder packetBuilder) {

		System.out
				.println("IPacketBuilder was set in the BMFNodesAndGroupsManager. Thank you DS!");
		this.iPacketBuilder = packetBuilder;
	}

	public synchronized void unsetIPacketBuilder(IPacketBuilder packetBuilder) {
		System.out
				.println("IPacketBuilder was unset in the BMFNodesAndGroupsManager. Why did you do this to me?");
		if (this.iPacketBuilder == packetBuilder) {
			this.iPacketBuilder = null;
		}
	}

	public synchronized void setINetworkManager(INetworkManager networkManager) {
		System.out
				.println("INetworkManager was set in the BMFNodesAndGroupsManager. Thank you DS!");
		this.iNetworkManager = networkManager;
	}

	public synchronized void unsetINetworkManager(INetworkManager requestManager) {
		System.out
				.println("INetworkManager was unset in the BMFNodesAndGroupsManager. Why did you do this to me?");
		if (this.iNetworkManager == requestManager) {
			this.iNetworkManager = null;
		}
	}

	@Override
	public Vector<IBMFNode> getNodes() {
		return iBMFNodesVector;
	}

	@Override
	public Vector<IBMFGroup> getGroups() {
		return iBMFGroupsVector;
	}

	@Override
	public boolean nodeIsUnknown(IBMFNode newNode) {
		return this.iBMFNodesVector.contains(newNode);
	}

	@Override
	public void addNode(IBMFNode newNode) {
		this.iBMFNodesVector.add(newNode);

	}

	@Override
	public IBMFNode getNodeByID(int id) {
		for (int i = 0; i < this.iBMFNodesVector.size(); i++) {
			if (iBMFNodesVector.elementAt(i).getNodeID() == id)
				return iBMFNodesVector.elementAt(i);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Vector<IBMFNode> getNodesFromDestinationFormula(
			boolean isDestinationNode, String dest)
			throws WrongDestinationFormulaException,
			InvalidPacketParametersException {

		Vector<IBMFNode> nodes = new Vector<IBMFNode>();

		if (isDestinationNode) {
			if (Integer.parseInt(dest) == Constants.ADDRESSEE_BROADCAST) {
				nodes = (Vector<IBMFNode>) this.iBMFNodesVector.clone();
			} else {
				IBMFNode node = getNodeByID(Integer.parseInt(dest));
				nodes.addElement(node);
				return nodes;
			}
		} else { // group/group composition destination
			IDestination destination = this.iPacketBuilder.getIDestination();
			destination.setDestinationGroups(dest);
			int[] destGroupIDs = destination.getDestGroupIDs();
			int[] isNotGroup = destination.getIsNotGroup();
			boolean[] isNotGroupBool = new boolean[isNotGroup.length];
			int[] associativeRules = destination.getAssociativeRules();

			for (int i = 0; i < isNotGroup.length; i++) {
				if (isNotGroup[i] == Constants.IS_NOT_FIELD_FALSE)
					isNotGroupBool[i] = false;
				else
					isNotGroupBool[i] = true;
			}

			Vector<IBMFGroup> destGroups = getGroupsByIDs(destGroupIDs);
			if (destGroups.size() != destGroupIDs.length)
				throw new WrongDestinationFormulaException();

			for (int i = 0; i < destGroups.size(); i++) {

				Vector<IBMFNode> nodes2 = (Vector<IBMFNode>) this.iBMFNodesVector
						.clone();
				Vector<IBMFNode> nodes3 = new Vector();

				for (int j = 0; j < nodes2.size(); j++) {
					boolean belongs = nodes2.elementAt(j).isBelongingToGroup(
							destGroups.elementAt(i));

					if (((belongs && !isNotGroupBool[i]) || (!belongs && isNotGroupBool[i]))) {
						nodes3.addElement(nodes2.elementAt(j));
					}
				}

				if (i > 0) {
					if (associativeRules[i - 1] == Constants.ASSOCIATIVE_RULE_AND)
						nodes = groupsAND(nodes, nodes3);
					if (associativeRules[i - 1] == Constants.ASSOCIATIVE_RULE_OR)
						nodes = groupsOR(nodes, nodes3);
				} else {
					nodes = (Vector<IBMFNode>) nodes3.clone();
				}
			}

		}

		return nodes;
	}

	private int getFirstBMFGroupIDAvailable() {
		if (this.iBMFGroupsVector.size() >= Constants.GROUP_ID_MAX_ID_AVAILABLE) {
			return Constants.GROUP_ID_MAX_ID_AVAILABLE + 1;
		}

		// naive algoritm to find the first ID available
		int[] free = new int[Constants.GROUP_ID_MAX_ID_AVAILABLE]; // free[i]
																	// will be 1
																	// if the id
																	// 1 is used
		for (int i = 0; i < this.iBMFGroupsVector.size(); i++)
			free[iBMFGroupsVector.elementAt(i).getGroupID() - 1] = 1;
		for (int i = 0; i < Constants.GROUP_ID_MAX_ID_AVAILABLE; i++)
			if (free[i] != 1)
				return i + 1;

		return Constants.GROUP_ID_MAX_ID_AVAILABLE + 1;
	}

	@Override
	public Vector<IBMFGroup> getGroupsByIDs(int[] destGroupIDs) {
		Vector<IBMFGroup> returnVector = new Vector<IBMFGroup>();
		for (int i = 0; i < destGroupIDs.length; i++) {
			for (int j = 0; j < this.iBMFGroupsVector.size(); j++) {
				if (iBMFGroupsVector.elementAt(j).getGroupID() == destGroupIDs[i]) {
					returnVector.addElement(iBMFGroupsVector.elementAt(j));
					break;
				}
			}
		}
		return returnVector;
	}

	@Override
	public Vector<IBMFNode> groupsAND(Vector<IBMFNode> nodes,
			Vector<IBMFNode> nodes2) {
		Vector<IBMFNode> returnVector = new Vector<IBMFNode>();

		for (int i = 0; i < nodes.size(); i++) {
			for (int j = 0; j < nodes2.size(); j++) {
				if (nodes.elementAt(i).equals(nodes2.elementAt(j))) {
					returnVector.addElement(nodes.elementAt(i));
					break;
				}
			}
		}
		return returnVector;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Vector<IBMFNode> groupsOR(Vector<IBMFNode> nodes,
			Vector<IBMFNode> nodes2) {
		Vector<IBMFNode> returnVector = (Vector<IBMFNode>) nodes.clone();

		for (int i = 0; i < nodes2.size(); i++) {
			boolean found = false;

			for (int j = 0; j < nodes.size(); j++) {
				if (nodes.elementAt(j).equals(nodes2.elementAt(i))) {
					found = true;
					break;
				}
			}
			if (!found)
				returnVector.addElement(nodes2.elementAt(i));
		}
		return returnVector;
	}

	@Override
	public IBMFGroup addGroup(String name) throws TooManyGroupsCreatedException {
		int newID = getFirstBMFGroupIDAvailable();
		if (newID != Constants.GROUP_ID_MAX_ID_AVAILABLE + 1) {
			IBMFGroup newGroup = this.iNetworkManager.getIBMFGroup(newID, name);
			if (!iBMFGroupsVector.contains(newGroup)) {
				iBMFGroupsVector.addElement(newGroup);
				System.out.println("ID: " + newGroup.getGroupID() + "   Name: "
						+ newGroup.getName() + "  CREATED!");
			}
			return newGroup;
		} else
			throw new TooManyGroupsCreatedException();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void removeGroup(IBMFGroup group) {
		for (int i = 0; i < this.iBMFGroupsVector.size(); i++) {
			if (iBMFGroupsVector.elementAt(i).equals(group)) {
				Vector<IBMFNode> nodes = (Vector<IBMFNode>) iBMFGroupsVector
						.elementAt(i).getNodes().clone();
				if (nodes.size() > 0) {
					Vector<IMembershipPacket> membs = new Vector();
					for (int j = 0; j < nodes.size(); j++) {
						IMembershipPacket mp = iPacketBuilder
								.getIMembershipPacket();
						mp.setDestinationNode(nodes.elementAt(j).getNodeID());
						mp.setMembershipType(Constants.MEMBERSHIP_TYPE_DELETE);
						mp.setGroupID(group.getGroupID());
						membs.addElement(mp);
					}
					new GroupUpdaterThread(group, membs, this.iNetworkManager,
							this).start();
				}
				iBMFGroupsVector.removeElementAt(i);
				break;
			}
		}
	}

	@Override
	public void updateNodeMemberships(boolean isDestinationNode, String dest,
			int membershipType, IBMFGroup[] newGroups)
			throws InvalidPacketParametersException,
			WrongDestinationFormulaException {
		Vector<IBMFNode> destinationNodes = getNodesFromDestinationFormula(
				isDestinationNode, dest);

		if (destinationNodes != null) {
			for (int i = 0; i < destinationNodes.size(); i++) {

				if (membershipType == Constants.MEMBERSHIP_TYPE_RESET) {
					destinationNodes.elementAt(i).resetGroups();
				} else if (membershipType == Constants.MEMBERSHIP_TYPE_DELETE) {
					for (int j = 0; j < newGroups.length; j++) {
						destinationNodes.elementAt(i).deleteGroup(newGroups[j]);
					}
				} else if (membershipType == Constants.MEMBERSHIP_TYPE_UPDATE) {
					destinationNodes.elementAt(i).resetGroups();
					for (int j = 0; j < newGroups.length; j++) {
						destinationNodes.elementAt(i).addGroup(newGroups[j]);
					}
				} else if (membershipType == Constants.MEMBERSHIP_TYPE_ADD) {
					for (int j = 0; j < newGroups.length; j++) {
						destinationNodes.elementAt(i).addGroup(newGroups[j]);
					}
				}
			}
		}
	}
}