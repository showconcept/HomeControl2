package de.android.freso.homecontrol2.devices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.google.gson.JsonObject;

import de.android.freso.homecontrol2.FhemServer;

/**
 * Created by Patrick on 08.01.2015.
 */
public abstract class FhemDevice {

    protected JsonObject object, internals, readings, attr;
    protected FhemServer server;
    protected  Context context;

    public FhemDevice(JsonObject object, FhemServer server, Context context) {
        this.object = object;
        this.server = server;
        this.context = context;
        internals = object.get("Internals").getAsJsonObject();
        readings = object.get("Readings").getAsJsonObject();
        attr = object.get("Attributes").getAsJsonObject();
    }

    public  String getName() {
        return object.get("Name").getAsString();
    }

    public JsonObject getAttr() {
        return attr;
    }

    public abstract View getView(LayoutInflater inflater);

    public abstract View getCard(LayoutInflater inflater);
}
