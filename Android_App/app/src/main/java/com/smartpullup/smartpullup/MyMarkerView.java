package com.smartpullup.smartpullup;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import org.w3c.dom.Text;

/**
 * Created by Jorren on 21/04/2018.
 */

public class MyMarkerView extends MarkerView {

    private TextView txt_detail;
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public MyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        txt_detail = findViewById(R.id.txt_detail);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        super.refreshContent(e, highlight);
        txt_detail.setText((Float.toString(e.getY())));
    }

    @Override
    public MPPointF getOffsetForDrawingAtPoint(float posX, float posY) {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
