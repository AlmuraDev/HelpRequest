package com.almuramc.helprequest.customs;

import com.almuramc.helprequest.RequestGUI;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;

public class DirectionButton extends GenericButton{
	private RequestGUI gui;
	private int dir;
	
	public DirectionButton(RequestGUI gui, int dir, String text) {
		super(text);
		this.gui = gui;
		this.dir = dir;
	}

	@Override
	public void onButtonClick(ButtonClickEvent event) {
		gui.onDirection(dir);
	}
}