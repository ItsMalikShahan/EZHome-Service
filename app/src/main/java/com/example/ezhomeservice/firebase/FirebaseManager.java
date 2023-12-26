package com.example.ezhomeservice.firebase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.ezhomeservice.Utils;
import com.example.ezhomeservice.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseManager {
    private static final FirebaseAuth auth = FirebaseAuth.getInstance();
    private static final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    public static DatabaseReference getUserReference() {
        return ref.child("EZHomeService");
    }

    public static FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }


    public static void userName(ValueEventListener listener) {
        FirebaseUser user = getCurrentUser();

        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userRef = getUserReference();
            userRef.child("Users").child(userId).addValueEventListener(listener);


        }
    }


    public static void UserSignUp(
            String name,
            String email,
            String password,
            String address,
            FirebaseAuth auth,
            DatabaseReference reference,
            Context context,
            Class<?> targetActivity
    ) {
        Utils.showLoadingDialog(context, false);
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String UID = auth.getUid();
                        UserModel model = new UserModel(name, email, password, address);
                        reference.child("EZHomeService")
                                .child("Users")
                                .child(UID)
                                .setValue(model)
                                .addOnCompleteListener(innerTask -> {
                                    if (innerTask.isSuccessful()) {
                                        context.startActivity(new Intent(context, targetActivity));
                                        ((Activity) context).finish();
                                        Utils.hideLoadingDialog();
                                    } else {
                                        Toast.makeText(context, "Task is failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(context, "Task is failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static void UserSignIn(
            String email,
            String password,
            FirebaseAuth auth,
            Context context,
            Class<?> targetActivity) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    Utils.showLoadingDialog(context, false);
                    if (task.isSuccessful()) {
                        context.startActivity(new Intent(context, targetActivity));
                        ((Activity) context).finish();
                        Utils.hideLoadingDialog();
                    } else {
                        Toast.makeText(context, "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
