package com.almuramc.helprequest;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.getspout.spoutapi.event.input.KeyPressedEvent;
import org.getspout.spoutapi.keyboard.Keyboard;

public class HRListener implements Listener{
	private HelpRequest main;
	
	public HRListener(HelpRequest main) {
		this.main = main;
	}
	
	@EventHandler
	public void onKeyPressed(KeyPressedEvent event) {
		if(event.getKey() == Keyboard.KEY_F10) {
			new RequestGUI(main, event.getPlayer());
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
	}
	
}
