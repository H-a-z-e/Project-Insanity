package game.skill;

import engine.event.Event;
import engine.event.EventContainer;
import engine.event.EventManager;
import engine.util.Misc;
import game.Client;


public class Fishing {

	private final static int getTimer(final Client c, final int npcId) {
		switch (npcId) {
		case 1: return 1300;
		case 2: return 1400;
		case 3: return 2500;
		case 4: return 3500;
		case 5: return 3800;
		case 6: return 1400;
		case 7: return 3600;
		default: return -1;
		}
	}

	private static void resetFishing(final Client c) {
		c.startAnimation(65535);
		c.getPA().resetVariables();
		c.getPA().removeAllWindows();
		c.playerIsFishing = false;
		for(int i = 0; i < 2; i++) {
			c.fishingProp[i] = -1;
		}
	}

	private final int[][] data = {
			{1, 1, 303, 317, 100, 621},	//SHRIMP
			{2, 20, 309, 335, 500, 622},	//SALMON
			{3, 40, 301, 377, 1000, 619},	//LOBSTER
			{4, 62, 305, 7944, 1200, 620},	//MONK FISH
			{5, 81, 303, 389, 2000, 621},	//Manta
			{6, 35, 311, 359, 800, 618},	//TUNA
			{7, 76, 311, 383, 1500, 618},	//SHARK
	};

	public Fishing(final Client c) {
	}

	public void attemptdata(final Client c, final int npcId) {
		if (c.getItems().freeSlots() == 0) {
			c.sendMessage("You haven't got enough inventory space to continue fishing!");
			c.getPA().sendStatement("You haven't got enough inventory space to continue fishing!");
			Fishing.resetFishing(c);
			return;
		}
		for(int i = 0; i < data.length; i++) {
			if(npcId == data[i][0]) {
				if (c.playerLevel[c.playerFishing] < data[i][1]) {
					c.sendMessage("You haven't got high enough fishing level to fish here!");
					c.sendMessage("You at list need the fishing level of "+ data[i][1] +".");
					c.getPA().sendStatement("You need the fishing level of "+ data[i][1] +" to fish here.");
					return;
				}
				if (!c.getItems().playerHasItem(data[i][2])) {
					c.sendMessage("You need a "+ c.getItems().getItemName(data[i][2]) +" to fish here.");
					return;
				}

				c.fishingProp[0] = data[i][5]; //	ANIM
				c.fishingProp[1] = data[i][3]; //	FISH
				c.fishingProp[2] = data[i][4]; //	XP

				c.sendMessage("You start fishing.");
				c.startAnimation(c.fishingProp[0]);
				c.stopPlayerSkill = true;
				if(c.playerIsFishing) {
					return;
				}
				c.playerIsFishing = true;
				EventManager.getSingleton().addEvent(new Event() {
					@Override
					public void execute(final EventContainer p) {
						c.getItems().addItem(c.fishingProp[1], 1);
						c.getPA().addSkillXP(c.fishingProp[2], c.playerFishing);
						c.sendMessage("You catch a "+ c.getItems().getItemName(c.fishingProp[1]) +".");
						c.startAnimation(c.fishingProp[0]);
						if(!c.stopPlayerSkill) {
							Fishing.resetFishing(c);
							p.stop();
						}
						if (c.getItems().freeSlots() == 0) {
							Fishing.resetFishing(c);
							c.sendMessage("You don't have enough inventory space to continue fishing.");
							c.getPA().sendStatement("You don't have enough inventory space to continue fishing.");
							p.stop();
						}
						if (Misc.random(15) == 0) {
							Fishing.resetFishing(c);
							p.stop();
						}
					}
				}, Fishing.getTimer(c, npcId) + Misc.random(2000));
			}
		}
	}

}

