package com.example.ezhomeservice;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ezhomeservice.model.ServiceItemModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Laundry extends Fragment {

    RecyclerView laundry;
    List<ServiceItemModel> list;
    ServiceAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_laundry, container, false);
        laundry = view.findViewById(R.id.rv_laundry);
        list = new ArrayList<>();
        Log.d("ACTechnician", "onCreate: is called");
        DatabaseReference serviceProviderRef = FirebaseDatabase.getInstance().getReference()
                .child("EZHomeService")
                .child("ServiceProviders");

        serviceProviderRef.orderByChild("field").equalTo("Laundry").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapShot : snapshot.getChildren()) {
                    ServiceItemModel serviceProvider = snapShot.getValue(ServiceItemModel.class);
                    if (serviceProvider != null) {
                        Log.d("AcTechnician", "onDataChange: " + serviceProvider.getDescription());
                        Log.d("AcTechnician", "onDataChange: " + serviceProvider.getProfileImgUrl());
                        Log.d("AcTechnician", "onDataChange: " + serviceProvider.getName());
                        Log.d("AcTechnician", "onDataChange: " + serviceProvider.getExperience());
                        Log.d("AcTechnician", "onDataChange: " + serviceProvider.getWorkingDays());
                        Log.d("AcTechnician", "onDataChange: " + serviceProvider.getWorkingHours());


                        list.add(serviceProvider);
                    }

                }


                adapter = new ServiceAdapter(getContext(), list);
                laundry.setLayoutManager(new LinearLayoutManager(getContext()));
                laundry.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
}