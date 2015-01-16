package de.android.freso.homecontrol2;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.android.freso.homecontrol2.helper.CommandHelper;
import de.android.freso.homecontrol2.modules.FhemModul;


public class MainActivity extends ActionBarActivity {

    private LinearLayout content;
    private Button aktualisieren;
    private FhemServer server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        content = (LinearLayout) findViewById(R.id.content);
        aktualisieren = (Button) findViewById(R.id.btn_aktualisieren);

        server = new FhemServer("192.168.178.20", 8083, this);

        aktualisieren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewAktualisieren();
            }
        });


    }

    public void viewAktualisieren() {
        HashMap<String, FhemModul> list = server.getAllDevices();
        Object[] set = list.keySet().toArray();


        //content.removeAllViewsInLayout();
        content.removeAllViews();

        for(int i=0; i < set.length; i++) {
            FhemModul device = list.get(set[i].toString());
            //content.addView(device.getView(getLayoutInflater()));
            if(device.getCard(getLayoutInflater()) != null) {
                content.addView(device.getCard(getLayoutInflater()));
            }
            Log.d("Aktualisieren", set[i].toString());
        }

        CommandHelper.parse(list);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
