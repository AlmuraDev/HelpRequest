package com.almuramc.helprequest;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class HelpRequest extends JavaPlugin {

	private List<FilledRequest> opened = new ArrayList<FilledRequest>();
	private List<FilledRequest> closed = new ArrayList<FilledRequest>();

	@Override
	public void onDisable() {
	}

	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new HRListener(this), this);
	}

	public List<FilledRequest> getRequestsFor(String who, int state) {
		List<FilledRequest> toRet = new ArrayList<FilledRequest>();
		switch (state) {
			case 0:
				for (FilledRequest fr : opened) {
					if (fr.getUsername().equals(who)) {
						toRet.add(fr);
					}
				}
				break;
			case 1:
				for (FilledRequest fr : closed) {
					if (fr.getUsername().equals(who)) {
						toRet.add(fr);
					}
				}
				break;
			case 2:
				for (FilledRequest fr : opened) {
					if (fr.getState() == 0) {
						toRet.add(fr);
					}
				}
				break;
		}
		return toRet;
	}
	
	public void addRequest(FilledRequest request) {
		opened.add(request);
	}
}
