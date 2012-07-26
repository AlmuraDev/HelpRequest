package com.almuramc.helprequest.customs;

import com.almuramc.helprequest.RequestGUI;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;

public class StateButton extends GenericButton{
	private RequestGUI gui;
	
	public StateButton(RequestGUI gui) {
		super();
		this.gui = gui;
	}

	@Override
	public void onButtonClick(ButtonClickEvent event) {
		gui.onStateChange();
	}
	
}
