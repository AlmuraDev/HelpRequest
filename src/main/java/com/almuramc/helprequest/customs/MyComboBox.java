/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almuramc.helprequest.customs;

import com.almuramc.helprequest.RequestListGUI;
import org.getspout.spoutapi.gui.GenericComboBox;

/**
 *
 * @author ZNickq
 */
public class MyComboBox extends GenericComboBox{
	private RequestListGUI gui;
	
	public MyComboBox(RequestListGUI gui) {
		this.gui = gui;
	}

	@Override
	public void onSelectionChanged(int i, String text) {
		gui.onSelectionChanged(text);
	}
}
