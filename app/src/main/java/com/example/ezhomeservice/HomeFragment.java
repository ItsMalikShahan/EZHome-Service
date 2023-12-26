package com.example.ezhomeservice;

import static com.example.ezhomeservice.Utils.Greeting;
import static com.example.ezhomeservice.firebase.FirebaseManager.userName;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ezhomeservice.model.UserModel;
import com.example.ezhomeservice.user.AcTechnician;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;


public class HomeFragment extends Fragment {
    View view;
    CarouselView loadingImages;
    RelativeLayout acTechnician, electrician, plumber, mechanic,
            washing, laundry;
    TextView userNameView, greeting;
    int[] images = {R.drawable.a11, R.drawable.a22, R.drawable.a33, R.drawable.a44,
            R.drawable.a55, R.drawable.a66, R.drawable.a77};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        loadingImages = view.findViewById(R.id.cv_sliderImage);
        loadingImages.setPageCount(images.length);
        loadingImages.setImageListener(listener);
        userNameView = view.findViewById(R.id.tv_name);
        greeting = view.findViewById(R.id.tv_hGreeting);
        acTechnician = view.findViewById(R.id.rl_acRepair);
        electrician = view.findViewById(R.id.rl_electrician);
        plumber = view.findViewById(R.id.rl_plumb);
        mechanic = view.findViewById(R.id.rl_mechanic);
        washing = view.findViewById(R.id.rl_wash);
        laundry = view.findViewById(R.id.rl_laundry);
        greeting.setText(Greeting());
        userName(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    // Attempt to retrieve the "name" field
                    UserModel user = snapshot.getValue(UserModel.class);
                    if (user != null) {
                        String userName = user.getName();
                        userNameView.setText(userName);

                    }
                } else {
                    Log.d("userName", "onDataChange: User data does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        acTechnician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openServiceLoadingActivity("AcTechnician");
            }
        });
        electrician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openServiceLoadingActivity("Electrician");
            }
        });
        plumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openServiceLoadingActivity("Plumber");
            }
        });
        mechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openServiceLoadingActivity("Mechanic");
            }
        });
        washing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openServiceLoadingActivity("Washing");
            }
        });
        laundry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openServiceLoadingActivity("Laundry");
            }
        });
        return view;
    }

    ImageListener listener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(images[position]);
        }
    };

    private void openServiceLoadingActivity(String fragmentName) {
        Intent intent = new Intent(getActivity(), ServiceLoading.class);
        intent.putExtra("fragment_name", fragmentName);
        startActivity(intent);
    }
}