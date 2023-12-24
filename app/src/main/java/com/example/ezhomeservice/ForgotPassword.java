package com.example.ezhomeservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    EditText emailTxt;
    RelativeLayout resetPassword;
    private FirebaseAuth auth;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Splash.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        emailTxt = findViewById(R.id.et_email);
        resetPassword = findViewById(R.id.rl_resetPwdBtn);
        auth = FirebaseAuth.getInstance();


        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailTxt.getText().toString();
                if (isValidEmail(email) && email.length() > 8) {
                    auth.sendPasswordResetEmail(emailTxt.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ForgotPassword.this, "Email Sent", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else
                    emailTxt.setError("email is not valid");
            }
        });
    }

    public boolean isValidEmail(CharSequence target) {
        return (target != null && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
