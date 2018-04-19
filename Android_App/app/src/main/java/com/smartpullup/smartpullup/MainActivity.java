package com.smartpullup.smartpullup;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivityJSON";

    @SuppressLint("HandlerLeak")
    private FirebaseAuth mAuth;
    public User currentUser;

    JSONBroadcastReceiver JSONBroadcastReceiver;

    private SectionsPagerAdapter mSectionsStatePagerAdapter;
    private ViewPager mViewPager;

    public final ExerciseFragment ExerciseFragment = new ExerciseFragment();
    public final LeaderboardFragment LeaderboardFragment = new LeaderboardFragment();
    public final ProfileFragment ProfileFragment = new ProfileFragment();
    public final WorkoutFragment WorkoutFragment = new WorkoutFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerJSONBroadcastReceiver();

        //navigation
        mSectionsStatePagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager =(ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.bottomNav);
        tabLayout.setupWithViewPager(mViewPager);

        BottomTabLayout(tabLayout);

    }

    private void BottomTabLayout(TabLayout tabLayout){

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_podium);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_workout);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_person);

        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        int tabIconColor = ContextCompat.getColor(MainActivity.this, R.color.ColorTheme);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        int tabIconColor = ContextCompat.getColor(MainActivity.this, R.color.Black);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);
                    }
                }
        );

    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(ExerciseFragment);
        adapter.addFragment(LeaderboardFragment);
        adapter.addFragment(WorkoutFragment);
        adapter.addFragment(ProfileFragment);
        viewPager.setAdapter(adapter);

    }

    private void registerJSONBroadcastReceiver() {
        JSONBroadcastReceiver = new JSONBroadcastReceiver();

        IntentFilter intentFilter = new IntentFilter(BTReceiverService.ACTION_MyIntentService);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(JSONBroadcastReceiver, intentFilter);

        Log.i(TAG, "registered JSONBroadcastReceiver");
    }

    public void ConnectToBar(View view) {
        Intent intentConnect = new Intent(MainActivity.this, ConnectBarActivity.class);
        startActivity(intentConnect);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        currentUser = new User(mAuth.getUid());
    }

    @Override
    public void onPause()
    {
        super.onPause();

        Intent MyIntentService = new Intent(this, BTReceiverService.class);
        stopService(MyIntentService );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(JSONBroadcastReceiver);
    }
}
