/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.ucd.clarity.bmf.GUI.common.formulas;

import java.util.ArrayList;

/**
 *
 * @author Andrea
 */
public class Manager {

    private ArrayList<Object> input=new ArrayList<Object>();
    private ArrayList<String> toprint=new ArrayList<String>();
    private int index = 0;

    public boolean add(Object o) {
    	if(o instanceof Union)
    		toprint.add("INTERSECTION");
    	else if (o instanceof Intersection)
    		toprint.add("UNION");
    	else if(o instanceof Not)
    		toprint.add("NOT");
    	else if (o instanceof Group)
    		toprint.add(((Group)o).getName());
        return input.add(o);
    }

    public Object nextSymbol() {
        if (index < input.size()) {
            return input.get(index++);
        } else {
            return null;
        }
    }
    public void removeLast(){
        input.remove(input.size()-1);
        toprint.remove(toprint.size()-1);
    }
    public String partial(){
    	String s="";
    	for(int i=0; i<toprint.size(); i++){
    		if(i!=toprint.size()-1)
    		s+=toprint.get(i)+" ";
    		else s+=toprint.get(i);
    		
    	}
    	return s;
    }
}
