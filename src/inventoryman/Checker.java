package inventoryman;

import java.util.ArrayList;
import java.util.List;

/**
 * This provides a simple test system for the InventoryMan system. 
 * 
 */
public class Checker {
	/**
	 * Set the amount of details to provide in reports.
	 */
	private static final boolean VERBOSE = false;

	/**
	 * Various constants to reduce errors.
	 */
	private static final String BOOK = "book";
	private static final String MUSIC = "music";
	private static final String FLAT_NAME = "251 Flat";

	private static final int TYPE_IDX = 0;
	private static final int CREATOR_IDX = 1;
	private static final int TITLE_IDX = 2;
	private static final int RELEASE_IDX = 3;
	private static final int ACQUISITION_IDX = 4;
	private static final int OWNER_IDX = 5;
	private static final int COST_IDX = 6;
	private static final int FORMAT_IDX = 7;
	private static final int PUBLISHER_IDX = 8;
	
	/**
	 * Candidate data
	 */
	private static final String[] Meyer_OOSC = { BOOK, "Bertrand Meyers", "Object-oriented software construction", "1988", "1990-03-03", 
			"Chris", "$87.50", "Hardcover", "Prentice Hall" };
	private static final String[] LedZepplin_I = { MUSIC, "Led Zeppelin", "I", "1969-01-12", "1992-03-19", 
			"Jing", "$25.99", "LP" };
	private static final String[] LedZepplin_IV = { MUSIC, "Led Zeppelin", "IV", "1971-11-08", "1998-10-29", 
			"Amira", "$27.99", "CD" };
	private static final String[] Clapton_Journeyman = { MUSIC, "Eric Clapton", "Journeyman", "1989-11-07", "1990-10-03",
			"Chris", "$18.99", "CD" };
	private static final String[] Munroe_TE = { BOOK, "Randall Munroe", "Thing Explainer: Complicated Stuff in Simple Words", "2015", "2015-12-02", 
			"Mary",	"$31.10", "Hardcover", "" };
	private static final String[] Asimov_Foundation = { BOOK, "Isaac Asimov", "Foundation", "1951", "2015-12-03", 
			"Mary",	"$31.10", "Paperback", "Panther Books" };
	private static final String[] Halestorm_Vicious = { MUSIC,  "Halestorm", "Vicious", "2018-07-27", "2018-07-28", 
			"Chris", "$20.99", "CD" };
	private static final String[] Higaonna_HoK = { BOOK, "Morio Higaonna", "The History of Karate", "1995", "2015-03-19", 
			"Amira", "$79.00", "Hardcover", "Dragon Books" };
	private static final String[] Bad_Date = { BOOK, "Bad Date Author", "Bad Date Title", "1900", "2015-3-19", 
			"Bad Date Owner", "$00.00", "Hardcover", "Bad Date Publisher" };
	
	/**
	 * This is the main method that calls the different tests. Tests can be turned off or on by
	 * commenting/un-commenting
	 * @param args Not used.
	 */
	public static void main(String[] args) {
		System.out.println("****Version: Checker 20200408.1 ****");
		// Start with focus on one type of item
		testAddItems(LedZepplin_I); // one music
		testAddItems(LedZepplin_IV, Halestorm_Vicious, LedZepplin_I); // more than one music
		testAddItems(Meyer_OOSC);  // one book
		testAddItems(Meyer_OOSC, Munroe_TE, Asimov_Foundation); // more than one book
		
		// Now multiple item types
		testAddItems(Halestorm_Vicious, Meyer_OOSC, Asimov_Foundation, Munroe_TE);
		
		// List all items in different orders
		testListAllCreatorOrdered();
		testListAllAcquisitionOrdered();
		testListAllTitleOrdered();
		
		// List subsets of items
		testListAllIn2020(); // no items
		testListAllIn1998(); // one item
		testListAllIn2015(); // more then one item
		
		testListCreators();
		
		testFlatReport();
		
		testBadDate();
	}

	/* *******
	 * Methods with names beginning with 'test' directly provide test cases
	 *********/
	private static void testAddItems(String[]...items) {
		String iut = "addItem, getItemToDisplay";
		System.out.println("==" + determineExecutingMethod() + ": add item==");
		InventoryMan inventoryMan = populateInventory(items);
		for (String[] item: items) {
			String actual = inventoryMan.getItemToDisplay(item[CREATOR_IDX], item[TITLE_IDX], item[FORMAT_IDX]);
			String expected = constructItemToDisplay(item);
			checkAndReport(iut, expected, actual, false);
		}
	}
	
