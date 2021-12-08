package com.example.calorietracker.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calorietracker.DashboardActivity;
import com.example.calorietracker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class BlankFragment extends Fragment {
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    TextView nameText, kcalText, emailText, kgText, ageText;

    public BlankFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_about_you, container, false);
        nameText = view.findViewById(R.id.aboutYou11);
        emailText = view.findViewById(R.id.aboutYou12);
        ageText = view.findViewById(R.id.aboutYou13);
        kgText = view.findViewById(R.id.aboutYou14);
        kcalText = view.findViewById(R.id.aboutYou15);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if (mAuth.getCurrentUser() != null) {
            DocumentReference documentReference = fStore.collection("users").document(mAuth.getCurrentUser().getUid());
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        try {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            Object firstName = documentSnapshot.get("firstName");
                            Object lastName = documentSnapshot.get("lastName");
                            nameText.setText(firstName.toString()+ " " + lastName.toString());
                            Object age = documentSnapshot.get("age");
                            ageText.setText(age.toString());
                            Object targetCalories = documentSnapshot.get("targetCalories");
                            kcalText.setText(targetCalories.toString());
                            Object currentWeight = documentSnapshot.get("currentWeight");
                            kgText.setText(currentWeight.toString());
                            String email = mAuth.getCurrentUser().getEmail();
                            emailText.setText(email);
                        } catch (Exception e) {
                            Log.e("Fragment", "get failed" + e.getMessage());
                        }

                    } else {
                        Log.e("Fragment", "get failed with", task.getException());
                    }
                }
            });
        }

        return view;
    }
}