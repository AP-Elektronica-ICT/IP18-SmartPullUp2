package com.smartpullup.smartpullup;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class WorkoutFragment extends Fragment {

    ImageView imageViewWorkout;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_workout, container, false);


        imageViewWorkout = (ImageView) view.findViewById(R.id.workout_imageView);

        imageViewWorkout.setImageResource(R.drawable.workout1);
        imageViewWorkout.setScaleType(ImageView.ScaleType.FIT_CENTER);


        // Inflate the layout for this fragment
        return view;
    }


}
