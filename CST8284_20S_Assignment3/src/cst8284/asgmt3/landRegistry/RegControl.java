
package cst8284.asgmt3.landRegistry;

import java.io.EOFException;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Assignment 3 starter code provided by Prof. D. Houtman
 * This code is for one-time use only during the Summer 2020 semester.
 * (c) D. Houtman.  All rights reserved
 *
 * @author Mayurathan Vigneswaran
 * @version Assignment 3
 * @since  July 29, 2020 *
 */
public class RegControl {

	private ArrayList<Registrant> registrants = new ArrayList<Registrant>();
	private ArrayList<Property> properties = new ArrayList<Property>();

	private ArrayList<Registrant> getRegistrants() {
		return registrants;
	}

	private ArrayList<Property> getProperties() {
		return properties;
	}

	/**
	 * The parameterized constructor having reg
	 * @param reg
	 * @return reg
	 */
	public Registrant addNewRegistrant(Registrant reg) {
		getRegistrants().add(reg);
		return reg;
	}

	/**
	 * The parameterized constructor having regNum
	 * @param regNum
	 * @return null
	 */
	public Registrant findRegistrant(int regNum) {
		for (int i = 0; i < getRegistrants().size(); i++)
			if (getRegistrants().get(i).getRegNum() == regNum)
				return getRegistrants().get(i);
		return null;
	}

	/**
	 * The array list for the list of registrants
	 * @return getRegistrants
	 */
	public ArrayList<Registrant> listOfRegistrants() {
		getRegistrants().removeAll(Collections.singletonList(null));
		return getRegistrants();
	}

	/**
	 * The ArrayList for the changePropertyRegistrant 
	 * @param oldRegNumPropertyArrayList
	 * @param newRegNum
	 * @return listOfProps
	 */
	public ArrayList<Property> changePropertyRegistrant(ArrayList<Property> oldRegNumPropertyArrayList, int newRegNum) {
		ArrayList<Property> listOfProps = new ArrayList<Property>();
		for (Property p : oldRegNumPropertyArrayList)
			listOfProps.add(new Property(p, newRegNum));
		return listOfProps;
	}

	/**
	 * The parameterized constructor for the property having prop
	 * @param prop
	 * @return prop
	 */

	public Property addNewProperty(Property prop) {
		Property overlapProperty = checkPropertyOverlap(prop);
		if (overlapProperty == null)
			getProperties().add(prop);
		else
			prop = overlapProperty;
		return prop;
	}

	/**
	 * The ArrayList for the listOfProperties that having parameter regNum
	 * @param regNum
	 * @return listOfProps
	 */

	public ArrayList<Property> listOfProperties(int regNum) {
		if (regNum == 0)
			return getProperties();
		ArrayList<Property> listOfProps = new ArrayList<Property>();
		for (Property prop : listOfAllProperties())
			if (prop.getRegNum() == regNum)
				listOfProps.add(prop);
		return listOfProps;
	}

	/**
	 *  The ArrayList for the listOfProperties that having no parameter 
	 * @return
	 */
	public ArrayList<Property> listOfAllProperties() {
		ArrayList<Property> listOfProps = new ArrayList<Property>();
		for (int listPropCtr = 0; listPropCtr < getProperties().size(); listPropCtr++)
			if (getProperties().get(listPropCtr) != null)
				listOfProps.add(getProperties().get(listPropCtr));
		return listOfProps;
	}

	/**
	 * The private Parameter that having prop
	 * @param prop
	 * @return null
	 */
	private Property checkPropertyOverlap(Property prop) {
		if (listOfAllProperties().size() > 0) {
			for (int i = 0; i < listOfAllProperties().size(); i++) {
				Property registeredProp = getProperties().get(i);
				if (registeredProp.overlaps(prop))
					return registeredProp;
			}
		}
		return null;
	}

	/**
	 * The method deleteProperties That having parameter props
	 * @param props
	 * @return true
	 */
	public boolean deleteProperties(ArrayList<Property> props) {
		if ((props == null) || (props.size() == 0))
			return false;

		getProperties().removeAll(props);
		return true;
	}

	/**
	 * The mathod deleteRegistrant having parameter regNum
	 * @param regNum
	 * @return registrantToDelete
	 */
	public Registrant deleteRegistrant(int regNum) {
		Registrant registrantToDelete = findRegistrant(regNum);
		if (registrantToDelete == null)
			return null;
		getRegistrants().remove(registrantToDelete);
		return registrantToDelete;
	}

	/**
	 * The method  saveToFile that having two parameter
	 * @param <T>
	 * @param source
	 * @param fileName
	 * @return false
	 */
	public <T> boolean saveToFile(ArrayList<T> source, String fileName) {
		File file = new File(fileName);
		if (file.exists())
			file.delete();
		try (FileOutputStream objectFileStream = new FileOutputStream(fileName);
				ObjectOutputStream oos = new ObjectOutputStream(objectFileStream);) {
			for (T obj : source)
				oos.writeObject(obj);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * The method loadFromFile having parameter fileName
	 * @param <T>
	 * @param fileName
	 * @return null
	 */
	public <T> ArrayList<T> loadFromFile(String fileName) {
		if (!new File(fileName).exists())
			return null;
		ArrayList<T> al = new ArrayList<>();
		try (FileInputStream objectFileStream = new FileInputStream(fileName);
				ObjectInputStream ois = new ObjectInputStream(objectFileStream);) {
			while (true)
				al.add((T) ois.readObject());
		} catch (EOFException e) {
			return al;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * The method for the refreshProperties
	 */
	public void refreshProperties() {
		properties.clear();
		properties = loadFromFile(RegView.PROPERTIES_FILE);
	}

	/**
	 * The method for refreshRegistrants
	 */
	public void refreshRegistrants() {
		registrants.clear();
		registrants = loadFromFile(RegView.REGISTRANTS_FILE);
	}

}
