package com.smartpullup.smartpullup;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.SimpleDateFormat;

/**
 * Created by Jorren on 21/04/2018.
 */

public class TimeMarkerView extends MarkerView {

    private TextView txt_date;
    private TextView txt_time;
    private SimpleDateFormat sdf;

    public TimeMarkerView(Context context) {
        super(context, R.layout.markerview_time);
        sdf = new SimpleDateFormat("dd/MM HH:mm");

        txt_date = findViewById(R.id.txt_date);
        txt_time = findViewById(R.id.txt_time);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        super.refreshContent(e, highlight);
        txt_date.setText((sdf.format(e.getX())));
        if(txt_time != null)
            txt_time.setText(converTime((int)e.getY()));

    }

    private String converTime(int time){
        if(time < 60)
            return Integer.toString(time) + "s";
        else
            return Integer.toString(time / 60) + "m" + converTime(time % 60);
    }

    @Override
    public MPPointF getOffsetForDrawingAtPoint(float posX, float posY) {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
