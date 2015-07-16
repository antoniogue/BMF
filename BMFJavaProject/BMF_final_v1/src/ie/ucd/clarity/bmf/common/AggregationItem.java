package ie.ucd.clarity.bmf.common;

/**
 * @author  Matthew
 */
public class AggregationItem {
	
	private int idSender;
	/**
	 * @uml.property  name="value"
	 */
	private long value;
	
	public AggregationItem(int idRequest, long value) {
		this.idSender = idRequest;
		this.value = value;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		long result = 1;
		result = prime * result + idSender;
		result = prime * result + value;
		return (int)result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AggregationItem other = (AggregationItem) obj;
		if (idSender == other.idSender)
			return true;
		return false;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Item [idSender=" + idSender + ", value=" + value + "]";
	}
	/**
	 * @return the idRequest
	 */
	public int getIdRequest() {
		return idSender;
	}
	/**
	 * @param idRequest the idRequest to set
	 */
	public void setIdRequest(int idRequest) {
		this.idSender = idRequest;
	}
	/**
	 * @return  the value
	 * @uml.property  name="value"
	 */
	public long getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
	

}
