package de.android.freso.homecontrol2;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Switch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.android.freso.homecontrol2.modules.AT;
import de.android.freso.homecontrol2.modules.FRM_RGB;
import de.android.freso.homecontrol2.modules.FhemModul;

/**
 * Created by Patrick on 15.12.2014.
 */
public class FhemServer {

    private String adresse;
    private int port;
    private Context context;

    public FhemServer(String adresse, int port, Context context) {
        this.adresse = adresse;
        this.port = port;
        this.context = context;
    }

    public String getHttpString() {
        return "http://" + adresse + ":" + port + "/fhem";
    }


    public static String sendeBefehl(final FhemServer server, final String befehl) {
        HttpResponse response = null;
        String responseString = null;

        try {
            responseString = new AsyncTask<String, Void, String>() {

                @Override
                protected String doInBackground(String... params) {
                    String rString = null;
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(server.getHttpString());
                    List paare = new ArrayList();
                    paare.add(new BasicNameValuePair("XHR", "1"));
                    paare.add(new BasicNameValuePair("cmd", befehl));
                    try {
                        httpPost.setEntity(new UrlEncodedFormEntity(paare));
                        HttpResponse response = httpClient.execute(httpPost);
                        rString = EntityUtils.toString(response.getEntity());
                        return rString;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return rString;
                }
            }.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(responseString != null) {
            return responseString;
        }

        return null;
    }


    public HashMap<String, FhemModul> getAllDevices() {
        HashMap<String, FhemModul> map = new HashMap<String, FhemModul>();
        String responseString = sendeBefehl(this, "jsonlist2");
        Gson gson = new GsonBuilder().create();

        JsonObject obj = gson.fromJson(responseString, JsonObject.class);
        JsonArray results = obj.getAsJsonArray("Results");

        for(int i=0; i < results.size(); i++) {
            JsonObject internals = results.get(i).getAsJsonObject().get("Internals").getAsJsonObject();
            JsonObject attr = results.get(i).getAsJsonObject().get("Attributes").getAsJsonObject();
            String type = internals.get("TYPE").getAsString();

            switch(type) {
                case "FRM_RGB":
                    FRM_RGB frmRgb_device = new FRM_RGB(results.get(i).getAsJsonObject(), this, context);
                    map.put(frmRgb_device.getName(), frmRgb_device);
                    break;
                case "at":
                    AT at_device = new AT(results.get(i).getAsJsonObject(),this,context);
                    map.put(at_device.getName(), at_device);
                    break;

            }
        }

        return map;
    }
}
