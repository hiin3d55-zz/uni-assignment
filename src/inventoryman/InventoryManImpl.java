package inventoryman;

import java.util.ArrayList;
import java.util.List;

/*
Manage the inventory of music and books for a flat, known as "items".
Each item has a "creator" (author for books and artist for music), a "title",
a date of "acquisition" for the item, an "owner",
a cost, and a "format". 

There can not be two items with the same creator, title, and format.

In addition to the attributes for items, books also have a "publication year", and a "publisher".
In addition to the attributes for items, music also has a "release date".
*/
public class InventoryManImpl implements InventoryMan{
	
	// Fields.
	private String _flatname; // Name of the flat.
	private InventoryList _inventoryList = new InventoryList(); // List of inventory of books and music.
	private String _errorMessage = "ERROR: The date provided does not follow the format specified: XXXX-XX-XX (ISO8601 format). "
			+ "OR The cost of the item provided does not follow the format specified: e.g. $123.00."; // Used for addBook and addMusic.
	
	// Constructor.
	public InventoryManImpl(String flatname) {
		_flatname = flatname;
	}
	
	
	/*
	 Add an item representing book to the inventory with the specified values.  
	
	 author The author of the book
	 title The title of the book
	 publicationYear The year the book was published (String with 4 digits)
	 publisher The publisher
	 acquisitionDateStr The date the book was acquired (ISO8601 format)
	 owner The owner of the book 
	 costStr The cost of the book (format "$" dollars "." cents, where dollars is a sequence of 1 or more digits and
	 cents is always 2 digits)
	 formatStr The format of the book, either "Hardcover" or "Paperback"
	 
	 Return A string, either "Success" if there are no problems or a string beginning with "ERROR" and the rest of the string
	 providing some details as to what went wrong. Only the date and cost formats need to be checked. 
	 */
	public String addBook(String author, String title, String publicationYear, String publisher,
			              String acquisitionDateStr, String owner, String costStr, String formatStr) {
		try {
			Book book = new Book(author, title, publicationYear, publisher, acquisitionDateStr, owner,
				             costStr, formatStr);
			// When successful.
			_inventoryList.addItem(book);
			return "Success";
		} 
		// If the format of the date and/or cost is incorrect.
		catch (IncorrectFormatException e) {
			return _errorMessage;
			} 
		}
	
	
	/*
	Add an item representing music to the inventory with the specified values.
	  
	artist The artist who made the music
	title The title of the musical piece
	releaseDateStr The date the piece was released (ISO8601 format)
	acquisitionDateStr The date the music was acquired (ISO8601 format)
	owner The owner of the music
	costStr The cost of the music (format "$" dollars "." cents, where dollars is a sequence of 1 or more digits and
	cents is always 2 digits)
	formatStr The format of the music, either "CD" or "LP"
	
	Return A string, either "Success" if there are no problems or a string beginning with "ERROR" and the rest of the string
	providing some details as to what went wrong. Only the date and cost formats need to be checked.
	*/
	public String addMusic(String artist, String title, String releaseDateStr, String acquisitionDateStr, 
			               String owner, String costStr, String formatStr) {
		try {
			Music music = new Music(artist, title, releaseDateStr, acquisitionDateStr, owner,
	                           costStr, formatStr);
			// When successful.
			_inventoryList.addItem(music);
			return "Success";
		} 
		// If the format of the date and/or cost is incorrect.
		catch (IncorrectFormatException e) {
			return _errorMessage;
		} 
	}
	
	
	/*
	Provide a string showing the details of the item with the specified details.
	The format of the string depends on whether it is a book or music. The formats are:
	Book - creator ", '" title "'. (" publication year ", " publisher "). [" format ", " owner ", " acquisition date + ", " cost "]"
	Music - "'" title "' by " creator ", " release date ". (" format ", " owner ", " acquisition date ", " cost ")"
	
	creator The artist or author of the item
	title The title of the item
	formatStr The format of the item
	Return A string for the details of the specified item.
	*/
	public String getItemToDisplay(String creator, String title, String formatStr) {
		
		// Search through the inventory list to find the item with the specified details.
		for (Item item: _inventoryList.giveList()) {
			if (creator.equals(item.giveCreator()) && title.equals(item.giveTitle()) && formatStr.equals(item.giveFormat())) {
				return item.giveItemDetails(item);
			}
		}
		return "item not found";
	}
	
	
	// Contains the constants that are used for the method getAll, which is in the class
	// InventoryManImpl. 
	public enum InfoType {
		Creator, Title, Acquisition;
	}

	
	/*
	Return a list of of all items in the specified order as a list of strings,
	where each string provides the details of the item using the same format
	as used by getItemToDisplay.
	Possible orders are: Creator - in alphabetical order of author or artist,
	Title - in alphabetical order of item title, Acquisition - in date order
	that the item was acquired.
	
	order The order to list the items
	
	Return A list of strings providing the details of all items in the specified
	order.
	*/
	public List<String> getAll(String order) {
		return _inventoryList.giveOrderedList(InfoType.valueOf(order));
	}
	
	
	/*
	Return a list of all items acquired in the specified year in order of 
	date of acquisition as a list of strings, where each string provides 
	the details of the item using the same format
	as used by getItemToDisplay. 
	
	year The year in which the item should have been acquired
	
	Return The list of items acquired in the year in order of date of acquisition.
	*/
	public List<String> getItemsAcquiredInYear(String year){
		return _inventoryList.giveListForTheYear(year);
	}
	
	
	/*
	Return a list of all creators of items in the inventory in alphabetical
	order. If there are multiple items with the same creator then that creator 
	should only appear once in the list. 
	
	Return The list of creators in alphabetical order.
	*/
	public List<String> getCreators() {
		return _inventoryList.constructCreatorList();
	}

	
	/*
	Return a report of the items in the inventory. The report is a list of strings
	where the first element is the name of the flat, and the remaining elements
	are strings representing items. The format of the strings is:
	Book - owner ": " creator ", '" title "'. (" format ")"
	Music - owner ": '" title "' by " creator " (" format ")"
	The order of the items is:
	
	Items are ordered in alphabetical order of their owner.
	
	Items owned by the same person are ordered with all books coming before all music.
	 
    All books owned by the same person are ordered first by creator, then by title.
	
	All music owned by the same person are ordered first by creator, then by title.
	*/
	public List<String> getFlatReport() {
		
		List<String> flatReport = new ArrayList<String>();
		flatReport.add(_flatname); // Add the name of the flat as the first element of the list.
		
		List<Item> sortedItemList = _inventoryList.constructOrderedListByOwner();
		
		for (int i = 0; i < sortedItemList.size(); i++) {
			flatReport.add(sortedItemList.get(i).getDetailsForReport(sortedItemList.get(i)));
		}
		return flatReport;
	}
}


