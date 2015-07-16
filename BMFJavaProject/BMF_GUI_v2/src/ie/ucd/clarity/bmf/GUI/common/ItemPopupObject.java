/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ie.ucd.clarity.bmf.GUI.common;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author  Andrea
 */
public class ItemPopupObject {

    /**
	 * @uml.property  name="name"
	 */
    private String name;
    /**
	 * @uml.property  name="object"
	 * @uml.associationEnd  
	 */
    private BMFObjectGUI object;
    /**
	 * @uml.property  name="node"
	 */
    private DefaultMutableTreeNode node;

    public ItemPopupObject(String name, BMFObjectGUI obj, DefaultMutableTreeNode node) {
        this.name = name;
        this.object = obj;
        this.node=node;
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

    /**
	 * @return
	 * @uml.property  name="object"
	 */
    public BMFObjectGUI getObject() {
        return object;
    }

    /**
	 * @param object
	 * @uml.property  name="object"
	 */
    public void setObject(BMFObjectGUI object) {
        this.object = object;
    }

    /**
	 * @return
	 * @uml.property  name="node"
	 */
    public DefaultMutableTreeNode getNode() {
        return node;
    }

    /**
	 * @param node
	 * @uml.property  name="node"
	 */
    public void setNode(DefaultMutableTreeNode node) {
        this.node = node;
    }



}
