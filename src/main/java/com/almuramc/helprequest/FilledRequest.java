package com.almuramc.helprequest;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class FilledRequest implements Serializable {

	private final String title, description;
	private final String username, time, location;
	private int state;

	public FilledRequest(String title, String description, Player creator) {
		this.title = title;
		this.description = description;
		username = creator.getName();
		time = currentTimeStamp();
		Location toParse = creator.getLocation();
		int bx = toParse.getBlockX();
		int by = toParse.getBlockY();
		int bz = toParse.getBlockZ();
		String world = toParse.getWorld().getName();
		this.location = world + ", at x:" + bx + " y:" + by + " z:" + bz;
		state = 0;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getUsername() {
		return username;
	}

	public String getTime() {
		return time;
	}

	public String getLocation() {
		return location;
	}

	public int getState() {
		return state;
	}

	public void setState(int newState) {
		state = newState;
	}

	public static String currentTimeStamp() {
		Date d = new Date();
		SimpleDateFormat timeStampFormatter = new SimpleDateFormat("hh:mm:ss");
		return timeStampFormatter.format(d);
	}
}
