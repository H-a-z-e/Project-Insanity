package game.skill;

import engine.event.Event;
import engine.event.EventContainer;
import engine.event.EventManager;
import engine.util.Misc;
import game.Client;
import game.Config;
import game.item.Item;

/*
 * Project Insanity - Evolved v.3
 * Thieving.java
 */

public class Thieving {

	private final Client c;

	//npc, level, exp, coin amount
	public int[][] npcThieving = {{1,1,8,200,1},{18,25,26,500,1},{9,40,47,1000,2},{26,55,85,1500,3},{20,70,152,2000,4},{21,80,273,3000,5}};

	public Thieving(final Client c) {
		this.c = c;
	}

	public void stealFromNPC(final int id) {
		if (System.currentTimeMillis() - c.lastThieve < 2000) {
			return;
		}
		for (final int[] element : npcThieving) {
			if (element[0] == id) {
				if (c.playerLevel[c.playerThieving] >= element[1]) {
					if (Misc.random(c.playerLevel[c.playerThieving] + 2 - element[1]) != 1) {
						c.getPA().addSkillXP(element[2] * Config.THIEVING_EXPERIENCE, c.playerThieving);
						c.getItems().addItem(995, element[3]);
						c.startAnimation(881);
						c.lastThieve = System.currentTimeMillis();
						c.sendMessage("You thieve some money...");
						break;
					} else {
						c.setHitDiff(element[4]);
						c.setHitUpdateRequired(true);
						c.playerLevel[3] -= element[4];
						c.getPA().refreshSkill(3);
						c.lastThieve = System.currentTimeMillis() + 2000;
						c.sendMessage("You fail to thieve the NPC.");
						break;
					}
				} else {
					c.sendMessage("You need a thieving level of " + element[1] + " to thieve from this NPC.");
				}
			}
		}
	}

	public void stealFromStall(final int id, final int xp, final int level, final int i, final int x, final int y) {
		if (System.currentTimeMillis() - c.lastThieve < 2500) {
			return;
		}
		if (c.playerLevel[c.playerThieving] >= level) {
			if (c.getItems().addItem(id,1)) {
				c.startAnimation(832);
				c.getPA().addSkillXP(xp * Config.THIEVING_EXPERIENCE, c.playerThieving);
				c.lastThieve = System.currentTimeMillis();
				c.sendMessage("You steal a " + Item.getItemName(id) + ".");
				c.getPA().checkObjectSpawn(5430 + Misc.random(1), x, y, 1, 10);
				EventManager.getSingleton().addEvent(new Event() {
					@Override
					public void execute(final EventContainer p) {
						c.getPA().checkObjectSpawn(i, x, y, 1, 10);
						p.stop();
					}
				}, 3500);
			}
		} else {
			c.sendMessage("You must have a thieving level of " + level + " to thieve from this stall.");
		}
	}

}

