package com.example.couple.Base.Handler;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

import lombok.Getter;

@Getter
public class FirebaseBase {
    DatabaseReference mRef;

    public FirebaseBase(String objName) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null)
            mRef = db.getReference(firebaseUser.getUid()).child(objName);
    }

    public <T> void addObject(T obj, String objectId) {
        mRef.child(objectId).setValue(obj, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, @NonNull DatabaseReference ref) {

            }
        });
    }

    public void updateObject(Map<String, Object> maps, String objectId) {
        mRef.child(objectId).updateChildren(maps);
    }

    public void removeObject(String objectId) {
        mRef.child(objectId).removeValue();
    }

    public String getKey() {
        return mRef.push().getKey();
    }
}
