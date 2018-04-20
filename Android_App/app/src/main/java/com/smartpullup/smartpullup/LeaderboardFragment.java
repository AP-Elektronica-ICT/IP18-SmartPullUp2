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
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
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

        lineTotalPullups = (LineChart)view.findViewById(R.id.lnChart);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Users/"+host.currentUser.getId()+"/exercises");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    pullups.add(new Entry(d.getValue(Exercise.class).getDate().getTime(), d.getValue(Exercise.class).getTotalPullups()));
                }
                LineDataSet totalPullups = new LineDataSet(pullups,"line");
                lineTotalPullups.setData(new LineData(totalPullups));
                lineTotalPullups.notifyDataSetChanged();
                lineTotalPullups.invalidate();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
/*        for(int i = 2; i < 7; i++){
            Calendar c = Calendar.getInstance();
            c.set(2018, 4, i);
            exercises.add(new Exercise(c.getTime(), 10.2, 14.2, 102.0, 10));
        }

        DatabaseReference databaseReference2 = database.getReference("Users/" + host.currentUser.getId());
        databaseReference2.child("exercises").setValue(exercises);
*/
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
        lineTotalPullups.getDescription().setText("Total pullups");

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
