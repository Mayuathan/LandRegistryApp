

package cst8284.asgmt3.landRegistry;

import java.util.Scanner;

import java.io.File;
import java.util.ArrayList;



/**
 * This is the RegView class for the land registry application.
 * This class is responsible to retrieve the user input and pass it to the controller class
 * or get the response from the controller class and display the result
 * 
 * Assignment 3 starter code provided by Prof. D. Houtman
 * This code is for one-time use only during the Summer 2020 semester.
 * (c) D. Houtman.  All rights reserved
 *
 * @author Mayurathan Vigneswaran
 * @version Assignment 3
 * @since  July 29, 2020
 *
 */
public class RegView {

	private static Scanner scan = new Scanner(System.in);
	private static RegControl rc;
	private static final int maxLengthNWidth = 1000;

	public static final String PROPERTIES_FILE = "LandRegistry.prop";
	public static final String REGISTRANTS_FILE = "LandRegistry.reg";

	private static final int ADD_NEW_REGISTRANT = 1, FIND_REGISTRANT = 2, LIST_REGISTRANTS = 3, DELETE_REGISTRANT = 4,
			ADD_NEW_PROPERTY = 5, DELETE_PROPERTY = 6, CHANGE_PROPERTY_REGISTRANT = 7, LIST_PROPERTY_BY_REGNUM = 8,
			LIST_ALL_PROPERTIES = 9, LOAD_LAND_REGISTRY_FROM_BACKUP = 10, SAVE_LAND_REGISTRY_TO_BACKUP = 11, EXIT = 0;

	/**
	 * This is the empty constructor for RegView class.
	 * this will initialize the RegControl object.
	 */
	public RegView() {
		rc = new RegControl();
	}

	/**
	 * This is the static method that return RegControl object
	 * @return rc, RegControl object
	 */
	public static RegControl getRegControl() {
		return rc;
	}

	/**
	 * This static method launch, when launching the application, 
	 * it check for the registrant and property files and if it exists, it will load the contents
	 * then display the user options for this land registry application. When existing the application,
	 * it will save the information into the registrant and property files.
	 * 
	 */
	public static void launch() {
		int choice = 0;

		if ((new File(REGISTRANTS_FILE)).exists()) {
			ArrayList<Registrant> regArrayList = getRegControl().loadFromFile(REGISTRANTS_FILE);
			for (Registrant reg : regArrayList)
				getRegControl().addNewRegistrant(reg);
		}

		if ((new File(PROPERTIES_FILE)).exists()) {
			ArrayList<Property> propArrayList = getRegControl().loadFromFile(PROPERTIES_FILE);
			for (Property prop : propArrayList)
				getRegControl().addNewProperty(prop);
		}

		do {
			choice = displayMenu();
			executeMenuItem(choice);
		} while (choice != EXIT);

		getRegControl().saveToFile(getRegControl().listOfRegistrants(), REGISTRANTS_FILE);
		getRegControl().saveToFile(getRegControl().listOfAllProperties(), PROPERTIES_FILE);

	}

	/**
	 * This method displayMenu is responsible to display the user options
	 * and get the user input and return the interger value of the choice.
	 * @return ch an integer value of the user choice
	 * 
	 */

	private static int displayMenu() {
		System.out.println("Enter a selection from the following menu:");
		System.out.println(ADD_NEW_REGISTRANT + ".  Enter a new registrant\n" + FIND_REGISTRANT
				+ ".  Find registrant by registration number\n" + LIST_REGISTRANTS + ".  Display list of registrants\n"
				+ DELETE_REGISTRANT + ".  Delete Registrant\n" + ADD_NEW_PROPERTY + ".  Register a new property\n"
				+ DELETE_PROPERTY + ".  Delete Property\n" + CHANGE_PROPERTY_REGISTRANT
				+ ".  Change a property's registrant\n" + LIST_PROPERTY_BY_REGNUM
				+ ".  Display all properties with the same registration number\n" + LIST_ALL_PROPERTIES
				+ ".  Display all registered properties\n" + LOAD_LAND_REGISTRY_FROM_BACKUP
				+ ". Load land registry from backup file\n" + SAVE_LAND_REGISTRY_TO_BACKUP
				+ ". Save land registry to backup file\n" + EXIT + ".  Exit program\n");
		int ch = scan.nextInt();
		scan.nextLine(); // 'eat' the next line in the buffer
		return ch;
	}

