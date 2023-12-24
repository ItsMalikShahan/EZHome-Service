package com.example.ezhomeservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.ezhomeservice.serviceprovider.CompleteProfileService;
import com.example.ezhomeservice.user.HomeUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Splash extends AppCompatActivity {

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    Intent myIntent =new Intent(Splash.this,LoginAsA.class);
//                    startActivity(myIntent);
//                    finish();
                    checkUserStatus();
                }
            }, 3000);
    }
    private void checkUserStatus(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null){
            isServiceProvider(firebaseUser);
        }else {
            startActivity(new Intent(Splash.this, LoginAsA.class));
            finish();
        }
    }
    private void isServiceProvider(FirebaseUser user){
        String userId = user.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("EZHomeService")
                .child("ServiceProviders").child(userId);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // User is a service provider
                    Log.e("Splash", "onDataChange: ServiceProvider" );
                    startActivity(new Intent(Splash.this, CompleteProfileService.class));
                } else {
                    // User is not a service provider, redirect to user home activity
                    startActivity(new Intent(Splash.this, HomeUser.class));
                }
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });

    }

}