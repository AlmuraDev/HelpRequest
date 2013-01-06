package com.almuramc.helprequest;

import com.almuramc.helprequest.customs.CloseButton;
import com.almuramc.helprequest.customs.FixedTextField;
import com.almuramc.helprequest.customs.NSButton;
import com.almuramc.helprequest.customs.TPButton;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.getspout.spoutapi.event.screen.TextFieldChangeEvent;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.Screen;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

public class ViewGUI extends GenericPopup {

	private FilledRequest isDisplaying;
	private int estate;
	private GenericTextField title = new FixedTextField();
	private GenericTextField description = new FixedTextField();
	private GenericLabel time = new GenericLabel(), location = new GenericLabel(), nickname = new GenericLabel(), username = new GenericLabel();
	private GenericLabel dname = new GenericLabel(), date = new GenericLabel();
	private GenericButton ns, tp;
	private HelpRequest main;
	private SpoutPlayer player;
	private String rawLocation = "";

	public ViewGUI(HelpRequest main, SpoutPlayer who, int estate, FilledRequest which) {
		super();

		//Get which requests the opened window should show
		this.main = main;
		this.estate = estate;
		this.isDisplaying = which;
		player = who;		

		//Set the background
		GenericTexture border = new GenericTexture("http://www.almuramc.com/images/playerplus.png");
		border.setAnchor(WidgetAnchor.CENTER_CENTER);
		border.setPriority(RenderPriority.High);
		border.setWidth(626).setHeight(240);
		border.shiftXPos(-213).shiftYPos(-118);

		GenericLabel usernameL = new GenericLabel("Username: ");
		usernameL.setAnchor(WidgetAnchor.CENTER_CENTER);
		usernameL.setHeight(15).setWidth(GenericLabel.getStringWidth(usernameL.getText()));
		usernameL.shiftXPos(-190).shiftYPos(-100);
		
		GenericLabel nicknameL = new GenericLabel("Nickname: ");
		nicknameL.setAnchor(WidgetAnchor.CENTER_CENTER);
		nicknameL.setHeight(15).setWidth(GenericLabel.getStringWidth(usernameL.getText()));
		nicknameL.shiftXPos(50).shiftYPos(-70);

		GenericLabel timeL = new GenericLabel("Time: ");
		timeL.setAnchor(WidgetAnchor.CENTER_CENTER);
		timeL.setHeight(15).setWidth(GenericLabel.getStringWidth(timeL.getText()));
		timeL.shiftXPos(-190).shiftYPos(-85);
		
		GenericLabel dateL = new GenericLabel("Date: ");
		dateL.setAnchor(WidgetAnchor.CENTER_CENTER);
		dateL.setHeight(15).setWidth(GenericLabel.getStringWidth(dateL.getText()));
		dateL.shiftXPos(-70).shiftYPos(-85);

		GenericLabel locationL = new GenericLabel("Location: ");
		locationL.setAnchor(WidgetAnchor.CENTER_CENTER);
		locationL.setHeight(15).setWidth(GenericLabel.getStringWidth(locationL.getText()));
		locationL.shiftXPos(-190).shiftYPos(-70);

		GenericLabel titleL = new GenericLabel("Subject: ");
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
		
		nickname.setAnchor(WidgetAnchor.CENTER_CENTER);
		nickname.setHeight(15).setWidth(80);
		nickname.shiftXPos(100).shiftYPos(-70);

		time.setAnchor(WidgetAnchor.CENTER_CENTER);
		time.setHeight(15).setWidth(80);
		time.shiftXPos(-125).shiftYPos(-85);
		
		date.setAnchor(WidgetAnchor.CENTER_CENTER);
		date.setHeight(15).setWidth(80);
		date.shiftXPos(-40).shiftYPos(-85);

		location.setAnchor(WidgetAnchor.CENTER_CENTER);
		location.setHeight(15).setWidth(80);
		location.shiftXPos(-125).shiftYPos(-70);

		title.setAnchor(WidgetAnchor.CENTER_CENTER);
		title.setPlaceholder("Subject Here");
		title.shiftXPos(-125).shiftYPos(-55);
		title.setMaximumLines(1).setMaximumCharacters(100);
		title.setHeight(15).setWidth(315);
		title.setTabIndex(0);

		description.setAnchor(WidgetAnchor.CENTER_CENTER);
		description.shiftXPos(-125).shiftYPos(-35);
		description.setPlaceholder("Request Here");
		description.setMaximumLines(9).setMaximumCharacters(2000);
		description.setHeight(130).setWidth(315);
		description.setTabIndex(1);


		dname.setAnchor(WidgetAnchor.CENTER_CENTER);
		dname.setHeight(15).setWidth(100);
		dname.shiftXPos(-50).shiftYPos(-110);

		tp = new TPButton(this, "TP to Location");
		tp.setAnchor(WidgetAnchor.CENTER_CENTER);
		tp.setHeight(15).setWidth(90);
		tp.shiftXPos(-25).shiftYPos(100);
		tp.setVisible(false);
		
		ns = new NSButton(this, "");
		ns.setAnchor(WidgetAnchor.CENTER_CENTER);
		ns.setHeight(15).setWidth(90);
		ns.shiftXPos(-125).shiftYPos(100);
		
		GenericButton close = new CloseButton(this, "Close");
		close.setAnchor(WidgetAnchor.CENTER_CENTER);
		close.shiftXPos(150).shiftYPos(100);
		close.setHeight(15).setWidth(40);
		
		attachWidget(main, usernameL).attachWidget(main, nicknameL).attachWidget(main, timeL).attachWidget(main, dateL).attachWidget(main, titleL).attachWidget(main, locationL).attachWidget(main, titleL).attachWidget(main, descriptionL);
		attachWidget(main, username).attachWidget(main, nickname).attachWidget(main, time).attachWidget(main, location).attachWidget(main, title).attachWidget(main, description).attachWidget(main, close);
		attachWidget(main, dname).attachWidget(main, date);
		attachWidget(main, ns).attachWidget(main, tp);;
		attachWidget(main, border);

		refreshForState();

		if (who.hasPermission("helprequest.admin") && estate >= 1) {
			tp.setVisible(true);
		}
		
		who.getMainScreen().attachPopupScreen(this);

	}