	/**
	 * this method executeMenuItem will get a user's choice as an argument and perform the action accordingly
	 * available choices are Add New Registrant, Find Registrant, List Registrants, Delete Registrant, Add New Property,
	 * Delete Property, Change Property Registrant, List Property by Registrant Number, List All Properties, Load and Land Registry From Backup,
	 * Save Land Registry From Backup, and Exit.
	 * @param choice it is an integer value of the user choice
	 */
	private static void executeMenuItem(int choice) {
		switch (choice) {
		case ADD_NEW_REGISTRANT:
			viewAddNewRegistrant();
			break;
		case FIND_REGISTRANT:
			viewFindRegistrant();
			break;
		case LIST_REGISTRANTS:
			viewListOfRegistrants();
			break;
		case DELETE_REGISTRANT:
			viewDeleteRegistrant();
			break;
		case ADD_NEW_PROPERTY:
			viewAddNewProperty();
			break;
		case DELETE_PROPERTY:
			viewDeleteProperty();
			break;
		case CHANGE_PROPERTY_REGISTRANT:
			viewChangePropertyRegistrant();
			break;
		case LIST_PROPERTY_BY_REGNUM:
			viewListPropertyByRegNum();
			break;
		case LIST_ALL_PROPERTIES:
			viewListAllProperties();
			break;
		case LOAD_LAND_REGISTRY_FROM_BACKUP:
			viewLoadLandRegistryFromBackUp();
			break;
		case SAVE_LAND_REGISTRY_TO_BACKUP:
			viewSaveLandRegistryToBackUp();
			break;
		case EXIT:
			System.out.println("Exiting Land Registry\n\n");
			break;
		default:
			System.out.println("Invalid choice: try again. (Select " + EXIT + " to exit.)\n");
		}
		System.out.println();
	}

	/**
	 * 
	 * @param s, String S will be the message displayed in the console to get the response from the user
	 * @return the user input
	 */
	private static String getResponseTo(String s) {
		System.out.print(s);
		return (scan.nextLine());
	}

	/**
	 * This static method requestRegNum is responsible to display message requesting user to 
	 * enter the registration number, it will keep asking the user to enter a valid number
	 * if the number is not valid, it will throws an exception.
	 * @return regNum an integer value of Registration number
	 * 
	 * @throws BadLandRegistryException if regNum is not a valid
	 * @throws Exception if any other exception happens
	 */
	private static int requestRegNum() {
		boolean validRegNumber = false;
		String regNum = null;
		while (!validRegNumber) {
			try {
				regNum = getResponseTo("Enter registration number : ");
				if (isInputValidRegistrationNumber(regNum))
					validRegNumber = true;
			} catch (BadLandRegistryException ex) {
				System.out.println(ex.getHeader() + " - " + ex.getMessage() + "; please re-enter\n");
			} catch (Exception e) {
				System.out.println("General Exception happend; please re-enter\n");
			}
		}
		return (Integer.parseInt(regNum));
	}

	/**
	 * This static method makeNewRegistrantFromUserInput is responsible to display message requesting user to 
	 * enter the first and last name, if the name is not valid, it will throws an exception and keep asking the user
	 * to enter the valid names.
	 * @return Registrant object
	 */
	public static Registrant makeNewRegistrantFromUserInput() {
		boolean validName = false;
		String firstLast = null;
		while (!validName) {
			try {
				firstLast = getResponseTo("Enter registant's first and Last name: ");
				validName = isValidName(firstLast);
			} catch (BadLandRegistryException ex) {
				System.out.println(ex.getHeader() + " - " + ex.getMessage() + "; please re-enter\n");
			} catch (Exception e) {
				System.out.println("General Exception happend; please re-enter\n");
			}
		}
		return (new Registrant(firstLast));
	}

