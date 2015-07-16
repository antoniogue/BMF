/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ie.ucd.clarity.bmf.GUI.notification;

import ie.ucd.clarity.bmf.network.manager.IBMFDataIn;

import java.util.Calendar;

/**
 * @author  Andrea
 */
public class DataIn implements Notification{
	
	/**
	 * @uml.property  name="data"
	 * @uml.associationEnd  
	 */
	IBMFDataIn data;
	/**
	 * @uml.property  name="requestID"
	 */
	int requestID;

    public DataIn(IBMFDataIn data, int requestID) {
    	this.data=data;
    	this.requestID=requestID;
    }

	/**
	 * @return  the data
	 * @uml.property  name="data"
	 */
	public IBMFDataIn getData() {
		return data;
	}

	/**
	 * @return  the requestID
	 * @uml.property  name="requestID"
	 */
	public int getRequestID() {
		return requestID;
	}
	
}
