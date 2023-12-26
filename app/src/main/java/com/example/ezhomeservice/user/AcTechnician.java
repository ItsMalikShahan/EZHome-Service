package com.example.ezhomeservice.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.example.ezhomeservice.ServiceAdapter;
import com.example.ezhomeservice.R;
import com.example.ezhomeservice.model.ServiceItemModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AcTechnician extends AppCompatActivity {

    RecyclerView acTechnicians;
    List<ServiceItemModel> list;
    ServiceAdapter acAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_technician);
        acTechnicians = findViewById(R.id.rv_acTechnician);
        list = new ArrayList<>();
        Log.d("ACTechnician", "onCreate: is called");
        DatabaseReference serviceProviderRef = FirebaseDatabase.getInstance().getReference()
                .child("EZHomeService")
                .child("ServiceProviders");

        serviceProviderRef.orderByChild("field").equalTo("Ac Technician").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapShot : snapshot.getChildren()) {
                    ServiceItemModel serviceProvider = snapShot.getValue(ServiceItemModel.class);
                    if (serviceProvider != null){
                        Log.d("AcTechnician", "onDataChange: "+serviceProvider.getDescription());
                        Log.d("AcTechnician", "onDataChange: "+serviceProvider.getProfileImgUrl());
                        Log.d("AcTechnician", "onDataChange: "+serviceProvider.getName());
                        Log.d("AcTechnician", "onDataChange: "+serviceProvider.getExperience());
                        Log.d("AcTechnician", "onDataChange: "+serviceProvider.getWorkingDays());
                        Log.d("AcTechnician", "onDataChange: "+serviceProvider.getWorkingHours());


                        list.add(serviceProvider);
                    }

                }


                acAdapter = new ServiceAdapter(getApplicationContext(), list);
                acTechnicians.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                acTechnicians.setAdapter(acAdapter);
                acAdapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}