	/**
	 * This static method makeNewPropertyFromUserInput is responsible to display message requesting user to 
	 * enter the property information, if any of the property value is not valid, it will throws an exception
	 * and keep requesting user to enter the property value
	 * @return Property object
	 */
	public static Property makeNewPropertyFromUserInput() {
		boolean validProperty = false;
		Property p = null;
		while (!validProperty) {
			try {
				String topLeftCoords = getResponseTo("Enter top and left coordinates of property (as X, Y): ");
				String lengthWidth = getResponseTo("Enter length and width of property (as length, width): ");
				int xLeft = Integer.parseInt(topLeftCoords.split(",")[0].trim());
				int yTop = Integer.parseInt(topLeftCoords.split(",")[1].trim());
				int xLength = Integer.parseInt(lengthWidth.split(",")[0].trim());
				int yWidth = Integer.parseInt(lengthWidth.split(",")[1].trim());
				p = new Property(xLength, yWidth, xLeft, yTop);
				if (isValidPropertyMeasurement(p))
					validProperty = true;
			} catch (BadLandRegistryException ex) {
				System.out.println(ex.getHeader() + " - " + ex.getMessage() + "; please re-enter\n");
			} catch (Exception e) {
				System.out.println("General Exception happend; please re-enter\n");
			}
		}
		return p;
	}

	/**
	 * This viewAddNewRegistatn is responsible to get create Registrant object and add it to the list.
	 * Then display to the user about the newly created Registrant object
	 */
	public static void viewAddNewRegistrant() {
		Registrant reg = makeNewRegistrantFromUserInput();
		getRegControl().addNewRegistrant(reg);
		System.out.println("Registrant added: \n" + reg.toString());
	}

	/**
	 * This static method viewFindRegistrant is responsible to get the registrant number from user
	 * find the registrant and display the message accordingly 
	 */
	public static void viewFindRegistrant() {
		int regNum = requestRegNum();
		Registrant reg = getRegControl().findRegistrant(regNum);
		System.out.println("" + ((reg == null) ? // Registrant does not exist
				"A registrant having registration number " + regNum + " could not be found in the registrants list.\n"
				: "The registrant associated with registration number " + regNum + " is " + reg.toString() + "\n"));
	}

	/**
	 * Save the list of properties and registrants into files.
	 */
	public static void viewSaveLandRegistryToBackUp() {
		System.out.println((getRegControl().saveToFile(getRegControl().listOfRegistrants(), REGISTRANTS_FILE))
				&& (getRegControl().saveToFile(getRegControl().listOfAllProperties(), PROPERTIES_FILE))
						? "Land Registry has been backed up to file"
						: "Could not back up land registry");
	}

	/**
	 * this viewLoadLAndRegistryFromBackUp method is responsible to check if the property and 
	 * registrant file exists, if exist load the information from the file.
	 */
	public static void viewLoadLandRegistryFromBackUp() {
		if (new File(PROPERTIES_FILE).exists() || new File(REGISTRANTS_FILE).exists()) {
			String confirmation = getResponseTo(
					"You are about to overwrite existing records; do you wish to continue? (Enter ‘Y’ to proceed.)");
			if (confirmation.equals("Y")) {
				getRegControl().refreshProperties();
				getRegControl().refreshRegistrants();
			} else {
				System.out.println("You choose not to overwrite exisitng records ");
			}
		}
	}

