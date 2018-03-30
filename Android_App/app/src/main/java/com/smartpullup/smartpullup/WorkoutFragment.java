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
import android.widget.ListView;
import android.widget.Toolbar;


public class WorkoutFragment extends Fragment {

    View view;

    ListView workout_listview;
    WorkoutListAdapter list_adapter;

    String[] workout_titel;

    public static int [] workout_images ={
            R.drawable.ic_home,
            R.drawable.ic_home,
            R.drawable.ic_home,
            R.drawable.ic_home,
            R.drawable.ic_home,
            R.drawable.ic_home,
            R.drawable.ic_home,
            R.drawable.ic_home,
            R.drawable.ic_home,
            R.drawable.ic_home,
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_workout, container, false);

        workout_titel = new String[] {

                getString(R.string.card_title_workout_1),
                getString(R.string.card_title_workout_2),
                getString(R.string.card_title_workout_3),
                getString(R.string.card_title_workout_4),
                getString(R.string.card_title_workout_5),
                getString(R.string.card_title_workout_6),
                getString(R.string.card_title_workout_7),
                getString(R.string.card_title_workout_8),
                getString(R.string.card_title_workout_9),
                getString(R.string.card_title_workout_10),

        };

        init();
        workout_listview.setAdapter(list_adapter);

        return view;
    }

    private void init() {

        //toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("List Cards");
        list_adapter = new WorkoutListAdapter(this,workout_titel, workout_images);
        workout_listview = (ListView) view.findViewById(R.id.workout_listView);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//// Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//// Handle action bar item clicks here. The action bar will
//// automatically handle clicks on the Home/Up button, so long
//// as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
////noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}



