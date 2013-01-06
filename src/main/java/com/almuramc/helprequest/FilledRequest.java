package com.almuramc.helprequest;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class FilledRequest implements Serializable {

	private String title, description;
	private String username, nickname, time, location, rawLocation, date;
	private int state;

	public FilledRequest(String title, String description, String time, String date, String nickname, Player creator) {
		this.title = title;
		this.description = description;
		username = creator.getName();
		this.nickname = creator.getDisplayName();
		refresh(creator);
		this.date = date;
		this.time = time;
		this.location = parse(creator.getLocation());
		this.rawLocation = rawParse(creator.getLocation());
		state = 0;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getNickname() {
		return nickname;
	}
	
	public String getUsername() {
		return username;
	}

	public String getTime() {
		return time;
	}
	
	public String getDate() {
		return date;
	}

	public String getLocation() {
		return location;
	}

	public String getRawLocation() {
		return rawLocation;
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

	public static String currentDateStamp() {
		Date d = new Date();
		SimpleDateFormat timeStampFormatter = new SimpleDateFormat("EEE, MMM d, yyyy");
		return timeStampFormatter.format(d);
	}
	
	public final void refresh(Player player) {
		time = currentTimeStamp();		
    }	
	
	public static String parse(Location location) {
		int bx = location.getBlockX();
		int by = location.getBlockY();
		int bz = location.getBlockZ();
		String world = location.getWorld().getName();
		return world + ", at x:" + bx + " y:" + by + " z:" + bz;
	}

	public static String rawParse(Location rawLocation) {
		int bx = rawLocation.getBlockX();
		int by = rawLocation.getBlockY();
		int bz = rawLocation.getBlockZ();
		String world = rawLocation.getWorld().getName();
		return world + "," + bx + "," + by + "," + bz;		
	}

	public void setTitle(String text) {
		title = text;
	}

	public void setDescription(String text) {
		description = text;
	}
}
