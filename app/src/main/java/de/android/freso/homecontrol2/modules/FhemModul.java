package de.android.freso.homecontrol2.modules;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import de.android.freso.homecontrol2.FhemServer;

/**
 * Created by Patrick on 08.01.2015.
 */
public abstract class FhemModul {

    protected JsonObject object, internals, readings, attr;
    protected FhemServer server;
    protected Context context;
    private ArrayList<AT> list_at = new ArrayList<AT>();
    private ArrayList<FhemModul> list_devices = new ArrayList<FhemModul>();


    public FhemModul(JsonObject object, FhemServer server, Context context) {
        this.object = object;
        this.server = server;
        this.context = context;
        internals = object.get("Internals").getAsJsonObject();
        readings = object.get("Readings").getAsJsonObject();
        attr = object.get("Attributes").getAsJsonObject();
    }

    public String getName() {
        return object.get("Name").getAsString();
    }

    public String getType() {return internals.get("TYPE").getAsString(); }

    public JsonObject getAttr() {
        return attr;
    }

    public ArrayList<AT> getList_at() { return list_at; }

    public ArrayList<FhemModul> getList_devices() { return list_devices; }

    public void addModulAt(AT at) { list_at.add(at); }

    public void addDevice(FhemModul device) { list_devices.add(device); }

    public abstract View getView(LayoutInflater inflater);

    public abstract View getCard(LayoutInflater inflater);
}
