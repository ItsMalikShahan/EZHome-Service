package com.example.ezhomeservice.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.ezhomeservice.Dashboard;
import com.example.ezhomeservice.HomeFragment;
import com.example.ezhomeservice.R;
import com.example.ezhomeservice.Setting;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeUser extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNav = findViewById(R.id.bn_nav);
        getSupportFragmentManager().beginTransaction().replace(R.id.rl_fragContainer, new HomeFragment()).commit();
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.i_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.i_dashboard:
                            selectedFragment = new Dashboard();
                            break;
                        case R.id.i_setting:
                            selectedFragment = new Setting();
                            break;
                        default:
                            selectedFragment = new HomeFragment();
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.rl_fragContainer, selectedFragment)
                            .commit();
                    return true;
                }
            };
}