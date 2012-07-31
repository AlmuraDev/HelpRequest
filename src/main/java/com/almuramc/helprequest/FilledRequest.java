package com.almuramc.helprequest;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class FilledRequest implements Serializable {

	private String title, description;
	private String username, time, location;
	private int state;

	public FilledRequest(String title, String description, String time, Player creator) {
		this.title = title;
		this.description = description;
		username = creator.getName();
		refresh(creator);
		this.time = time;
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

    public final void refresh(Player player) {
		time = currentTimeStamp();
		this.location = parse(player.getLocation());
    }
	
	
	public static String parse(Location location) {
		int bx = location.getBlockX();
		int by = location.getBlockY();
		int bz = location.getBlockZ();
		String world = location.getWorld().getName();
		return world + ", at x:" + bx + " y:" + by + " z:" + bz;
	}


	public void setTitle(String text) {
		title = text;
	}

	public void setDescription(String text) {
		description = text;
	}
}
