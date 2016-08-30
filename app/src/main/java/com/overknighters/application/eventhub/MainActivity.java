package com.overknighters.application.eventhub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.overknighters.application.eventhub.helper.SlidingTabLayout;
import com.overknighters.application.eventhub.helper.network.ConnectionDetector;

public class MainActivity extends ActionBarActivity implements ViewPager.OnPageChangeListener {

    private Toolbar toolbar;
    ConnectionDetector cd;
    private ViewPager mPager;
    private SlidingTabLayout mTabs;
    TextView tabText;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cd=new ConnectionDetector(getApplicationContext());
        if(!cd.isConnectingToInternet())
        {
            Toast.makeText(this,"Check your Network :)",Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPref = null;
        sharedPref=getSharedPreferences("user", Context.MODE_PRIVATE);
        email=sharedPref.getString("email", "default");

        if(email.equalsIgnoreCase("default"))
        {
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("");
        toolbar.setLogo(R.mipmap.event_hub);
        tabText = (TextView) findViewById(R.id.tabText);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mPager.setCurrentItem(1);
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setDistributeEvenly(true);
        mTabs.setCustomTabView(R.layout.custom_tab_view, R.id.tabText);
        mTabs.setSelectedIndicatorColors(getResources().getColor(R.color.colorAccent));
        mTabs.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTabs.setViewPager(mPager);

    }


    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }


    @Override
    public void onPageSelected(int position) {
        tabText.setTextColor(Color.WHITE);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    class MyPagerAdapter extends FragmentPagerAdapter {
        String[] tabs;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs = getResources().getStringArray(R.array.tabs);
        }


        @Override
        public Fragment getItem(int index) {
            Log.d("index", String.valueOf(index));
            switch (index) {
                case 0:
                    return new ProfileFragment();
                case 1:
                    return new TodayFragment();
                case 2:
                    return new UpComingFragment();
            }

            return null;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        @Override
        public int getCount() {
            return 3;
        }


    }

}


