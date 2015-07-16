package ie.ucd.clarity.bmf.GUI.common.formulas;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Expression implements Iterable<Expression> {

    public abstract void accept(Visitor v);

    public Expression left() {
        throw new UnsupportedOperationException();
    }

    public Expression right() {
        throw new UnsupportedOperationException();
    }

    public void setLeft(Expression e) {
        throw new UnsupportedOperationException();
    }

    public void setRight(Expression e) {
        throw new UnsupportedOperationException();
    }

    protected Iterator<Expression> it;

    public Iterator<Expression> iterator() {
        return it;
    }

    public void setIterator(Iterator it) {
        this.it = it;
    }

    public abstract String returnValue();
} //Espressione




