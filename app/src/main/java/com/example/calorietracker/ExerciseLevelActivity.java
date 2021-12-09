package com.example.calorietracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ExerciseLevelActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String userId;
    private Button next;
    private MaterialCardView sedentaryCard, lightlyActiveCard, veryActiveCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_level);
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        sedentaryCard = findViewById(R.id.sedentaryCard);
        lightlyActiveCard = findViewById(R.id.lightlyActiveCard);
        veryActiveCard = findViewById(R.id.veryActiveCard);

        sedentaryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sedentaryCard.setChecked(true);
                lightlyActiveCard.setChecked(false);
                veryActiveCard.setChecked(false);
            }
        });
        lightlyActiveCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sedentaryCard.setChecked(false);
                lightlyActiveCard.setChecked(true);
                veryActiveCard.setChecked(false);
            }
        });
        veryActiveCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sedentaryCard.setChecked(false);
                lightlyActiveCard.setChecked(false);
                veryActiveCard.setChecked(true);
            }
        });
        next = findViewById(R.id.exerciseLevelNextButton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuth.getCurrentUser() != null) {
                    userId = mAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("users").document(userId);
                    Map<String, Object> user = new HashMap<>();
                    if (sedentaryCard.isChecked()) {
                        user.put("activityLevel", "sedentary");
                    }
                    if (lightlyActiveCard.isChecked()) {
                        user.put("activityLevel", "light");
                    }
                    if (veryActiveCard.isChecked()) {
                        user.put("activityLevel", "very");
                    }
                    if (!sedentaryCard.isChecked() && !lightlyActiveCard.isChecked() && !veryActiveCard.isChecked()) {
                        Toast.makeText(ExerciseLevelActivity.this, "Please check one option!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(ExerciseLevelActivity.this, "Activity level added for user" + userId, Toast.LENGTH_SHORT).show();
                            openDashboard();
                        }
                    });

                }
            }
        });
    }

    public void openDashboard() {
        startActivity(new Intent(ExerciseLevelActivity.this, DashboardActivity.class));
        finish();
    }

    public void goToPreviousActivityOnClick(View view) {
        finish();
    }
}