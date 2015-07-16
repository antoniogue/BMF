/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ie.ucd.clarity.bmf.GUI.notification;

/**
 *
 * @author Andrea
 */
public class ToPrint implements Notification{	
	private String s;
	public ToPrint(String s){		
		this.s=s;
	}
	public String getString(){
		return s;		
	}
}
