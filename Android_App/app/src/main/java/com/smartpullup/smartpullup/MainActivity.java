package com.smartpullup.smartpullup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.BottomNavigationView;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public final ExerciseFragment ExerciseFragment = new ExerciseFragment();

    TextView pullUp_TextView = null;

    private JSONBroadcastReceiver JSONBroadcastReceiver;
    private String JSONStructureInput = "";
    JSONObject JSONInputData = null;

    private String typeJsonData = null;
    private int machine_ID_JsonData = 0;
    private int upJsonData = 0;
    private int downJsonData = 0;
    private int weightJsonData = 0;


    private SectionsPagerAdapter mSectionsStatePagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsStatePagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager =(ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.bottomNav);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_podium);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_person);

        //Start BroadcastReceiver
        JSONBroadcastReceiver = new JSONBroadcastReceiver();

        //register BroadcastReceiver
        IntentFilter intentFilter = new IntentFilter(BTReceiverService.ACTION_MyIntentService);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(JSONBroadcastReceiver, intentFilter);
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ExerciseFragment());
        adapter.addFragment(new LeaderboardFragment());
        adapter.addFragment(new ProfileFragment());
        viewPager.setAdapter(adapter);

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

    private void ConnectToBar(View view) {
        Intent intentConnect = new Intent(MainActivity.this, ConnectBarActivity.class);
        startActivity(intentConnect);
    }

}
