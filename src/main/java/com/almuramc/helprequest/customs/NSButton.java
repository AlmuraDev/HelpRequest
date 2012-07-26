package com.almuramc.helprequest.customs;

import com.almuramc.helprequest.RequestGUI;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;

public class NSButton extends GenericButton {

	private RequestGUI gui;

	public NSButton(RequestGUI gui) {
		super("Create");
		this.gui = gui;
	}

	@Override
	public void onButtonClick(ButtonClickEvent event) {
		if (getText().equals("Create")) {
			setText("Save");
		} else {
			setText("Create");
		}
		setDirty(true);
		gui.onNS();
	}
}
