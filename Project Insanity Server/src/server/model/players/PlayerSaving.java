package server.model.players;

import java.util.ArrayList;

import server.Server;

/**
 * @author Sanity
 */

public class PlayerSaving implements Runnable {

	private ArrayList<Integer> requests = new ArrayList<Integer>();
	private Thread thread;
	private static PlayerSaving singleton;
	private static long lastGroupSave;
	private static final int SAVE_TIMER = 25000;

	public static PlayerSaving getSingleton() {
		return singleton;
	}

	public static void initialize() {
		singleton = new PlayerSaving();
		singleton.thread = new Thread(singleton);
		singleton.thread.start();
	}

	public synchronized void run() {
		while(true) {
			saveAllPlayers();
			try {
				Thread.sleep(300000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}

	public synchronized void requestSave(int i) {
		if (!requests.contains(i)) {
			requests.add(i);
			notify();
		}
	}

	public void saveAllPlayers() {
		lastGroupSave = System.currentTimeMillis();
		long start = lastGroupSave;
		for (Player p : PlayerHandler.players) {
			if (p != null)
				PlayerSave.saveGame((Client)p);
			if (System.currentTimeMillis() - start >= (Server.getSleepTimer() - 5)) {
				return;
			}	
		}
	}

	public void saveRequests() {
		for (int id : requests) {
			if (PlayerHandler.players[id] != null) {
				PlayerSave.saveGame((Client)PlayerHandler.players[id]);
			}
		}
		requests.clear();
	}

	public static boolean saveRequired() {
		return System.currentTimeMillis() - lastGroupSave > SAVE_TIMER;
	}
}