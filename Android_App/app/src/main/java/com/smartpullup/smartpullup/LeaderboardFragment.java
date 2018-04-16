package com.smartpullup.smartpullup;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Random;


/**
 * Created by bjorn on 1/03/2018.
 */

public class LeaderboardFragment extends Fragment {
    private static final String TAG = "FragmentLeaderboard";

    private MainActivity host;

    BarChart barChart;
    LineChart lineChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        barChart = (BarChart) view.findViewById(R.id.graphBar);
        lineChart = (LineChart)view.findViewById(R.id.lnChart);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i=0;i<20;i++) {
            Random ran = new Random();
            int ranY = ran.nextInt(10)+1;
            barEntries.add(new BarEntry(i, ranY));
        }
        ArrayList<Entry> lineEnteries = new ArrayList<>();
        for (int i=0;i<20;i++) {
            Random ran = new Random();
            int ranY = ran.nextInt(10)+1;
            lineEnteries.add(new Entry(i, ranY));
        }

        BarDataSet set = new BarDataSet(barEntries,"bar");
        LineDataSet setline = new LineDataSet(lineEnteries,"line");
        set.setColor(Color.BLUE);
        set.setDrawValues(true);
        BarData data = new BarData(set);
        LineData data2 = new LineData(setline);
        barChart.setData(data);
        lineChart.setData(data2);


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            host = (MainActivity) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "is not MainActivity");
        }
    }
}
