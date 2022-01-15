package com.hutech.waiter.screens.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hutech.lib.provider.CacheUserProvider;
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
    private Context context;
    public MainActivity() {
        this.cacheUserProvider = new CacheUserProvider(this);
        this.context = this;
    }
    private static CacheUserProvider cacheUserProvider;

    public static void start (Context context, CacheUserProvider userProvider){
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        cacheUserProvider = userProvider;
        context.startActivity(intent);
    }
    private BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_main_activity);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new ServingFragment());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cacheUserProvider = null;
    }
    private int mMenuItemSelected;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            mMenuItemSelected = item.getItemId();
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
                    fragment = new AccountFragment(context);
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

    @Override
    public void onBackPressed() {
        int selectedItemId = navigation.getSelectedItemId();
        if (R.id.serving != selectedItemId) {
            setHomeItem(MainActivity.this);
        } else {
            super.onBackPressed();
        }
    }

    public static void setHomeItem(Activity activity) {
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                activity.findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.serving);
    }
}