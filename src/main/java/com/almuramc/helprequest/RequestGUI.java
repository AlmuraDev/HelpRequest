package com.almuramc.helprequest;

import com.almuramc.helprequest.customs.DirectionButton;
import com.almuramc.helprequest.customs.FixedTextField;
import com.almuramc.helprequest.customs.MyComboBox;
import com.almuramc.helprequest.customs.NSButton;
import com.almuramc.helprequest.customs.StateButton;
import java.util.ArrayList;
import java.util.List;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericComboBox;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.WidgetAnchor;
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

        //Set the background
        GenericTexture border = new GenericTexture("http://www.pixentral.com/pics/1duZT49LzMnodP53SIPGIqZ8xdKS.png");
        border.setAnchor(WidgetAnchor.CENTER_CENTER);
        border.setPriority(RenderPriority.High);
        border.setWidth(626).setHeight(240);
        border.shiftXPos(-213).shiftYPos(-118);

        //Create the widgets which are there every time       
        DirectionButton next = new DirectionButton(this, 1, ">");
        DirectionButton prev = new DirectionButton(this, -1, "<");
        DirectionButton close = new DirectionButton(this, 0, "Close");
        
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
        dname.shiftXPos(20).shiftYPos(-110);

        ns.setAnchor(WidgetAnchor.CENTER_CENTER);
        ns.setHeight(15).setWidth(50);
        ns.shiftXPos(-125).shiftYPos(85);

        prev.setAnchor(WidgetAnchor.CENTER_CENTER);
        prev.setHeight(15).setWidth(15);
        prev.shiftXPos(-65).shiftYPos(85);

        next.setAnchor(WidgetAnchor.CENTER_CENTER);
        next.setHeight(15).setWidth(15);
        next.shiftXPos(-45).shiftYPos(85);

        close.setAnchor(WidgetAnchor.CENTER_CENTER);
        close.setHeight(15).setWidth(50);
        close.shiftXPos(-20).shiftYPos(85);

        attachWidget(main, usernameL).attachWidget(main, timeL).attachWidget(main, titleL).attachWidget(main, locationL).attachWidget(main, titleL).attachWidget(main, descriptionL);
        attachWidget(main, username).attachWidget(main, time).attachWidget(main, location).attachWidget(main, title).attachWidget(main, description);
        attachWidget(main, dname);
        attachWidget(main, ns);
        attachWidget(main, border);
        attachWidget(main, close);
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
        if (!(isDisplaying.isEmpty())) {
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
        if (inNewMode) { //Saving
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
        if (dir == -1) {
            currentOne--;
            if (currentOne == -1) {
                currentOne++;
                return;
            } else {
                updateCurrentPage();
            }
        }
        if (dir == 0 && state != 1 && !isDisplaying.isEmpty()) {
            FilledRequest fr = isDisplaying.get(currentOne);
            main.closeRequest(fr);
            refreshForState();
        }
        if (dir == 1) {
            currentOne++;
            if (currentOne > isDisplaying.size() - 1) {
                currentOne--;
                return;
            } else {
                updateCurrentPage();
            }
        }
    }
}
