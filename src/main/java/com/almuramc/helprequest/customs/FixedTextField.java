package com.almuramc.helprequest.customs;

import org.getspout.spoutapi.event.screen.TextFieldChangeEvent;
import org.getspout.spoutapi.gui.GenericTextField;

public class FixedTextField extends GenericTextField{

	@Override
	public void onTextFieldChange(TextFieldChangeEvent event) {
		setDirty(true);
	}
	
}
