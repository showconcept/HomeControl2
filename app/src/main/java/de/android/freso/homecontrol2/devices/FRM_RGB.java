package de.android.freso.homecontrol2.devices;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.ValueBar;

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

    private static final String KEY_COLOR_1 = "settings_frm_rgb_color1";
    private static final String KEY_COLOR_2 = "settings_frm_rgb_color2";
    private static final String KEY_COLOR_3 = "settings_frm_rgb_color3";
    private static final String KEY_COLOR_4 = "settings_frm_rgb_color4";
    private static final String KEY_COLOR_5 = "settings_frm_rgb_color5";
    private static final String KEY_COLOR_6 = "settings_frm_rgb_color6";

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
        return null;
    }

    private String colorBerechnen(int rot, int gruen, int blau) {
        String farbeString = String.format("%02x%02x%02x", rot, gruen, blau);
        int colorInt = Color.parseColor("#"+farbeString);
        layoutColor.setBackgroundColor(colorInt);

        return farbeString;
    }

    private int getIntensität() {
        JsonObject pctObject = readings.getAsJsonObject("pct");
        int pct = pctObject.get("Value").getAsInt();
        return pct;
    }

    private String getColorString() {
        String colorString = "#" + internals.get("STATE").getAsString();
        return colorString;
    }

    private int getColorInt() {
        return Color.parseColor(getColorString());
    }

    private int getColorInt(String color) {
        return Color.parseColor(color);
    }

    private int getStandardColorInt() {
        String colorString = "#" + attr.get("comment").getAsString();
        return Color.parseColor(colorString);
    }

    private void colorSenden(int color) {
        int rot = Color.red(color);
        int gruen = Color.green(color);
        int blau = Color.blue(color);
        String colorString = String.format("%02x%02x%02x", rot, gruen, blau);
        server.sendeBefehl(server, "set " + getName() + " rgb " + colorString);
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
        View v = inflater.inflate(R.layout.cardviews, null);

        Card card = new Card(context);
        CardHeader header = new CardHeader(context);
        card.addCardHeader(header);

        CardView cardView = (CardView) v.findViewById(R.id.cardview);
        card.setInnerLayout(R.layout.frm_rgb_layout);

        header.setTitle(getName());
        cardView.setCard(card);


        final View v2 = cardView.getInternalContentLayout();
        final GridLayout grid_color_buttons = (GridLayout) v2.findViewById(R.id.grid_color_buttons);
        final ColorPicker colorPicker = (ColorPicker) v2.findViewById(R.id.colorPicker);
        final ValueBar valueBar = (ValueBar) v2.findViewById(R.id.valueBar);
        final CircleButton btnColor1 = (CircleButton) v2.findViewById(R.id.btn_color_1);
        final CircleButton btnColor2 = (CircleButton) v2.findViewById(R.id.btn_color_2);
        final CircleButton btnColor3 = (CircleButton) v2.findViewById(R.id.btn_color_3);
        final CircleButton btnColor4 = (CircleButton) v2.findViewById(R.id.btn_color_4);
        final CircleButton btnColor5 = (CircleButton) v2.findViewById(R.id.btn_color_5);
        final CircleButton btnColor6 = (CircleButton) v2.findViewById(R.id.btn_color_6);

        final SharedPreferences settings = context.getSharedPreferences(context.getApplicationInfo().name, Context.MODE_PRIVATE);
        final SharedPreferences.Editor settingsEdit = settings.edit();

        btnColor1.setColor(settings.getInt(KEY_COLOR_1, getColorInt("#000000")));
        btnColor2.setColor(settings.getInt(KEY_COLOR_2, getColorInt("#000000")));
        btnColor3.setColor(settings.getInt(KEY_COLOR_3, getColorInt("#000000")));
        btnColor4.setColor(settings.getInt(KEY_COLOR_4, getColorInt("#000000")));
        btnColor5.setColor(settings.getInt(KEY_COLOR_5, getColorInt("#000000")));
        btnColor6.setColor(settings.getInt(KEY_COLOR_6, getColorInt("#000000")));

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

        colorPicker.addValueBar(valueBar);
        colorPicker.setColor(getColorInt());
        colorPicker.setOldCenterColor(getStandardColorInt());

        colorPicker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int i) {
               colorSenden(i);
            }
        });

        btnColor1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                btnColor1.setColor(colorPicker.getColor());
                settingsEdit.putInt(KEY_COLOR_1, colorPicker.getColor());
                settingsEdit.commit();
                Toast.makeText(context, "Gespeichert", Toast.LENGTH_SHORT).show();
                mainActivity.viewAktualisieren();
                return false;
            }
        });
        btnColor2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                btnColor2.setColor(colorPicker.getColor());
                settingsEdit.putInt(KEY_COLOR_2, colorPicker.getColor());
                settingsEdit.commit();
                Toast.makeText(context, "Gespeichert", Toast.LENGTH_SHORT).show();
                mainActivity.viewAktualisieren();
                return false;
            }
        });
        btnColor3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                btnColor3.setColor(colorPicker.getColor());
                settingsEdit.putInt(KEY_COLOR_3, colorPicker.getColor());
                settingsEdit.commit();
                Toast.makeText(context, "Gespeichert", Toast.LENGTH_SHORT).show();
                mainActivity.viewAktualisieren();
                return false;
            }
        });
        btnColor4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                btnColor4.setColor(colorPicker.getColor());
                settingsEdit.putInt(KEY_COLOR_4, colorPicker.getColor());
                settingsEdit.commit();
                Toast.makeText(context, "Gespeichert", Toast.LENGTH_SHORT).show();
                mainActivity.viewAktualisieren();
                return false;
            }
        });
        btnColor5.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                btnColor5.setColor(colorPicker.getColor());
                settingsEdit.putInt(KEY_COLOR_5, colorPicker.getColor());
                settingsEdit.commit();
                Toast.makeText(context, "Gespeichert", Toast.LENGTH_SHORT).show();
                mainActivity.viewAktualisieren();
                return false;
            }
        });
        btnColor6.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                btnColor6.setColor(colorPicker.getColor());
                settingsEdit.putInt(KEY_COLOR_6, colorPicker.getColor());
                settingsEdit.commit();
                Toast.makeText(context, "Gespeichert", Toast.LENGTH_SHORT).show();
                mainActivity.viewAktualisieren();
                return false;
            }
        });

        btnColor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorSenden(settings.getInt(KEY_COLOR_1, 0));
                colorPicker.setColor(settings.getInt(KEY_COLOR_1, 0));
            }
        });
        btnColor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorSenden(settings.getInt(KEY_COLOR_2, 0));
                colorPicker.setColor(settings.getInt(KEY_COLOR_2, 0));
            }
        });
        btnColor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorSenden(settings.getInt(KEY_COLOR_3, 0));
                colorPicker.setColor(settings.getInt(KEY_COLOR_3, 0));
            }
        });
        btnColor4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorSenden(settings.getInt(KEY_COLOR_4, 0));
                colorPicker.setColor(settings.getInt(KEY_COLOR_4, 0));
            }
        });
        btnColor5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorSenden(settings.getInt(KEY_COLOR_5, 0));
                colorPicker.setColor(settings.getInt(KEY_COLOR_5, 0));
            }
        });
        btnColor6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorSenden(settings.getInt(KEY_COLOR_6, 0));
                colorPicker.setColor(settings.getInt(KEY_COLOR_6, 0));
            }
        });

        return v;
    }
}
