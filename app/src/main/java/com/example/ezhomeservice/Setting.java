package com.example.ezhomeservice;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ezhomeservice.serviceprovider.SignUpService;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;

public class Setting extends Fragment {

    TextView serviceProvider, contact, share, about, logout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        contact = view.findViewById(R.id.tv_contactUs);
        share = view.findViewById(R.id.tv_share);
        about = view.findViewById(R.id.tv_about);
        logout = view.findViewById(R.id.tv_logout);

        serviceProvider = view.findViewById(R.id.tv_serviceLogin);


        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactDialog();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareApplication();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), Splash.class));
                getActivity().finish();

            }
        });
//        serviceProvider.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent startActivity = new Intent(getActivity(), ServiceProvider.class);
//                getParentFragment().getActivity().finish();
//                startActivity(startActivity);
//            }
//        });


        return view;

    }

    public void contactDialog() {
        Dialog dialog = new Dialog();
        dialog.show(getActivity().getSupportFragmentManager(), "Dialog");
    }

    public void shareApplication() {

        ApplicationInfo app = getActivity().getApplicationContext().getApplicationInfo();
        String filePath = app.sourceDir;

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("application/vnd.android.package-archive");
        sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
        startActivity(Intent.createChooser(sendIntent, "Share app via"));
    }

    private boolean checkServiceAccount() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    private void performLogin() {
        Toast.makeText(getActivity(), "User Exist", Toast.LENGTH_SHORT).show();
    }

    private void openServiceProviderSignUp() {
        Intent startActivity = new Intent(getActivity(), SignUpService.class);
        getParentFragment().getActivity().finish();
        startActivity(startActivity);
    }
}