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

    private static final String MY_PREFS_NAME = "DataFromPullUpBar";
    private SharedPreferences prefs;

    private TextView counterUpTextView;
    private TextView counterDownTextView;
    private TextView weightTextView;
    private TextView machineID_TextView;
    private TextView type_TextView;

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

    private TextView textView;
    private View view;

    private String typeInput;
    private int upInput;
    private int downInput;
    private int machine_ID_Input;
    private double weightInput;


    private int counterUp = 0;
    private int counterDown = 0;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getContext().getSharedPreferences(MY_PREFS_NAME, Context.MODE_MULTI_PROCESS);

        pullupSpeeds = new ArrayList<>();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_exercise, container, false);

        txt_PullupSpeed = (TextView)view.findViewById(R.id.txt_PullupSpeed);
        txt_PullupAverageSpeed = (TextView)view.findViewById(R.id.txt_PullupAverageSpeed);
        txt_TotalTime = (TextView)view.findViewById(R.id.txt_TotalTime);
/*
        Button pullupButton = (Button)view.findViewById(R.id.test_pullup);
        prevTime = System.currentTimeMillis();
        pullupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long time = (System.currentTimeMillis() - prevTime) / 1000;
                setPullupSpeed((double)time);
                prevTime = System.currentTimeMillis();
            }
        });*/

        prefs.registerOnSharedPreferenceChangeListener(
                new SharedPreferences.OnSharedPreferenceChangeListener() {
                    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {

                        InputData(prefs);

                        CounterUp();
                        CounterDown();

                        SetTextUpCounter(String.valueOf(counterUp));
                        SetTextDownCounter(String.valueOf(counterDown));
                        SetTextUpMachineID(String.valueOf(machine_ID_Input));
                        SetTextUpTypeMesurament(typeInput);
                        SetTextWeight(String.valueOf(weightInput));

                    }
                });

        return view;
    }


    private void CounterUp(){
        final int previousValue = upInput;

        if(previousValue != counterUp)
        {
            counterUp++;
        }
    }


    private void CounterDown(){
        final int previousValue = downInput;

        if(previousValue != downInput)
        {
            counterDown++;
        }
    }

    private void InputData(SharedPreferences prefs) {
        typeInput = prefs.getString("type", "");
        upInput = prefs.getInt("up", 0);
        downInput = prefs.getInt("down", 0);
        machine_ID_Input = prefs.getInt("machine_ID", 0);
        weightInput = prefs.getInt("weight", 0);
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

    private void SetTextUpCounter(String text) {
        counterUpTextView = (TextView) view.findViewById(R.id.pullUpCounter_textView);
        counterUpTextView.setText(text);
    }

    private void SetTextDownCounter(String text){
        counterDownTextView = (TextView) view.findViewById(R.id.down_Counter_textView);
        counterDownTextView.setText(text);
    }

    private void SetTextWeight(String text){
        weightTextView = (TextView) view.findViewById(R.id.weight_textView);
        weightTextView.setText(text);
    }

    private void SetTextUpMachineID(String text){
        machineID_TextView = (TextView) view.findViewById(R.id.machien_ID_textView);
        machineID_TextView.setText(text);
    }

    private void SetTextUpTypeMesurament(String text){
        type_TextView = (TextView) view.findViewById(R.id.TypeMesurament_textView);
        type_TextView.setText(text);
    }





}
