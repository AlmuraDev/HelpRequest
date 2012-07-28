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
    private GenericComboBox clist = new MyComboBox(this);
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
        GenericLabel usernameL = new GenericLabel("Username: ");
        GenericLabel timeL = new GenericLabel("Time: ");
        GenericLabel locationL = new GenericLabel("Location: ");
        GenericLabel titleL = new GenericLabel("Title: ");
        GenericLabel descriptionL = new GenericLabel("Description: ");

        DirectionButton next = new DirectionButton(this, 1, ">");
        DirectionButton prev = new DirectionButton(this, -1, "<");
        DirectionButton close = new DirectionButton(this, 0, "Close");
        
        usernameL.setHeight(15).setWidth(GenericLabel.getStringWidth(usernameL.getText()));
        usernameL.setAnchor(WidgetAnchor.TOP_LEFT);
        usernameL.shiftXPos(15).shiftYPos(30);

        timeL.setHeight(15).setWidth(GenericLabel.getStringWidth(timeL.getText()));
        timeL.setAnchor(WidgetAnchor.TOP_LEFT);
        timeL.shiftXPos(15).shiftYPos(45);

        locationL.setHeight(15).setWidth(GenericLabel.getStringWidth(locationL.getText()));
        locationL.setAnchor(WidgetAnchor.TOP_LEFT);
        locationL.shiftXPos(15).shiftYPos(60);

        titleL.setHeight(15).setWidth(GenericLabel.getStringWidth(titleL.getText()));
        titleL.setAnchor(WidgetAnchor.TOP_LEFT);
        titleL.shiftXPos(15).shiftYPos(75);

        descriptionL.setHeight(15).setWidth(GenericLabel.getStringWidth(descriptionL.getText()));
        descriptionL.setAnchor(WidgetAnchor.CENTER_LEFT);
        descriptionL.shiftXPos(15).shiftYPos(-15);

        clist.setHeight(15).setWidth(70);
        clist.setAnchor(WidgetAnchor.CENTER_RIGHT); //TOP_RIGHT widget doesn't work
        clist.setText("List");
        clist.shiftXPos(-80).shiftYPos(-95);

        username.setHeight(15).setWidth(80);
        username.setAnchor(WidgetAnchor.TOP_LEFT);
        username.shiftXPos(85).shiftYPos(30);

        time.setHeight(15).setWidth(80);
        time.setAnchor(WidgetAnchor.TOP_LEFT);
        time.shiftXPos(85).shiftYPos(45);

        location.setHeight(15).setWidth(80);
        location.setAnchor(WidgetAnchor.TOP_LEFT);
        location.shiftXPos(85).shiftYPos(60);

        title.setHeight(15).setWidth(80);
        title.setAnchor(WidgetAnchor.TOP_LEFT);
        title.shiftXPos(85).shiftYPos(75);

        description.setMaximumLines(9);
        description.setMaximumCharacters(1000);
        description.setHeight(110).setWidth(253);
        description.setAnchor(WidgetAnchor.CENTER_LEFT);
        description.shiftXPos(85).shiftYPos(-15);

        dname.setHeight(15).setWidth(100);
        dname.setAnchor(WidgetAnchor.TOP_CENTER);
        dname.shiftXPos(25).shiftYPos(30);

        ns.setHeight(15).setWidth(50);
        ns.setAnchor(WidgetAnchor.BOTTOM_CENTER);
        ns.shiftXPos(-130).shiftYPos(-25);

        prev.setHeight(15).setWidth(15);
        prev.setAnchor(WidgetAnchor.BOTTOM_CENTER);
        prev.shiftXPos(-70).shiftYPos(-25);

        next.setHeight(15).setWidth(15);
        next.setAnchor(WidgetAnchor.BOTTOM_CENTER);
        next.shiftXPos(-50).shiftYPos(-25);

        close.setHeight(15).setWidth(50);
        close.setAnchor(WidgetAnchor.BOTTOM_CENTER);
        close.shiftXPos(-30).shiftYPos(-25);

        attachWidget(main, usernameL).attachWidget(main, timeL).attachWidget(main, titleL).attachWidget(main, locationL).attachWidget(main, titleL).attachWidget(main, descriptionL);
        attachWidget(main, username).attachWidget(main, time).attachWidget(main, location).attachWidget(main, title).attachWidget(main, description);
        attachWidget(main, dname);
        attachWidget(main, ns);
        attachWidget(main, clist);
        attachWidget(main, border);
        attachWidget(main, close);
        attachWidget(main, next).attachWidget(main, prev);

        refreshForState();
        who.getMainScreen().attachPopupScreen(this);

    }

    private void refreshForState() {
        isDisplaying = main.getRequestsFor(player.getName(), state);
        refreshList();

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

    private void refreshList() {
        List<String> toSet = new ArrayList<String>();
        for(FilledRequest fr: isDisplaying) {
            toSet.add(fr.getTitle());
        }
        clist.setItems(toSet).setDirty(true);
    }

    public void onSelectionChanged(int i) {
        if(i<0)
            return;
        if(i>=isDisplaying.size())
            return;
        currentOne = i;
        updateCurrentPage();
    }
}
