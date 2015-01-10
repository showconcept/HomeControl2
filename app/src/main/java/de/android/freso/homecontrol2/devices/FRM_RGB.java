package de.android.freso.homecontrol2.devices;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import de.android.freso.homecontrol2.FhemServer;
import de.android.freso.homecontrol2.MainActivity;
import de.android.freso.homecontrol2.R;

/**
 * Created by Patrick on 08.01.2015.
 */
public class FRM_RGB extends FhemDevice {

    private String colorString;
    private FrameLayout layoutColor;
    private MainActivity mainActivity;

    public FRM_RGB(JsonObject object, FhemServer server, Context context) {
        super(object, server, context);
        mainActivity = (MainActivity) context;
        colorString = internals.get("STATE").getAsString();
    }

    @Override
    public View getView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.frm_rgb_layout, null);

        TextView tvName = (TextView) v.findViewById(R.id.tv_name);
        Button btnColor1 = (Button) v.findViewById(R.id.btnColor1);
        Button btnColor2 = (Button) v.findViewById(R.id.btnColor2);
        Button btnColor3 = (Button) v.findViewById(R.id.btnColor3);
        Button btnColor4 = (Button) v.findViewById(R.id.btnColor4);
        Button btnAus = (Button) v.findViewById(R.id.btn_aus);
        SeekBar seekRot = (SeekBar) v.findViewById(R.id.seekBar_rot);
        SeekBar seekGruen = (SeekBar) v.findViewById(R.id.seekBar_gruen);
        SeekBar seekBlau = (SeekBar) v.findViewById(R.id.seekBar_blau);
        layoutColor = (FrameLayout) v.findViewById(R.id.layoutColor);

        int colorInt = Color.parseColor("#"+colorString);
        layoutColor.setBackgroundColor(colorInt);

        seekRot.setProgress(Color.red(colorInt));
        seekGruen.setProgress(Color.green(colorInt));
        seekBlau.setProgress(Color.blue(colorInt));

        btnColor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                server.sendeBefehl(server, "set " + getName() + " rgb D00366");
                mainActivity.viewAktualisieren();
            }
        });

        btnColor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                server.sendeBefehl(server, "set " + getName() + " rgb 03CAD0");
                mainActivity.viewAktualisieren();
            }
        });

        btnColor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                server.sendeBefehl(server, "set " + getName() + " rgb 5A08FD");
                mainActivity.viewAktualisieren();
            }
        });

        btnColor4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                server.sendeBefehl(server, "set " + getName() + " rgb 3CCE5E");
                mainActivity.viewAktualisieren();
            }
        });

        btnAus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                server.sendeBefehl(server, "set " + getName() + " off");
                mainActivity.viewAktualisieren();
            }
        });

        MySeekBarChangeListener listener = new MySeekBarChangeListener(seekRot, seekGruen, seekBlau);

        seekRot.setOnSeekBarChangeListener(listener);
        seekGruen.setOnSeekBarChangeListener(listener);
        seekBlau.setOnSeekBarChangeListener(listener);

        tvName.setText(getName());
        return v;
    }

    private String colorBerechnen(int rot, int gruen, int blau) {
        String farbeString = String.format("%02x%02x%02x", rot, gruen, blau);
        int colorInt = Color.parseColor("#"+farbeString);
        layoutColor.setBackgroundColor(colorInt);
        //Toast.makeText(context, farbeString, Toast.LENGTH_LONG).show();

        return farbeString;
    }


    private class MySeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        private SeekBar seekRot, seekGruen, seekBlau;

        MySeekBarChangeListener(SeekBar seekRot, SeekBar seekGruen, SeekBar seekBlau) {
            this.seekBlau = seekBlau;
            this.seekGruen = seekGruen;
            this.seekRot = seekRot;
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            String farbe = colorBerechnen(seekRot.getProgress(), seekGruen.getProgress(), seekBlau.getProgress());
            server.sendeBefehl(server, "set " + getName() + " rgb " + farbe);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}
