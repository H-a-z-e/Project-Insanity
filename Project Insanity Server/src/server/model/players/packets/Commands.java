package server.model.players.packets;

import server.Config;
import server.Server;
import server.model.players.Client;
import server.model.players.PacketType;
import server.util.Misc;

/**
 * Commands
 * 
 * @author Zack
 */

public class Commands implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		String playerCommand = c.getInStream().readString();
		if(Config.SERVER_DEBUG)
			Misc.println(c.playerName+" playerCommand: "+playerCommand);
		if (playerCommand.startsWith("/") && playerCommand.length() > 1) {
			if (c.clanId >= 0) {
				System.out.println(playerCommand);
				playerCommand = playerCommand.substring(1);
				Server.clanChat.playerMessageToClan(c.playerId, playerCommand, c.clanId);
			} else {
				if (c.clanId != -1)
					c.clanId = -1;
				c.sendMessage("You are not in a clan.");
			}
			return;
		}
		if(c.playerRights >= 0) {

		}
		if(c.playerRights >= 1) {

		}
		if(c.playerRights >= 2) {

		}
		if(c.playerRights >= 3) {

		}
	}
}
