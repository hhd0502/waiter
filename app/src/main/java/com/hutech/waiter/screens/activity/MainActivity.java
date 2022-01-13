package com.hutech.waiter.screens.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hutech.waiter.R;
import com.hutech.waiter.screens.fragment.AccountFragment;
import com.hutech.waiter.screens.fragment.MapFragment;
import com.hutech.waiter.screens.fragment.NotiFragment;
import com.hutech.waiter.screens.fragment.ServingFragment;
import com.hutech.waiter.screens.fragment.StatisticFragment;


public class MainActivity extends AppCompatActivity implements View.OnCreateContextMenuListener{

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_activity);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new ServingFragment());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.serving:
                    fragment = new ServingFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.map:
                    fragment = new MapFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.statistic:
                    fragment = new StatisticFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.noti:
                    fragment = new NotiFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.account:
                    fragment = new AccountFragment();
                    Intent intent = getIntent();
                    Bundle userInfo = intent.getExtras();
                    fragment.setArguments(userInfo);
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };
    public void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}