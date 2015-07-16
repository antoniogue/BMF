/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ie.ucd.clarity.bmf.network.manager.nodesAndGroups;

import ie.ucd.clarity.bmf.common.InvalidPacketParametersException;
import ie.ucd.clarity.bmf.communication.IMembershipPacket;
import ie.ucd.clarity.bmf.network.manager.IBMFGroup;
import ie.ucd.clarity.bmf.network.manager.INetworkManager;
import ie.ucd.clarity.bmf.network.manager.nodesAndGroupsManager.IBMFNodesAndGroupsManager;
import ie.ucd.clarity.bmf.network.manager.nodesAndGroupsManager.WrongDestinationFormulaException;

import java.io.IOException;
import java.util.Vector;

/**
 * @author  Antonio
 */
class GroupUpdaterThread extends Thread{

    Vector<IMembershipPacket> membs;
    /**
	 * @uml.property  name="group"
	 * @uml.associationEnd  
	 */
    IBMFGroup group;
	/**
	 * @uml.property  name="iNetworkManager"
	 * @uml.associationEnd  
	 */
	private INetworkManager iNetworkManager;
	/**
	 * @uml.property  name="bmfNodesAndGroupsManager"
	 * @uml.associationEnd  
	 */
	private IBMFNodesAndGroupsManager bmfNodesAndGroupsManager;

    public GroupUpdaterThread(IBMFGroup group, Vector<IMembershipPacket> membs, INetworkManager iNetworkManager, IBMFNodesAndGroupsManager bmfNodesAndGroupsManager) {
        this.membs = membs;
        this.group = group;
        this.iNetworkManager = iNetworkManager;
        this.bmfNodesAndGroupsManager = bmfNodesAndGroupsManager;
        
    }

    @Override
    public void run(){
        for(int i=0; i<membs.size(); i++){
            try {
				this.iNetworkManager.sendPacket(membs.elementAt(i));
			} catch (IOException e) {
				
			}

            IBMFGroup bmfg[] = new IBMFGroup[1];
            bmfg[0] = group;
            try {
				this.bmfNodesAndGroupsManager.updateNodeMemberships(true, ""+membs.elementAt(i).getDestination().getDestNodeID(),
				    membs.elementAt(i).getMembershipType(), bmfg);
			} catch (InvalidPacketParametersException e) {
				e.printStackTrace();
			} catch (WrongDestinationFormulaException e) {
				e.printStackTrace();
			}

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
