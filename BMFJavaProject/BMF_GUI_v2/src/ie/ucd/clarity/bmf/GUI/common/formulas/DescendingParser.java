package ie.ucd.clarity.bmf.GUI.common.formulas;

import ie.ucd.clarity.bmf.GUI.common.BMFGroupGUI;


/**
 * @author  Matthew
 */
public class DescendingParser {

	/**
	 * @uml.property  name="root"
	 * @uml.associationEnd  
	 */
	Expression root;
    Object symbol = null;
    String error = null;
    /**
	 * @uml.property  name="result"
	 * @uml.associationEnd  
	 */
    Expression result = null;
    /**
	 * @uml.property  name="manager"
	 * @uml.associationEnd  
	 */
    Manager manager;

    public DescendingParser(Manager manager) {
        this.manager = manager;
        term();
        result = getResult();
    }

    void term() {
        factor();
        while ((symbol instanceof Intersection) || (symbol instanceof Union)) {
            if (symbol instanceof Union) {
                Union or = (Union) symbol;// genera nodo Or
                or.setLeft(root);//legagli root come nodo sinistro
                factor();
                or.setRight(root);//lega root come lato destro di Or
                root = or;//rendi root il nodo Or
            }else if (symbol instanceof Intersection) {
                Intersection and = (Intersection) symbol;
                and.setLeft(root);//legagli root come nodo sinistro
                factor();
                and.setRight(root);//lega root come lato destro di And e
                root = and;//rendi root il nodo And
            }
        }
    } //termine

    void factor() {
        symbol = nextSymbol();
        if (symbol instanceof Not) {
            Not not = (Not) symbol; //crea nodo Not
            factor();
            not.setLeft(root);    //lega root corrente come figlio sinistro di Not
            root = not;    //rendi root il nodo Not
        } else if (symbol instanceof String) {
            if (((String) symbol).equals("(")) {
                term();
                nextSymbol(); //salta ()
            }
        } else if (symbol instanceof Group) {
        	Group g = (Group) symbol; //crea costante e rendila root
            root = g;
            symbol = nextSymbol();
        }
    } //fattore

    public Object nextSymbol() {
        return manager.nextSymbol();
    }

    /**
	 * @return
	 * @uml.property  name="result"
	 */
    public Expression getResult() {
        return root;
    }
}
