package inventoryman;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

// Hello!
// List that records the inventory, which are books and music. 
public class InventoryList {

	// Field.
	private List<Item> _list = new ArrayList<Item>();
	
	// Default constructor.
	public InventoryList() {
	}

	
	public void addItem(Item item) {
		_list.add(item);
	}
	
	
	public List<Item> giveList() {
		return _list;
	}
	
	// Swaps the positions of two adjacent items based on the alphabetical order of the creator.
	public void swapByCreator(List<Item> itemList, int i, int j) {
		if (itemList.get(i).giveCreator().compareTo(itemList.get(j).giveCreator()) > 0) {
			Collections.swap(itemList, i, j);
		}
	}
	
	
	// Swaps the positions of two adjacent items based on the alphabetical order of the title.
	public void swapByTitle(List<Item> itemList, int i, int j) {
		if (itemList.get(i).giveTitle().compareTo(itemList.get(j).giveTitle()) > 0) {
			Collections.swap(itemList, i, j);
		}
	}
	
	
	// Swaps the positions of two adjacent items based on the chronological order of the acquisition Date.
	public void swapByAcquisitionDate(List<Item> itemList, int i, int j) {
		if (itemList.get(i).giveAcquisitionDate().compareTo(itemList.get(j).giveAcquisitionDate()) > 0) {
			Collections.swap(itemList, i, j);
		}
	}
	
	
	/*
	Return a list of of all items in the specified order as a list of strings,
	where each string provides the details of the item using the same format
	as used by getItemToDisplay.
	Possible orders are: Creator - in alphabetical order of author or artist,
	Title - in alphabetical order of item title, Acquisition - in date order
	that the item was acquired.
	*/
	public List<String> giveOrderedList(InventoryManImpl.InfoType infoType) {
		
		List<Item> listCopy = _list;
		
		for (int i = 0; i < listCopy.size(); i++) {
			for (int j = i + 1; j < listCopy.size(); j++) {
				
				switch (infoType) {
				case Creator:
					swapByCreator(listCopy, i, j);
					if (listCopy.get(i).giveCreator().compareTo(listCopy.get(j).giveCreator()) == 0) {
						swapByTitle(listCopy, i, j);
						if (listCopy.get(i).giveTitle().compareTo(listCopy.get(j).giveTitle()) == 0) {
							swapByAcquisitionDate(listCopy, i, j);
						}
					}
					break;
					
				case Title:
					swapByTitle(listCopy, i, j);
					if (listCopy.get(i).giveTitle().compareTo(listCopy.get(j).giveTitle()) == 0) {
						swapByCreator(listCopy, i, j);
						if (listCopy.get(i).giveCreator().compareTo(listCopy.get(j).giveCreator()) == 0) {
							swapByAcquisitionDate(listCopy, i, j);
						}
					}
					break;
					
				case Acquisition:
					swapByAcquisitionDate(listCopy, i, j);
					if (listCopy.get(i).giveAcquisitionDate().compareTo(listCopy.get(j).giveAcquisitionDate()) == 0) {
						swapByCreator(listCopy, i, j);
						if (listCopy.get(i).giveCreator().compareTo(listCopy.get(j).giveCreator()) == 0) {
							swapByTitle(listCopy, i, j);
						}
					}
					break;
				}
			}
		}
		return constructDetailsList(listCopy);
	}
	
	
	/*
	Return a list of all items acquired in the specified year in order of 
	date of acquisition as a list of strings, where each string provides 
	the details of the item using the same format
	as used by getItemToDisplay. 
	*/
	public List<String> giveListForTheYear(String year) {
		
		List<Item> itemListForTheYear = new ArrayList<Item>();
		
		// Populate the list with the items that were acquired in the specified year.
		for (int i = 0; i < _list.size(); i++) {
			if (_list.get(i).giveAcquisitionDate().contains(year)) {
				itemListForTheYear.add(_list.get(i));
			}
		}
		// Sort the items in chronological order.
		for (int i = 0; i < itemListForTheYear.size(); i++) {
			for (int j = i + 1; j < itemListForTheYear.size(); j++) {
				swapByAcquisitionDate(itemListForTheYear, i, j);
			}
		}
		return constructDetailsList(itemListForTheYear);
	}
	
	
	/*
	Return a list of all creators of items in the inventory in alphabetical
	order. If there are multiple items with the same creator then that creator 
	should only appear once in the list. 
	*/
	public List<String> constructCreatorList() {
		
		List<String> creatorList = new ArrayList<String>();
		
		// Populate the list with creators. Check if the creator is already recorded
		// in the list before adding.
		for (int i = 0; i < _list.size(); i++) {
			if (!creatorList.contains(_list.get(i).giveCreator())) {
				creatorList.add(_list.get(i).giveCreator());
			}
		}
		// Sort the creators in alphabetical order.
		for (int i = 0; i < creatorList.size(); i++) {
			for (int j = i + 1; j < creatorList.size(); j++) {
				if (creatorList.get(i).compareTo(creatorList.get(j)) > 0) {
					Collections.swap(creatorList, i, j);
				}
			}
		}
		return creatorList;
	}
	
	
	// Construct the details list by populating the list with each item's details.
	public List<String> constructDetailsList(List<Item> itemList) {
		
		List<String> detailsList = new ArrayList<String>();
		
		for (int i = 0; i < itemList.size(); i++) {
			detailsList.add(itemList.get(i).giveItemDetails(itemList.get(i)));
		}
		return detailsList;
	}
	
	
	/*
	Return a list of items in the inventory that is ordered in alphabetical order 
	of the owner. The order of the items is:
	
	Items are ordered in alphabetical order of their owner.
	
	Items owned by the same person are ordered with all books coming before all music.
	 
    All books owned by the same person are ordered first by creator, then by title.
	
	All music owned by the same person are ordered first by creator, then by title.
	*/
	public List<Item> constructOrderedListByOwner() {
		
		List<Item> listCopy = _list;
		
		for (int i = 0; i < listCopy.size(); i++) {
			for (int j = i + 1; j < listCopy.size(); j++) {
				if (listCopy.get(i).giveOwner().compareTo(listCopy.get(j).giveOwner()) > 0) {
					Collections.swap(listCopy, i, j);
				} else if (listCopy.get(i).giveOwner().compareTo(listCopy.get(j).giveOwner()) == 0) {
					if ( (listCopy.get(i) instanceof Music) && (listCopy.get(j) instanceof Book) ) {
						Collections.swap(listCopy, i, j);
					} else if ( (listCopy.get(i) instanceof Book && listCopy.get(j) instanceof Book) 
						   || (listCopy.get(i) instanceof Music && listCopy.get(j) instanceof Music) ) {
						swapByCreator(listCopy, i, j);
						if (listCopy.get(i).giveCreator().compareTo(listCopy.get(j).giveCreator()) == 0) {
							swapByTitle(listCopy, i, j);
						}
					}
				}
			}
		}
		return listCopy;
	}
}

