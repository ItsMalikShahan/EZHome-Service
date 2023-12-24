package com.example.ezhomeservice.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ezhomeservice.ForgotPassword;
import com.example.ezhomeservice.R;
import com.example.ezhomeservice.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.subhrajyoti.passwordview.PasswordView;

public class SignInUser extends AppCompatActivity {

    PasswordView passwordTxt;
    EditText emailTxt;
    RelativeLayout signIn;
    TextView forgotPassword;
    private String password, email;
    private FirebaseAuth auth;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SignInUser.this, SignUpUser.class));
        finish();
    }

    //
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (auth.getCurrentUser() != null) {
//            startActivity(new Intent(SignInUser.this, HomeUser.class));
//            finish();
//        }
//
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        signIn = findViewById(R.id.rl_signInbtn);
        forgotPassword = findViewById(R.id.tv_forgotPwd);
        auth = FirebaseAuth.getInstance();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUser();
//                startActivity(new Intent(SignInActivity.this, HomeActivity.class));
//                finish();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInUser.this, ForgotPassword.class));
            }
        });

    }

    private void validateUser() {
        passwordTxt = findViewById(R.id.pv_password);
        emailTxt = findViewById(R.id.et_email);
        password = passwordTxt.getText().toString();
        email = emailTxt.getText().toString();

        if (!(isValidEmail(email))){
            emailTxt.setError("Not a valid email");
        }else if (password.length()<8){
            passwordTxt.setError("Password is too Short");
        }else {
            signIn();
        }
//        if (password.isEmpty() && email.isEmpty()) {
//            Toast.makeText(this, "Please provide details...", Toast.LENGTH_SHORT).show();
//        }

    }

    private void signIn() {
        Utils.showLoadingDialog(this, false);
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignInUser.this, "Welcome on Board", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignInUser.this, HomeUser.class));
                            finish();
                            Utils.hideLoadingDialog();
                        } else {
                            Toast.makeText(SignInUser.this, "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public boolean isValidEmail(CharSequence target) {
        return (target != null && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    }
