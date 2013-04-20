package game.skill;

import game.Client;
import game.Config;
import game.Server;
import game.object.Objects;

/*
 * Project Insanity - Evolved v.3
 * FireMaking.java
 */

public class Firemaking {

	private final Client c;
	private final int[] logs = {1511,1521,1519,1517,1515,1513};
	private final int[] level = {1,15,30,45,60,75};
	public long lastLight;
	private final int DELAY = 1250;
	public boolean resetAnim = false;

	public Firemaking(final Client c) {
		this.c = c;
	}

	public void checkLogType(final int logType, final int otherItem) {
		for (int j = 0; j < logs.length;j++) {
			if (logs[j] == logType || logs[j] == otherItem) {
				lightFire(j);
				return;
			}
		}
	}

	public void lightFire(final int slot) {
		if (c.duelStatus >= 5) {
			c.sendMessage("Why am I trying to light a fire in the duel arena?");
			return;
		}
		if (c.playerLevel[c.playerFiremaking] >= level[slot]) {
			if (c.getItems().playerHasItem(590) && c.getItems().playerHasItem(logs[slot])) {
				if (System.currentTimeMillis() - lastLight > DELAY) {
					c.startAnimation(733,0);
					c.getItems().deleteItem(logs[slot], c.getItems().getItemSlot(logs[slot]), 1);
					c.getPA().addSkillXP(logs[slot] * Config.FIREMAKING_EXPERIENCE, c.playerFiremaking);
					final Objects fire = new Objects(2732,c.getX(),c.getY(), 0, -1, 10, 3);
					final Objects fire2 = new Objects(-1,c.getX(),c.getY(), 0, -1, 10, 60);
					Server.objectHandler.addObject(fire);
					Server.objectHandler.addObject(fire2);
					c.sendMessage("You light the fire.");
					c.getPA().walkTo(-1,0);
					c.turnPlayerTo(c.getX() + 1, c.getY());
					lastLight = System.currentTimeMillis();
					//c.getPA().frame1();
					resetAnim = true;
				}
			}
		}
	}

}

