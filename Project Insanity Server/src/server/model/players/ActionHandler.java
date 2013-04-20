package server.model.players;

import server.Config;
import server.Server;
import server.model.objects.Object;
import server.util.Misc;
import server.util.ScriptManager;

public class ActionHandler {
	
	private Client c;
	
	public ActionHandler(Client Client) {
		this.c = Client;
	}
	
	
	public void firstClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		switch(objectType) {
		
		default:
		}
	}
	
	public void secondClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		switch(objectType) {

		default:
		}
	}
	
	
	public void thirdClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		switch(objectType) {
		
		default:
		}
	}
	
    public void firstClickNpc(int i) {
        c.clickNpcType = 0;
        c.npcClickIndex = 0;
        switch(i) {
        
        }
    }


    public void secondClickNpc(int i) {
        c.clickNpcType = 0;
        c.npcClickIndex = 0;
        switch(i) {
 
        }
    }
	
	public void thirdClickNpc(int npcType) {
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		switch(npcType) {
		
			default:
		}
	}
	

}