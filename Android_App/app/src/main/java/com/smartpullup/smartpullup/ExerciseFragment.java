package com.smartpullup.smartpullup;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.BroadcastReceiver;

import org.json.JSONObject;

import java.io.Console;

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

    private int pullupSpeed;
    public int getPullupSpeed() {
        return pullupSpeed;
    }
    public void setPullupSpeed(int pullupSpeed) {
        this.pullupSpeed = pullupSpeed;
        updateUI();
    }

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

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_exercise, container, false);

        txt_PullupSpeed = (TextView)view.findViewById(R.id.txt_PullupSpeed);
        txt_PullupAverageSpeed = (TextView)view.findViewById(R.id.txt_PullupAverageSpeed);
        txt_TotalTime = (TextView)view.findViewById(R.id.txt_TotalTime);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    public void setText(String test){
        //t = (TextView) view.findViewById(R.id.pullUpCounter_textView);
        //t.setText(test);
    }

    private void updateUI() {
        txt_PullupSpeed.setText(pullupSpeed);
    }
}
