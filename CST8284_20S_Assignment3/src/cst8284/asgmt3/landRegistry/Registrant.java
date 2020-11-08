
package cst8284.asgmt3.landRegistry;

/**
 * Assignment 3 starter code provided by Prof. D. Houtman
 * This code is for one-time use only during the Summer 2020 semester.
 * (c) D. Houtman.  All rights reserved
 *
 * @author Mayurathan Vigneshwaran
 * @version Assignment 3
 * @since  July 29, 2020 *
 */
public class Registrant {

	private static final int REGNUM_START = 1000;
	private static int currentRegNum = REGNUM_START;
	private final int REGNUM = currentRegNum;

	private String firstName, lastName;

	public Registrant() {
		this("unknown unknown");
	}

	public Registrant(String firstLastName) {
		incrToNextRegNum();
		setFirstName(firstLastName.split(" ")[0]);
		setLastName(firstLastName.split(" ")[1]);
	}

	/**
	 * The getter for the RegNum
	 * @return REGNUM
	 */
	public int getRegNum() {
		return REGNUM;
	}

	/**
	 * The static method incrToNextRegNum that incrementing currentRegNum
	 */
	private static void incrToNextRegNum() {
		currentRegNum++;
	}

	/**
	 * The getter for the FirstName
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * The setter for the firstName
	 * @param firstName the firstname of the person
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * The getter for the lastName
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * The setter for the lastName
	 * @param lastName last name of the person
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * The equal method that having obj parameter
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof Registrant))
			return false;
		Registrant reg = (Registrant) obj;
		return ((reg.getFirstName().equals(this.getFirstName())) && (reg.getLastName().equals(this.getLastName()))
				&& (reg.getRegNum() == (this.getRegNum())));
	}

	/**
	 * toString method
	 * @return String representation of the Registrant object
	 * */
	public String toString() {
		return "Name: " + getFirstName() + " " + getLastName() + "\n" + "Registration Number: #" + getRegNum();
	}

}
