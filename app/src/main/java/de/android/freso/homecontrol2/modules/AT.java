package de.android.freso.homecontrol2.modules;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import de.android.freso.homecontrol2.FhemServer;
import de.android.freso.homecontrol2.commands.Command;

/**
 * Created by Patrick on 14.01.2015.
 */
public class AT extends FhemModul {

    private ArrayList<Command> commandList = null;

    public AT(JsonObject object, FhemServer server, Context context) {
        super(object, server, context);
    }

    @Override
    public View getView(LayoutInflater inflater) {
        return null;
    }

    @Override
    public View getCard(LayoutInflater inflater) {
        return null;
    }

    public String getCommand() { return internals.get("COMMAND").getAsString(); }

    public void setCommandList(ArrayList<Command> list) { commandList = list; }

    public ArrayList<Command> getCommandList() { return commandList; }

    public View getViewForDevice(String deviceName) {
        return null;
    }
}
