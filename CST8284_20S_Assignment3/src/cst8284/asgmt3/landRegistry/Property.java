

package cst8284.asgmt3.landRegistry;

/**
 * Assignment 3 starter code provided by Prof. D. Houtman
 * This code is for one-time use only during the Summer 2020 semester.
 * (c) D. Houtman.  All rights reserved
 *
 * @author Mayurathan Vigneswaran
 * @version Assignment 3
 * @since  July 29, 2020 *
 */

public class Property {

	private static final double TAX_RATE_PER_M2 = 12.50;
	private static final int DEFAULT_REGNUM = 999;

	private int xLeft, yTop;
	private int xLength, yWidth;
	private int regNum = DEFAULT_REGNUM;
	private int area = getArea();
	private double taxes = getTaxes();

	/**
	 * No parameter Constructor for the Property Class
	 */
	public Property() {
		this(0, 0, 0, 0);
	}

	/**
	 * Constructor and initializes a Property on position
	 * 
	 * @param xLength
	 * @param yWidth
	 * @param xLeft
	 * @param yTop
	 */
	public Property(int xLength, int yWidth, int xLeft, int yTop) {
		this(xLength, yWidth, xLeft, yTop, DEFAULT_REGNUM);
	}

	/**
	 * Property method takes
	 * 
	 * @param prop
	 * @param regNum
	 */

	public Property(Property prop, int regNum) {
		this(prop.getXLength(), prop.getYWidth(), prop.getXLeft(), prop.getYTop(), regNum);
	}

	/**
	 * 
	 * @param xLength
	 * @param yWidth
	 * @param xLeft
	 * @param yTop
	 * @param regNum
	 */
	public Property(int xLength, int yWidth, int xLeft, int yTop, int regNum) {
		setXLength(xLength);
		setYWidth(yWidth);
		setXLeft(xLeft);
		setYTop(yTop);
		setRegNum(regNum);
	}

	/**
	 * 
	 * @return xLeft
	 */
	public int getXLeft() {
		return xLeft;
	}

	/**
	 * 
	 * @param left x coordination of the property
	 */
	public void setXLeft(int left) {
		this.xLeft = left;
	}

	/**
	 * 
	 * @return 
	 */
	public int getXRight() {
		return getXLeft() + getXLength();
	}

	/**
	 * 
	 * @return
	 */
	public int getYTop() {
		return yTop;
	}

	/*
	 * 
	 */
	public void setYTop(int top) {
		this.yTop = top;
	}

	/**
	 * 
	 * @return
	 */
	public int getYBottom() {
		return getYTop() + getYWidth();
	}

	/**
	 * 
	 * @return
	 */

	public int getYWidth() {
		return yWidth;
	}

	/**
	 * The setter for the width for the property
	 * @param yWidth width of the property
	 */
	public void setYWidth(int yWidth) {
		this.yWidth = yWidth;
	}

	/**
	 * The getter for the length for the property
	 * @return xLength length of the property
	 */
	public int getXLength() {
		return xLength;
	}

	/**
	 * The setter for the length for the property
	 * @param xLength length of the property
	 */

	public void setXLength(int xLength) {
		this.xLength = xLength;
	}

	/**
	 * The getter for the regNum for the property
	 * @return regNum
	 */

	public int getRegNum() {
		return regNum;
	}

	/**
	 * The setter for the regNum for the property
	 * @param regNum register number for the property
	 */

	private void setRegNum(int regNum) {
		this.regNum = regNum;
	}

	/**
	 * The getter for the getArea for the property
	 * @return getXLength * getYWidth
	 */
	public int getArea() {
		return (getXLength() * getYWidth());
	}

	/**
	 * The getter for getting taxes
	 * @return getArea * TAX_RATE_PER_M2
	 */
	public double getTaxes() {
		return getArea() * TAX_RATE_PER_M2;
	}

	/**
	 * toString method for displaying properties 
	 */
	@Override
	public String toString() {
		return "Coordinates: " + getXLeft() + ", " + getYTop() + "\n" + "Length: " + getXLength() + " m  Width: "
				+ getYWidth() + " m\n" + "Registrant #: " + getRegNum() + "\n" + "Area: " + getArea()
				+ " m2\nProperty Taxes : $" + getTaxes();
	}

	/**
	 * The parameterized method for equals that return boolean values
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Property))
			return false;
		Property prop = (Property) obj;
		return prop.getYTop() == this.getYTop() && prop.getXLeft() == this.getXLeft() && hasSameSides(prop);
	}

	/**
	 * The parameterized method that return boolean values
	 * @param prop
	 * @return
	 */
	public boolean hasSameSides(Property prop) {
		return prop.getXLength() == this.getXLength() && prop.getYWidth() == this.getYWidth();
	}

	/**
	 * The overlaps method with the parameterized that having property prop
	 * @param prop
	 * @return
	 */
	public boolean overlaps(Property prop) {
		return prop.getXRight() > this.getXLeft() && prop.getXLeft() < this.getXRight()
				&& prop.getYTop() < this.getYBottom() && prop.getYBottom() > this.getYTop();
	}

}
