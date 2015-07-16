package ie.ucd.clarity.bmf.network.manager;

import java.util.Vector;

/**
 * @author   Matthew
 */
public interface IBMFGroup {
    
	public boolean addNode(IBMFNode node);

    public Vector<IBMFNode> getNodes();
    
    /**
     * This method is to delete a node from the group composition.
     * The modification is propagated to the corresponding BMFNode Object
     * @param node
     * @return
     */
    public boolean deleteNode(IBMFNode node);

    /**
	 * @return
	 * @uml.property  name="name"
	 */
    public String getName();
    
    /**
	 * @param  s
	 * @uml.property  name="name"
	 */
    public void setName(String s);

    public int getGroupID();
    
}
