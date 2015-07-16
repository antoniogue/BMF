package ie.ucd.clarity.bmf.GUI.common.formulas;

public class Not
        extends Operator {

	public String returnValue(){
		return toString();		
	}

    @Override
    public String toString() {
        return "!"+left();
    }
} //Not
