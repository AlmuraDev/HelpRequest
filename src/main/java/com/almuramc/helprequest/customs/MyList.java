/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almuramc.helprequest.customs;

import com.almuramc.helprequest.RequestListGUI;
import org.getspout.spoutapi.gui.GenericListWidget;

/**
 *
 * @author ZNickq
 */
public class MyList extends GenericListWidget{
	private RequestListGUI main;
	
	public MyList(RequestListGUI main) {
		this.main = main;
	}

	@Override
	public void onSelected(int item, boolean doubleClick) {
		main.onSelected(item, doubleClick);
	}
	
}