	private static void testListAllCreatorOrdered() {
		String iut = "getAll";
		System.out.println("==" + determineExecutingMethod() + ": list all items in creator order==");
		String[][] inputItems = {
				Halestorm_Vicious, Asimov_Foundation, LedZepplin_I, LedZepplin_IV, Meyer_OOSC, Munroe_TE, 
		};
		String[][] sortedItems = {
				Meyer_OOSC, Halestorm_Vicious, Asimov_Foundation, LedZepplin_I, LedZepplin_IV, Munroe_TE, 
		};
		InventoryMan inventoryMan = populateInventory(inputItems);
		List<String> actual = inventoryMan.getAll("Creator");
		printListFormatted(actual);
		checkItems(iut, actual, sortedItems);
	}
	private static void testListAllAcquisitionOrdered() {
		String iut = "getAll";
		System.out.println("==" + determineExecutingMethod() + ": list all items in acquisition order ==");
		String[][] inputItems = {
				Halestorm_Vicious, Asimov_Foundation, LedZepplin_IV, Meyer_OOSC, Munroe_TE, LedZepplin_I,  
		};
		String[][] sortedItems = {
				Meyer_OOSC, LedZepplin_I, LedZepplin_IV, Munroe_TE, Asimov_Foundation, Halestorm_Vicious,  
		};
		InventoryMan inventoryMan = populateInventory(inputItems);
		List<String> actual = inventoryMan.getAll("Acquisition");
		printListFormatted(actual);
		checkItems(iut, actual, sortedItems);
	}
	private static void testListAllTitleOrdered() {
		String iut = "getAll";
		System.out.println("==" + determineExecutingMethod() + ": list all items in title order ==");
		String[][] inputItems = {
				Meyer_OOSC, Halestorm_Vicious, Asimov_Foundation, LedZepplin_IV, Munroe_TE, LedZepplin_I,  
		};
		String[][] sortedItems = {
				Asimov_Foundation, LedZepplin_I, LedZepplin_IV, Meyer_OOSC, Munroe_TE, Halestorm_Vicious,  
		};
		InventoryMan inventoryMan = populateInventory(inputItems);
		List<String> actual = inventoryMan.getAll("Title");
		printListFormatted(actual);
		checkItems(iut, actual, sortedItems);
	}
	private static void testListAllIn2020() {
		String[][] inputItems = {
				Meyer_OOSC, Halestorm_Vicious, Asimov_Foundation, LedZepplin_IV, Munroe_TE, LedZepplin_I,  
		};
		String[][] sortedItems = {
		};
		checkListInYear(inputItems, sortedItems, "2020");
	}

	private static void testListAllIn1998() {
		String[][] inputItems = {
				Meyer_OOSC, Halestorm_Vicious, Asimov_Foundation, LedZepplin_IV, Munroe_TE, LedZepplin_I,  
		};
		String[][] sortedItems = {
				LedZepplin_IV
		};
		checkListInYear(inputItems, sortedItems, "1998");
	}

	private static void testListAllIn2015() {
		String[][] inputItems = {
				Meyer_OOSC, Halestorm_Vicious, Asimov_Foundation, LedZepplin_IV, Munroe_TE, LedZepplin_I,  
		};
		String[][] sortedItems = {
				Munroe_TE, Asimov_Foundation 
		};
		checkListInYear(inputItems, sortedItems, "2015");
	}

	private static void testListCreators() {
		String iut = "getCreators";
		System.out.println("==" + determineExecutingMethod() + ": list all creators in alphabetical order ==");
		String[][] inputItems = {
				Meyer_OOSC, Halestorm_Vicious, Asimov_Foundation, LedZepplin_IV, Munroe_TE, LedZepplin_I,  
		};
		String[] expectedArray = {
				Meyer_OOSC[CREATOR_IDX], Halestorm_Vicious[CREATOR_IDX], Asimov_Foundation[CREATOR_IDX], 
				LedZepplin_I[CREATOR_IDX], Munroe_TE[CREATOR_IDX], 
		};
		InventoryMan inventoryMan = populateInventory(inputItems);
		List<String> actual = inventoryMan.getCreators();
		printListFormatted(actual);
		List<String> expected = new ArrayList<String>();
		for (String item: expectedArray) {
			expected.add(item);
		}
		checkAndReport(iut, expected, actual);
	}
	
