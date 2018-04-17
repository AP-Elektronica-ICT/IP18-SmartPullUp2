package com.smartpullup.smartpullup;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


/**
 * Created by bjorn on 1/03/2018.
 */

public class LeaderboardFragment extends Fragment {
    private static final String TAG = "FragmentLeaderboard";

    private MainActivity host;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;


    private List<Exercise> exercises;
    private List<Entry> pullups;
    //private List<String> dates;

    BarChart barChart;
    LineChart lineChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Users/"+host.currentUser.getId()+"/exercises");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Exercise e = dataSnapshot.getValue(Exercise.class);
                if(e != null){
                    //pullups.add(new Entry(e.getDate().getTime(), e.getTotalPullups()));

                    LineDataSet setline = new LineDataSet(pullups,"line");
                    setline.addEntry(new Entry(e.getDate().getTime(), e.getTotalPullups()));
                    LineData data2 = new LineData(setline);
                    lineChart.getDescription().setText("Total pullups");
                    lineChart.notifyDataSetChanged();
                    lineChart.invalidate();
                    Log.i(TAG, "onChildAdded: new exercise");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //exercises = host.currentUser.getExercises();
/*        Collections.sort(exercises, new Comparator<Exercise>() {
            @Override
            public int compare(Exercise e1, Exercise e2) {
                if(e1.getDate() == null || e2.getDate() == null)
                    return 0;
                return e1.getDate().compareTo(e2.getDate());
            }
        });
*/
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM");
        pullups = new ArrayList<>();
/*        pullups.add(new Entry(new Date(2018, 6, 2).getTime(), 15));
        pullups.add(new Entry(new Date(2018, 6, 3).getTime(), 16));
        pullups.add(new Entry(new Date(2018, 6, 4).getTime(), 17));
        pullups.add(new Entry(new Date(2018, 6, 5).getTime(), 20));
        pullups.add(new Entry(new Date(2018, 6, 6).getTime(), 21));
        pullups.add(new Entry(new Date(2018, 6, 7).getTime(), 22));
        pullups.add(new Entry(new Date(2018, 6, 8).getTime(), 25));
        pullups.add(new Entry(new Date(2018, 6, 9).getTime(), 30));
*/        //dates = new ArrayList<>();
        //int index = 0;
        //for(Exercise e : exercises){
            //pullups.add(new Entry(e.getDate().getTime(), e.getTotalPullups()));
            //index++;
            //dates.add(dateFormat.format(e.getDate()));
        //}

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

        IAxisValueFormatter axisValueFormatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return dateFormat.format(new Date((long)value));
            }
        };
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(axisValueFormatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        BarDataSet set = new BarDataSet(barEntries,"bar");
        LineDataSet setline = new LineDataSet(pullups,"line");
        set.setColor(Color.BLUE);
        set.setDrawValues(true);
        BarData data = new BarData(set);
        LineData data2 = new LineData(setline);
        barChart.setData(data);
        lineChart.setData(data2);
        lineChart.getLegend().setEnabled(false);
        lineChart.getAxisRight().setDrawLabels(false);
        if(pullups.size() > 0)
            lineChart.getDescription().setText("Total pullups");
        else
            lineChart.getDescription().setText("Total pullups (no data yet)");

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
