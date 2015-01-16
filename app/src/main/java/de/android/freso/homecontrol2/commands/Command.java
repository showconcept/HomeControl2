package de.android.freso.homecontrol2.commands;

import android.view.View;

/**
 * Created by Patrick on 14.01.2015.
 */
public abstract class Command {

    protected Command() {
    }

    public abstract View getView();
}
