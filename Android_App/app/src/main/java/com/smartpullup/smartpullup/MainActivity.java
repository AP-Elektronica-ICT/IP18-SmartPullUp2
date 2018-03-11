package com.smartpullup.smartpullup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.BottomNavigationView;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public final ExerciseFragment ExerciseFragment = new ExerciseFragment();

    private JSONBroadcastReceiver JSONBroadcastReceiver;
    private String JSONStructureInput = "";
    JSONObject JSONInputData = null;

    private String typeJsonData = null;
    private int machine_ID_JsonData = 0;
    private int upJsonData = 0;
    private int downJsonData = 0;
    private int weightJsonData = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

      bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                       Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_item1:
                                selectedFragment = ExerciseFragment.newInstance();
                                break;
                            case R.id.action_item2:
                                selectedFragment = ProfileFragment.newInstance();
                                break;
                        }

                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, ExerciseFragment.newInstance());
        transaction.commit();

        //Start BroadcastReceiver
        JSONBroadcastReceiver = new JSONBroadcastReceiver();

        //register BroadcastReceiver
        IntentFilter intentFilter = new IntentFilter(BTReceiverService.ACTION_MyIntentService);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(JSONBroadcastReceiver, intentFilter);



    }

    @Override
    protected void onPause() {
        Intent MyIntentService = new Intent(this, BTReceiverService.class);
        stopService(MyIntentService );

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(JSONBroadcastReceiver);
    }

    public class JSONBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            JSONStructureInput = intent.getStringExtra(BTReceiverService.EXTRA_KEY_OUT);
//            Log.i(TAG, JSONStructureInput);
            //Making JSON obj from input string
            try {
                JSONInputData = new JSONObject(JSONStructureInput);

                typeJsonData = JSONInputData.getString("type");
                Log.i(TAG, "type= " + typeJsonData);
                machine_ID_JsonData = JSONInputData.getInt("machine_ID");
                Log.i(TAG, "machine ID= " + machine_ID_JsonData);
                upJsonData = JSONInputData.getInt("up");
                Log.i(TAG, "up= " + upJsonData);
                downJsonData = JSONInputData.getInt("down");
                Log.i(TAG, "down= " + downJsonData);
                weightJsonData = JSONInputData.getInt("weight");
                Log.i(TAG, "weight= " + weightJsonData);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    public void ConnectToBar(View view) {
        Intent intentConnect = new Intent(MainActivity.this, ConnectBarActivity.class);
        startActivity(intentConnect);
    }

}
