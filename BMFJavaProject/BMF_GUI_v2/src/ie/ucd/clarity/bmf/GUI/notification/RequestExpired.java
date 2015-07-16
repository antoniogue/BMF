/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ie.ucd.clarity.bmf.GUI.notification;


/**
 *
 * @author Andrea
 */
public class RequestExpired implements Notification{

	int id;
	
	public RequestExpired(int requestID) {
		this.id=requestID;
	}

	public int getID(){
		return id;
	}
    
}
