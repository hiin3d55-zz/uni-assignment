package inventoryman;

// Represents a book.
public class Book extends Item {
	
	private String _publisher;
	
	public Book(String creator, String title, String creationDateStr, String publisher, String acquisitionDateStr, 
		    String owner, String costStr, String formatStr) throws IncorrectFormatException{
		
		super(creator, title, creationDateStr, acquisitionDateStr, owner, costStr, formatStr);
		_publisher = publisher;
	}
	
	
	public String giveItemDetails(Item item) {
		return item.giveCreator() + ", '" + item.giveTitle() + "'. (" + item.giveCreationDate() + ", " + _publisher + "). [" 
         + item.giveFormat() + ", " + item.giveOwner() + ", " + item.giveAcquisitionDate() + ", " + item.giveCost() + "]";
	}

		
	public String getDetailsForReport(Item item) {
		return item.giveOwner() + ": " + item.giveCreator() + ", '" + item.giveTitle() + "'. (" + item.giveFormat() + ")";
	}	 
}
