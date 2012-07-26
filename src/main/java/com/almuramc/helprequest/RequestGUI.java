package com.almuramc.helprequest;

import com.almuramc.helprequest.customs.DirectionButton;
import com.almuramc.helprequest.customs.FixedTextField;
import com.almuramc.helprequest.customs.NSButton;
import com.almuramc.helprequest.customs.StateButton;
import java.util.List;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.player.SpoutPlayer;

public class RequestGUI extends GenericPopup {

	private List<FilledRequest> isDisplaying;
	private int state, currentOne;
	private GenericTextField title = new FixedTextField();
	private GenericTextField description = new FixedTextField();
	private GenericLabel time = new GenericLabel(), location = new GenericLabel(), username = new GenericLabel();
	private GenericButton dname = new StateButton(this);
	private GenericButton ns = new NSButton(this);
	private HelpRequest main;
	private SpoutPlayer player;
	private boolean inNewMode;

	public RequestGUI(HelpRequest main, SpoutPlayer who) {
		super();

		//Get which requests the opened window should show
		this.main = main;
		player = who;
		state = 0;
		inNewMode = false;

		//Create the widgets which are there every time
		GenericLabel usernameL = new GenericLabel("Username: ");
		GenericLabel timeL = new GenericLabel("Time: ");
		GenericLabel locationL = new GenericLabel("Location: ");
		GenericLabel titleL = new GenericLabel("Title: ");
		GenericLabel descriptionL = new GenericLabel("Description: ");
		
		DirectionButton next = new DirectionButton(this, 1, ">");
		DirectionButton prev = new DirectionButton(this, -1, "<");

		usernameL.setX(30).setY(10).setHeight(15).setWidth(GenericLabel.getStringWidth(usernameL.getText()));
		timeL.setX(30).setY(25).setHeight(15).setWidth(GenericLabel.getStringWidth(timeL.getText()));
		locationL.setX(30).setY(40).setHeight(15).setWidth(GenericLabel.getStringWidth(locationL.getText()));
		titleL.setX(30).setY(55).setHeight(15).setWidth(GenericLabel.getStringWidth(titleL.getText()));
		descriptionL.setX(30).setY(90).setHeight(15).setWidth(GenericLabel.getStringWidth(descriptionL.getText()));

		username.setX(100).setY(10).setHeight(15).setWidth(80);
		time.setX(100).setY(25).setHeight(15).setWidth(80);
		location.setX(100).setY(40).setHeight(15).setWidth(80);
		title.setX(100).setY(55).setHeight(15).setWidth(80);
		description.setX(100).setY(90).setHeight(110).setWidth(300);
		dname.setX(300).setY(10).setHeight(15).setWidth(80);
		ns.setX(100).setY(210).setHeight(15).setWidth(50);
		next.setX(180).setY(210).setHeight(15).setWidth(15);
		prev.setX(160).setY(210).setHeight(15).setWidth(15);


		attachWidget(main, usernameL).attachWidget(main, timeL).attachWidget(main, titleL).attachWidget(main, locationL).attachWidget(main, titleL).attachWidget(main, descriptionL);
		attachWidget(main, username).attachWidget(main, time).attachWidget(main, location).attachWidget(main, title).attachWidget(main, description);
		attachWidget(main, dname);
		attachWidget(main, ns);
		attachWidget(main, next).attachWidget(main, prev);

		refreshForState();
		who.getMainScreen().attachPopupScreen(this);

	}

	private void refreshForState() {
		isDisplaying = main.getRequestsFor(player.getName(), state);

		switch (state) {
			case 0:
				dname.setText("Opened requests");
				dname.setDirty(true);
				break;
			case 1:
				dname.setText("Closed requests");
				dname.setDirty(true);
				break;
			case 2:
				dname.setText("Help requests");
				dname.setDirty(true);
				break;
		}
		
		currentOne = 0;
		time.setText("");
		location.setText("").setDirty(true);
		username.setText("").setDirty(true);
		title.setText("").setPlaceholder("").setDirty(true);
		description.setText("").setPlaceholder("").setDirty(true);
		if(!(isDisplaying.isEmpty())) {
			updateCurrentPage();
		}

	}
	
	public void updateCurrentPage() {
		FilledRequest current = isDisplaying.get(currentOne);
		time.setText(current.getTime());
		location.setText(current.getLocation());
		username.setText(current.getUsername());
		title.setText(current.getTitle());
		description.setText(current.getDescription());
		inNewMode = false;
		ns.setText("Create").setDirty(true);
	}

	public void onStateChange() {
		state++;
		if (state == 3) {
			state = 0;
		}
		if (state == 2 && !player.hasPermission("helprequest.moderate")) {
			state = 0;
		}
		refreshForState();
	}

	public void onNS() {
		if(inNewMode) { //Saving
			inNewMode = false;
			FilledRequest fr = new FilledRequest(title.getText(), description.getText(), player);
			main.addRequest(fr);
			refreshForState();
		} else { //Free stuff, get ready for editing
			time.setText("");
			location.setText("");
			username.setText("");
			title.setText("").setPlaceholder("Title here");
			description.setText("").setPlaceholder("Description here");
			inNewMode = true;
		}
	}

	public void onDirection(int dir) {
		if(dir == -1) {
			currentOne--;
			if(currentOne == -1) {
				currentOne++;
				return;
			} else {
				updateCurrentPage();
			}
		}
		if(dir == 1) {
			currentOne++;
			System.out.println("Going to page "+currentOne+" from ");
			System.out.println(isDisplaying.size() - 1);
			if(currentOne > isDisplaying.size() - 1) {
				currentOne--;
				return;
			} else {
				updateCurrentPage();
			}
		}
	}
}
