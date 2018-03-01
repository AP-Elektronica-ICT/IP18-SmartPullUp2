package com.smartpullup.smartpullup;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    public final ExerciseFragment ExerciseFragment = new ExerciseFragment();

    private SectionsPagerAdapter mSectionsStatePagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //ft.replace(R.id.Content, ExerciseFragment, "exerciseFragment");
        //ft.commit();

        mViewPager =(ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.bottomNav);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_podium);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_person);
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ExerciseFragment(), "FragmentExcercise");
        adapter.addFragment(new LeaderboardFragment(), "FragmentLeaderboard");
        adapter.addFragment(new ProfileFragment(), "FragmentProfile");
    }
    public void setViewPager(int FragmentNumber){
        mViewPager.setCurrentItem(FragmentNumber);
    }
}
