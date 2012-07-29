package com.almuramc.helprequest.customs;

import com.almuramc.helprequest.ViewGUI;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;

public class NSButton extends GenericButton {

	private ViewGUI gui;

	public NSButton(ViewGUI gui, String text) {
		super(text);
		this.gui = gui;
	}

	@Override
	public void onButtonClick(ButtonClickEvent event) {
		gui.onNS();
	}
}
