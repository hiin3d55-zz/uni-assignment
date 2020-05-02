package inventoryman;

import java.util.regex.Pattern;

// Represents the item that is stored in the inventory. An item can be either a book or music.
public abstract class Item {
	
	// Fields.
	private String _creator;
	private String _title; 
	private String _creationDateStr;
	private String _acquisitionDateStr;
	private String _owner;
	private String _costStr;
	private String _formatStr;
	
	// Constructor.
	public Item(String creator, String title, String creationDateStr, String acquisitionDateStr, String owner, String costStr, String formatStr) throws IncorrectFormatException {
		
		// Check if the date and cost provided by the user is in a valid format.
		if (!Pattern.matches("\\d{4}-\\d{2}-\\d{2}", acquisitionDateStr) || !Pattern.matches("\\$\\d+\\.\\d{2}", costStr)) {
				throw new IncorrectFormatException();
		}
	
		_creator = creator;
		_title = title;
		_creationDateStr = creationDateStr;
		_acquisitionDateStr = acquisitionDateStr;
		_owner = owner;
		_costStr = costStr;
		_formatStr = formatStr;
	}
	
	
	public String giveCreator() {
		return _creator;
	}

	
	public String giveTitle() {
		return _title;
	}
	
	
	public String giveCreationDate() {
		return _creationDateStr;
	}
	
	
	public String giveAcquisitionDate() {
		return _acquisitionDateStr;
	}
	
	
	public String giveOwner() {
		return _owner;
	}
	
	
	public String giveCost() {
		return _costStr;
	}
	
	
	public String giveFormat() {
		return _formatStr;
	}
	
	
	public abstract String giveItemDetails(Item item);

	
	public abstract String getDetailsForReport(Item item);
}
	
	