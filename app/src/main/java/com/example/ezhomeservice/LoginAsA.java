package com.example.ezhomeservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.ezhomeservice.serviceprovider.SignUpService;
import com.example.ezhomeservice.user.SignUpUser;
import com.google.firebase.auth.FirebaseUser;

public class LoginAsA extends AppCompatActivity {

    TextView serviceProvider, user;
    FirebaseUser fireUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_as);
        serviceProvider = findViewById(R.id.tv_service);
        user = findViewById(R.id.tv_user);


        serviceProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginAsA.this, SignUpService.class));
                Log.e("TAG", "onClick: is opening properly");
                finish();
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAG", "onClick: user is being called");
                startActivity(new Intent(LoginAsA.this, SignUpUser.class));
                finish();
            }
        });
    }
}