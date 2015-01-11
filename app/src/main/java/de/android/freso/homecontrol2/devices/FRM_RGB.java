package de.android.freso.homecontrol2.devices;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import com.google.gson.JsonObject;

import at.markushi.ui.CircleButton;
import de.android.freso.homecontrol2.FhemServer;
import de.android.freso.homecontrol2.MainActivity;
import de.android.freso.homecontrol2.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardView;

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
//        View v = inflater.inflate(R.layout.frm_rgb_layout, null);
//
//        //TextView tvName = (TextView) v.findViewById(R.id.tv_name);
//        Button btnColor1 = (Button) v.findViewById(R.id.btnColor1);
//        Button btnColor2 = (Button) v.findViewById(R.id.btnColor2);
//        Button btnColor3 = (Button) v.findViewById(R.id.btnColor3);
//        Button btnColor4 = (Button) v.findViewById(R.id.btnColor4);
//        Button btnAus = (Button) v.findViewById(R.id.btn_aus);
//        SeekBar seekRot = (SeekBar) v.findViewById(R.id.seekBar_rot);
//        SeekBar seekGruen = (SeekBar) v.findViewById(R.id.seekBar_gruen);
//        SeekBar seekBlau = (SeekBar) v.findViewById(R.id.seekBar_blau);
//        layoutColor = (FrameLayout) v.findViewById(R.id.layoutColor);
//
//        int colorInt = Color.parseColor("#"+colorString);
//        layoutColor.setBackgroundColor(colorInt);
//
//        seekRot.setProgress(Color.red(colorInt));
//        seekGruen.setProgress(Color.green(colorInt));
//        seekBlau.setProgress(Color.blue(colorInt));
//
//        btnColor1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                server.sendeBefehl(server, "set " + getName() + " rgb D00366");
//                mainActivity.viewAktualisieren();
//            }
//        });
//
//        btnColor2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                server.sendeBefehl(server, "set " + getName() + " rgb 03CAD0");
//                mainActivity.viewAktualisieren();
//            }
//        });
//
//        btnColor3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                server.sendeBefehl(server, "set " + getName() + " rgb 5A08FD");
//                mainActivity.viewAktualisieren();
//            }
//        });
//
//        btnColor4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                server.sendeBefehl(server, "set " + getName() + " rgb 3CCE5E");
//                mainActivity.viewAktualisieren();
//            }
//        });
//
//        btnAus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                server.sendeBefehl(server, "set " + getName() + " off");
//                mainActivity.viewAktualisieren();
//            }
//        });
//
//        MySeekBarChangeListener listener = new MySeekBarChangeListener(seekRot, seekGruen, seekBlau);
//
//        seekRot.setOnSeekBarChangeListener(listener);
//        seekGruen.setOnSeekBarChangeListener(listener);
//        seekBlau.setOnSeekBarChangeListener(listener);
//
//        //tvName.setText(getName());
        return null;
    }

    private String colorBerechnen(int rot, int gruen, int blau) {
        String farbeString = String.format("%02x%02x%02x", rot, gruen, blau);
        int colorInt = Color.parseColor("#"+farbeString);
        layoutColor.setBackgroundColor(colorInt);

        return farbeString;
    }

    private void fillView(GridLayout grid) {
        int ideal;
        CircleButton button;

        int idealWidth = (int) (grid.getWidth() / grid.getColumnCount());
        int idealHeight = (int) (grid.getHeight() / grid.getRowCount());

        if(idealHeight < idealWidth) {
            ideal = idealHeight;
        } else {
            ideal = idealWidth;
        }

        for(int i=0; i < grid.getChildCount(); i++) {
            FrameLayout frame = (FrameLayout) grid.getChildAt(i);
            button = (CircleButton) frame.getChildAt(0);
            float multi = context.getResources().getDisplayMetrics().density / 2;

            button.setLayoutParams(new FrameLayout.LayoutParams((int) (ideal * multi), (int) (ideal * multi)));

        }
    }




    public View getCard(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.cardview, null);

        Card card = new Card(context);
        CardHeader header = new CardHeader(context);
        card.addCardHeader(header);

        CardView cardView = (CardView) v.findViewById(R.id.cardview);
        card.setInnerLayout(R.layout.frm_rgb_layout);

        header.setTitle(getName());
        cardView.setCard(card);

        final View v2 = cardView.getInternalContentLayout();
        GridLayout grid_color_buttons = (GridLayout) v2.findViewById(R.id.grid_color_buttons);


        ViewTreeObserver vto = grid_color_buttons.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                GridLayout grid_color_buttons = (GridLayout) v2.findViewById(R.id.grid_color_buttons);
                fillView(grid_color_buttons);
                ViewTreeObserver vto = grid_color_buttons.getViewTreeObserver();
                vto.removeOnGlobalLayoutListener(this);
            }
        });




        return v;
    }
}
