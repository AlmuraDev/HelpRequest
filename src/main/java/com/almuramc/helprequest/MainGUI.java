/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almuramc.helprequest;

import com.almuramc.helprequest.customs.DirectionButton;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.Screen;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 *
 * @author ZNickq / Dockter
 */
public class MainGUI extends GenericPopup {

	private HelpRequest main;
	private SpoutPlayer who;

	public MainGUI(HelpRequest main, SpoutPlayer who) {
		this.main = main;
		this.who = who;

		
		//Set the background
		GenericTexture border = new GenericTexture("http://www.pixentral.com/pics/1duZT49LzMnodP53SIPGIqZ8xdKS.png");
		border.setAnchor(WidgetAnchor.CENTER_CENTER);
		border.setPriority(RenderPriority.High);
		border.setWidth(170).setHeight(130);
		border.shiftXPos(-85).shiftYPos(-80);
		
		GenericLabel gl = new GenericLabel("Welcome to HelpRequest");
		gl.setScale(1.2F);
		gl.setAnchor(WidgetAnchor.CENTER_CENTER);
		gl.setHeight(15).setWidth(GenericLabel.getStringWidth(gl.getText()));
		gl.shiftXPos(-70).shiftYPos(-70);

		GenericButton create = new DirectionButton(this, 1, "Create new request");
		GenericButton viewown = new DirectionButton(this, 2, "Edit existing requests");
		GenericButton viewother = new DirectionButton(this, 3, "View all requests");
		GenericButton close = new DirectionButton(this, 4, "Close");

		create.setAnchor(WidgetAnchor.CENTER_CENTER);
		viewown.setAnchor(WidgetAnchor.CENTER_CENTER);
		viewother.setAnchor(WidgetAnchor.CENTER_CENTER);
		close.setAnchor(WidgetAnchor.CENTER_CENTER);

		create.setHeight(16).setWidth(120).shiftXPos(-60).shiftYPos(-40);
		viewown.setHeight(16).setWidth(120).shiftXPos(-60).shiftYPos(-20);
		viewother.setHeight(16).setWidth(120).shiftXPos(-60).shiftYPos(0);
		close.setHeight(16).setWidth(40).shiftXPos(20).shiftYPos(30);
		
		attachWidget(main, border);
		attachWidget(main, gl).attachWidget(main, create).attachWidget(main, viewown).attachWidget(main, close);

		if (who.hasPermission("helprequest.admin") || who.isOp()) {
			attachWidget(main, viewother);
		}

		who.getMainScreen().closePopup();
		who.getMainScreen().attachPopupScreen(this);
	}

	public void onDirection(int dir) {
		switch (dir) {
			case 1: //Create
				new ViewGUI(main, who, 0, null);
				break;
			case 2: //Edit
				new RequestListGUI(main, who, true);
				break;
			case 3: //View
				new RequestListGUI(main, who, false);
				break;
			case 4:
				Screen screen = ((SpoutPlayer) getPlayer()).getMainScreen();
				screen.removeWidget(this);				
				((SpoutPlayer) getPlayer()).closeActiveWindow();
				break;
		}
	}
}
