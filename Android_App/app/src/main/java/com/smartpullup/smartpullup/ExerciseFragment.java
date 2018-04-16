package com.smartpullup.smartpullup;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import pl.pawelkleczkowski.customgauge.CustomGauge;

/**
 * Created by Jorren on 22/02/2018.
 */

public class ExerciseFragment extends Fragment {
    private static final String TAG = "FragmentExcercise";

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private MainActivity host;

    private static final String MY_PREFS_NAME = "DataFromPullUpBar";
    private SharedPreferences prefs;

    private TextView counterUpTextView;
    //private TextView counterDownTextView;
    private TextView weightTextView;
    //private TextView machineID_TextView;
    private TextView type_TextView;

    private TextView txt_PullupSpeed;
    private TextView txt_PullupAverageSpeed;
    private TextView txt_TotalTime;

    CustomGauge pbCounterUp;
    //ProgressBar pbCounterDown;

    private double pullupSpeed;

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
    private int previousValueUp;
    private int previousValueDown;

     MediaPlayer beepSound;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

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

        counterUpTextView = (TextView) view.findViewById(R.id.pullUpCounter_textView);
        //counterDownTextView = (TextView) view.findViewById(R.id.down_Counter_textView);
        weightTextView = (TextView) view.findViewById(R.id.weight_textView);
        //machineID_TextView = (TextView) view.findViewById(R.id.machien_ID_textView);
        type_TextView = (TextView) view.findViewById(R.id.TypeMesurament_textView);

        pbCounterUp = (CustomGauge) view.findViewById(R.id.progress_pullups);
        //pbCounterDown = (ProgressBar) view.findViewById(R.id.progress_calories);

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

        MeasurementOfExercise();

        return view;
    }

    private void MeasurementOfExercise(){
        prefs.registerOnSharedPreferenceChangeListener(
                new SharedPreferences.OnSharedPreferenceChangeListener() {
                    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {

                        InputData(prefs);
                        CounterUp();
                        CounterDown();
                        updateUI();
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        MeasurementOfExercise();
    }

    private void CounterUp(){
        if(upInput != previousValueUp)
        {
            counterUp++;
            beepSound =MediaPlayer.create(getActivity(),R.raw.beep);
            beepSound.start();
            calculateSpeed();
            previousValueUp = upInput;
        }else if (upInput == 0){
            counterUp = 0;
        }
    }

    private void CounterDown(){
        if(downInput != previousValueDown)
        {
            counterDown++;
            previousValueDown = downInput;
        }else if (downInput == 0){
            counterDown = 0;
        }
    }

    public void setPullupSpeed(double pullupSpeed) {
        this.pullupSpeed = pullupSpeed;
        pullupSpeeds.add(pullupSpeed);
    }

    private void InputData(SharedPreferences prefs) {
        typeInput = prefs.getString("type", "");
        upInput = prefs.getInt("up", 0);
        downInput = prefs.getInt("down", 0);
        Log.i(TAG, String.valueOf("Up: " + upInput + "Down: " + downInput));
        //machine_ID_Input = prefs.getInt("machine_ID", 0);
        weightInput = prefs.getInt("weight", 0);
    }

    private void updateUI() {
        txt_PullupSpeed.setText("Speed: " + String.format("%.2f", pullupSpeed) + " s");
        txt_PullupAverageSpeed.setText("Average Speed: " + String.format("%.2f", calculateAverage()) + " s");
        txt_TotalTime.setText("duration: " + Double.toString(upInput / 1000.0) + " s");
        counterUpTextView.setText(Integer.toString(counterUp));
        //counterDownTextView.setText(Integer.toString(counterDown));
        weightTextView.setText(Double.toString(weightInput) + " kg");
        //machineID_TextView.setText(Integer.toString(machine_ID_Input));
        type_TextView.setText(typeInput);

        pbCounterUp.setValue(counterUp);
        
    }

    private double calculateAverage() {
        if(pullupSpeeds.size() == 0)
            return 0;
        double sum = 0;
        for (double i:pullupSpeeds) {
            sum += i;
        }
        return sum / pullupSpeeds.size();
    }

    private void calculateSpeed() {
        setPullupSpeed((upInput - previousValueUp)/1000.0);
    }

    private void PushExercise(){
        double maxSpeed = 0;
        for (double speed : pullupSpeeds){
            if(speed > maxSpeed)
                maxSpeed = speed;
        }

        Exercise e = new Exercise(maxSpeed, calculateAverage(), upInput / 1000, counterUp);
        host.currentUser.getExercises().add(e);
        databaseReference.child("Users").child(host.currentUser.getId()).child("Exercises").setValue(host.currentUser.getExercises());
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
