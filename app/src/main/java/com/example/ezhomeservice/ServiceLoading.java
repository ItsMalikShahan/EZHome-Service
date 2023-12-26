package com.example.ezhomeservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.ezhomeservice.user.HomeUser;

public class ServiceLoading extends AppCompatActivity {

    TextView heading, noService;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, HomeUser.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_loading);
        heading = findViewById(R.id.tv_text);
        noService = findViewById(R.id.tv_notFound);
        String fragmentName = getIntent().getStringExtra("fragment_name");
        Log.d("ServiceLoading", "onCreate: " + fragmentName);
        if (fragmentName != null) {
            heading.setText(fragmentName);
            loadFragment(getFragment(fragmentName));
        } else {
            noService.setVisibility(View.VISIBLE);
        }
    }

    private Fragment getFragment(String fragmentName) {
        switch (fragmentName) {
            case "AcTechnician":
                return new AcTechnician();
            case "Electrician":
                return new Electrician();
            case "Plumber":
                return new Plumber();
            case "Mechanic":
                return new Mechanic();
            case "Washing":
                return new Washing();
            case "Laundry":
                return new Laundry();
        }
        return null;
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}