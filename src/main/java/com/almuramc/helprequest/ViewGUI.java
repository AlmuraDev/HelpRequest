package com.almuramc.helprequest;

import com.almuramc.helprequest.customs.FixedTextField;
import com.almuramc.helprequest.customs.NSButton;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

public class ViewGUI extends GenericPopup {

	private FilledRequest isDisplaying;
	private int estate;
	private GenericTextField title = new FixedTextField();
	private GenericTextField description = new FixedTextField();
	private GenericLabel time = new GenericLabel(), location = new GenericLabel(), username = new GenericLabel();
	private GenericLabel dname = new GenericLabel();
	private GenericButton ns;
	private HelpRequest main;
	private SpoutPlayer player;

	public ViewGUI(HelpRequest main, SpoutPlayer who, int estate, FilledRequest which) {
		super();

		//Get which requests the opened window should show
		this.main = main;
		this.estate = estate;
		this.isDisplaying = which;
		player = who;

		//Set the background
		GenericTexture border = new GenericTexture("http://www.pixentral.com/pics/1duZT49LzMnodP53SIPGIqZ8xdKS.png");
		border.setAnchor(WidgetAnchor.CENTER_CENTER);
		border.setPriority(RenderPriority.High);
		border.setWidth(626).setHeight(240);
		border.shiftXPos(-213).shiftYPos(-118);

		GenericLabel usernameL = new GenericLabel("Username: ");
		usernameL.setAnchor(WidgetAnchor.CENTER_CENTER);
		usernameL.setHeight(15).setWidth(GenericLabel.getStringWidth(usernameL.getText()));
		usernameL.shiftXPos(-190).shiftYPos(-100);

		GenericLabel timeL = new GenericLabel("Time: ");
		timeL.setAnchor(WidgetAnchor.CENTER_CENTER);
		timeL.setHeight(15).setWidth(GenericLabel.getStringWidth(timeL.getText()));
		timeL.shiftXPos(-190).shiftYPos(-85);

		GenericLabel locationL = new GenericLabel("Location: ");
		locationL.setAnchor(WidgetAnchor.CENTER_CENTER);
		locationL.setHeight(15).setWidth(GenericLabel.getStringWidth(locationL.getText()));
		locationL.shiftXPos(-190).shiftYPos(-70);

		GenericLabel titleL = new GenericLabel("Title: ");
		titleL.setAnchor(WidgetAnchor.CENTER_CENTER);
		titleL.setHeight(15).setWidth(GenericLabel.getStringWidth(titleL.getText()));
		titleL.shiftXPos(-190).shiftYPos(-55);

		GenericLabel descriptionL = new GenericLabel("Description: ");
		descriptionL.setAnchor(WidgetAnchor.CENTER_CENTER);
		descriptionL.setHeight(15).setWidth(GenericLabel.getStringWidth(descriptionL.getText()));
		descriptionL.shiftXPos(-190).shiftYPos(-35);

		username.setAnchor(WidgetAnchor.CENTER_CENTER);
		username.setHeight(15).setWidth(80);
		username.shiftXPos(-125).shiftYPos(-100);

		time.setAnchor(WidgetAnchor.CENTER_CENTER);
		time.setHeight(15).setWidth(80);
		time.shiftXPos(-125).shiftYPos(-85);

		location.setAnchor(WidgetAnchor.CENTER_CENTER);
		location.setHeight(15).setWidth(80);
		location.shiftXPos(-125).shiftYPos(-70);

		title.setAnchor(WidgetAnchor.CENTER_CENTER);
		title.setHeight(15).setWidth(80);
		title.shiftXPos(-125).shiftYPos(-55);

		description.setAnchor(WidgetAnchor.CENTER_CENTER);
		description.shiftXPos(-125).shiftYPos(-35);
		description.setMaximumLines(9).setMaximumCharacters(1000);
		description.setHeight(110).setWidth(253);


		dname.setAnchor(WidgetAnchor.CENTER_CENTER);
		dname.setHeight(15).setWidth(100);
		dname.shiftXPos(-50).shiftYPos(-110);


		ns = new NSButton(this, "");
		ns.setAnchor(WidgetAnchor.CENTER_CENTER);
		ns.setHeight(15).setWidth(50);
		ns.shiftXPos(-125).shiftYPos(85);

		attachWidget(main, usernameL).attachWidget(main, timeL).attachWidget(main, titleL).attachWidget(main, locationL).attachWidget(main, titleL).attachWidget(main, descriptionL);
		attachWidget(main, username).attachWidget(main, time).attachWidget(main, location).attachWidget(main, title).attachWidget(main, description);
		attachWidget(main, dname);
		attachWidget(main, ns);
		attachWidget(main, border);

		refreshForState();

		who.getMainScreen().closePopup();
		who.getMainScreen().attachPopupScreen(this);

	}

	private void refreshForState() {
		switch (estate) {
			case 0:
				dname.setText("Creating Request").setDirty(true);
				break;
			case 1:
				dname.setText("Editing Request").setDirty(true);
				break;
			case 2:
				dname.setText("Viewing Request").setDirty(true);
				break;
		}
		time.setText("");
		location.setText("").setDirty(true);
		username.setText("").setDirty(true);
		title.setText("").setPlaceholder("").setDirty(true);
		description.setText("").setPlaceholder("").setDirty(true);
		updateCurrentPage();

	}

	public void updateCurrentPage() {
		FilledRequest current = isDisplaying;
		if (current != null) {
			time.setText(current.getTime());
			location.setText(current.getLocation());
			username.setText(current.getUsername());
			title.setText(current.getTitle());
			description.setText(current.getDescription());
		}
		if (estate == 0) {
			ns.setText("Create");
		}
		if (estate == 1) {
			ns.setText("Save");
		}
		if (estate == 2) {
			ns.setText("Edit");
		}

		ns.setDirty(true);
	}
	public void onNS() {
		if (estate == 0) {
			estate = 2;
			FilledRequest fr = new FilledRequest(title.getText(), description.getText(), player);
			main.addRequest(fr);
			isDisplaying = fr;
			refreshForState();
			return;
		}
		if (estate == 1) {
			isDisplaying.setTitle(title.getText());
			isDisplaying.setDescription(description.getText());
			isDisplaying.refresh(player);
			estate = 2;
			refreshForState();
			return;
		}
		if (estate == 2) {
			estate = 1;
			refreshForState();
		}
	}
}
