package ie.ucd.clarity.bmf.GUI.common.formulas;

import java.util.Iterator;

/**
 * @author  Matthew
 */
public class Operator
        extends Expression {

    /**
	 * @uml.property  name="left"
	 * @uml.associationEnd  
	 */
    private Expression left;
	/**
	 * @uml.property  name="right"
	 * @uml.associationEnd  
	 */
	private Expression right;
    /**
	 * @uml.property  name="parent"
	 * @uml.associationEnd  
	 */
    private Operator parent;

    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public Iterator<Expression> iterator() {
        return it;
    }

    @Override
    public void setIterator(Iterator it) {
        this.it = it;
    }

    @Override
    public Expression left() {
        return left;
    }

    @Override
    public Expression right() {
        return right;
    }

    /**
	 * @param e
	 * @uml.property  name="left"
	 */
    @Override
    public void setLeft(Expression e) {
        left = e;
    }

    /**
	 * @param e
	 * @uml.property  name="right"
	 */
    @Override
    public void setRight(Expression e) {
        right = e;
    }

    /**
	 * @param e
	 * @uml.property  name="parent"
	 */
    public void setParent(Operator e) {
        parent = e;
    }

    public Operator parent() {
        return parent;
    }

    public String returnValue() {
        return null;
    }

    @Override
    public String toString() {
        return returnValue();
    }

    ;
} //Operatore