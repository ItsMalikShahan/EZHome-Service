package com.example.ezhomeservice.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ezhomeservice.LoginAsA;
import com.example.ezhomeservice.R;
import com.example.ezhomeservice.firebase.FirebaseManager;
import com.example.ezhomeservice.model.UserModel;
import com.example.ezhomeservice.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.subhrajyoti.passwordview.PasswordView;

public class SignUpUser extends AppCompatActivity {


    EditText name, email, address;
    RelativeLayout signUp;
    TextView signIn;
    FirebaseUser fireUser;
    PasswordView password, confirmPassword;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private String passwordTxt, emailTxt, nameTxt, addressTxt;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, LoginAsA.class));
    }

    //    @Override
//    protected void onStart() {
//        super.onStart();
//         fireUser = FirebaseAuth.getInstance().getCurrentUser();
//
//        if (fireUser != null) {
//            userTypeAndNavigate();
//            finish();
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUp = findViewById(R.id.rl_signUpBtn);
        signIn = findViewById(R.id.tv_signIn);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUser();

            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAG", "onClick: is it opening" );
                startActivity(new Intent(SignUpUser.this, SignInUser.class));
                finish();
            }
        });
    }

    private void validateUser() {
        name = findViewById(R.id.et_fullName);
        email = findViewById(R.id.et_email);
        address = findViewById(R.id.et_address);

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
        addressTxt = address.getText().toString();
        nameTxt = name.getText().toString();
        String confirmPwd = confirmPassword.getText().toString();
        passwordTxt = password.getText().toString();
        emailTxt = email.getText().toString();

        if (nameTxt.isEmpty()) {
            name.setError("Name Required");
        } else if (!(isValidEmail(emailTxt))) {
            email.setError("Email is not valid");
        } else if (passwordTxt.isEmpty() && passwordTxt.length() < 8) {
            password.setError("Password Needed");
        } else if (!confirmPwd.matches(passwordTxt)) {
            confirmPassword.setError("Password not matched");

//         }
//        if (nameTxt.isEmpty() && passwordTxt.isEmpty() && emailTxt.isEmpty() && confirmPwd.isEmpty()){
//            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else if (addressTxt.isEmpty()) {
            address.setError("Address Required");
        } else {
            FirebaseManager.UserSignUp(nameTxt,emailTxt,passwordTxt,addressTxt,
                    auth, reference,this,HomeUser.class);

        }
    }


    public boolean isValidEmail(CharSequence target) {
        return (target != null && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


}