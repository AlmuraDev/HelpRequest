package com.almuramc.helprequest;

import com.almuramc.helprequest.customs.DirectionButton;
import com.almuramc.helprequest.customs.MyComboBox;
import java.util.Arrays;
import java.util.List;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericComboBox;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericListWidget;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.ListWidgetItem;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.Screen;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 *
 * @author ZNickq
 */
public class RequestListGUI extends GenericPopup {

	private HelpRequest main;
	private SpoutPlayer who;
	private boolean justForHim;
	private int state;
	private List<FilledRequest> isDisplaying;
	private GenericListWidget gle = new GenericListWidget();
	private GenericButton closereq;

	public RequestListGUI(HelpRequest main, SpoutPlayer who, boolean justForHim) {
		this.main = main;
		this.who = who;
		this.justForHim = justForHim;
		this.state = 0;

		//Set the background
		GenericTexture border = new GenericTexture("http://www.pixentral.com/pics/1duZT49LzMnodP53SIPGIqZ8xdKS.png");
		border.setAnchor(WidgetAnchor.CENTER_CENTER);
		border.setPriority(RenderPriority.High);
		border.setWidth(370).setHeight(330);
		border.shiftXPos(-155).shiftYPos(-120);

		GenericLabel gl = new GenericLabel("List of Requests");
		gl.setScale(1.2F);
		gl.setAnchor(WidgetAnchor.CENTER_CENTER);
		gl.setHeight(15).setWidth(GenericLabel.getStringWidth(gl.getText()));
		gl.shiftXPos(-15).shiftYPos(-105);

		gle.setAnchor(WidgetAnchor.CENTER_CENTER);
		gle.shiftXPos(-130).shiftYPos(-80);
		gle.setHeight(170).setWidth(310);

		GenericButton viewreq = new DirectionButton(this, 0, "Edit Request");
		viewreq.setAnchor(WidgetAnchor.CENTER_CENTER);
		viewreq.shiftXPos(-140).shiftYPos(95);
		viewreq.setHeight(15).setWidth(80);

		closereq = new DirectionButton(this, 1, "Close Request");
		closereq.setAnchor(WidgetAnchor.CENTER_CENTER);
		closereq.shiftXPos(-50).shiftYPos(95);
		closereq.setHeight(15).setWidth(90);

		GenericButton close = new DirectionButton(this, 2, "Close");
		close.setAnchor(WidgetAnchor.CENTER_CENTER);
		close.shiftXPos(150).shiftYPos(95);
		close.setHeight(15).setWidth(40);

		GenericComboBox myBox = new MyComboBox(this);
		myBox.setAnchor(WidgetAnchor.CENTER_CENTER);
		myBox.shiftXPos(120).shiftYPos(-108);
		myBox.setText("Filters");
		myBox.setHeight(15).setWidth(GenericLabel.getStringWidth("Filters") + 35);
		myBox.setItems(Arrays.asList("Opened", "Closed"));

		attachWidget(main, border).attachWidget(main, gle).attachWidget(main, gl).attachWidget(main, viewreq).attachWidget(main, closereq).attachWidget(main, close);

		attachWidget(main, myBox);

		refreshForContent();

		who.getMainScreen().closePopup();
		who.getMainScreen().attachPopupScreen(this);
	}

	private void refreshForContent() {
		if (justForHim) {
			isDisplaying = main.getRequestsFor(who.getName(), state); //0-> opened, 1->closed, 2->all opened
		} else {
			isDisplaying = main.getRequestsFor(who.getName(), 2 + state);
		}
		gle.clear();
		for (FilledRequest fre : isDisplaying) {
			gle.addItem(new ListWidgetItem(fre.getTitle() + " - by " + fre.getUsername(), fre.getDescription()));
		}
	}

	public void onSelected(int item, boolean doubleClick) {
		//TODO maybe open on double-click or w/e
		//Nickq: Do Nothing
	}

	public void onDirection(int dir) {

		FilledRequest cur = null;
		try {
			cur = isDisplaying.get(gle.getSelectedRow());
		} catch (Exception ex) {
		}

		if (dir == 2) { //close window
			Screen screen = who.getMainScreen();
			screen.removeWidget(this);
			who.closeActiveWindow();
		}
		if (cur == null) {
			return;
		}
		if (dir == 0) { //view request
			new ViewGUI(main, who, 1, cur);
		}
		if (dir == 1) { //close request
			if (state == 0) {
				main.closeRequest(cur);
			} else {
				main.reopenRequest(cur);
			}
			refreshForContent();
		}


	}

	public void onSelectionChanged(String text) {
		if (text == null) {
			return;
		}
		if (text.equals("Opened")) {
			state = 0;
			closereq.setText("Close Request").setDirty(true);
			refreshForContent();
		} else {
			state = 1;
			closereq.setText("Reopen Request").setDirty(true);
			refreshForContent();
		}
	}
}
