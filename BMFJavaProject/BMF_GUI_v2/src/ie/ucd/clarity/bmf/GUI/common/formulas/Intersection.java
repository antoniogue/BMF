package ie.ucd.clarity.bmf.GUI.common.formulas;

public class Intersection  // Intersezione insiemistica corrisponde && in Java
        extends Operator {

	public String returnValue(){
		return toString();		
	}

    @Override
    public String toString() {
        return left()+"&"+right();
    }

    ;
} //And