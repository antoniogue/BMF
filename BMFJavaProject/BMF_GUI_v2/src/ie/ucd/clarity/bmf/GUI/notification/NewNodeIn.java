/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ie.ucd.clarity.bmf.GUI.notification;

import ie.ucd.clarity.bmf.network.manager.IBMFNode;

import java.util.Calendar;

/**
 * @author  Andrea
 */
public class NewNodeIn implements Notification{
	
	/**
	 * @uml.property  name="newNode"
	 * @uml.associationEnd  
	 */
	IBMFNode newNode;
	/**
	 * @uml.property  name="time"
	 */
	Calendar time;

    public NewNodeIn(IBMFNode newNode, Calendar time) {
    	this.newNode=newNode;
    	this.time=time;
    }

	/**
	 * @return  the newNode
	 * @uml.property  name="newNode"
	 */
	public IBMFNode getNewNode() {
		return newNode;
	}

	/**
	 * @return  the time
	 * @uml.property  name="time"
	 */
	public Calendar getTime() {
		return time;
	}
    

}
