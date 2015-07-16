

package ie.ucd.clarity.bmf.network.manager;


import java.util.Vector;

/**
 * @author  Antonio
 */
public class BMFGroup implements IBMFGroup {
    
	/**
	 * @uml.property  name="name"
	 */
	private String name;
    private int ID;
    /**
	 * @uml.property  name="nodes"
	 */
    private Vector<IBMFNode> nodes;

    public BMFGroup(int ID, String name){
        this.name = name;
        this.ID = ID;
        nodes = new Vector<IBMFNode>();
    }

    public boolean addNode(IBMFNode node){
        if(nodes.contains(node)) 
        	return false;
        nodes.addElement(node);
        node.addGroup(this);
        return true;
    }

    /**
	 * @return
	 * @uml.property  name="nodes"
	 */
    public Vector<IBMFNode> getNodes(){
        return nodes;
    }

    public boolean deleteNode(IBMFNode node){
        if(!nodes.contains(node)) 
        	return false;
        nodes.removeElement(node);
        node.deleteGroup(this);
        return true;
    }

    /**
	 * @return
	 * @uml.property  name="name"
	 */
    public String getName(){
        return this.name;
    }
    
    /**
	 * @param s
	 * @uml.property  name="name"
	 */
    public void setName(String s){
        this.name=s;
    }

    public int getGroupID(){
        return ID;
    }

    @Override
    public String toString(){
        return name;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof IBMFGroup)) return false;
        if (((IBMFGroup) o).getGroupID() == this.ID || ((IBMFGroup) o).getName().equals(this.getName())) return true;
        return false;
    }
}
