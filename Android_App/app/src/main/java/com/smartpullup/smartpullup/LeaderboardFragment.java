package com.smartpullup.smartpullup;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by bjorn on 1/03/2018.
 */

public class LeaderboardFragment extends Fragment {
    private static final String TAG = "FragmentLeaderboard";

    private MainActivity host;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private List<Entry> pullups;
    private List<Entry> maxSpeeds;
    //private List<Exercise> exercises;

    LineChart lineTotalPullups;
    LineChart lineMaxSpeed;
    List<LineChart> lineCharts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM");
        pullups = new ArrayList<>();
        maxSpeeds = new ArrayList<>();
        //exercises = new ArrayList<>();

        lineCharts = new ArrayList<>();
        lineTotalPullups = (LineChart)view.findViewById(R.id.lineTotalPullups);
        lineMaxSpeed = (LineChart)view.findViewById(R.id.lineMaxSpeed);
        lineCharts.add(lineTotalPullups);
        lineCharts.add(lineMaxSpeed);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Users/"+host.currentUser.getId()+"/exercises");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    pullups.add(new Entry(d.getValue(Exercise.class).getDate().getTime(), d.getValue(Exercise.class).getTotalPullups()));
                    maxSpeeds.add(new Entry(d.getValue(Exercise.class).getDate().getTime(), ((float) d.getValue(Exercise.class).getMaxSpeed())));
                }
                setLineChart(pullups, lineTotalPullups);
                setLineChart(maxSpeeds, lineMaxSpeed);
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
        
        for(LineChart lc : lineCharts){
            XAxis xAxis = lc.getXAxis();
            xAxis.setValueFormatter(axisValueFormatter);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            YAxis yAxis = lc.getAxisLeft();
            yAxis.setGranularity(1.0f);
            yAxis.setGranularityEnabled(true);
            yAxis.setAxisMinimum(0f);
            lc.getAxisRight().setDrawGridLines(false);
            lc.getLegend().setEnabled(false);
            lc.getAxisRight().setDrawLabels(false);
            lc.getDescription().setText("");
            lc.setExtraOffsets(5f,35f,5f,10f);
        }
        
        return view;
    }

    private void setLineChart(List<Entry> entries, LineChart chart) {
        LineDataSet lineDataSet = new LineDataSet(entries,"line");
        lineDataSet.setDrawValues(false);
        chart.setData(new LineData(lineDataSet));
        calculateMinMax(chart, chart.getAxisLeft().getLabelCount());
        chart.notifyDataSetChanged();
        chart.invalidate();
    }

    private void calculateMinMax(LineChart chart, int labelCount) {
        float maxValue = chart.getData().getYMax();
        float minValue = chart.getData().getYMin();

        if ((maxValue - minValue) < labelCount) {
            float diff = labelCount - (maxValue - minValue);
            maxValue += diff;
            minValue -= diff;
            chart.getAxisLeft().setAxisMaximum(maxValue);
            chart.getAxisLeft().setAxisMinimum(minValue);
        }
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
