package com.almuramc.helprequest.customs;

import com.almuramc.helprequest.MainGUI;
import com.almuramc.helprequest.RequestListGUI;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;

public class DirectionButton extends GenericButton{
	private Object gui;
	private int dir;
	
	public DirectionButton(Object gui, int dir, String text) {
		super(text);
		this.gui = gui;
		this.dir = dir;
	}

	@Override
	public void onButtonClick(ButtonClickEvent event) {
		if(gui instanceof MainGUI) {
			((MainGUI)gui).onDirection(dir);
		}
		if(gui instanceof RequestListGUI) {
			((RequestListGUI)gui).onDirection(dir);
		}
	}
}