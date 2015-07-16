package ie.ucd.clarity.bmf.GUI.common.formulas;

public class Union extends Operator { //Unione Insiemistica corrisponde || in java

    @Override
    public String toString() {
        return left()+"|"+right();
    }
    public String returnValue(){
		return toString();		
	}

} //Or