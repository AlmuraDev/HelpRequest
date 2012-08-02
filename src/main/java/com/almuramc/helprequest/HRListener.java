package com.almuramc.helprequest;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.getspout.spoutapi.event.input.KeyPressedEvent;
import org.getspout.spoutapi.event.screen.TextFieldChangeEvent;
import org.getspout.spoutapi.keyboard.Keyboard;

public class HRListener implements Listener{
	private HelpRequest main;

	public HRListener(HelpRequest main) {
		this.main = main;
	}

	@EventHandler
	public void onKeyPressed(KeyPressedEvent event) {
		if(event.getKey() == Keyboard.valueOf(HelpRequest.hotkeys)) {
			if (event.getPlayer().hasPermission("helprequest.use")) {
				new MainGUI(main, event.getPlayer());
			} else {
				event.getPlayer().sendMessage(ChatColor.GREEN + "[HelpRequest]" + ChatColor.WHITE + "- Permissions Denied");
			}
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {

	}

}
