package com.almuramc.helprequest;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class HelpRequest extends JavaPlugin {

	private List<FilledRequest> opened = new ArrayList<FilledRequest>();
	private List<FilledRequest> closed = new ArrayList<FilledRequest>();
	private List<String> admins = new ArrayList<String>();

	@Override
	public void onDisable() {
		getDataFolder().mkdir();
		try {
			SLAPI.save(opened, getDataFolder() + File.separator + "opened.dat");
			SLAPI.save(closed, getDataFolder() + File.separator + "closed.dat");
		} catch (Exception ex) {
			Logger.getLogger(HelpRequest.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void onEnable() {
		try {
			opened = (List<FilledRequest>) SLAPI.load(getDataFolder() + File.separator + "opened.dat");
			closed = (List<FilledRequest>) SLAPI.load(getDataFolder() + File.separator + "closed.dat");
		} catch (Exception ex) {
			
		}
		FileConfiguration config = getConfig();
		config.addDefault("Admins", Arrays.asList("Notch","Jeb_"));
		config.options().copyDefaults(true);
		saveConfig();
		
		admins = config.getStringList("Admins");
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new HRListener(this), this);
	}

	public List<FilledRequest> getRequestsFor(String who, int state) {
		List<FilledRequest> toRet = new ArrayList<FilledRequest>();
		switch (state) {
			case 0:
				for (int i = opened.size() - 1; i >= 0; i--) {
					FilledRequest fr = opened.get(i);
					if (fr.getUsername().equals(who)) {
						toRet.add(fr);
					}
				}
				break;
			case 1:
				for (int i = closed.size() - 1; i >= 0; i--) {
					FilledRequest fr = closed.get(i);
					if (fr.getUsername().equals(who)) {
						toRet.add(fr);
					}
				}
				break;
			case 2:
				for (int i = opened.size() - 1; i >= 0; i--) {
					FilledRequest fr = opened.get(i);
					if (fr.getState() == 0) {
						toRet.add(fr);
					}
				}
				break;
			case 3:
				
				for (int i = closed.size() - 1; i >= 0; i--) {
					FilledRequest fr = closed.get(i);
					if (fr.getState() != 0) {
						toRet.add(fr);
					}
				}
				break;
		}
		return toRet;
	}

	public void addRequest(FilledRequest request) {
		opened.add(request);
		for(String admin : admins) {
			Player aplr = Bukkit.getPlayer(admin);
			if(aplr != null) {
				aplr.sendMessage(ChatColor.GREEN+"A new request has been added!");
			}
		}
	}

	public void closeRequest(FilledRequest fr) {
		fr.setState(1);
		opened.remove(fr);
		closed.add(fr);
		String username = fr.getUsername();
		Player player = Bukkit.getPlayer(username);
		if (player != null) {
			player.sendMessage(ChatColor.GOLD + "One of your issues, called " + ChatColor.GREEN + "\"" + fr.getTitle() + "\"" + ChatColor.GOLD + " has been closed!");
		}
	}

	void reopenRequest(FilledRequest fr) {
		fr.setState(0);
		closed.remove(fr);
		opened.add(fr);
		String username = fr.getUsername();
		Player player = Bukkit.getPlayer(username);
		if (player != null) {
			player.sendMessage(ChatColor.GOLD + "One of your issues, called " + ChatColor.GREEN + "\"" + fr.getTitle() + "\"" + ChatColor.GOLD + " has been reopened!");
		}
	}
}
