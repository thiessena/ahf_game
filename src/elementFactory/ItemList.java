package src.elementFactory;

import java.util.HashMap;

public class ItemList {
	HashMap<Items, Integer> itemList;
	
	public ItemList() {
		itemList = new HashMap<Items, Integer>();
		putItems();
	}
	
	private void putItems() {
		for(Items i : Items.values()) {
			itemList.put(i, i.getInt());
		}
	}
	
	public int getItem(Items pItem) {
		return itemList.get(pItem);
	}
}
