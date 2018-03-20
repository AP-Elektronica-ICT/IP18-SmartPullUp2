package com.smartpullup.smartpullup;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jorren on 22/02/2018.
 */

public class ExerciseFragment extends Fragment {
    private static final String TAG = "FragmentExcercise";

    JSONBroadcastReceiver JSONBroadcastReceiver;
    //SharedPreferences getSharedPreferences;

    public static final String MY_PREFS_NAME = "MyPrefsFile";

    private TextView txt_PullupSpeed;
    private TextView txt_PullupAverageSpeed;
    private TextView txt_TotalTime;

    private double pullupSpeed;
    public double getPullupSpeed() {
        return pullupSpeed;
    }
    public void setPullupSpeed(double pullupSpeed) {
        this.pullupSpeed = pullupSpeed;
        pullupSpeeds.add(pullupSpeed);
        updateUI();
    }

    private List<Double> pullupSpeeds;

    //for testing pullupTiming
    private long prevTime;

    private TextView textView;
    private View view;
    private String type;
    private int up;

    String message = "";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_MULTI_PROCESS);
        type = prefs.getString("type", "");
        up = prefs.getInt("up", 0);

        pullupSpeeds = new ArrayList<>();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_exercise, container, false);

        txt_PullupSpeed = (TextView)view.findViewById(R.id.txt_PullupSpeed);
        txt_PullupAverageSpeed = (TextView)view.findViewById(R.id.txt_PullupAverageSpeed);
        txt_TotalTime = (TextView)view.findViewById(R.id.txt_TotalTime);

        Button pullupButton = (Button)view.findViewById(R.id.test_pullup);
        prevTime = System.currentTimeMillis();
        pullupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long time = (System.currentTimeMillis() - prevTime) / 1000;
                setPullupSpeed((double)time);
                prevTime = System.currentTimeMillis();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    public void setText(String test){
        //t = (TextView) view.findViewById(R.id.pullUpCounter_textView);
        //t.setText(test);
    }

    private void updateUI() {
        txt_PullupSpeed.setText(Double.toString(pullupSpeed));
        txt_PullupAverageSpeed.setText(Double.toString(calculateAverage()));
    }

    private double calculateAverage() {
        int sum = 0;
        for (double i:pullupSpeeds) {
            sum += i;
        }
        return sum / pullupSpeeds.size();
    }
}
