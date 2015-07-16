package ie.ucd.clarity.bmf.common;

/**
 * @author  Matthew
 */
public class BMFException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8337979032185287098L;
	/**
	 * @uml.property  name="error"
	 */
	private String error;
	
	public BMFException(String s){
		this.error = s;
	}

	public BMFException(){
		this.error = "ERROR!!!";
	}
	
	/**
	 * @return
	 * @uml.property  name="error"
	 */
	public String getError() {
		return error;
	}
	
	public String toString(){
		return error;
	}

}