	private static void testFlatReport() {
		String iut = "flatReport";
		System.out.println("==" + determineExecutingMethod() + ": produce a report on the flat inventory ==");
		String[][] inputItems = {
				Meyer_OOSC, Halestorm_Vicious, Asimov_Foundation, LedZepplin_IV, Munroe_TE, LedZepplin_I,  
				Higaonna_HoK, Clapton_Journeyman
		};
		InventoryMan inventoryMan = populateInventory(inputItems);
		List<String> actual = inventoryMan.getFlatReport();
		
		String[][] outputItems = {
				// Amira
				Higaonna_HoK,
				LedZepplin_IV,
				// Chris
				Meyer_OOSC, 
				Clapton_Journeyman,
				Halestorm_Vicious,
				// Jing
				LedZepplin_I,
				// Mary
				Asimov_Foundation,
				Munroe_TE,
		};
		List<String> expected = new ArrayList<String>();
		expected.add(FLAT_NAME);
		for (String[] item: outputItems) {
			expected.add(constructItemByOwnerToDisplay(item));
		}
		
		checkAndReport(iut, expected, actual);		
	}
	
	private static void testBadDate() {
		InventoryMan inventoryMan = new InventoryManImpl(FLAT_NAME);
		String[] item = Bad_Date;
		String result = inventoryMan.addBook(item[CREATOR_IDX], item[TITLE_IDX], item[RELEASE_IDX], item[PUBLISHER_IDX],
				item[ACQUISITION_IDX], item[OWNER_IDX], item[COST_IDX], item[FORMAT_IDX]);
		checkAndReport("addBook", "ERROR", result, true);
	}
		
	/* ******************************************************************
	 * 'check' methods do the actual checking that what was provided
	 * matches what we expected.
	 */
	/**
	 * Check that the items for the specified year are produced in the correct order
	 * @param inputItems The items in the inventory
	 * @param sortedItems The expected items in the correct order
	 * @param year The year we want the items from
	 */
	private static void checkListInYear(String[][] inputItems, String[][] sortedItems, String year) {
		String iut = "getItemsAcquiredInYear";
		System.out.println("==" + determineExecutingMethod() + ": list all items purchased in " + year + " in order of acquisition ==");
		InventoryMan inventoryMan = populateInventory(inputItems);
		List<String> actual = inventoryMan.getItemsAcquiredInYear(year);
		printListFormatted(actual);
		checkItems(iut, actual, sortedItems);
		
	}
	
	/**
	 * Check that the actual items agree with the expected items. This mostly provides a means
	 * to compare are list with an array (because array's are easier to specify).
	 * @param iut The implementation under test
	 * @param actual The actual items provided by the inventory manager
	 * @param expectedArray The expected values in the expected order
	 */
	private static void checkItems(String iut, List<String> actual, String[][] expectedArray) {
		List<String> expected = new ArrayList<String>();
		for (String[] item: expectedArray) {
			expected.add(constructItemToDisplay(item));
		}
		checkAndReport(iut, expected, actual);
	}
	
	/**
	 * Check that the actual list of strings provided by the implementation under test (iut) matches the expected
	 * list of strings (including order). Report the result (note use of VERBOSE).
	 * @param iut The implementation under test (use for reporting)
	 * @param expected The list of expected strings
	 * @param actual The list of actual strings.
	 */
	private static void checkAndReport(String iut, List<String> expected, List<String> actual) {
		if (!expected.equals(actual)) {
			System.out.println(iut + " expected:\n\t" + expected + "\n  returned incorrect actual:\n\t" + actual);
		} else if (VERBOSE) {
			System.out.println(iut + " for " + expected + " ---> PASS");
		}		
	}
	
