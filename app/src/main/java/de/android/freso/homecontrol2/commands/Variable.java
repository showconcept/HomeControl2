package de.android.freso.homecontrol2.commands;

import android.view.View;

/**
 * Created by Patrick on 18.01.2015.
 */
public class Variable extends Command {

    private String name;

    public Variable(String cmdLine) {
    }

    public String getName() {
        return name;
    }

    @Override
    public View getView() {
        return null;
    }
}
