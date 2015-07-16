/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ie.ucd.clarity.bmf.GUI.common;

import javax.swing.tree.TreePath;

/**
 * @author  Andrea
 */
public class BMFFunctionsGUI implements BMFObjectGUI{

    /**
	 * @uml.property  name="name"
	 */
    private String name;
    
    public BMFFunctionsGUI(String name) {
        this.name = name;
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

    public BMFObjectGUI setPath(TreePath path) {
        return null;
    }

}
