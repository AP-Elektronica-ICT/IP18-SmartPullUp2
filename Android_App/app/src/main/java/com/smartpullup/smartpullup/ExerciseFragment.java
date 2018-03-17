package com.smartpullup.smartpullup;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Jorren on 22/02/2018.
 */

public class ExerciseFragment extends android.support.v4.app.Fragment {
    private static final String TAG = "FragmentExcercise";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_exercise, container, false);

        LayoutInflater lf = getActivity().getLayoutInflater();

        View view =  lf.inflate(R.layout.fragment_exercise, container, false);
        TextView textView = (TextView) view.findViewById(R.id.pullUpCounter_textView);
        textView.setText("hello");//


        return view;
    }
}
