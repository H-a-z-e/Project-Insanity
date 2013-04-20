package game.minigame;

import game.Client;
import game.Server;
import game.npc.NPC;
import game.npc.NPCHandler;
import game.player.Player;
import game.player.PlayerHandler;

/*
 * Project Insanity - Evolved v.3
 * PestControl.java
 */

public class PestControl {

	public final int GAME_TIMER = 70; //5 minutes
	public final int WAIT_TIMER = 7;
	public int gameTimer = -1;
	public int waitTimer = 15;
	public int properTimer = 0;
	public PestControl() {

	}

	public boolean allPortalsDead() {
		int count = 0;
		for (final NPC npc : NPCHandler.npcs) {
			if (npc != null) {
				if (npc.npcType >= 3777 && npc.npcType <= 3780) {
					if (npc.needRespawn) {
						count++;
					}
				}
			}
		}
		return count >= 4;
	}

	public void endGame(final boolean won) {
		gameTimer = -1;
		waitTimer = WAIT_TIMER;
		for (final Player player : PlayerHandler.players) {
			if (player != null) {
				if (player.inPcGame()) {
					final Client c = (Client)player;
					c.getPA().movePlayer(2657, 2639, 0);
					if (won && c.pcDamage > 4) {
						c.sendMessage("You have won the pest control game and have been awarded 4 pest control points.");
						c.pcPoints += 4;
						c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
						c.playerLevel[5] = c.getLevelForXP(c.playerXP[5]);
						c.specAmount = 10;
						c.getItems().addItem(995, c.combatLevel * 50);
						c.getPA().refreshSkill(3);
						c.getPA().refreshSkill(5);
					} else if (won) {
						c.sendMessage("The void knights notice your lack of zeal.");
					} else {
						c.sendMessage("You failed to kill all the portals in 5 minutes and have not been awarded any points.");
					}
					c.pcDamage = 0;
					c.getItems().addSpecialBar(c.playerEquipment[c.playerWeapon]);
					c.getCombat().resetPrayers();
				}
			}
		}
		for (int j = 0; j < NPCHandler.npcs.length; j++) {
			if (NPCHandler.npcs[j] != null) {
				if (NPCHandler.npcs[j].npcType >= 3777 && NPCHandler.npcs[j].npcType <= 3780) {
					NPCHandler.npcs[j] = null;
				}
			}
		}
	}

	public void movePlayer(final int index) {
		final Client c = (Client)PlayerHandler.players[index];
		if (c.combatLevel < 40) {
			c.sendMessage("You must be at least 40 to enter this boat.");
			return;
		}
		c.getPA().movePlayer(2658,2611,0);
	}

	public int playersInBoat() {
		int count = 0;
		for (final Player player : PlayerHandler.players) {
			if (player != null) {
				if (player.inPcBoat()) {
					count++;
				}
			}
		}
		return count;
	}

	public void process() {
		setInterface();
		if (properTimer > 0) {
			properTimer--;
			return;
		} else {
			properTimer = 4;
		}
		if (waitTimer > 0) {
			waitTimer--;
		} else if (waitTimer == 0) {
			startGame();
		}
		if (gameTimer > 0) {
			gameTimer--;
			if (allPortalsDead()) {
				endGame(true);
			}
		} else if (gameTimer == 0) {
			endGame(false);
		}
	}

	public void setInterface() {
		for (int j = 0; j < PlayerHandler.players.length; j++) {
			if (PlayerHandler.players[j] != null) {
				if (PlayerHandler.players[j].inPcBoat()) {
					final Client c = (Client)PlayerHandler.players[j];
					c.getPA().sendFrame126("Next Departure: "+waitTimer+"", 21120);
					c.getPA().sendFrame126("Players Ready: "+playersInBoat()+"", 21121);
					c.getPA().sendFrame126("(Need 3 to 25 players)", 21122);
					c.getPA().sendFrame126("Points: "+c.pcPoints+"", 21123);
				}
				if (PlayerHandler.players[j].inPcGame()) {
					final Client c = (Client)PlayerHandler.players[j];
					for (j = 0; j < NPCHandler.npcs.length; j++) {
						if (NPCHandler.npcs[j] != null) {
							if (NPCHandler.npcs[j].npcType == 3777) {
								c.getPA().sendFrame126("" + NPCHandler.npcs[j].HP + "", 21111);
							}
							if (NPCHandler.npcs[j].npcType == 3778) {
								c.getPA().sendFrame126("" + NPCHandler.npcs[j].HP + "", 21112);
							}
							if (NPCHandler.npcs[j].npcType == 3779) {
								c.getPA().sendFrame126("" + NPCHandler.npcs[j].HP + "", 21113);
							}
							if (NPCHandler.npcs[j].npcType == 3780) {
								c.getPA().sendFrame126("" + NPCHandler.npcs[j].HP + "", 21113);
							}
						}
					}
					c.getPA().sendFrame126("0", 21115);
					c.getPA().sendFrame126("0", 21116);
				}
			}
		}
	}

	public void spawnNpcs() {
		Server.npcHandler.spawnNpc2(3777,2628,2591,0,0,200,0,0,100);
		Server.npcHandler.spawnNpc2(3778,2680,2588,0,0,200,0,0,100);
		Server.npcHandler.spawnNpc2(3779,2669,2570,0,0,200,0,0,100);
		Server.npcHandler.spawnNpc2(3780,2645,2569,0,0,200,0,0,100);
	}

	public void startGame() {
		if (playersInBoat() > 2) {
			gameTimer = GAME_TIMER;
			waitTimer = -1;
			//spawn npcs
			spawnNpcs();
			//move players into game
			for (int j = 0; j < PlayerHandler.players.length; j++) {
				if (PlayerHandler.players[j] != null) {
					if (PlayerHandler.players[j].inPcBoat()) {
						movePlayer(j);
					}
				}
			}
		} else {
			waitTimer = WAIT_TIMER;
			for (final Player player : PlayerHandler.players) {
				if (player != null) {
					if (player.inPcBoat()) {
						final Client c = (Client)player;
						c.sendMessage("There need to be at least 3 players to start a game of pest control.");
					}
				}
			}
		}
	}

}