	private void refreshForState() {
		switch (estate) {
			case 0:
				dname.setText("Creating Request").setScale(1.2F).setDirty(true);				
				break;
			case 1:
				dname.setText("Editing Request").setScale(1.2F).setDirty(true);				
				break;
			case 2:
				dname.setText("Viewing Request").setScale(1.2F).setDirty(true);						
				break;
		}
	
		title.setText("").setPlaceholder("").setDirty(true);
		description.setText("").setPlaceholder("").setDirty(true);
		updateCurrentPage();

	}

	public void onCloseClick() {
		new MainGUI(main, player);		
	}
	

	public void updateCurrentPage() {
		FilledRequest current = isDisplaying;
		if (current != null) {
			time.setText(ChatColor.YELLOW + current.getTime());
			date.setText(ChatColor.YELLOW + current.getDate());
			location.setText(ChatColor.YELLOW + current.getLocation());
			username.setText(ChatColor.GREEN + current.getUsername());
			nickname.setText(ChatColor.GOLD + current.getNickname());
			rawLocation = current.getRawLocation();
			title.setText(current.getTitle());
			description.setText(current.getDescription());
		} else {
			username.setText(ChatColor.GREEN + player.getName());
			date.setText(ChatColor.YELLOW + FilledRequest.currentDateStamp());
			time.setText(ChatColor.YELLOW + FilledRequest.currentTimeStamp());
			location.setText(ChatColor.YELLOW + FilledRequest.parse(player.getLocation()));	
			rawLocation = FilledRequest.rawParse(player.getLocation());
			nickname.setText(ChatColor.GOLD + player.getDisplayName());
		}
		if (estate == 0) {
			ns.setText("Create & Save");
		}
		if (estate == 1) {
			ns.setText("Save Changes");
		}
		if (estate == 2) {
			ns.setText("Edit");
		}

		ns.setDirty(true);
	}
	public void onNS() {
		if (estate == 0) {
			estate = 2;
			FilledRequest fr = new FilledRequest(title.getText(), description.getText(), time.getText(), date.getText(), player.getDisplayName(), player);
			main.addRequest(fr);
			//isDisplaying = fr;
			//refreshForState();
			player.sendMessage(ChatColor.GREEN + "[HelpRequest]" + ChatColor.WHITE + " - Request Created & Saved!");
			new MainGUI(main, player);
			return;
		}
		if (estate == 1) {
			isDisplaying.setTitle(title.getText());
			isDisplaying.setDescription(description.getText());			
			isDisplaying.refresh(player);			
			//estate = 2;
			//refreshForState();
			player.sendMessage(ChatColor.GREEN + "[HelpRequest]" + ChatColor.WHITE + " - Request Saved!");
			new MainGUI(main, player);
			return;
			
		}
		if (estate == 2) {
			estate = 1;
			refreshForState();
		}
	}
	
	public void onTP() {
		FilledRequest current = isDisplaying;
        String[] tpLocation = current.getRawLocation().split(",");
        String world = tpLocation[0]; // World
        int x = Integer.parseInt(tpLocation[1]); // X
        int y = Integer.parseInt(tpLocation[2]); // Y
        int z = Integer.parseInt(tpLocation[3]); // Z
        World tpWorld = Bukkit.getServer().getWorld(world);
        Location location = tpWorld.getBlockAt(x, y, z).getLocation();
        player.teleport(location);
      //  Screen screen = ((SpoutPlayer) getPlayer()).getMainScreen();
	//	screen.removeWidget(this);				
	//	((SpoutPlayer) getPlayer()).closeActiveWindow();
	}
}
