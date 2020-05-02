package inventoryman;

import java.util.List;

/**
 * Manage the inventory of music and books for a flat, known as "items".
 * Each item has a "creator" (author for books and artist for music), a "title",
 * a date of "acquisition" for the item, an "owner",
 * a cost, and a "format". 
 * 
 * There can not be two items with the same creator, title, and format.
 * 
 * In addition to the attributes for items, books also have a "publication year", and a "publisher".
 * In addition to the attributes for items, music also has a "release date".
 *
 * @see #addBook(String, String, String, String, String, String, String, String) 
 * @see #addMusic(String, String, String, String, String, String, String)
 * 
 * @version 20200415
 */
public interface InventoryMan {
	/**
	 * <b>(1 mark)</b> Add an item representing book to the inventory with the specified values.  
	 * 
	 * @param author The author of the book
	 * @param title The title of the book
	 * @param publicationYear The year the book was published (String with 4 digits)
	 * @param publisher The publisher
	 * @param acquisitionDateStr The date the book was acquired (ISO8601 format)
	 * @param owner The owner of the book 
	 * @param costStr The cost of the book (format "$" dollars "." cents, where dollars is a sequence of 1 or more digits and
	 * cents is always 2 digits)
	 * @param formatStr The format of the book, either "Hardcover" or "Paperback"
	 * @return A string, either "Success" if there are no problems or a string beginning with "ERROR" and the rest of the string
	 * providing some details as to what went wrong. Only the date and cost formats need to be checked. You don't have to check
	 * if the values "make sense" (e.g. a date like 2030-03-59).
	 * 
	 */
	public String addBook(String author, String title, String publicationYear, String publisher,
			String acquisitionDateStr, String owner, String costStr, String formatStr);

	/**
	 * <b>(1 mark)</b> Add an item representing music to the inventory with the specified values.  
	 * @param artist The artist who made the music
	 * @param title The title of the musical piece
	 * @param releaseDateStr The date the piece was released (ISO8601 format)
	 * @param acquisitionDateStr The date the music was acquired (ISO8601 format)
	 * @param owner The owner of the music
	 * @param costStr The cost of the music (format "$" dollars "." cents, where dollars is a sequence of 1 or more digits and
	 * cents is always 2 digits)
	 * @param formatStr The format of the music, either "CD" or "LP"
	 * @return A string, either "Success" if there are no problems or a string beginning with "ERROR" and the rest of the string
	 * providing some details as to what went wrong. Only the date and cost formats need to be checked. You don't have to check
	 * if the values "make sense" (e.g. a date like 2030-03-59).
	 */
	public String addMusic(String artist, String title, String releaseDateStr, String acquisitionDateStr, 
			String owner, String costStr, String formatStr);

	
	/**
	 * <b>(1 mark)</b> Provide a string showing the details of the item with the specified details.
	 * The format of the string depends on whether it is a book or music. The formats are:<br>
	 * Book - creator ", '" title "'. (" publication year ", " publisher "). [" format ", " owner ", " acquisition date + ", " cost "]"<br>
	 * Music - "'" title "' by " creator ", " release date ". (" format ", " owner ", " acquisition date ", " cost ")"<br>
	 * 
	 * @param creator The artist or author of the item
	 * @param title The title of the item
	 * @param formatStr The format of the item
	 * @return A string for the details of the specified item.
	 */
	public String getItemToDisplay(String creator, String title, String formatStr);

	/**
	 * <b>(3 marks)</b> Return a list of of all items in the specified order as a list of strings,
	 * where each string provides the details of the item using the same format
	 * as used by {@link #getItemToDisplay(String, String, String)}. 
	 * Possible orders are: Creator - in alphabetical order of author or artist,
	 * Title - in alphabetical order of item title, Acquisition - in date order
	 * that the item was acquired.
	 * @param order The order to list the items
	 * @return A list of strings providing the details of all items in the specified
	 * order.
	 */
	public List<String> getAll(String order);

	/**
	 * <b>(2 marks)</b> Return a list of all items acquired in the specified year in order of 
	 * date of acquisition as a list of strings, where each string provides 
	 * the details of the item using the same format
	 * as used by {@link #getItemToDisplay(String, String, String)}. 
	 * @param year The year in which the item should have been acquired
	 * @return The list of items acquired in the year in order of date of acquisition.
	 */
	public List<String> getItemsAcquiredInYear(String year);

	/**
	 * <b>(2 marks)</b> Return a list of all creators of items in the inventory in alphabetical
	 * order. If there are multiple items with the same creator then that creator 
	 * should only appear once in the list. 
	 * @return The list of creators in alphabetical order.
	 */
	public List<String> getCreators();
	
	/**
	 * <b>(2 marks)</b> Return a report of the items in the inventory. The report is a list of strings
	 * where the <em>first element</em> is the name of the flat, and the remaining elements
	 * are strings representing items. The format of the strings is:<br>
	 * Book - owner ": " creator ", '" title "'. (" format ")"<br>
	 * Music - owner ": '" title "' by " creator " (" format ")"<br>
	 * The order of the items is:
	 * <ol>
	 * <li>
	 * Items are ordered in alphabetical order of their owner.
	 * <li>
	 * Items owned by the same person are ordered with all books coming before all music.
	 * <li>
	 * All books owned by the same person are ordered first by creator, then by title.
	 * <li>
	 * All music owned by the same person are ordered first by creator, then by title.
	 * </ol>
	 * @return A list of items with the format and order specified. 
	 */
	public List<String> getFlatReport();
}
