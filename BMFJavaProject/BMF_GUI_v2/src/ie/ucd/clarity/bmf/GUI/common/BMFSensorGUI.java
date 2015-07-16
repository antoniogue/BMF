/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ie.ucd.clarity.bmf.GUI.common;

import ie.ucd.clarity.bmf.common.CodeConversion;

import javax.swing.tree.TreePath;

/**
 * @author  Andrea
 */
public class BMFSensorGUI implements BMFObjectGUI{

    /**
	 * @uml.property  name="name"
	 */
    private String name;
    /**
	 * @uml.property  name="sensorCode"
	 */
    private int sensorCode;
    private TreePath path;



    public BMFSensorGUI(int sensorCode) {
    	
    	this.sensorCode=sensorCode;
        this.name = CodeConversion.getSensorName(sensorCode);
        path=null;
    }

    /**
	 * @return
	 * @uml.property  name="name"
	 */
    public String getName() {
        return name;
    }

    /**
	 * @param name
	 * @uml.property  name="name"
	 */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        return name.equals(obj);
    }

    @Override
    public String toString() {
        return name;
    }
    
    

    /**
	 * @return  the sensorCode
	 * @uml.property  name="sensorCode"
	 */
	public int getSensorCode() {
		return sensorCode;
	}

	/**
	 * @param path
	 * @return
	 * @uml.property  name="path"
	 */
	public BMFObjectGUI setPath(TreePath path) {
        this.path = path;
        return this;
    }

}
