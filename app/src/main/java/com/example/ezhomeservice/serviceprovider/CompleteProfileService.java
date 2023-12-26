package com.example.ezhomeservice.serviceprovider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ezhomeservice.PortfolioAdapter;
import com.example.ezhomeservice.model.PortfolioModel;
import com.example.ezhomeservice.R;
import com.example.ezhomeservice.model.ServiceProviderModel;
import com.example.ezhomeservice.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class CompleteProfileService extends AppCompatActivity {


    private static final int PICK_IMAGE_REQUEST = 2;
    private static final int REQUEST_IMAGE_FOR_PORTFOLIO = 1;
    private Uri selectedImageUri;
    ImageView getImg;
    CircleImageView profileImg;
    EditText serviceBio, areaCover, workingDays, workingHours;
    TextView workPortfolio;
    Button completeProfile;
    RecyclerView portfolio;
    List<PortfolioModel> imageUrls;
    PortfolioAdapter imageAdapter;
    private String imgURL, serviceDes, areaCovr, wrkgDys, wrkgHrs;
    FirebaseStorage storage;

    @Override
    protected void onStart() {
        super.onStart();
        checkProfile();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                selectedImageUri = result.getUri();
                imgURL = selectedImageUri.toString();
                Glide.with(this)
                        .load(selectedImageUri)
                        .into(profileImg);
            } else if (requestCode == REQUEST_IMAGE_FOR_PORTFOLIO) {
                selectedImageUri = data.getData();
                handleImageAndStore(selectedImageUri);

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_profile);
        profileImg = findViewById(R.id.profile_image);
        serviceBio = findViewById(R.id.et_bio);
        areaCover = findViewById(R.id.et_areaCovered);
        workingDays = findViewById(R.id.et_hours);
        workingHours = findViewById(R.id.et_days);
        workPortfolio = findViewById(R.id.tv_workPortfolio);
        completeProfile = findViewById(R.id.btn_complete);
        portfolio = findViewById(R.id.rv_portfolio);
        getImg = findViewById(R.id.iv_getImg);
        imageUrls = new ArrayList<>();

        getImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onImageClick();
            }
        });


        completeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateProfile();
            }
        });
        workPortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                portfolioImagePicker();
            }
        });
    }

    private void onImageClick() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(this);
        openImagePicker();

    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void validateProfile() {

        serviceDes = serviceBio.getText().toString();
        areaCovr = areaCover.getText().toString();
        wrkgDys = workingDays.getText().toString();
        wrkgHrs = workingHours.getText().toString();

        if (imgURL.isEmpty()){
            Toast.makeText(this, "Please choose profile picture", Toast.LENGTH_SHORT).show();
        }else if (serviceDes.isEmpty()) {
            serviceBio.setError("Please describe your work");
        } else if (areaCovr.isEmpty()) {
            areaCover.setError("Please provide nearest places where you provide services");
        } else if (wrkgDys.isEmpty()) {
            workingDays.setError("Mon - Sun");
        } else if (wrkgHrs.isEmpty()) {
            workingHours.setError("8:00AM - 9:00PM");
        } else {
            storeAndProceed();
        }
    }

    private void storeAndProceed() {
        Utils.showLoadingDialog(this, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String userId = user.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("EZHomeService")
                .child("ServiceProviders").child(userId);

        Map<String, Object> updateFields = new HashMap<>();
        updateFields.put("profileImgUrl", imgURL);
        updateFields.put("workingHours", wrkgHrs);
        updateFields.put("workingDays", wrkgDys);
        updateFields.put("description", serviceDes);
        updateFields.put("areaCover", areaCovr);

        // Update only the specified fields under the user's UID
        reference.updateChildren(updateFields)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CompleteProfileService.this, "Task is done", Toast.LENGTH_SHORT).show();
                            // Proceed to the next activity or perform other actions
                            startActivity(new Intent(CompleteProfileService.this, ServiceHome.class));
                            finish();
                            Utils.hideLoadingDialog();
                        } else {
                            // Handle the failure to update specific fields
                            Toast.makeText(CompleteProfileService.this, "Failed to update fields", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void portfolioImagePicker() {
        Intent portfolioIntent = new Intent(Intent.ACTION_PICK);
        portfolioIntent.setType("image/*");
        startActivityForResult(portfolioIntent, REQUEST_IMAGE_FOR_PORTFOLIO);
    }

    private void handleImageAndStore(Uri selectedImageUri) {
        new UploadImagesTask().execute(selectedImageUri);
//        String filename = "portfolio/" + System.currentTimeMillis(); // Unique file name within the 'portfolio' folder
//        storage = FirebaseStorage.getInstance();
//        StorageReference ref = storage.getReference().child(filename);
//
//        ref.putFile(selectedImageUri)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                // Get the download URL of the uploaded file
//                                PortfolioModel model = new PortfolioModel(uri);
//                                imageUrls.add(model);
//
//                                int spanCount = 3;
//                                imageAdapter = new PortfolioAdapter(CompleteProfileService.this, imageUrls);
//                                portfolio.setLayoutManager(new GridLayoutManager(CompleteProfileService.this, spanCount));
//                                portfolio.setAdapter(imageAdapter);
//                                imageAdapter.notifyDataSetChanged();
//
//                            }
//                        });
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // Handle the failure to upload
//                    }
//                });


    }

    private class UploadImagesTask extends AsyncTask<Uri, Void, Uri> {

        @Override
        protected Uri doInBackground(Uri... uris) {
            Uri selectedImageUri = uris[0];
            String filename = "portfolio/" + System.currentTimeMillis(); // Unique file name within the 'portfolio' folder
            storage = FirebaseStorage.getInstance();
            StorageReference ref = storage.getReference().child(filename);
            try {
                // Upload image in the background
                UploadTask uploadTask = ref.putFile(selectedImageUri);

                // Wait for the upload to complete
                Tasks.await(uploadTask);

                // Get download URL in the background
                Task<Uri> downloadUrlTask = ref.getDownloadUrl();

                // Wait for the download URL retrieval to complete
                Tasks.await(downloadUrlTask);

                // Return the download URL from doInBackground
                return downloadUrlTask.getResult();
            } catch (Exception e) {
                // Handle exceptions if any
                // Return null or an error message if needed
                return null;
            }
        }

        @Override
        protected void onPostExecute(Uri uri) {
            // This method runs on the UI thread, use the result (download URL) here

            if (uri != null) {
                // Image upload and download succeeded
                PortfolioModel model = new PortfolioModel(uri);
                model.setLoading(false);
                imageUrls.add(model);

                int spanCount = 3;
                imageAdapter = new PortfolioAdapter(CompleteProfileService.this, imageUrls);
                portfolio.setLayoutManager(new GridLayoutManager(CompleteProfileService.this, spanCount));
                portfolio.setAdapter(imageAdapter);
                imageAdapter.notifyDataSetChanged();
            } else {
                // Image upload or download failed, show an error message
                Toast.makeText(CompleteProfileService.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void checkProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                    .child("EZHomeService")
                    .child("ServiceProviders")
                    .child(user.getUid());

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        ServiceProviderModel model = snapshot.getValue(ServiceProviderModel.class);
                        if (model != null && model.getProfileImgUrl() != null && model.getDescription() != null) {
                            startActivity(new Intent(CompleteProfileService.this, ServiceHome.class));
                            finish();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}




