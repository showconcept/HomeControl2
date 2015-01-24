package de.android.freso.homecontrol2;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;

import de.android.freso.homecontrol2.modules.FhemModul;


public class MainActivity extends ActionBarActivity {

    private LinearLayout content, row1, row2;
    private Button aktualisieren;
    private FhemServer server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        content = (LinearLayout) findViewById(R.id.content);
        row1 = (LinearLayout) findViewById(R.id.row1);
        row2 = (LinearLayout) findViewById(R.id.row2);
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
        //content.removeAllViews();
        row1.removeAllViews();
        row2.removeAllViews();

        for(int i=0; i < set.length; i++) {
            FhemModul device = list.get(set[i].toString());
            //content.addView(device.getDetailView(getLayoutInflater()));
            if(device.getOverView(getLayoutInflater()) != null) {
                //content.addView(device.getOverView(getLayoutInflater()));
                if(row1.getChildCount() == row2.getChildCount()) {
                    Toast.makeText(this, "ROW1", Toast.LENGTH_SHORT).show();
                    row1.addView(device.getOverView(getLayoutInflater()));
                } else {
                    row2.addView(device.getOverView(getLayoutInflater()));
                    Toast.makeText(this, "ROW2", Toast.LENGTH_SHORT).show();
                }
            }
            Log.d("Aktualisieren", set[i].toString());
        }

        //CommandHelper.parse(list);
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
