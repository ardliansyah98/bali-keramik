package com.btikk.balikeramik;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.btikk.balikeramik.fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DashboardActivity extends AppCompatActivity {
    private BottomNavigationView mainBottomNav;
    private Fragment homeFragment = new HomeFragment();
    private FragmentManager fm = getSupportFragmentManager();
    Fragment active = this.homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mainBottomNav = (BottomNavigationView) findViewById(R.id.bottomnav_main);
        fm.beginTransaction().add(R.id.frame_utama, homeFragment, "1").commit();
        mainBottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                return false;
            }
        });
    }
}