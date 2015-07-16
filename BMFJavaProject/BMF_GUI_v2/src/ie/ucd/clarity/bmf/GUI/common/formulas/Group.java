/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ie.ucd.clarity.bmf.GUI.common.formulas;

import ie.ucd.clarity.bmf.GUI.common.BMFNodeGUI;

import java.util.ArrayList;

/**
 * @author  Andrea
 */
public class Group extends Expression{

    /**
	 * @uml.property  name="iD"
	 */
    int ID;
    /**
	 * @uml.property  name="name"
	 */
    String name;
    /**
	 * @uml.property  name="nodes"
	 */
    ArrayList<BMFNodeGUI> nodes;

    public Group(int id, String name, ArrayList<BMFNodeGUI> nodes){
        this.ID=id;
        this.name=name;
        this.nodes=nodes;
    }

     @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public String returnValue() {
        // TODO Auto-generated method stub
        return getID() + "";
    }

    /**
	 * @return
	 * @uml.property  name="iD"
	 */
    public int getID() {
        return ID;
    }
    
    /**
	 * @return
	 * @uml.property  name="name"
	 */
    public String getName(){
    	return name;
    }

    /**
	 * @return
	 * @uml.property  name="nodes"
	 */
    public ArrayList<BMFNodeGUI> getNodes() {
        return nodes;
    }

    @Override
    public String toString() {
        return getID()+"";
    }



}