	/**
	 * the viewDeleteProperty is responsible to get the registration number of the property to be delete.
	 * verify if the regisration is available, then get the list of the properties associated with that registration number
	 * and then delete the property and display the deleted properties
	 */
	public static void viewDeleteProperty() {
		int regNum = Integer.parseInt(getResponseTo("Enter registration number of property to delete: "));
		Registrant reg = getRegControl().findRegistrant(regNum);
		if (reg != null) {
			ArrayList<Property> props = getRegControl().listOfProperties(regNum);
			if (props != null) {

				System.out.println(toString(props));
				String userResponse = getResponseTo("You are about to delete " + props.size()
						+ " properties; do you wish to continue?  (Enter ‘Y’ to proceed.)");
				if (userResponse.equals("Y")) {
					if (getRegControl().deleteProperties(props)) {
						System.out.println("Property/ies deleted");
					} else {
						System.out.println("Error happened while deleting property/ies");
					}
				} else {
					System.out.println("You choose note to delete property/ies");
				}

			} else {
				System.out.println("There is no properties registred under this Registration number. ");
			}
		} else {
			System.out.println("No properties are associated with that registration number. ");
		}

	}

	/**
	 * The viewDeleteREgistrant is responsible to get the registration number from user, delete the registration number
	 * and delete the properties associated with regNumber.
	 */
	public static void viewDeleteRegistrant() {
		int regNum = requestRegNum();
		if (getRegControl().findRegistrant(regNum) != null) {
			String resp = getResponseTo(
					"You are about to delete a registrant and all the its associated properties; do you wish to continue? (Enter ¡®Y¡¯ to proceed.)");
			if (resp.toLowerCase().equals("y")) {
				getRegControl().deleteRegistrant(regNum);
				getRegControl().deleteProperties(getRegControl().listOfProperties(regNum));
				System.out.print("Registrant and related properties deleted\n");
			}
		} else
			System.out.println("Registrant doesn't exist");
	}

	/**
	 * the viewListOfRegistrants method is respnsible to get list of Registrants and display it.
	 */
	public static void viewListOfRegistrants() {
		ArrayList<Registrant> regs = getRegControl().listOfRegistrants();
		if (regs.size() == 0)
			System.out.println("No Registrants are loaded yet");
		else {
			System.out.println("List of registrants:\n");

			System.out.println(toString(regs));
		}
	}

	/**
	 * the viewChangePropertyRegistrant is responsible to get the registration number from user, validate that
	 * registrant number and get the list properties associated with that number, get the new registant number from
	 * user and update the properties with new registration number
	 */
	public static void viewChangePropertyRegistrant() {
		int oldRegNum = Integer.parseInt(getResponseTo("Enter original registrants number: "));
		if (getRegControl().findRegistrant(oldRegNum) == null) // original registrant number does not exist
			System.out.println("Invalid registrant number.  Can't complete request.");
		else {
			ArrayList<Property> props = getRegControl().listOfProperties(oldRegNum);
			if (props == null)
				System.out.println("No properties are associated with the original regNum");
			else { // Properties exist with the oldRegNum
				int newRegNum = Integer.parseInt(getResponseTo("Enter new registrants number: "));
				if (getRegControl().findRegistrant(newRegNum) == null) // new registrant number does not exist
					System.out.println("Invalid registrant number.  Can't complete request.");
				else { // Add properties with the newRegNum, and delete all Properties with the
						// oldRegNum

					getRegControl().changePropertyRegistrant(props, newRegNum);
					getRegControl().deleteProperties(props);
					System.out.println("Operation completed; the new registration number, " + newRegNum + ", "
							+ "has replaced the old registration number, " + oldRegNum + ","
							+ "in all affected properties.");
				}
			}
		}
	}

