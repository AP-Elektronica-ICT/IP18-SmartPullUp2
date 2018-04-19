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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    private List<Entry> pullups;
    private List<Exercise> exercises;

    BarChart barChart;
    LineChart lineTotalPullups;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM");
        pullups = new ArrayList<>();
        exercises = new ArrayList<>();
        final LineDataSet totalPullups = new LineDataSet(null,"line");

        lineTotalPullups = (LineChart)view.findViewById(R.id.lnChart);
        lineTotalPullups.setData(new LineData());

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Users/"+host.currentUser.getId()+"/exercises");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Exercise e = dataSnapshot.getValue(Exercise.class);
                if(e != null){
                    if(lineTotalPullups.getData() == null){
                        lineTotalPullups.setData(new LineData(totalPullups));
                        lineTotalPullups.getDescription().setText("Total pullups");
                    }

                    if(exercises.size() > 0){
                        boolean ok = true;
                        for(Exercise ex: exercises){
                            if(dateFormat.format(e.getDate().getTime()).equals(dateFormat.format(ex.getDate().getTime()))){
                               ok = false;
                                Log.i(TAG, "onChildAdded: new exercise was from same day");
                            }
                        }
                        if(ok){
                            lineTotalPullups.moveViewTo((float)(lineTotalPullups.getData().getEntryCount() - 7), 20f, YAxis.AxisDependency.LEFT);
                            addEntryToLineDataSet(e, totalPullups);
                        }

                    }
                    //else
                        //addEntryToLineDataSet(e, totalPullups);
                    exercises.add(e);
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
        //dates = new ArrayList<>();
        //int index = 0;
        //for(Exercise e : exercises){
            //pullups.add(new Entry(e.getDate().getTime(), e.getTotalPullups()));
            //index++;
            //dates.add(dateFormat.format(e.getDate()));
        //}

        IAxisValueFormatter axisValueFormatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return dateFormat.format(new Date((long)value));
            }
        };
        XAxis xAxis = lineTotalPullups.getXAxis();
        xAxis.setValueFormatter(axisValueFormatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        lineTotalPullups.getLegend().setEnabled(false);
        lineTotalPullups.getAxisRight().setDrawLabels(false);
/*
        totalPullups.addEntry(new Entry(new Date(System.currentTimeMillis() - 3 * 86400500).getTime(), 15));
        totalPullups.addEntry(new Entry(new Date(System.currentTimeMillis() - 4 * 86400600).getTime(), 16));
        totalPullups.addEntry(new Entry(new Date(System.currentTimeMillis() - 5 * 86400700).getTime(), 17));
        totalPullups.addEntry(new Entry(new Date(System.currentTimeMillis() - 6 * 86400800).getTime(), 20));
        totalPullups.addEntry(new Entry(new Date(System.currentTimeMillis() - 7 * 86400900).getTime(), 21));
        totalPullups.addEntry(new Entry(new Date(System.currentTimeMillis() - 8 * 86401000).getTime(), 22));
        totalPullups.addEntry(new Entry(new Date(System.currentTimeMillis() - 9 * 86401100).getTime(), 25));
        totalPullups.addEntry(new Entry(new Date(System.currentTimeMillis() - 10 * 86401200).getTime(), 30));
        totalPullups.notifyDataSetChanged();
        lineTotalPullups.notifyDataSetChanged();
        lineTotalPullups.invalidate();
*/
        return view;
    }

    private void addEntryToLineDataSet(Exercise e, LineDataSet totalPullups) {
        totalPullups.addEntry(new Entry(e.getDate().getTime(), e.getTotalPullups()));
        totalPullups.notifyDataSetChanged();
        lineTotalPullups.notifyDataSetChanged();
        lineTotalPullups.invalidate();
        Log.i(TAG, "onChildAdded: new exercise");
    }

    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "Pullups");
        set.setLineWidth(2.5f);
        set.setCircleRadius(4.5f);
        set.setColor(Color.rgb(240, 99, 99));
        set.setCircleColor(Color.rgb(240, 99, 99));
        set.setHighLightColor(Color.rgb(190, 190, 190));
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setValueTextSize(10f);

        return set;
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
