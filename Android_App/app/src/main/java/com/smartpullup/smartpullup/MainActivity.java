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


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public final ExerciseFragment ExerciseFragment = new ExerciseFragment();

    private MyBroadcastReceiver myBroadcastReceiver;
//    private MyBroadcastReceiver_Update myBroadcastReceiver_Update;

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

        myBroadcastReceiver = new MyBroadcastReceiver();
        //myBroadcastReceiver_Update = new MyBroadcastReceiver_Update();
        //register BroadcastReceiver
        IntentFilter intentFilter = new IntentFilter(BTReceiverService.ACTION_MyIntentService);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(myBroadcastReceiver, intentFilter);

//        IntentFilter intentFilter_update = new IntentFilter(BTReceiverService.ACTION_MyUpdate);
//        intentFilter_update.addCategory(Intent.CATEGORY_DEFAULT);
//        registerReceiver(myBroadcastReceiver_Update, intentFilter_update);
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
        //un-register BroadcastReceiver
        unregisterReceiver(myBroadcastReceiver);
        //unregisterReceiver(myBroadcastReceiver_Update);
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String result = intent.getStringExtra(BTReceiverService.EXTRA_KEY_OUT);
            Log.i(TAG, result);
            //textResult.setText(result);
        }
    }

//    public class MyBroadcastReceiver_Update extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            int update = intent.getIntExtra(BTReceiverService.EXTRA_KEY_UPDATE, 0);
//            //progressBar.setProgress(update);
//        }
//    }

    public void ConnectToBar(View view) {
        Intent intentConnect = new Intent(MainActivity.this, ConnectBarActivity.class);
        startActivity(intentConnect);
    }


}
