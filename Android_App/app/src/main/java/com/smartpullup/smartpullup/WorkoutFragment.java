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

    String[] languages = new String[] {

            String.valueOf(R.string.card_title_workout_1),
            String.valueOf(R.string.card_title_workout_2),

    };

    public static int [] language_images={
            R.drawable.ic_home,
            R.drawable.ic_home,};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_workout, container, false);

        init();
        workout_listview.setAdapter(list_adapter);

        return view;
    }

    private void init() {

        //toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("List Cards");
        list_adapter = new WorkoutListAdapter(this,languages, language_images);
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



