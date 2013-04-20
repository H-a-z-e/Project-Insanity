package server.model.items;

import server.model.players.Client;
import server.util.Misc;
import server.Config;

/**
 * 
 * @author Ryan / Lmctruck30
 *
 */

public class UseItem {

	public static void ItemonObject(Client c, int objectID, int objectX, int objectY, int itemId) {
		if (!c.getItems().playerHasItem(itemId, 1))
			return;
		switch(objectID) {

		}	
	}

	public static void ItemonItem(Client c, int itemUsed, int useWith) {
		if (itemUsed >= 11710 && itemUsed <= 11714 && useWith >= 11710 && useWith <= 11714) {
			if (c.getItems().hasAllShards()) {
				c.getItems().makeBlade();
			}		
		}
		if (itemUsed == 2368 && useWith == 2366 || itemUsed == 2366 && useWith == 2368) {
			c.getItems().deleteItem(2368, c.getItems().getItemSlot(2368),1);
			c.getItems().deleteItem(2366, c.getItems().getItemSlot(2366),1);
			c.getItems().addItem(1187,1);
		}
		
		if (c.getItems().isHilt(itemUsed) || c.getItems().isHilt(useWith)) {
			int hilt = c.getItems().isHilt(itemUsed) ? itemUsed : useWith;
			int blade = c.getItems().isHilt(itemUsed) ? useWith : itemUsed;
			if (blade == 11690) {
				c.getItems().makeGodsword(hilt);
			}
		}
		
		switch(itemUsed) {
		}	
	}
	public static void ItemonNpc(Client c, int itemId, int npcId, int slot) {
		switch(itemId) {
		}
	}


}
