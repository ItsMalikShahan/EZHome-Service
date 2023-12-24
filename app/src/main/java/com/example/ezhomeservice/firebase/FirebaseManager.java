package com.example.ezhomeservice.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseManager {
    private static final FirebaseAuth auth = FirebaseAuth.getInstance();
    private static final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    public static DatabaseReference getUserReference(){
        return ref.child("EZHomeService");
    }
    public static FirebaseUser getCurrentUser(){
        return auth.getCurrentUser();
    }
    public static void userName(ValueEventListener listener){
        FirebaseUser user =    getCurrentUser();

        if (user != null){
            String userId = user.getUid();
            DatabaseReference userRef = getUserReference();
            userRef.child("Users").child(userId).addValueEventListener(listener);


        }
    }
}
