/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simpleConfigurationPanel;

import ie.ucd.clarity.bmf.network.manager.BMFGroup;
import ie.ucd.clarity.bmf.communication.MembershipPacket;

import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Antonio
 */
class GroupUpdaterThread extends Thread{

    Vector<MembershipPacket> membs;
    GUIFrame dad;
    BMFGroup group;

    public GroupUpdaterThread(BMFGroup group, Vector<MembershipPacket> membs, GUIFrame dad) {
        this.dad = dad;
        this.membs = membs;
        this.group = group;
    }

    @Override
    public void run(){
        for(int i=0; i<membs.size(); i++){
            try {
				dad.manager.sendPacket(membs.elementAt(i));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            BMFGroup bmfg[] = new BMFGroup[1];
            bmfg[0] = group;
            dad.updateNodeMemberships(true, ""+membs.elementAt(i).getDestination().getDestNodeID(),
                membs.elementAt(i).getMembershipType(), bmfg);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

}
