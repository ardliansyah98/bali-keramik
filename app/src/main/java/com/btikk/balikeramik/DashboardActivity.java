package com.btikk.balikeramik;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.btikk.balikeramik.fragments.AccountFragment;
import com.btikk.balikeramik.fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DashboardActivity extends AppCompatActivity {
    private BottomNavigationView mainBottomNav;
    private Fragment homeFragment = new HomeFragment();
    private Fragment accountFragment = new AccountFragment();
    private FragmentManager fm = getSupportFragmentManager();
    Fragment active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mainBottomNav = (BottomNavigationView) findViewById(R.id.bottomnav_main);
        active = homeFragment;
        fm.beginTransaction().add(R.id.frame_utama, homeFragment, "1").commit();
        fm.beginTransaction().add(R.id.frame_utama, accountFragment, "2").hide(accountFragment).commit();
        mainBottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            switch (itemId){
                case R.id.nav_account:
                    fm.beginTransaction().hide(active).show(accountFragment).commit();
                    active = accountFragment;
                    break;
                case R.id.nav_home:
                    fm.beginTransaction().hide(active).show(homeFragment).commit();
                    active = homeFragment;
                    break;
            }
            return true;
        });
    }
}