package game.skill;

import engine.event.Event;
import engine.event.EventContainer;
import engine.event.EventManager;
import engine.util.Misc;
import game.Client;
import game.player.PlayerHandler;

/*
 * Project Insanity - Evolved v.3
 * Mining.java
 */

public class Mining {

	private final Client c;

	public final int[][] Pick_Settings = {
			{1265, 1, 1, 6753}, //Bronze
			{1267, 1, 2, 6754}, //Iron
			{1269, 6, 3, 6755}, //Steel
			{1271, 31, 5, 6756}, //Addy
			{1273, 21, 4, 6757}, //Mithril
			{1275, 41, 6, 6752}, //Rune
			{15259, 61, 7, 12188}, //Dragon
			{13661, 41, 7, 10222} //Adze
	};

	public final int[][] Rock_Settings = {
			{2091, 1, 18, 3, 436}, //Copper
			{2095, 1, 18, 3, 438}, //Tin
			{2093, 15, 35, 7, 440}, //Iron
			{2097, 30, 50, 38, 453}, //Coal
			{2103, 55, 80, 155, 447}, //Mithril
			{2105, 70, 95, 315, 449}, //Addy
			{2107, 85, 125, 970, 451}, //Rune
			{2090, 1, 18, 3, 436}, //Copper
			{2094, 1, 18, 3, 438}, //Tin
			{2092, 15, 35, 7, 440}, //Iron
			{2096, 30, 50, 38, 453}, //Coal
			{2102, 55, 80, 155, 447}, //Mithril
			{2104, 70, 95, 315, 449}, //Addy
			{2106, 85, 125, 970, 451}, //Rune
			{2100, 20, 40, 78, 442}, //Silver
			{2101, 20, 40, 78, 442}, //Silver
			{2098, 40, 65, 78, 444}, //Gold
			{2099, 40, 65, 78, 444} //Gold
	};

	int a = -1;

	public Mining(final Client c) {
		this.c = c;
	}

	public int getTimer(final int b, final int c, final int level) {
		final double timer = (Rock_Settings[b][1]  * 2 + 20 + Misc.random(20))-(Pick_Settings[c][2] * (Pick_Settings[c][2] * 0.75) + level);
		if (timer < 2.0) {
			return 2;
		} else {
			return (int)timer;
		}
	}

	public void mineRock(final int respawnTime, final int x, final int y, final int type, final int i) {
		new Object();
		for (int t = 0; t < PlayerHandler.players.length; t++) {
			if (PlayerHandler.players[t] != null) {
				if (PlayerHandler.players[t].rockX == x && PlayerHandler.players[t].rockY == y) {
					PlayerHandler.players[t].isMining = false;
					PlayerHandler.players[t].startAnimation(65535);
					PlayerHandler.players[t].rockX = 0;
					PlayerHandler.players[t].rockY = 0;
				}
			}
		}
	}

	public void startMining(final int j, final int x, final int y, final int type) {
		if (c.isMining) {
			return;
		}
		if (c.mining) {
			return;
		}
		final int miningLevel = c.playerLevel[c.playerMining] ;
		a = -1;
		c.turnPlayerTo(x, y);
		if (Rock_Settings[j][1] > miningLevel) {
			c.sendMessage("You need a Mining level of " + Rock_Settings[j][1] + " to mine this rock.");
			return;
		}
		for (int i = 0; i < Pick_Settings.length; i++) {
			if (c.getItems().playerHasItem(Pick_Settings[i][0]) || c.playerEquipment[c.playerWeapon] == Pick_Settings[i][0]) {
				if (Pick_Settings[i][1] <= miningLevel) {
					a = i;
				}
			}
		}
		if (a == -1) {
			c.sendMessage("You need a pickaxe to mine this rock.");
			return;
		}
		if (c.getItems().freeSlots() < 1) {
			c.sendMessage("You do not have enough inventory slots to do that.");
			return;
		}
		c.startAnimation(Pick_Settings[a][3]);
		c.isMining = true;
		c.rockX = x;
		c.rockY = y;
		c.mining = true;
		EventManager.getSingleton().addEvent(new Event() {
			@Override
			public void execute(final EventContainer e) {
				if (!c.isMining) {
					e.stop();
					c.startAnimation(65535);
					return;
				}
				if (c.isMining) {
					c.getItems().addItem(Rock_Settings[j][4], 1);
					c.getPA().addSkillXP(Rock_Settings[j][2], c.playerMining);
				}
				if (c.getItems().freeSlots() < 1) {
					c.sendMessage("You have ran out of inventory slots.");
					e.stop();
				}
				mineRock(Rock_Settings[j][3], x, y, type, Rock_Settings[j][0]);
				c.isMining = false;
				e.stop();
			}
			@SuppressWarnings("unused")
			public void stop() {
				c.getPA().removeAllWindows();
				c.startAnimation(65535);
				c.isMining = false;
				c.rockX = 0;
				c.rockY = 0;
				c.mining = false;
				return;
			}
		}, getTimer(j, a, miningLevel));
	}

}
