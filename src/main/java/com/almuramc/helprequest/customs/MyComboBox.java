/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almuramc.helprequest.customs;

import com.almuramc.helprequest.RequestGUI;
import org.getspout.spoutapi.gui.GenericComboBox;

/**
 *
 * @author ZNickq
 */
public class MyComboBox extends GenericComboBox{
    private RequestGUI main;
    
    public MyComboBox(RequestGUI main) {
        this.main = main;
    }

    @Override
    public void onSelectionChanged(int i, String text) {
        main.onSelectionChanged(i);
    }
    
}
