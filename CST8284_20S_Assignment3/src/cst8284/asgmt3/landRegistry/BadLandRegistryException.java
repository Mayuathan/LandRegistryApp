package cst8284.asgmt3.landRegistry;

/**
 * The BadLandRegistryException is an exception handler class for Land Registry
 * Application. it is extend from RuntimeException class/
 * 
 * 
 * @author Mayurathan Vigneswaran
 * @version Assignment3
 * @since July 29, 2020
 * 
 * 
 */
public class BadLandRegistryException extends RuntimeException {
	// serialVersionUID
	private static final long serialVersionUID = 1L;
	// String variable header
	private String header;

	/**
	 * This is an empty constructor
	 * 
	 */
	public BadLandRegistryException() {
		this("Please try again", "Bad land registry data entered");
	}

	/**
	 * This is an argument constructor for BadLandRegistryException
	 * 
	 * @param header  This is the first parameter to BadLandRegistryException
	 *                constructor
	 * @param message This is the second parameter to BadLandRegistryException
	 *                constructor
	 * 
	 */
	public BadLandRegistryException(String header, String message) {
		super(message);
		this.setHeader(header);
	}

	/**
	 * This is the getHeader method for BadLandRegistryException
	 * 
	 * @return header return the header message for the exception
	 */
	public String getHeader() {
		return header;
	}

	/**
	 * This is the setHeader method for BadLandRegistryException
	 * 
	 * @param header set the header message for the exception
	 */
	public void setHeader(String header) {
		this.header = header;
	}
}
