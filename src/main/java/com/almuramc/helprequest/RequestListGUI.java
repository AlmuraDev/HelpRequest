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
import org.getspout.spoutapi.gui.ListWidgetItem;
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

	public RequestListGUI(HelpRequest main, SpoutPlayer who, boolean justForHim) {
		this.main = main;
		this.who = who;
		this.justForHim = justForHim;
		this.state = 0;

		gle.setAnchor(WidgetAnchor.CENTER_CENTER);
		gle.shiftXPos(-190).shiftYPos(-100);
		gle.setHeight(200).setWidth(400);

		GenericButton view = new DirectionButton(this, 0, "View");
		view.setAnchor(WidgetAnchor.CENTER_CENTER);
		view.shiftXPos(-190).shiftYPos(100);
		view.setHeight(15).setWidth(GenericLabel.getStringWidth("View") + 10);

		GenericButton close = new DirectionButton(this, 1, "Close");
		close.setAnchor(WidgetAnchor.CENTER_CENTER);
		close.shiftXPos(-150).shiftYPos(100);
		close.setHeight(15).setWidth(GenericLabel.getStringWidth("Close") + 10);

		GenericComboBox myBox = new MyComboBox(this);
		myBox.setAnchor(WidgetAnchor.CENTER_CENTER);
		myBox.shiftXPos(100).shiftYPos(-118);
		myBox.setText("Filters");
		myBox.setHeight(15).setWidth(GenericLabel.getStringWidth("Filters") + 35);
		myBox.setItems(Arrays.asList("Opened", "Closed"));

		attachWidget(main, gle).attachWidget(main, view).attachWidget(main, close);

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
	}

	public void onDirection(int dir) {

		FilledRequest cur = null;
		try {
			cur = isDisplaying.get(gle.getSelectedRow());
		} catch (Exception ex) {
		}
		if (cur == null) {
			return;
		}
		if (dir == 0) { //view
			new ViewGUI(main, who, 2, cur);
		}
		if (dir == 1) { //close
			main.closeRequest(cur);
			refreshForContent();
		}
	}

	public void onSelectionChanged(String text) {
		if(text == null) {
			return;
		}
		if (text.equals("Opened")) {
			state = 0;
			refreshForContent();
		} else {
			state = 1;
			refreshForContent();
		}
	}
}
