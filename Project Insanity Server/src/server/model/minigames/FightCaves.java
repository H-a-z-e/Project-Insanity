package game.minigame;

import game.Client;
import game.npc.NPCHandler;
import game.player.Player;

/*
 * Project Insanity - Evolved v.3
 * FightCaves.java
 */

public class FightCaves {

	private final int[][] WAVES = {
			{2627},{2627,2627},{2630},{2630,2627},{2630,2627,2627},{2630,2630},{2631},{2631,2627},{2631,2627,2627},{2631,2630},
			{2631,2630,2627},{2631,2630,2627,2627},{2631,2630,2630},{2631,2631},{2741},{2741,2627},{2741,2627,2627},{2741,2630},
			{2741,2630,2627},{2741,2630,2627,2627},{2741,2630,2630},{2741,2631},{2741,2631,2627},{2741,2631,2627,2627},{2741,2631,2630},
			{2741,2631,2630,2627},{2741,2631,2630,2627,2627},{2741,2631,2630,2630},{2741,2631,2631},{2741,2741},{2743},{2743,2627},
			{2743,2627,2627},{2743,2630},{2743,2630,2627},{2743,2630,2627,2627},{2743,2630,2630},{2743,2631},{2743,2631,2627},
			{2743,2631,2627,2627},{2743,2631,2630},{2743,2631,2630,2627},{2743,2631,2630,2627,2627},{2743,2631,2630,2630},{2743,2631,2631},
			{2743,2741},{2743,2741,2627},{2743,2741,2627,2627},{2743,2741,2630},{2743,2741,2630,2627},{2743,2741,2630,2627,2627},{2743,2741,2630,2630},
			{2743,2741,2631},{2743,2741,2631,2627},{2743,2741,2631,2627,2627},{2743,2741,2631,2630},{2743,2741,2631,2630,2627},{2743,2741,2631,2630,2627,2627},
			{2743,2741,2631,2630,2630},{2743,2741,2631,2631},{2743,2741,2741},{2743,2743},{2745}
	};

	private final int[][] coordinates = {{2398,5086},{2387,5095},{2407,5098},{2417,5082},{2390,5076},{2410, 5090}};

	public int getAtk(final int npc) {
		switch (npc) {
		case 2627:
			return 30;
		case 2630:
			return 50;
		case 2631:
			return 100;
		case 2741:
			return 150;
		case 2743:
			return 450;
		case 2745:
			return 650;
		}
		return 100;
	}

	public int getDef(final int npc) {
		switch (npc) {
		case 2627:
			return 30;
		case 2630:
			return 50;
		case 2631:
			return 100;
		case 2741:
			return 150;
		case 2743:
			return 300;
		case 2745:
			return 500;
		}
		return 100;
	}

	public int getHp(final int npc) {
		switch (npc) {
		case 2627:
			return 10;
		case 2630:
			return 20;
		case 2631:
			return 40;
		case 2741:
			return 80;
		case 2743:
			return 150;
		case 2745:
			return 250;
		}
		return 100;
	}

	public int getMax(final int npc) {
		switch (npc) {
		case 2627:
			return 4;
		case 2630:
			return 7;
		case 2631:
			return 13;
		case 2741:
			return 28;
		case 2743:
			return 54;
		case 2745:
			return 97;
		}
		return 5;
	}

	public void spawnNextWave(final Client c) {
		if (c != null) {
			if (c.waveId >= WAVES.length) {
				c.waveId = 0;
				return;
			}
			if (c.waveId < 0){
				return;
			}
			final int npcAmount = WAVES[c.waveId].length;
			for (int j = 0; j < npcAmount; j++) {
				final int npc = WAVES[c.waveId][j];
				final int X = coordinates[j][0];
				final int Y = coordinates[j][1];
				final int H = Player.heightLevel;
				final int hp = getHp(npc);
				final int max = getMax(npc);
				final int atk = getAtk(npc);
				final int def = getDef(npc);
				NPCHandler.spawnNpc(c, npc, X, Y, H, 0, hp, max, atk, def, true, false);
			}
			c.tzhaarToKill = npcAmount;
			c.tzhaarKilled = 0;
		}
	}

}

