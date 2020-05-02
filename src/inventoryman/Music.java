package inventoryman;

import java.util.regex.Pattern;

// Represents music.
public class Music extends Item {
	
	// Constructor.
	public Music(String creator, String title, String creationDateStr, String acquisitionDateStr, 
			     String owner, String costStr, String formatStr) throws IncorrectFormatException {
		
		super(creator, title, creationDateStr, acquisitionDateStr, owner, costStr, formatStr);
		
		if (!Pattern.matches("\\d{4}-\\d{2}-\\d{2}", creationDateStr)) {
			throw new IncorrectFormatException();
		}
	}
	

	public String giveItemDetails(Item item) {
		return "'" + item.giveTitle() + "' by " + item.giveCreator() + ", " + giveCreationDate() + ". (" + item.giveFormat() + ", " 
			 + item.giveOwner() + ", " +item.giveAcquisitionDate() + ", " + item.giveCost() + ")";
	}

	
	public String getDetailsForReport(Item item) {
		return item.giveOwner() + ": '" + item.giveTitle() + "' by " + item.giveCreator() + " (" + item.giveFormat() + ")";
	}
}

