package com.example.ezhomeservice.serviceprovider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ezhomeservice.LoginAsA;
import com.example.ezhomeservice.R;
import com.example.ezhomeservice.ServiceProviderModel;
import com.example.ezhomeservice.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.subhrajyoti.passwordview.PasswordView;

import java.util.ArrayList;

public class SignUpService extends AppCompatActivity {

    EditText name, email, contact, experience, address;
    RelativeLayout signUp;
    Spinner field;
    TextView signIn;
    ArrayList<String> fieldData;
    PasswordView password, confirmPassword;
    private FirebaseAuth auth;
    private String passwordTxt;
    private String emailTxt;
    private String nameTxt;
    private String contactTxt;
    private String experienceTxt;
    private String addressTxt;
    private String fieldTxt;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, LoginAsA.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider);
        signUp = findViewById(R.id.rl_signUpBtn);
        auth = FirebaseAuth.getInstance();
        signIn = findViewById(R.id.tv_signIn);
        fieldData = new ArrayList<>();
        field = findViewById(R.id.et_field);
        fieldData.add("Electrician");
        fieldData.add("Mechanic");
        fieldData.add("Ac Technician");
        fieldData.add("Plumber");
        fieldData.add("Washing");
        fieldData.add("Laundry");
        ArrayAdapter<String> fieldAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, fieldData);
        fieldAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        field.setAdapter(fieldAdapter);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUser();

            }
        });
        field.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fieldTxt = fieldData.get(i);
                Log.d("fieldCheck", "onItemSelected: "+fieldTxt);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Opening", "onClick: SignInService");
                startActivity(new Intent(SignUpService.this, SignInService.class));
            }
        });
    }

    private void validateUser() {
        name = findViewById(R.id.et_fullName);
        email = findViewById(R.id.et_email);
        address = findViewById(R.id.et_address);
        contact = findViewById(R.id.et_contact);
        experience = findViewById(R.id.et_experience);
        password = findViewById(R.id.pv_password);
        confirmPassword = findViewById(R.id.pv_confirmPwd);

        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int lineCount = address.getLineCount();
                if (lineCount > 5) {
                    address.setMaxLines(lineCount);
                }
            }
        });

        experienceTxt = experience.getText().toString();
        contactTxt = contact.getText().toString();
        addressTxt = address.getText().toString();
        nameTxt = name.getText().toString();
        String confirmPwd = confirmPassword.getText().toString();
        passwordTxt = password.getText().toString();
        emailTxt = email.getText().toString();

        if (nameTxt.isEmpty()) {
            name.setError("Name Required");
        } else if (!(isValidEmail(emailTxt))) {
            email.setError("Email is not valid");
        } else if (contactTxt.isEmpty() || contactTxt.length() != 11) {
            Toast.makeText(this, "Please provide 11 digit contact number", Toast.LENGTH_SHORT).show();
        } else if (experienceTxt.isEmpty()) {
            experience.setError("Please mention experience year");
        } else if (passwordTxt.isEmpty() || passwordTxt.length() < 8) {
            password.setError("Password Needed");
        } else if (!confirmPwd.matches(passwordTxt)) {
            confirmPassword.setError("Password not matched");

//         }
//        if (nameTxt.isEmpty() && passwordTxt.isEmpty() && emailTxt.isEmpty() && confirmPwd.isEmpty()){
//            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else if (addressTxt.isEmpty()) {
            address.setError("Address Required");
        } else if (fieldTxt.isEmpty()) {
            Toast.makeText(this, "Select a Field", Toast.LENGTH_SHORT).show();
        }else{
            registerUser();

        }
    }

    private void registerUser() {
        Utils.showLoadingDialog(this, false);
        auth.createUserWithEmailAndPassword(emailTxt, passwordTxt)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String UID = auth.getUid();
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("EZHomeService")
                                    .child("ServiceProviders");
                            ServiceProviderModel model = new ServiceProviderModel(nameTxt, emailTxt, contactTxt, fieldTxt,
                                    passwordTxt, addressTxt, experienceTxt);
                            databaseReference.child(UID).setValue(model)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(SignUpService.this, "Task is done", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(SignUpService.this, CompleteProfileService.class));
                                            finish();
                                            Utils.hideLoadingDialog();
                                        }
                                    });

                        } else if (task.isCanceled()) {

//                            Toast.makeText(SignUp.this, "Task is failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public boolean isValidEmail(CharSequence target) {
        return (target != null && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    private void selectedField(){

    }


}