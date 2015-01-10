package de.android.freso.homecontrol2;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import java.util.ArrayList;

import de.android.freso.homecontrol2.devices.FhemDevice;


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
        ArrayList<FhemDevice> list = server.getAllDevices();

        content.removeAllViewsInLayout();

        for(int i=0; i < list.size(); i++) {
            FhemDevice device = list.get(i);
            content.addView(device.getView(getLayoutInflater()));
        }
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