	/**
	 * viewAddNewProperty is responsible to get the registrant number from user, get the property
	 * information from user and add it to the property list
	 */
	public static void viewAddNewProperty() {
		int regNum = requestRegNum();
		if (getRegControl().findRegistrant(regNum) == null) // Registrant does not exist
			System.out.println("Invalid registrant number");
		else { // Registrant exists
			Property prop = makeNewPropertyFromUserInput();
			prop = new Property(prop, regNum);
			Property returnProp = getRegControl().addNewProperty(prop);
			if (returnProp == null) // registrant array is full
				System.out.println("New property could not be registered; registrant's array is full.");
			else if (prop.equals(returnProp)) // Registrant has been registered
				System.out.println("New property has been registered as: \n" + prop.toString());
			else // Property already exists at that location
				System.out.println("New property could not be registered; \n"
						+ "There is already a property registered at: \n" + returnProp.toString());
		}
	}

	/**
	 * viewListPropertyByRegNumber will display all the properties registered under a registration number.
	 */
	public static void viewListPropertyByRegNum() {
		ArrayList<Property> props = getRegControl().listOfProperties(requestRegNum());
		if (props.size() == 0)
			System.out.println("Property Registry empty; no properties to display\n");
		else
			System.out.println(toString(props));
	}

	/**
	 * the viewListAllProperties is responsible display all the properties.
	 */
	private static void viewListAllProperties() {
		ArrayList<Property> props = getRegControl().listOfAllProperties();
		if (props.size() == 0)
			System.out.println("Property Registry empty; No properties to display");
		else
			System.out.println(toString(props));
	}

	/**
	 * 
	 * @param displayList <T> a generic arraylist of T
	 * @return s String
	 */
	private static <T> String toString(ArrayList<T> displayList) {
		String s = null;
		for (T t : displayList)
			s = s + t.toString() + "\n";
		return s;

	}

	/**
	 * This isInputValidRegistrationNumber method responsible to validate the registration number
	 * @param regNumber a registration number
	 * @return true if regNumber is valid, false otherwise.
	 */
	private static boolean isInputValidRegistrationNumber(String regNumber) {
		if (regNumber == null)
			throw new BadLandRegistryException("Null value entered",
					"An attempt was made to pass a null value to a variable.");
		if (regNumber.trim().isEmpty())
			throw new BadLandRegistryException("Missing value", "Missing an input value.");
		for (int i = 0; i < regNumber.trim().length(); i++) {
			if (!Character.isDigit(regNumber.charAt(i)))
				throw new BadLandRegistryException("Invalid Registration number",
						"Registration number must contain digits only; alphabetic and special characters are prohibited.");
		}
		if (getRegControl().listOfRegistrants().size() == 0)
			throw new BadLandRegistryException("No registrants available",
					"There are no registrants currently listed.");
		if (getRegControl().findRegistrant(Integer.parseInt(regNumber)) == null)
			throw new BadLandRegistryException("Unregistered value",
					"There is no registrant having that registration number.");
		return true;
	}

	/**
	 * 
	 * @param name is string
	 * @return true if the name is valid false othersie.
	 */
	public static boolean isValidName(String name) {
		if (name == null)
			throw new BadLandRegistryException("Null value entered",
					"An attempt was made to pass a null value to a variable.");
		if (name.isEmpty())
			throw new BadLandRegistryException("Missing value", "Missing an input value.");
		return true;
	}

	/**
	 * 
	 * @param p is a property object
	 * @return true of the property values are valid, false otherwise
	 */
	public static boolean isValidPropertyMeasurement(Property p) {
		if (p.getXLength() < 20 || p.getYWidth() < 10)
			throw new BadLandRegistryException("Property below minimum size",
					"The minimum property size entered must have a length of at least 20 m and a width of 10 m.");
		if (p.getXRight() > maxLengthNWidth || p.getYBottom() > maxLengthNWidth)
			throw new BadLandRegistryException("Property exceeds available size",
					"The property requested extends beyond the boundary of the available land.");
		if (p.getXLeft() < 0 || p.getYTop() < 0)
			throw new BadLandRegistryException("Property coordinates in negative",
					"The property coordinates are negative.");
		return true;
	}

}
