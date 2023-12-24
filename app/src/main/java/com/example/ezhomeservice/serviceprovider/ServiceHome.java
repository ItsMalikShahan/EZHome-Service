package com.example.ezhomeservice.serviceprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ezhomeservice.R;
import com.example.ezhomeservice.Splash;
import com.google.firebase.auth.FirebaseAuth;

public class ServiceHome extends AppCompatActivity {

    Button logout;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_home);
        logout = findViewById(R.id.button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ServiceHome.this, Splash.class));
            }
        });
    }
}