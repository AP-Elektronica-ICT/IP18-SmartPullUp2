package com.smartpullup.smartpullup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.support.design.widget.BottomNavigationView;
import android.view.View;


public class MainActivity extends AppCompatActivity {


    public final ExerciseFragment ExerciseFragment = new ExerciseFragment();


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

    }

    @Override
    protected void onPause() {
        Intent MyIntentService = new Intent(this, BTReceiverService.class);
        stopService(MyIntentService );

        super.onPause();
    }

    public void ConnectToBar(View view) {
        Intent intentConnect = new Intent(MainActivity.this, ConnectBarActivity.class);
        startActivity(intentConnect);
    }


}