	/**
	 * Check that the actual string provided by the implementation under test (iut) matches the expected
	 * string. Report the result (note use of VERBOSE).
	 * @param iut The implementation under test (use for reporting)
	 * @param expected The expected string (possibly only the prefix)
	 * @param actual The actual string.
	 * @param prefixOnly If true, then only match the prefix
	 */
	private static void checkAndReport(String iut, String expected, String actual, boolean prefixOnly) {
		
		if ((prefixOnly && !actual.startsWith(expected)) || (!prefixOnly && !actual.equals(expected))) {
			System.out.println(iut + " expected:\n\t" + expected + "\n  returned incorrect actual:\n\t" + actual);
		} else if (VERBOSE) {
			System.out.println(iut + " for " + expected + " ---> PASS");
		}		
	}		
	
	/* **********************************************************
	 * Various utility methods, including for generating expected 
	 * values in the required format
	 */
	/**
	 * Put the provided items in the inventory
	 * @param inputItems The items to put in the inventory
	 * @return The resulting inventory
	 */
	private static InventoryMan populateInventory(String[][] inputItems) {
		InventoryMan inventoryMan = new InventoryManImpl(FLAT_NAME);
		for (String[] item: inputItems) {
			if (item[TYPE_IDX].equals(BOOK)) {
				inventoryMan.addBook(item[CREATOR_IDX], item[TITLE_IDX], item[RELEASE_IDX], item[PUBLISHER_IDX],
						item[ACQUISITION_IDX], item[OWNER_IDX], item[COST_IDX], item[FORMAT_IDX]);
			} else {
				inventoryMan.addMusic(item[CREATOR_IDX], item[TITLE_IDX], item[RELEASE_IDX],
						item[ACQUISITION_IDX], item[OWNER_IDX], item[COST_IDX], item[FORMAT_IDX]);
			}
		}
		return inventoryMan;
	}

	/**
	 * Create a string for the specified item in the appropriate format for the type of item
	 * for 'display' results
	 * @param item The item to format
	 * @return the item in the correct format
	 */
	private static String constructItemToDisplay(String[] item) {
		if (item[TYPE_IDX].equals(BOOK)) {
			String result = item[CREATOR_IDX] + ", '" + item[TITLE_IDX] + 
					"'. (" + item[RELEASE_IDX] + ", " + item[PUBLISHER_IDX] + 
					"). [" + item[FORMAT_IDX] +  ", " + item[OWNER_IDX] + ", " +  item[ACQUISITION_IDX] + ", " + item[COST_IDX] + "]";
			return result;
		} else {
			String result = "'" + item[TITLE_IDX] + "' by " + item[CREATOR_IDX] + ", " + item[RELEASE_IDX] + ". (" + 
					item[FORMAT_IDX] +  ", " + item[OWNER_IDX] + ", " +  item[ACQUISITION_IDX] + ", " + item[COST_IDX] + ")";
			return result;
		}
	}

	/**
	 * Create a string for the specified item in the appropriate format for the type of item
	 * for 'ownership' results
	 * @param item The item to format
	 * @return the item in the correct format
	 */
	private static String constructItemByOwnerToDisplay(String[] item) {
		if (item[TYPE_IDX].equals(BOOK)) {
			String result = item[OWNER_IDX] + ": " + item[CREATOR_IDX] + ", '" + item[TITLE_IDX] + 
					"'. (" + item[FORMAT_IDX] + ")";
			return result;
		} else {
			String result = item[OWNER_IDX] + ": '" + item[TITLE_IDX] + "' by " + item[CREATOR_IDX] + " (" + 
					item[FORMAT_IDX] + ")";
			return result;
		}
	}
	
	/**
	 * Output the list provided to stdout in a format that might be easier to read
	 * @param list The list to print
	 */
	private static void printListFormatted(List<String> list) {
		if (!VERBOSE) {
			return;
		}
		System.out.println("---------");
		for (String line: list) {
			System.out.println(line);
		}
		System.out.println("---------");
	}
	
	/**
	 * This does some magic to determine the name of the method that is executing
	 * the test. This reduces the chance of incorrectly reporting the method name
	 * in a test that fails. Usually this kind of thing would be hidden by the
	 * system used for testing, but as we are not using such a system we do it
	 * here.
	 * @return The name of the method that called this method.
	 */
	private static String determineExecutingMethod() {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		StackTraceElement element = stacktrace[2]; // '2' because we want the method that called this method, not this method
		return element.getMethodName();
	}
}
