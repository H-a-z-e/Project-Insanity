package game.skill;

import game.Client;

public class RuneCraft {

	public static int[][] runeInfo = {
		//	{itemID, LevelReq, XP per}
		{556, 1, 150},//air
		{558, 2, 200},//mind
		{555, 5, 300},//water
		{557, 9, 350},//earth
		{554, 14, 450},//fire
		{559, 20, 500},//body
		{564, 27, 550},//cosmic
		{562, 35, 600},//chaos
		{561, 44, 650},//nature
		{563, 54, 700},//law
		{560, 65, 1000},//death
		{565, 77, 2500},//blood
		{566, 90, 4000},//soul
		{4694, 95, 20},//steam

	};

	public static void craftRunes(final Client c, final int itemID) {
		int index = -1;
		int essence;
		int multiplier = 1;
		int multiplier2 = 0;
		for (int i1 = 0; i1 < RuneCraft.runeInfo.length; i1++) {
			if (RuneCraft.runeInfo[i1][0] == itemID) {
				index = i1;
			}
		}
		if (c.getPA().getXPForLevel(20) < RuneCraft.runeInfo[index][1]) {
			c.sendMessage("You need at least "+RuneCraft.runeInfo[index][1]+" to runecraft this.");
			return;
		}
		if (c.getItems().getItemAmount(1436) > 0) {
			essence = c.getItems().getItemAmount(1436);
		} else {
			return;
		}
		if (index == 0) {
			multiplier = 11;
		} else if (index <= 5) {
			multiplier2 = 18+2*(index-2);
		} else if (index <= 9) {
			multiplier2 = 63+2*(index-2);
		}
		if (index <= 9) {
			for (int i2 = 1; i2 < 11; i2++) {
				if (c.getPA().getXPForLevel(20) >= multiplier2*i2) {
					multiplier = i2+1;
				}
			}
		}
		for (int i = 0; i < essence; i++) {
			c.getItems().deleteItem(1436, c.getItems().getItemSlot(1436), 1);
		}
		c.getItems().addItem(itemID, essence*multiplier);
		c.getPA().addSkillXP(RuneCraft.runeInfo[index][2]*essence, 20);
		c.getLevelForXP(20);
		c.sendMessage("You bind the temple's power into "+c.getItems().getItemName(itemID)+"s.");
		c.gfx0(186);
		c.startAnimation(791, 0);
	}

	public static void locate(final Client c, final int xPos, final int yPos) {
		String X = "";
		String Y = "";
		if (c.absX >= xPos) {
			X = "west";
		}
		if (c.absY > yPos) {
			Y = "South";
		}
		if (c.absX < xPos) {
			X = "east";
		}
		if (c.absY <= yPos) {
			Y = "North";
		}
		c.sendMessage("You need to travel "+Y+"-"+X+".");
	}

}

